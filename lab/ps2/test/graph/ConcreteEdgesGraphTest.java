/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   TODO
    @Test
    public void testToStringEmptyGraph() {
        Graph<String> graph = emptyInstance();
        assertEquals("Graph with 0 vertices and 0 edges.", graph.toString());
    }

    @Test
    public void testToStringGraphWithVerticesOnly() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        assertEquals("Graph with 2 vertices and 0 edges.", graph.toString());
    }

    @Test
    public void testToStringGraphWithVerticesAndEdges() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 3);
        assertEquals("Graph with 2 vertices and 1 edges.", graph.toString());
    }

    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue(graph.add("A"));
        assertFalse(graph.add("A")); // Adding again should return false
        assertEquals(1, graph.vertices().size());
        assertTrue(graph.vertices().contains("A"));
    }

    @Test
    public void testSetEdges() {
        Graph<String> graph = emptyInstance();
        assertEquals(0, graph.set("A", "B", 5));
        assertTrue(graph.vertices().contains("A"));
        assertTrue(graph.vertices().contains("B"));
        assertEquals(5, (int) graph.targets("A").get("B"));
        assertEquals(5, (int) graph.sources("B").get("A"));
    }

    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        assertTrue(graph.remove("A"));
        assertFalse(graph.vertices().contains("A"));
        assertFalse(graph.targets("B").containsKey("A"));
    }

    @Test
    public void testSourcesAndTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.set("A", "B", 5);
        graph.set("C", "B", 3);
        assertEquals(2, graph.sources("B").size());
        assertTrue(graph.sources("B").containsKey("A"));
        assertTrue(graph.sources("B").containsKey("C"));
        assertEquals(1, graph.targets("A").size());
        assertTrue(graph.targets("A").containsKey("B"));
    }



    // TODO tests for ConcreteEdgesGraph.toString()
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   TODO
    
    // TODO tests for operations of Edge
    @Test
    public void testEdgeConstructionAndObservation() {
        Edge edge = new Edge("A", "B", 5);
        assertEquals("A", edge.getSource());
        assertEquals("B", edge.getTarget());
        assertEquals(5, edge.getWeight());
    }

    @Test
    public void testEdgeToString() {
        Edge edge = new Edge("A", "B", 5);
        assertEquals("A -> B (5)", edge.toString());
    }
}
