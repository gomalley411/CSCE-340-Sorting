package com.gommeh.EWDG;

/******************************************************************************
 *  An edge-weighted digraph, implemented using adjacency lists.
 ******************************************************************************/

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

/**
 *  The EdgeWeightedDigraph class represents an edge-weighted
 *  digraph of vertices named 0 through V - 1, where each
 *  directed edge is of type DirectedEdge and has a real-valued weight.
 *  It supports the following two primary operations: add a directed edge
 *  to the digraph and iterate over all of edges incident from a given vertex.
 *  It also provides methods for returning the indegree or outdegree of a
 *  vertex, the number of vertices V in the digraph, and
 *  the number of edges E in the digraph.
 *  Parallel edges and self-loops are permitted.
 *  <p>
 *  This implementation uses an adjacency-lists representation, which
 *  is a vertex-indexed array of Bag objects.
 *  It uses Î¸(E + V) space, where E is
 *  the number of edges and V is the number of vertices.
 *  All instance methods take Î¸(1) time. (Though, iterating over
 *  the edges returned by #adj(int) takes time proportional
 *  to the outdegree of the vertex.)
 *  Constructing an empty edge-weighted digraph with V vertices
 *  takes Î¸(V) time; constructing an edge-weighted digraph
 *  with E edges and V vertices takes
 *  Î¸(E + V) time. 
 */
 
public class EdgeWeightedDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;                // number of vertices in this digraph
    private int E;                      // number of edges in this digraph
    private Bag<DirectedEdge>[] adj;    // adj[v] = adjacency list for vertex v
    private int[] indegree;             // indegree[v] = indegree of vertex v
    
    /**
     * Initializes an empty edge-weighted digraph with V vertices and 0 edges.
     *
     * @param  V the number of vertices
     * @throws IllegalArgumentException if V < 0
     */
    public EdgeWeightedDigraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");
        this.V = V;
        this.E = 0;
        this.indegree = new int[V];
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<DirectedEdge>();
    }

    /**
     * Initializes a random edge-weighted digraph with V vertices and E edges.
     *
     * @param  V the number of vertices
     * @param  E the number of edges
     * @throws IllegalArgumentException if V < 0
     * @throws IllegalArgumentException if E < 0
     */
    public EdgeWeightedDigraph(int V, int E) {
        this(V);
        if (E < 0) throw new IllegalArgumentException("Number of edges in a Digraph must be non-negative");
        
        Random rand = new Random();
        for (int i = 0; i < E; i++) {
            int v = rand.nextInt(V);
            int w = rand.nextInt(V);
            double weight = rand.nextDouble();
            
            DirectedEdge e = new DirectedEdge(v, w, weight);
            addEdge(e);
        }
    }

    /**  
     * Initializes an edge-weighted digraph from the specified input file.
     * The format is the number of vertices V,
     * followed by the number of edges E,
     * followed by E pairs of vertices and edge weights,
     * with each entry separated by whitespace.
     *
     * @param  input the input file
     * @throws IllegalArgumentException if input is null
     * @throws IllegalArgumentException if the endpoints of any edge are not in prescribed range
     * @throws IllegalArgumentException if the number of vertices or edges is negative
     */
    public EdgeWeightedDigraph(Scanner input) {
        if (input == null) throw new IllegalArgumentException("argument is null");
        
        try {
            this.V = input.nextInt();
            if (V < 0) throw new IllegalArgumentException("number of vertices in a Digraph must be non-negative");
            indegree = new int[V];
            adj = (Bag<DirectedEdge>[]) new Bag[V];
            for (int v = 0; v < V; v++) 
                adj[v] = new Bag<DirectedEdge>();

            int E = input.nextInt();
            if (E < 0) throw new IllegalArgumentException("Number of edges must be non-negative");
            for (int i = 0; i < E; i++) {
                int v = input.nextInt();
                int w = input.nextInt();
                validateVertex(v);
                validateVertex(w);
                double weight = input.nextDouble();
                addEdge(new DirectedEdge(v, w, weight));
            }
        }   
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in EdgeWeightedDigraph constructor", e);
        }
    }

    /**
     * Initializes a new edge-weighted digraph that is a deep copy of G.
     *
     * @param  G the edge-weighted digraph to copy
     */
    public EdgeWeightedDigraph(EdgeWeightedDigraph G) {
        this(G.V());
        this.E = G.E();
        
        for (int v = 0; v < G.V(); v++)
            this.indegree[v] = G.indegree(v);
            
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<DirectedEdge> reverse = new Stack<DirectedEdge>();
            for (DirectedEdge e : G.adj[v]) 
                reverse.push(e);
            
            for (DirectedEdge e : reverse) 
                adj[v].add(e);
        }
    }

    /**
     * Returns the number of vertices in this edge-weighted digraph.
     *
     * @return the number of vertices in this edge-weighted digraph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this edge-weighted digraph.
     *
     * @return the number of edges in this edge-weighted digraph
     */
    public int E() {
        return E;
    }

    // throw an IllegalArgumentException unless 0 <= v < V
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Adds the directed edge e to this edge-weighted digraph.
     *
     * @param  e the edge
     * @throws IllegalArgumentException unless endpoints of edge are between 0
     *         and V-1
     */
    public void addEdge(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        validateVertex(v);
        validateVertex(w);
        
        adj[v].add(e);
        indegree[w]++;
        E++;
    }


    /**
     * Returns the directed edges incident from vertex v.
     *
     * @param  v the vertex
     * @return the directed edges incident from vertex v as an Iterable
     * @throws IllegalArgumentException unless 0 <= v < V
     */
    public Iterable<DirectedEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * Returns the number of directed edges incident from vertex v.
     * This is known as the outdegree of vertex v.
     *
     * @param  v the vertex
     * @return the outdegree of vertex v
     * @throws IllegalArgumentException unless 0 <= v < V
     */
    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * Returns the number of directed edges incident to vertex v.
     * This is known as the indegree of vertex v.
     *
     * @param  v the vertex
     * @return the indegree of vertex v
     * @throws IllegalArgumentException unless 0 <= v < V
     */
    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    /**
     * Returns all directed edges in this edge-weighted digraph.
     * To iterate over the edges in this edge-weighted digraph, use foreach notation:
     * for (DirectedEdge e : G.edges()).
     *
     * @return all edges in this edge-weighted digraph, as an iterable
     */
    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> list = new Bag<DirectedEdge>();
        for (int v = 0; v < V; v++) 
            for (DirectedEdge e : adj(v)) 
                list.add(e);
                
        return list;
    } 

    /**
     * Returns a string representation of this edge-weighted digraph.
     *
     * @return the number of vertices V, followed by the number of edges E,
     *         followed by the V adjacency lists of edges
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (DirectedEdge e : adj[v]) 
                s.append(e + "  ");
            
            s.append(NEWLINE);
        }
        return s.toString();
    }
}
