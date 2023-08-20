/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();

    // Abstraction function:
    // Represents a graph where each Vertex in the 'vertices' list is a node in the graph.
    // Representation invariant:
    // Each vertex in 'vertices' has a unique label.
    // Safety from rep exposure:
    // 'vertices' is private, final and is never returned to the client.
    // All returned objects are either immutable or defensive copies.
    
    // TODO constructor

    public ConcreteVerticesGraph() {
        checkRep();
    }
    
    // TODO checkRep
    private void checkRep() {
        Set<L> labels = new HashSet<>();
        for (Vertex<L> v : vertices) {
            assert !labels.contains(v.getLabel()) : "Duplicate vertex found";
            labels.add(v.getLabel());
        }
    }


    /**
     * Adds a new vertex to the graph.
     *
     * @param vertex the label of the vertex to add
     * @return true if the vertex was added, false if it already existed
     */
    @Override
    public boolean add(L vertex) {
        checkRep();
        for (Vertex v : vertices) {
            if (v.getLabel().equals(vertex)) {
                return false; // vertex already exists
            }
        }
        vertices.add(new Vertex(vertex));
        checkRep();
        return true;
    }

    /**
     * Sets the weight of an edge.
     *
     * @param source the label of the source vertex
     * @param target the label of the target vertex
     * @param weight the weight of the edge
     * @return the previous weight of the edge, or 0 if the edge did not exist
     */
    @Override
    public int set(L source, L target, int weight) {
        checkRep();
        Vertex sourceVertex = findOrCreate(source);
        Vertex targetVertex = findOrCreate(target);

        int oldWeight = sourceVertex.getWeight(target);
        if (weight == 0) {
            sourceVertex.removeTarget(target);
            targetVertex.removeSource(source);
        } else {
            sourceVertex.addTarget(target, weight);
            targetVertex.addSource(source, weight);
        }
        checkRep();
        return oldWeight;
    }

    /**
     * Removes a vertex from the graph.
     *
     * @param vertex the label of the vertex to remove
     * @return true if the vertex was removed, false if it did not exist
     */
    @Override
    public boolean remove(L vertex) {
        checkRep();
        Vertex v = find(vertex);
        if (v == null) {
            return false;
        }
        vertices.remove(v);
        // Remove all edges involving v
        for (Vertex other : vertices) {
            other.removeTarget(vertex);
            other.removeSource(vertex);
        }
        checkRep();
        return true;
    }

    /**
     * Returns a set of all vertices in the graph.
     *
     * @return a set of all vertex labels
     */
    @Override
    public Set<L> vertices() {
        checkRep();
        return vertices.stream().map(Vertex::getLabel).collect(Collectors.toSet());
    }

    /**
     * Returns a map from source vertices to edge weights for the given target vertex.
     *
     * @param target the label of the target vertex
     * @return a map where keys are source vertex labels and values are edge weights
     */
    @Override
    public Map<L, Integer> sources(L target) {
        checkRep();
        Vertex v = find(target);
        if (v == null) {
            return new HashMap<>();
        }
        return v.getSources();
    }

    /**
     * Returns a map from target vertices to edge weights for the given source vertex.
     *
     * @param source the label of the source vertex
     * @return a map where keys are target vertex labels and values are edge weights
     */
    @Override
    public Map<L, Integer> targets(L source) {
        checkRep();
        Vertex v = find(source);
        if (v == null) {
            return new HashMap<>();
        }
        return v.getTargets();
    }

    // Helper function to find a Vertex by label, returns null if not found.
    private Vertex find(L label) {
        for (Vertex v : vertices) {
            if (v.getLabel().equals(label)) {
                return v;
            }
        }
        return null;
    }

    // Helper function to find a Vertex by label, or create and return a new one if not found.
    private Vertex findOrCreate(L label) {
        Vertex v = find(label);
        if (v == null) {
            v = new Vertex(label);
            vertices.add(v);
        }
        return v;
    }
    
    // TODO toString()
    @Override
    public String toString() {
        return "Graph with " + vertices.size() + " vertices: " + vertices.toString();
    }

}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    
    // TODO fields
    private final L label;
    private final Map<L, Integer> sources = new HashMap<>();
    private final Map<L, Integer> targets = new HashMap<>();

    // Abstraction function:
    // Represents a node in a graph with label 'label'. Incoming edges are stored in 'sources'
    // and outgoing edges are stored in 'targets'.
    // Representation invariant:
    // No key in 'sources' or 'targets' is equal to 'label'.
    // Safety from rep exposure:
    // All fields are private. 'label' is an immutable L.
    // 'sources' and 'targets' are never returned directly; defensive copies are used.
    
    // TODO constructor
    public Vertex(L label) {
        this.label = label;
        checkRep();
    }
    
    // TODO checkRep
    private void checkRep() {
        assert !sources.containsKey(label) : "Vertex has a source edge with itself";
        assert !targets.containsKey(label) : "Vertex has a target edge with itself";
    }

    // TODO methods


    public Map<L, Integer> getSources() {
        checkRep();
        return new HashMap<>(sources);
    }


    public Map<L, Integer> getTargets() {
        checkRep();
        return new HashMap<>(targets);
    }

    /**
     * Adds or updates the weight of an outgoing edge to the specified target.
     *
     * @param target the target vertex label
     * @param weight the weight of the edge
     * @return previous weight of the edge, or 0 if there was no such edge
     */
    public int addTarget(L target, int weight) {
        checkRep();
        Optional<Integer> result = Optional.ofNullable(targets.put(target, weight));
        return result.orElse(0);
    }

    /**
     * Adds or updates the weight of an incoming edge from the specified source.
     *
     * @param source the source vertex label
     * @param weight the weight of the edge
     * @return previous weight of the edge, or 0 if there was no such edge
     */
    public int addSource(L source, int weight) {
        checkRep();
        Optional<Integer> result = Optional.ofNullable(sources.put(source, weight));
        return result.orElse(0);
    }

    /**
     * Removes an outgoing edge to the specified target.
     *
     * @param target the target vertex label
     * @return the weight of the removed edge, or 0 if there was no such edge
     */
    public int removeTarget(L target) {
        checkRep();
        Optional<Integer> result = Optional.ofNullable(targets.remove(target));
        return result.orElse(0);
    }

    /**
     * Removes an incoming edge from the specified source.
     *
     * @param source the source vertex label
     * @return the weight of the removed edge, or 0 if there was no such edge
     */
    public int removeSource(L source) {
        checkRep();
        Optional<Integer> result = Optional.ofNullable(sources.remove(source));
        return result.orElse(0);
    }

    /**
     * Returns the label of the vertex.
     *
     * @return the label of the vertex
     */
    public L getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vertex vertex = (Vertex) o;
        return Objects.equals(label, vertex.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }


    // TODO toString()
    @Override
    public String toString() {
        return label + " -> " + targets.toString();
    }

    /**
     * Returns the weight of the edge to the specified target.
     *
     * @param target the target vertex label
     * @return the weight of the edge, or 0 if there is no such edge
     */
    public int getWeight(L target) {
        checkRep();
        return targets.getOrDefault(target, 0);
    }
}
