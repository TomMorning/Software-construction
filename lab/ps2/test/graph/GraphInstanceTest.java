/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   TODO
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();

        assertTrue("expected vertex to be added", graph.add("A"));
        assertTrue("expected vertex set to contain 'A'", graph.vertices().contains("A"));

        assertFalse("expected duplicate vertex not to be added", graph.add("A"));

        assertTrue("expected vertex to be added", graph.add("B"));
        assertEquals("expected two vertices", 2, graph.vertices().size());
    }

    @Test
    public void testSetEdge() {
        Graph<String> graph = emptyInstance();

        // Add an edge between two non-existing vertices
        assertEquals("expected no previous edge", 0, graph.set("A", "B", 5));
        assertTrue("expected vertex 'A' to exist", graph.vertices().contains("A"));
        assertTrue("expected vertex 'B' to exist", graph.vertices().contains("B"));
        assertEquals("expected correct weight for edge A->B", (Integer) 5, graph.targets("A").get("B"));

        // Update weight of existing edge
        assertEquals("expected previous weight to be returned", 5, graph.set("A", "B", 7));
        assertEquals("expected updated weight for edge A->B", (Integer) 7, graph.targets("A").get("B"));
    }

    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.set("A", "B", 5); // Adds B and edge A->B

        assertTrue("expected vertex 'A' to be removed", graph.remove("A"));
        assertFalse("expected vertex 'A' not to exist post removal", graph.vertices().contains("A"));
        assertTrue("expected vertex 'B' to remain", graph.vertices().contains("B"));
        assertTrue("expected sources of 'B' to be empty post removal of 'A'", graph.sources("B").isEmpty());
    }

}
