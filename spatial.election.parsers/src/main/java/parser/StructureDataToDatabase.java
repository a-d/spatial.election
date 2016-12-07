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
import java.util.List;
import java.util.Map.Entry;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import edu.spatial.election.database.DatabaseConnection;
import edu.spatial.election.domain.County;
import edu.spatial.election.domain.CountyData;
import edu.spatial.election.domain.DataKey;
import edu.spatial.election.domain.Election;

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
	private EntityManager em;
	private LinkedList<Result> results = new LinkedList<Result>();
	private StructureDataToDatabase(File file) {
		this(file, DatabaseConnection.createManager());
	}
	
	
	private StructureDataToDatabase(File file, EntityManager entityManager) {
		this.file = file;
		this.em = entityManager;
		
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		em.close();
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

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<County> q = cb.createQuery(County.class);
		Root<County> r = q.from(County.class);
		
		
		LinkedList<Predicate> criteria = new LinkedList<Predicate>();
		if(isStadt || isLandkreis) {
			Path<String> ctg = r.<String>get("countyTypeGerman");
			criteria.add(
					cb.or(cb.like(ctg, isStadt ? "%kreisfreie%" : "%Landkreis%"),
					cb.isNull(ctg),
					cb.equal(ctg, "")));
		}
		
		for(int x=0; x<parts.length; x++) {
			Predicate conjs = cb.and( (Predicate[]) criteria.toArray());
			Predicate disjunction = cb.or(
					cb.like(r.<String>get("countyName"), "%"+parts[x]+"%"),
					cb.like(r.<String>get("countyNameVars"), "%"+parts[x]+"%"));

			List<County> founds = em.createQuery(q.where(cb.and(conjs, disjunction)).distinct(true))
					.setMaxResults(50)
					.getResultList();

			if(founds.size()>1) {
				criteria.add(disjunction);
			}
			else if(founds.size()==1) {
				return (County) founds.get(0);
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
		CriteriaBuilder cb = em.getCriteriaBuilder();
		HashMap<String, DataKey> dataKeys = new HashMap<String, DataKey>();
		
		em.getTransaction().begin();
		{
			{
				CriteriaQuery<Election> q = cb.createQuery(Election.class);
				HashMap<Integer, Election> elections = new HashMap<Integer, Election>();
				for(Result result : results) {
					if(!elections.containsKey(result.year)) {
						Calendar cal = Calendar.getInstance();
						cal.set(result.year, 1, 1);
						Date date = cal.getTime();
						
						Election election = null;
						try {
							election = em.createQuery(q.where(cb.equal(q.from(Election.class).get("year"), result.year))).getSingleResult();
						}
						catch(NoResultException e) {
							election = new Election();
							election.setYear(result.year);
							election.setDate(date);	
							em.persist(election);
						}
						elections.put(result.year, election);
					}
				}
			}
			
			{
				CriteriaQuery<DataKey> q = cb.createQuery(DataKey.class);
				for(String key : results.get(0).ages.keySet()) {
					if(!dataKeys.containsKey(key)) {
						DataKey dataKey = null;
						try {
							dataKey = em.createQuery(q.where(cb.equal(q.from(DataKey.class).get("name"), key))).getSingleResult();
						}
						catch(NoResultException e) {
							dataKey = new DataKey();
							dataKey.setName(key);
							em.persist(dataKey);
						}
						dataKeys.put(key, dataKey);
					}
				}
			}
		}
		em.getTransaction().commit();
		
		
		
		em.getTransaction().begin();
		{
			CriteriaQuery<CountyData> q = cb.createQuery(CountyData.class);
			System.out.println("Skipping: ");
			int i=0;
			for(Result result : results) {
				try {
					for(Entry<String, Long> age : result.ages.entrySet()) {
						DataKey key = dataKeys.get(age.getKey());
	
						try {
							em.createQuery(q.where(cb.and(
								cb.equal(q.from(CountyData.class).get("county"), result.county),
								cb.equal(q.from(CountyData.class).get("key"), key)))).getSingleResult();
							
							System.out.print(".");
							if(++i % 100 == 0) System.out.println();
						}
						catch(NoResultException e) {
							CountyData countyData = new CountyData();
							countyData.setCounty(result.county);
							countyData.setKey(key);
							countyData.setValue((double) age.getValue());
							em.persist(countyData);
						}
					}
				}
				catch(Exception e) {
					System.err.println("Error with result: "+e.getMessage());
					e.printStackTrace();
				}
			}
		}
		em.getTransaction().commit();
		em.flush();	
	}

}
