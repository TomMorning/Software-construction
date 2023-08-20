/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Map;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteVerticesGraph.toString()
    @Test
    public void testToStringEmptyGraph() {
        Graph<String> graph = emptyInstance();
        assertEquals("Graph with 0 vertices: []", graph.toString());
    }

    @Test
    public void testToStringGraphWithSingleVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertTrue(graph.toString().contains("Graph with 1 vertices:"));
        assertTrue(graph.toString().contains("A"));
    }

    @Test
    public void testToStringGraphWithMultipleVertices() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        assertTrue(graph.toString().contains("Graph with 2 vertices:"));
        assertTrue(graph.toString().contains("A"));
        assertTrue(graph.toString().contains("B"));
    }

    @Test
    public void testToStringGraphWithMultipleVerticesAndEdges() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 2);
        assertTrue(graph.toString().contains("Graph with 2 vertices:"));
        assertTrue(graph.toString().contains("A"));
        assertTrue(graph.toString().contains("B"));
        // This might be implementation dependent; adjust as necessary
    }



    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   TODO
    
    // TODO tests for operations of Vertex
    @Test
    public void testVertexCreation() {
        Vertex vertex = new Vertex("A");
        assertEquals("A", vertex.getLabel());
    }

    @Test
    public void testVertexWithoutEdges() {
        Vertex vertex = new Vertex("A");
        assertTrue(vertex.getSources().isEmpty());
        assertTrue(vertex.getTargets().isEmpty());
    }

    @Test
    public void testVertexWithMultipleEdges() {
        Vertex vertex = new Vertex("A");
        vertex.addSource("B", 3);
        vertex.addTarget("C", 5);

        Map<String, Integer> sources = vertex.getSources();
        Map<String, Integer> targets = vertex.getTargets();

        assertEquals(1, sources.size());
        assertTrue(sources.containsKey("B"));
        assertEquals(3, (int) sources.get("B"));

        assertEquals(1, targets.size());
        assertTrue(targets.containsKey("C"));
        assertEquals(5, (int) targets.get("C"));
    }

    @Test
    public void testVertexToString() {
        Vertex vertex = new Vertex("A");
        vertex.addSource("B", 3);
        vertex.addTarget("C", 5);
        assertEquals("A -> {C=5}", vertex.toString());
    }
    
}
