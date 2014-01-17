package edu.spatial.election.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Coordinate;
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


	@JsonIgnore
	@JsonProperty(value="geometry")
	public String getGeometryString() {
		return geom.toText();
	}
	

	@JsonProperty(value="coordinates")
	public double[][] getGeometryArray() {
		Coordinate[] coords = geom.getCoordinates();
		double[][] out = new double[coords.length][];
		if(coords.length>0)
		{
			boolean d1 = Double.isNaN(coords[0].y), d2 = !d1 &&  Double.isNaN(coords[0].z), d3 = !d1 && !d2;
			for(int i=0; i<coords.length; i++) {
				out[i] = new double[d1 ? 1 : d2 ? 2 : 3];
				out[i][0] = coords[i].x;
				if(d2 || d3) out[i][1] = coords[i].y;
				if(d3) out[i][2] = coords[i].z;
			}
		}
		return out;
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
