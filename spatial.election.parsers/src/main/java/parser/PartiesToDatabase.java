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

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

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
	private Session session;
	private LinkedList<String> partyNames = new LinkedList<String>();
	private TreeMap<Integer, Party> parties = new TreeMap<Integer, Party>();
	private Random rand;

	public PartiesToDatabase(File file) {
		this(file, DatabaseConnection.openSession());
	}
	
	public PartiesToDatabase(File file, Session session) {
		this.file = file;
		this.session = session;
		this.rand = new Random();
	}
	
	@Override
	protected void finalize() throws Throwable {
		session.close();
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
		Transaction tr = session.beginTransaction();		int pos = 0;
		for(String partyName : partyNames) {
			List<?> alrdyParty = session.createCriteria(Party.class).add(Restrictions.eq("partyName", partyName)).list();
			Party party = alrdyParty!=null && alrdyParty.size()>0 ? (Party) alrdyParty.get(0) : null;
			if(party==null) {
				party = new Party();
				party.setPartyName(partyName);
				party.setColor(generateColor());
				session.saveOrUpdate(party);
			}
			parties.put(pos++, party);
		}
		tr.commit();
		session.flush();
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
