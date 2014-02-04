package edu.spatial.election.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.MultiPolygon;

import edu.spatial.election.domain.kind.ExportableGeometry;

@SuppressWarnings("serial")
@Entity
public class County extends ExportableGeometry implements Serializable {

	@Id
	@GeneratedValue
	private long gid;

	@Column(name="id_0")
	private int countryId;
	
	@Column(name="iso")
	private String countryIso;

	@Column(name="name_0")
	private String countryName;

	@Column(name="id_1")
	private int stateId;

	@Column(name="name_1")
	private String stateName;
	

	@Column(name="id_2")
	private int districtId;

	@Column(name="name_2")
	private String districtName;

	@Column(name="id_3", unique=true)
	private int countyId;

	@Column(name="name_3")
	private String countyName;

	// NOT used:
	// private String nl_name_3;
	// private String varname_3;
	// private String eng_type_3;

	@Column(name="type_3")
	private String countyTypeGerman;
	
	@Column(name="eng_type_3")
	private String countyTypeEnglish;

	@JsonIgnore
    @Type(type="org.hibernate.spatial.GeometryType")
	@Column(columnDefinition="Geometry")
	private MultiPolygon geom;

	@JsonIgnore
	@OneToMany
	@JoinColumn(name="county_id", referencedColumnName="id_3")
	@OrderBy("areaQuota")
	private List<CountyContainsConstituency> dependingConstituencies = new LinkedList<CountyContainsConstituency>();
	

	
	@ElementCollection(targetClass = java.lang.Double.class, fetch=FetchType.EAGER)
	@MapKeyJoinColumn(name = "key_id")
	@CollectionTable(name = "COUNTY_DATA", joinColumns = @JoinColumn(name = "county_id"))
	@Column(name = "value")
	private Map<DataKey, Double> data = new HashMap<DataKey, Double>();

	
	public long getGid() {
		return gid;
	}

	public void setGid(long gid) {
		this.gid = gid;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getCountryIso() {
		return countryIso;
	}

	public void setCountryIso(String countryIso) {
		this.countryIso = countryIso;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getProvinceName() {
		return districtName;
	}

	public void setProvinceName(String provinceName) {
		this.districtName = provinceName;
	}

	public int getCountyId() {
		return countyId;
	}

	public void setCountyId(int countyId) {
		this.countyId = countyId;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCountyTypeGerman() {
		return countyTypeGerman;
	}

	public void setCountyTypeGerman(String countyTypeGerman) {
		this.countyTypeGerman = countyTypeGerman;
	}

	public String getCountyTypeEnglish() {
		return countyTypeEnglish;
	}

	public void setCountyTypeEnglish(String countyTypeEnglish) {
		this.countyTypeEnglish = countyTypeEnglish;
	}

	public MultiPolygon getGeom() {
		return geom;
	}

	public void setGeom(MultiPolygon geom) {
		this.geom = geom;
	}


	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public List<CountyContainsConstituency> getDependingConstituencies() {
		return dependingConstituencies;
	}

	public void setDependingConstituencies(List<CountyContainsConstituency> dependingConstituencies) {
		this.dependingConstituencies = dependingConstituencies;
	}

	public Map<DataKey, Double> getData() {
		return data;
	}

	public void setData(Map<DataKey, Double> data) {
		this.data = data;
	}
}