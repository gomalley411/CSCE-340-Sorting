package com.gommeh.dijkstra;


/******************************************************************************
 *  Dijkstra's algorithm. Computes the shortest path tree.
 *  Assumes all weights are non-negative.
 ******************************************************************************/

import java.util.Stack;
import com.gommeh.EWDG.*;

/**
 *  The DijkstraSP class represents a data type for solving the
 *  single-source shortest paths problem in edge-weighted digraphs
 *  where the edge weights are non-negative.
 *  <p>
 *  This implementation uses Dijkstra's algorithm with a
 *  binary heap. The constructor takes
 *  Î¸(ElogV) time in the worst case,
 *  where V is the number of vertices and E is
 *  the number of edges. Each instance method takes Î¸(1) time.
 *  It uses Î¸(V) extra space (not including the
 *  edge-weighted digraph).
 *  <p>
 *  This correctly computes shortest paths if all arithmetic performed is
 *  without floating-point rounding error or arithmetic overflow.
 *  This is the case if all edge weights are integers and if none of the
 *  intermediate results exceeds 2^52. Since all intermediate
 *  results are sums of edge weights, they are bounded by V C,
 *  where V is the number of vertices and C is the maximum
 *  weight of any edge.
 */
public class DijkstraSP {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices

    /**
     * Computes a shortest-paths tree from the source vertex s to every other
     * vertex in the edge-weighted digraph G.
     *
     * @param  G the edge-weighted digraph
     * @param  s the source vertex
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless 0 <= s < V
     */
    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }

        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];

        validateVertex(s);

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : G.adj(v))
                relax(e);
        }

        // check optimality conditions
        assert check(G, s);
    }

    // relax edge e and update pq if changed
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            
            if (pq.contains(w)) 
                pq.decreaseKey(w, distTo[w]);
            else                
                pq.insert(w, distTo[w]);
        }
    }

    /**
     * Returns the length of a shortest path from the source vertex s to vertex v.
     * @param  v the destination vertex
     * @return the length of a shortest path from the source vertex s to vertex v;
     *         Double.POSITIVE_INFINITY if no such path
     * @throws IllegalArgumentException unless 0 <= v < V
     */
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * Returns true if there is a path from the source vertex s to vertex v.
     *
     * @param  v the destination vertex
     * @return true if there is a path from the source vertex
     *         s to vertex v; false otherwise
     * @throws IllegalArgumentException unless 0 <= v < V
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path from the source vertex s to vertex v.
     *
     * @param  v the destination vertex
     * @return a shortest path from the source vertex s to vertex v
     *         as an iterable of edges, and null if no such path
     * @throws IllegalArgumentException unless 0 <= v < V
     */
    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) 
            return null;
            
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) 
            path.push(e);
        
        return path;
    }


    // check optimality conditions:
    // (i) for all edges e: distTo[e.to()] <= distTo[e.from()] + e.weight()
    // (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
    private boolean check(EdgeWeightedDigraph G, int s) {
        // check that edge weights are non-negative
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s) 
                continue;
                
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.V(); v++) {
            for (DirectedEdge e : G.adj(v)) {
                int w = e.to();
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.V(); w++) {
            if (edgeTo[w] == null) 
                continue;
                
            DirectedEdge e = edgeTo[w];
            int v = e.from();
            if (w != e.to()) 
                return false;
                
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }

    // throw an IllegalArgumentException unless 0 <= v < V
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
}