package org.acme.graph.model;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

import org.n52.jackson.datatype.jts.GeometrySerializer;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *
 * Un arc matérialisé par un sommet source et un sommet cible
 *
 * @author MBorne
 *
 */
public class Edge {
	/**
	 * Identifiant de l'arc
	 */
	private String id;

	/**
	 * Sommet initial
	 */
	private Vertex source;

	/**
	 * Sommet final
	 */
	private Vertex target;

	/**
	 * Géométrie
	 */
	private LineString geometry;

	/**
	 * Constructeur
	 * @param source
	 * @param target
	 */
	public Edge(Vertex source, Vertex target) {

		if(source == null || target == null){
			this.source = new Vertex();
			this.target = new Vertex();

		}else{
			this.source = source;
			this.target = target;
		}

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Source avec rendu JSON sous forme d'identifiant
	 *
	 * @return
	 */
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	public Vertex getSource() {
		return source;
	}

	public void setSource(Vertex source) {
		this.source = source;
	}

	/**
	 * Cible avec rendu JSON sous forme d'identifiant
	 *
	 * @return
	 */
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	public Vertex getTarget() {
		return target;
	}

	public void setTarget(Vertex target) {
		this.target = target;
	}

	/**
	 * dijkstra - coût de parcours de l'arc (distance géométrique)
	 *
	 * @return
	 */
	public double getCost() {
		if ( this.geometry == null ){
			return source.getCoordinate().distance(target.getCoordinate());
		}else{
			return this.geometry.getLength();
		}
	}

	@JsonSerialize(using = GeometrySerializer.class)
	public LineString getGeometry() {
		if ( this.geometry == null ){
			GeometryFactory gf = new GeometryFactory();
			return gf.createLineString(new Coordinate[] {
					source.getCoordinate(),
					target.getCoordinate()
			});
		}else{
			return this.geometry;
		}
	}

	public void setGeometry(LineString geometry) {
		this.geometry = geometry;
	}

	@Override
	public String toString() {
		return id + " (" + source + "->" + target + ")";
	}

}