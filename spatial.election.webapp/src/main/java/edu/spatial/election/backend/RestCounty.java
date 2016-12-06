package edu.spatial.election.backend;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.spatial.election.backend.holder.CountyVotes;
import edu.spatial.election.database.DatabaseConnection;
import edu.spatial.election.database.dao.CountyDAO;
import edu.spatial.election.database.dao.SpatialDAOFactory;
import edu.spatial.election.database.exceptions.CountyNotFoundException;
import edu.spatial.election.domain.County;
import edu.spatial.election.domain.DataKey;


@Path("/county")
public class RestCounty {
	private static final long CACHED_SECONDS = 3600*24*7;

	private SpatialDAOFactory f = SpatialDAOFactory.getDAOFactory(SpatialDAOFactory.POSTGIS);

	private static ConcurrentHashMap<Double, CountiesByDetail> cachedAllCountiesByDetail = new ConcurrentHashMap<Double, CountiesByDetail>();

	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<County> getAllCounties() {
		return getAllCountiesByDetail(0);
	}
	
	@GET
	@Path("/detail/{level}")
	@Produces({MediaType.APPLICATION_JSON})
	public List<County> getAllCountiesByDetail(@PathParam("level") double level) {
		return getCountyDetailsByLevel(level).counties;
	}
	
	
	private CountiesByDetail getCountyDetailsByLevel(double level) {
		CountiesByDetail cached = cachedAllCountiesByDetail.get(level);
		if(cached==null) {
			cachedAllCountiesByDetail.put(level, cached = new CountiesByDetail());
		}
		if(cached.counties==null || cached.counties.isEmpty() || cached.time==null || (new Date().getTime()-cached.time.getTime())/1000 > CACHED_SECONDS) {
			if(cached.em==null) {
				cached.em = DatabaseConnection.createManager();
			}

			// Create a DAO
			CountyDAO countyDAO = f.getCountyDAO();
			countyDAO.setEntityManager(cached.em);
			
			cached.counties = countyDAO.getCounties();
			cached.time = new Date();
			
			for(County c : cached.counties) {
				c.setGeometryDetail(level);
				c.getGeometryArray();
			}
		}
		return cached;
	}

	@GET
	@Path("/votes/{level}")
	@Produces({MediaType.APPLICATION_JSON})
	public List<CountyVotes> getAllCountiesWithResultsByDetail(@PathParam("level") double level) {

		CountiesByDetail counties = getCountyDetailsByLevel(level);
		List<CountyVotes> out = new LinkedList<CountyVotes>();

		for(County c : counties.counties)
		{
			CountyVotes v = new CountyVotes(c);
			out.add(v);
		}
		return out;
	}

	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public County getCountyById(@PathParam("id") int id) throws CountyNotFoundException {
		EntityManager em = DatabaseConnection.createManager();
		// Create a DAO
		CountyDAO countyDAO = f.getCountyDAO();
		countyDAO.setEntityManager(em);

		County c = countyDAO.findCountyById(id);
		
		em.close();
		return c;
	}
	
	
	
	public class CountyDataPair {
		public String key;
		public Double value;
		public CountyDataPair(String key, Double value) {
			this.key = key;
			this.value = value;
		}
	}
	
	
	@GET
	@Path("{id}/data")
	@Produces({MediaType.APPLICATION_JSON})
	public List<CountyDataPair> getCountyDataById(@PathParam("id") int id) throws CountyNotFoundException {
		EntityManager em = DatabaseConnection.createManager();
		// Create a DAO
		CountyDAO countyDAO = f.getCountyDAO();
		countyDAO.setEntityManager(em);

		List<CountyDataPair> out = new LinkedList<CountyDataPair>();
		Map<DataKey, Double> data = new TreeMap<DataKey, Double>(countyDAO.findCountyById(id).getData());

		for(Entry<DataKey, Double> entry : data.entrySet()) {
			out.add(new CountyDataPair(entry.getKey().toString(), entry.getValue()));
		}

		em.close();
		
		return out;
	}
	
	

	
	
	

	private class CountiesByDetail {
		public EntityManager em;
		public List<County> counties = new LinkedList<County>();
		public Date time = new Date();
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((counties == null) ? 0 : counties.hashCode());
			result = prime * result + ((time == null) ? 0 : time.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CountiesByDetail other = (CountiesByDetail) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (counties == null) {
				if (other.counties != null)
					return false;
			} else if (!counties.equals(other.counties))
				return false;
			if (time == null) {
				if (other.time != null)
					return false;
			} else if (!time.equals(other.time))
				return false;
			return true;
		}
		private RestCounty getOuterType() {
			return RestCounty.this;
		}
	}
	
}