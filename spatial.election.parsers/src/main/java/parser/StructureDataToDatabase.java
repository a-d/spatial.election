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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import edu.spatial.election.database.DatabaseConnection;
import edu.spatial.election.domain.Constituency;
import edu.spatial.election.domain.County;
import edu.spatial.election.domain.CountyData;
import edu.spatial.election.domain.DataKey;
import edu.spatial.election.domain.Election;
import edu.spatial.election.domain.ElectionResult;
import edu.spatial.election.domain.Party;

public class StructureDataToDatabase {

	private static final String COLUMN_DELIMITER = ";";
	private static final String DEFAULT_FILE = "../spatial.election.data/12411-0017.csv";
	private static final int SKIP_ROWS = 6;
	private static final String[] IGNORES = new String[] { "Region" };

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
			StructureDataToDatabase iter = new StructureDataToDatabase(file);
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
		public HashMap<String, Long> ages = new HashMap<String, Long>();
		public County county;
		public int year;

		public Result(int year, County county) {
			this.county = county;
			this.year = year;
		}
		
		public void Add(String key, Long num) {
			ages.put(key, num);
		}
	}

	
	

	private File file;
	private Session session;
	private LinkedList<Result> results = new LinkedList<Result>();
	private PartiesToDatabase partyLoader;

	private StructureDataToDatabase(File file) {
		this(file, DatabaseConnection.openSession());
	}
	
	
	private StructureDataToDatabase(File file, Session session) {
		this.file = file;
		this.session = session;
		
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		session.close();
	}
	
	
	private void Load() throws HibernateException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("ISO-8859-1")));
		String line = null;
		for(int i=0; i<SKIP_ROWS; i++) {
			br.readLine();
		}
		
		int COUNT_INDEX = 3;
		HashMap<String, int[]> USE_COLS = new HashMap<String, int[]>();
		USE_COLS.put("unter 3 Jahre", new int[] { 4, 21 });
		USE_COLS.put("3 bis unter 6 Jahre", new int[] { 5, 22 });
		USE_COLS.put("6 bis unter 10 Jahre", new int[] { 6, 23 });
		USE_COLS.put("10 bis unter 15 Jahre", new int[] { 7, 24 });
		USE_COLS.put("15 bis unter 18 Jahre", new int[] { 8, 25 });
		USE_COLS.put("18 bis unter 20 Jahre", new int[] { 9, 26 });
		USE_COLS.put("20 bis unter 25 Jahre", new int[] { 10, 27 });
		USE_COLS.put("25 bis unter 30 Jahre", new int[] { 11, 28 });
		USE_COLS.put("30 bis unter 35 Jahre", new int[] { 12, 29 });
		USE_COLS.put("35 bis unter 40 Jahre", new int[] { 13, 30 });
		USE_COLS.put("40 bis unter 45 Jahre", new int[] { 14, 31 });
		USE_COLS.put("45 bis unter 50 Jahre", new int[] { 15, 32 });
		USE_COLS.put("50 bis unter 55 Jahre", new int[] { 16, 33 });
		USE_COLS.put("55 bis unter 60 Jahre", new int[] { 17, 34 });
		USE_COLS.put("60 bis unter 65 Jahre", new int[] { 18, 35 });
		USE_COLS.put("65 bis unter 75 Jahre", new int[] { 19, 36 });
		USE_COLS.put("75 Jahre und mehr", new int[] { 20, 37 });
		
		while((line = br.readLine())!=null) {
			String[] cols = splitText(line, COLUMN_DELIMITER);
			
			County county = tryGetCounty(cols, COUNT_INDEX-1);
			if(county == null) {
				System.err.println("Could not find a county by \""+ (cols.length<=COUNT_INDEX-1 ? line : cols[COUNT_INDEX-1])+"\".");
				continue;
			}
			
			Result result = new Result(2013, county);
			
			for(Entry<String, int[]> use_col : USE_COLS.entrySet()) {
				long num = 0L;
				for(int i : use_col.getValue()) {
					try {
						num += tryGetLong(cols, i-1);
					}
					catch(Exception e) {
						System.out.println("Schlecht: "+e.getMessage());
					}
				}
				result.Add(use_col.getKey(), num);
			}
			
			results.add(result);
		}
		br.close();
	}
	
	private long tryGetLong(String[] cols, int i) {
		return !"-".equals(cols[i]) ? Long.parseLong(cols[i]) : 0L;
	}


	private County tryGetCounty(String[] cols, int i) {
		if(cols.length<=i) {
			return null;
		}
		String label = cols[i].trim();
		for(String ign : IGNORES) {
			label.replace(ign, "");
		}
		
		String[] parts = label.split("[^\\wäöü]+");
		
		// check for "Stadt"
		boolean isStadt = label.toLowerCase().contains("kreisfreie");
		boolean isLandkreis = label.toLowerCase().contains("landkreis");
		
		if(isStadt && isLandkreis) {
			System.out.println("no!");
		}
		
		LinkedList<Criterion> criterions = new LinkedList<Criterion>();
		if(isStadt) {
			criterions.add(
					Restrictions.disjunction()
						.add(Restrictions.ilike("countyTypeGerman", "kreisfreie", MatchMode.ANYWHERE))
						.add(Restrictions.eq("countyTypeGerman", ""))
						.add(Restrictions.isNull("countyTypeGerman")));
		}
		else if(isLandkreis) {
			criterions.add(
					Restrictions.disjunction()
						.add(Restrictions.ilike("countyTypeGerman", "Landkreis", MatchMode.ANYWHERE))
						.add(Restrictions.eq("countyTypeGerman", ""))
						.add(Restrictions.isNull("countyTypeGerman")));
		}
		
		for(int x=0; x<parts.length; x++) {
			Criteria crit = session.createCriteria(County.class);
			crit.setMaxResults(50);
			crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			Conjunction conf = Restrictions.conjunction();
			crit.add(conf);
			
			for(Criterion crite : criterions) {
				conf.add(crite);
			}
			
			Disjunction disjunction = Restrictions.disjunction();
			conf.add(disjunction);


			for(MatchMode mm : new MatchMode[] { MatchMode.EXACT, MatchMode.ANYWHERE }) {
				if(MatchMode.EXACT == mm) {
					disjunction.add(Restrictions.eq("countyName", parts[x]));
					disjunction.add(Restrictions.eq("countyNameVars", parts[x]));
				}
				else {
					disjunction.add(Restrictions.ilike("countyName", parts[x], mm));
					disjunction.add(Restrictions.ilike("countyNameVars", parts[x], mm));
				}
				
				List<?> founds = crit.list();
				if(founds.size()>1) {
					criterions.add(disjunction);
				}
				else if(founds.size()==1) {
					return (County) founds.get(0);
				}
			}
		}
		return null;
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
		Transaction tr = session.beginTransaction();
		HashMap<Integer, Election> elections = new HashMap<Integer, Election>();
		for(Result result : results) {
			if(!elections.containsKey(result.year)) {
				Calendar cal = Calendar.getInstance();
				cal.set(result.year, 1, 1);
				Date date = cal.getTime();
				
				Election election = null;
				List<?> o = session.createCriteria(Election.class).add(Restrictions.eq("year", result.year)).list();
				if(o !=null && o.size()>0) {
					election = (Election) o.get(0);
				}
				else {
					election = new Election();
					election.setYear(result.year);
					election.setDate(date);	
					session.save(election);
				}
				elections.put(result.year, election);
			}
		}
		HashMap<String, DataKey> dataKeys = new HashMap<String, DataKey>();
		for(String key : results.get(0).ages.keySet()) {
			if(!dataKeys.containsKey(key)) {
				DataKey dataKey = null;
				List<?> o = session.createCriteria(DataKey.class).add(Restrictions.eq("name", key)).list();
				if(o !=null && o.size()>0) {
					dataKey = (DataKey) o.get(0);
				}
				else {
					dataKey = new DataKey();
					dataKey.setName(key);
					session.save(dataKey);
				}
				dataKeys.put(key, dataKey);
			}
		}
		tr.commit();
		
		tr = session.beginTransaction();
		System.out.println("Skipping: ");
		int i=0;
		for(Result result : results) {
			try {
				for(Entry<String, Long> age : result.ages.entrySet()) {
					DataKey key = dataKeys.get(age.getKey());
					if(session.createCriteria(CountyData.class)
							.add(Restrictions.eq("county", result.county))
							.add(Restrictions.eq("key", key)).list().size()<1
					) {
						CountyData countyData = new CountyData();
						countyData.setCounty(result.county);
						countyData.setKey(key);
						countyData.setValue((double) age.getValue());
						session.saveOrUpdate(countyData);
					}
					else {
						System.out.print(".");
						if(++i % 100 == 0) System.out.println();
					}
				}
			}
			catch(Exception e) {
				System.err.println("Error with result: "+e.getMessage());
				e.printStackTrace();
			}
		}
		tr.commit();
		session.flush();	
	}

}
