package edu.spatial.election.domain.kind;

import com.vividsolutions.jts.geom.MultiPolygon;

public interface Geometry {

	public MultiPolygon getGeom();
}
