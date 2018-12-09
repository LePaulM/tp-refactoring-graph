package org.acme.graph.routing;

import java.util.List;

import org.acme.graph.TestGraphFactory;
import org.acme.graph.model.Edge;
import org.acme.graph.model.Graph;

import junit.framework.TestCase;

/**
 * Tests fonctionnels sur DijkstraPathFinder
 * 
 * @author MBorne
 *
 */
public class DijkstraRegressTest extends TestCase {

	private Graph graph;

	private DijkstraPathFinder finder;

	@Override
	protected void setUp() throws Exception {
		this.graph = TestGraphFactory.createGraph01();
		this.finder = new DijkstraPathFinder(graph);
	}

	public void testABFound() {
		List<Edge> path = finder.findPath(graph.findVertex("a"), graph.findVertex("b"));
		assertNotNull(path);
		assertEquals(1, path.size());
	}

	public void testBANotFound() {
		List<Edge> path = finder.findPath(graph.findVertex("b"), graph.findVertex("a"));
		assertNull(path);
	}

	public void testACFoundWithCorrectOrder() {
		List<Edge> path = finder.findPath(graph.findVertex("a"), graph.findVertex("c"));
		assertNotNull(path);
		assertEquals(2, path.size());

		int index = 0;
		{
			Edge edge = path.get(index++);
			assertEquals("a", edge.getSource().getId());
			assertEquals("b", edge.getTarget().getId());
		}
		{
			Edge edge = path.get(index++);
			assertEquals("b", edge.getSource().getId());
			assertEquals("c", edge.getTarget().getId());
		}
	}
}