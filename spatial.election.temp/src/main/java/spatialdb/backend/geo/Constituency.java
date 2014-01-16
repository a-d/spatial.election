package spatialdb.backend.geo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.MultiPolygon;

@Entity
@Table(name = "CONSTITUENCY")
public class Constituency {

	@Id
    @GenericGenerator(name="increment", strategy = "increment")
	private long gid;
	
	private int wkr_nr;
	
	private String wkr_name;
	
	private String land_nr;
	
	private String land_name;
	
	// for convenience purposes, see documentation
	private String jsonRepresentation;

	@JsonIgnore
    @Type(type="org.hibernate.spatial.GeometryType")
	private MultiPolygon geom;

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

	public String getJsonRepresentation() {
		return jsonRepresentation;
	}

	public void setJsonRepresentation(String jsonRepresentation) {
		this.jsonRepresentation = jsonRepresentation;
	}
}
