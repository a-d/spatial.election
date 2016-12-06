package parser;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import edu.spatial.election.database.DatabaseConnection;
import edu.spatial.election.domain.Party;

public class PartiesToDatabase {

	private static final String COLUMN_DELIMITER = ";";
	private static final String DEFAULT_FILE = "../spatial.election.data/results.csv";
	private static final String[] IGNORES = new String[] { "ID" }; // sorted

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File file = null;
		if(args==null || args.length!=1) {
			System.err.println("Usage: PartiesToDatabase [FILE]");
			
			if(DEFAULT_FILE!=null)
			{
				System.out.println("Taking default file: \""+DEFAULT_FILE+"\"");
				file = new File(DEFAULT_FILE);
			}
		}
		
		if(file==null || !file.canRead())
		{
			System.err.println("Could not read from file: "+file.getAbsolutePath());
		}
		
		try {
			PartiesToDatabase iter = new PartiesToDatabase(file);
			iter.Load();
			iter.Save();
		}
		catch(HibernateException e) {
			System.err.println("A Hibernate problem occured: "+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("A file access problem occured: "+e.getMessage());
			e.printStackTrace();
		}
	}





	private File file;
	private EntityManager em;
	private LinkedList<String> partyNames = new LinkedList<String>();
	private TreeMap<Integer, Party> parties = new TreeMap<Integer, Party>();
	private Random rand;

	public PartiesToDatabase(File file) {
		this(file, DatabaseConnection.createManager());
	}
	
	public PartiesToDatabase(File file, EntityManager entityManager) {
		this.file = file;
		this.em = entityManager;
		this.rand = new Random();
	}
	
	@Override
	protected void finalize() throws Throwable {
		em.close();
	}
	
	
	private void Load() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("ISO-8859-1")));
		String firstLine = br.readLine();
		String[] cols = firstLine.split(COLUMN_DELIMITER);
		for(String col : cols) {
			col = col.trim();
			if(!"".equals(col) && Arrays.binarySearch(IGNORES, col)<0) {
				partyNames.add(col);
			}
		}
		br.close();
	}

	private void Save() throws HibernateException {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Party> q = cb.createQuery(Party.class);
		Root<Party> r = q.from(Party.class);
		em.getTransaction().begin();
		int pos = 0;
		for(String partyName : partyNames) {
			CriteriaQuery<Party> query = q.select(r).where(cb.equal(r.get("partyName"), partyName));
			List<Party> alrdyParty = em.createQuery(query).getResultList();
			Party party = alrdyParty!=null && alrdyParty.size()>0 ? (Party) alrdyParty.get(0) : null;
			if(party==null) {
				party = new Party();
				party.setPartyName(partyName);
				party.setColor(generateColor());
				
				em.persist(party);
			}
			parties.put(pos++, party);
		}
		em.getTransaction().commit();
		em.flush();
	}


	private String generateColor() {
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		Color randomColor = new Color(r, g, b);
		String rgb = Integer.toHexString(randomColor.getRGB());
		return "#"+rgb.substring(2, rgb.length());
	}

	public TreeMap<Integer, Party> getParties() throws IOException, HibernateException {
		Load();
		Save();
		return parties;
	}
}
