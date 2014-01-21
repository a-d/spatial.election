package edu.spatial.election.domain.kind;

import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.vividsolutions.jts.geom.Coordinate;

public abstract class ExportableGeometry implements Geometry {

	@JsonIgnore
	@Transient
	private double detail = 0; // #points' = #points/2^detail


	@JsonIgnore
	@JsonProperty(value="geometry")
	public String getGeometryString() {
		return getGeom().toText();
	}
	

	@JsonProperty(value="coordinates")
	public double[][][] getGeometryArray() {
		int MIN_SIZE = 3;
		int polys = getGeom().getNumGeometries();
		double[][][] out = new double[polys][][];
		for(int c=0; c<polys; c++)
		{
			Coordinate[] coords = getGeom().getGeometryN(c).getCoordinates();
			int step = (int) Math.floor(Math.pow(2, detail));
			int size = (int) Math.ceil(coords.length / (double) step);
			if(size<MIN_SIZE) {
				size = MIN_SIZE;
				step = (int) Math.floor(coords.length / size);
			}
			if(size>coords.length) {
				size = coords.length;
				step = 1;
			}
			
			double[][] row = out[c] = new double[size][];
			if(coords.length>0)
			{
				boolean d1 = Double.isNaN(coords[0].y), d2 = !d1 &&  Double.isNaN(coords[0].z), d3 = !d1 && !d2;
				for(int i=0, s=0; i<row.length; i++, s+=(int) step) {
					row[i] = new double[d1 ? 1 : d2 ? 2 : 3];
					row[i][0] = coords[s].x;
					if(d2 || d3) row[i][1] = coords[s].y;
					if(d3) row[i][2] = coords[s].z;
				}
			}
		}
		return out;
	}
	

	public void setGeometryDetail(double detail) {
		this.detail = detail;
	}
	
}
