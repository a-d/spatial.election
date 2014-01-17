package edu.spatial.election.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;

@Entity
@Table(name = "CONSTITUENCY")
public class Constituency {

	@Id
	@GeneratedValue
	private long gid;
	
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


	@JsonProperty(value="geometry")
	public String getGeometryString() {
		return geom.toText();
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
	
}
