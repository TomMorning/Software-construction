/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();

    // Abstraction function:
    //  AF(vertices, edges) = a graph where each L in `vertices`
    //  represents a vertex in the graph and each edge in `edges`
    //  represents a directed, weighted edge between two nodes in the graph.

    // Representation invariant:
    //  - Every edge in `edges` has its source and target vertices
    //    contained in `vertices`.
    //  - No two edges have the same source and target vertices.

    // Safety from rep exposure:
    //  - All fields are private.
    //  - All returned fields are immutable or defensive copies.
    
    // TODO constructor
    public ConcreteEdgesGraph() {
        checkRep();
    }
    
    // TODO checkRep
    private void checkRep() {
        for (Edge edge : edges) {
            assert vertices.contains(edge.getSource());
            assert vertices.contains(edge.getTarget());
            for (Edge otherEdge : edges) {
                if (edge != otherEdge) {
                    assert !(edge.getSource().equals(otherEdge.getSource()) &&
                            edge.getTarget().equals(otherEdge.getTarget()));
                }
            }
        }
    }

    /**
     * Add the given vertex to this graph.
     *
     * @param vertex the vertex
     * @return true if successfully added, false otherwise
     */
    @Override public boolean add(L vertex) {
        checkRep();
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            checkRep();
            return true;
        }
        return false;
    }

    /**
     * Set the weight of an edge in this graph.
     *
     * @param source the source vertex
     * @param target the target vertex
     * @param weight the weight
     * @return the previous weight, or 0 if no such edge existed
     */
    @Override public int set(L source, L target, int weight) {
        checkRep();
        if (weight < 0) {
            throw new IllegalArgumentException("weight must be non-negative");
        }
        vertices.add(source);
        vertices.add(target);
        for (Edge e : edges) {
            if (e.getSource().equals(source) && e.getTarget().equals(target)) {
                int oldWeight = e.getWeight();
                if (weight == 0) {
                    edges.remove(e);
                } else {
                    edges.remove(e);
                    edges.add(new Edge(source, target, weight));
                }
                checkRep();
                return oldWeight;
            }
        }
        if (weight > 0) {
            edges.add(new Edge(source, target, weight));
        }
        checkRep();
        return 0;
    }

    /**
     * Remove the given vertex from this graph.
     *
     * @param vertex the vertex
     * @return true if successfully removed, false otherwise
     */
    @Override public boolean remove(L vertex) {
        checkRep();
        boolean removed = vertices.remove(vertex);
        edges.removeIf(e -> e.getSource().equals(vertex) || e.getTarget().equals(vertex));
        checkRep();
        return removed;
    }

    /**
     * Get all vertices in this graph.
     *
     * @return the set of vertices
     */
    @Override public Set<L> vertices() {
        return new HashSet<>(vertices);
    }

    /**
     * Get the source vertices and their associated weights of the given target vertex.
     *
     * @param target the target vertex
     * @return a map where the keys are source vertices and the values are weights
     */
    @Override public Map<L, Integer> sources(L target) {
        checkRep();
        return edges.stream()
                .filter(e -> e.getTarget().equals(target))
                .collect(Collectors.toMap(Edge::getSource, Edge::getWeight));
    }

    /**
     * Get the target vertices and their associated weights of the given source vertex.
     *
     * @param source the source vertex
     * @return a map where the keys are target vertices and the values are weights
     */
    @Override public Map<L, Integer> targets(L source) {
        checkRep();
        return edges.stream()
                .filter(e -> e.getSource().equals(source))
                .collect(Collectors.toMap(Edge::getTarget, Edge::getWeight));
    }
    
    // TODO toString()
    @Override
    public String toString() {
        return "Graph with " + vertices.size() + " vertices and " + edges.size() + " edges.";
    }
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    
    // TODO fields
    private final L source;
    private final L target;
    private final int weight;

    // Abstraction function:
    //   AF(source, target, weight) = a directed edge from `source`
    //   to `target` with a weight of `weight`.

    // Representation invariant:
    //   source and target are not null.
    //   weight is positive.

    // Safety from rep exposure:
    //   All fields are private and final.
    //   L is immutable.
    
    // TODO constructor
    public Edge(L source, L target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }
    
    // TODO checkRep
    private void checkRep() {
        assert source != null;
        assert target != null;
        assert weight > 0;
    }
    
    // TODO methods
    public L getSource() {
        return source;
    }

    public L getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }
    
    // TODO toString()
    @Override
    public String toString() {
        return source + " -> " + target + " (" + weight + ")";
    }
}
