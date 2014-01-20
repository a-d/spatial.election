package edu.spatial.election.domain;

import com.vividsolutions.jts.geom.MultiPolygon;

public interface Geometry {

	public MultiPolygon getGeom();
}
