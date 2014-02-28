package edu.spatial.election.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;

import edu.spatial.election.domain.kind.ExportableGeometry;

@SuppressWarnings("serial")
@Entity
@Table(name = "CONSTITUENCY")
public class Constituency extends ExportableGeometry implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long gid;

	@Column(unique=true)
	private int wkr_nr;

	private String wkr_name;

	private String land_nr;

	private String land_name;

	@JsonIgnore
	@Type(type="org.hibernate.spatial.GeometryType")
	@Column(columnDefinition="Geometry")
	private Point centerPoint;

	@JsonIgnore
	@Type(type="org.hibernate.spatial.GeometryType")
	@Column(columnDefinition="Geometry")
	private MultiPolygon geom;
		

	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="constituency_id", referencedColumnName="wkr_nr")
	@OrderBy("areaQuota")
	private List<CountyContainsConstituency> dependingCounties = new LinkedList<CountyContainsConstituency>();
	
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="constituencyId", referencedColumnName="gid")
	private Set<ElectionResult> electionResults = new HashSet<ElectionResult>();

	
	@ElementCollection(targetClass = java.lang.Double.class, fetch=FetchType.EAGER)
	@MapKeyJoinColumn(name = "key_id")
	@CollectionTable(name = "CONSTITUENCY_DATA", joinColumns = @JoinColumn(name = "constituency_id"))
	@Column(name = "value")
	private Map<DataKey, Double> data = new HashMap<DataKey, Double>();

	
	public static Constituency createSaveProxy(final Constituency c) {
		Constituency prox = new Constituency() { 
			@Override
			public List<CountyContainsConstituency> getDependingCounties() {
				return c.getDependingCounties();
			}
			@Override
			public Set<ElectionResult> getElectionResults() {
				return c.getElectionResults();
			}
			@Override
			public MultiPolygon getGeom() {
				return c.getGeom();
			}
			@Override
			public Point getCenterPoint() {
				return c.getCenterPoint();
			}
		};
		prox.setGid(c.getGid());
		prox.setWkr_nr(c.getWkr_nr());
		prox.setWkr_name(c.getWkr_name());
		prox.setLand_nr(c.getLand_nr());
		prox.setLand_name(c.getLand_name());
		prox.setData(c.getData());
		return prox;
	}
	

	public long getGid() {
		return gid;
	}

	public void setGid(long gid) {
		this.gid = gid;
	}

	public int getWkr_nr() {
		return wkr_nr;
	}

	public void setWkr_nr(int wkr_nr) {
		this.wkr_nr = wkr_nr;
	}

	public String getWkr_name() {
		return wkr_name;
	}

	public void setWkr_name(String wkr_name) {
		this.wkr_name = wkr_name;
	}

	public String getLand_nr() {
		return land_nr;
	}

	public void setLand_nr(String land_nr) {
		this.land_nr = land_nr;
	}

	public String getLand_name() {
		return land_name;
	}

	public void setLand_name(String land_name) {
		this.land_name = land_name;
	}

	public MultiPolygon getGeom() {
		return geom;
	}

	public void setGeom(MultiPolygon geom) {
		this.geom = geom;
	}

	public Point getCenterPoint() {
		return centerPoint;
	}

	public void setCenterPoint(Point centerPoint) {
		this.centerPoint = centerPoint;
	}

	public List<CountyContainsConstituency> getDependingCounties() {
		return dependingCounties;
	}

	public void setDependingCounties(List<CountyContainsConstituency> dependingCounties) {
		this.dependingCounties = dependingCounties;
	}


	public Set<ElectionResult> getElectionResults() {
		return electionResults;
	}

	public void setElectionResults(Set<ElectionResult> electionResults) {
		this.electionResults = electionResults;
	}

	public Map<DataKey, Double> getData() {
		return data;
	}

	public void setData(Map<DataKey, Double> data) {
		this.data = data;
	}
}
