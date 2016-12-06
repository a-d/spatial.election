package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import org.hibernate.HibernateException;
import edu.spatial.election.database.DatabaseConnection;
import edu.spatial.election.domain.Constituency;
import edu.spatial.election.domain.Election;
import edu.spatial.election.domain.ElectionResult;
import edu.spatial.election.domain.Party;

public class ResultsToDatabase {

	private static final String COLUMN_DELIMITER = ";";
	private static final String DEFAULT_FILE = "../spatial.election.data/results.csv";

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
			ResultsToDatabase iter = new ResultsToDatabase(file);
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



	
	private class Result {
		public Party party;
		public long first;
		public long second;
		public int year;
		public int constId;

		public Result(Party party, int year, int constId, long first, long second) {
			this.party = party;
			this.year = year;
			this.constId = constId;
			this.first = first;
			this.second = second;
		}
	}

	
	

	private File file;
	private EntityManager em;
	private LinkedList<Result> results = new LinkedList<Result>();
	private PartiesToDatabase partyLoader;

	private ResultsToDatabase(File file) {
		this(file, DatabaseConnection.createManager());
	}
	
	
	private ResultsToDatabase(File file, EntityManager entityManager) {
		this.file = file;
		this.em = entityManager;
		
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		em.close();
	}
	
	
	private void Load() throws HibernateException, IOException {
		partyLoader = new PartiesToDatabase(file, em);
		TreeMap<Integer, Party> parties = partyLoader.getParties();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("ISO-8859-1")));
		String line = br.readLine();
		while((line = br.readLine())!=null) {
			String[] cols = splitText(line, COLUMN_DELIMITER); 
			for(Entry<Integer, Party> partyCol : parties.entrySet()) {
				
				int constId = 0;
				int first1 = 1+ partyCol.getKey()*4;
				int second1 = 2+ partyCol.getKey()*4;
				int first2 = 3+ partyCol.getKey()*4;
				int second2 = 4+ partyCol.getKey()*4;

				constId = Integer.parseInt(cols[constId].trim());
				Long f1 = "".equals((cols[first1]=cols[first1].trim())) ? 0L : Long.parseLong(cols[first1]);
				Long s1 = "".equals((cols[second1]=cols[second1].trim())) ? 0L : Long.parseLong(cols[second1]);
				Long f2 = "".equals((cols[first2]=cols[first2].trim())) ? 0L : Long.parseLong(cols[first2]);
				Long s2 = "".equals((cols[second2]=cols[second2].trim())) ? 0L : Long.parseLong(cols[second2]);

				results.add(new Result(partyCol.getValue(), 2009, constId, f1, s1));
				results.add(new Result(partyCol.getValue(), 2013, constId, f2, s2));
			}
		}
		br.close();
	}

	private String[] splitText(String line, String columnDelimiter) {
		LinkedList<String> parts = new LinkedList<String>();
		int pos = -1;
		while((pos = line.indexOf(columnDelimiter))>=0)
		{
			String s = line.substring(0, pos);
			parts.add(s);
			line = line.substring(pos+columnDelimiter.length());
		}
		parts.add(line);
		return parts.toArray(new String[0]);
	}


	private void Save() {
		em.getTransaction().begin();
		HashMap<Integer, Election> elections = new HashMap<Integer, Election>();
		
		for(Result result : results) {
			if(!elections.containsKey(result.year)) {
				Calendar cal = Calendar.getInstance();
				cal.set(result.year, 1, 1);
				Date date = cal.getTime();
				
				Election election = new Election();
				election.setYear(result.year);
				election.setDate(date);
				
				em.persist(election);
				elections.put(result.year, election);
			}
		}
		
		em.getTransaction().commit();
		em.getTransaction().begin();

		HashMap<Integer, Constituency> constituencies = new HashMap<Integer, Constituency>();
		
		for(Result result : results) {
			try {
				Constituency consti = constituencies.get(result.constId);
				if(consti==null) {
					constituencies.put(result.constId, consti = em.find(Constituency.class, result.constId));
				}
				ElectionResult electionResult = new ElectionResult();
				electionResult.setPrimaryVotes(result.first);
				electionResult.setSecondaryVotes(result.second);
				electionResult.setParty(result.party);
				electionResult.setElection(elections.get(result.year));
				electionResult.setConstituency(consti);
				em.persist(electionResult);
			}
			catch(Exception e) {
				System.err.println("Error with result: "+e.getMessage());
				e.printStackTrace();
			}
		}
		em.getTransaction().commit();
		em.flush();	
	}

}
