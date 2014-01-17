package edu.spatial.election.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.MultiPolygon;

@Entity
public class County {

	@Id
	@GeneratedValue
	private long gid;

	private int id_0;
	
	private String iso;
	
	private String name_0;
	
	private int id_1;
	
	private String name_1;
	
	private int id_2;
	
	private String name_2;

	private int id_3;
	
	private String name_3;

	private String nl_name_3;
	
	private String varname_3;

	private String type_3;
	
	private String eng_type_3;
	
	@Type(type="org.hibernate.spatial.GeometryType")
	private MultiPolygon geom;

	public long getGid() {
		return gid;
	}

	public void setGid(long gid) {
		this.gid = gid;
	}

	public int getId_0() {
		return id_0;
	}

	public void setId_0(int id_0) {
		this.id_0 = id_0;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getName_0() {
		return name_0;
	}

	public void setName_0(String name_0) {
		this.name_0 = name_0;
	}

	public int getId_1() {
		return id_1;
	}

	public void setId_1(int id_1) {
		this.id_1 = id_1;
	}

	public String getName_1() {
		return name_1;
	}

	public void setName_1(String name_1) {
		this.name_1 = name_1;
	}

	public int getId_2() {
		return id_2;
	}

	public void setId_2(int id_2) {
		this.id_2 = id_2;
	}

	public String getName_2() {
		return name_2;
	}

	public void setName_2(String name_2) {
		this.name_2 = name_2;
	}

	public int getId_3() {
		return id_3;
	}

	public void setId_3(int id_3) {
		this.id_3 = id_3;
	}

	public String getName_3() {
		return name_3;
	}

	public void setName_3(String name_3) {
		this.name_3 = name_3;
	}

	public String getNl_name_3() {
		return nl_name_3;
	}

	public void setNl_name_3(String nl_name_3) {
		this.nl_name_3 = nl_name_3;
	}

	public String getVarname_3() {
		return varname_3;
	}

	public void setVarname_3(String varname_3) {
		this.varname_3 = varname_3;
	}

	public String getType_3() {
		return type_3;
	}

	public void setType_3(String type_3) {
		this.type_3 = type_3;
	}

	public String getEng_type_3() {
		return eng_type_3;
	}

	public void setEng_type_3(String eng_type_3) {
		this.eng_type_3 = eng_type_3;
	}

	public MultiPolygon getGeom() {
		return geom;
	}

	public void setGeom(MultiPolygon geom) {
		this.geom = geom;
	}
}