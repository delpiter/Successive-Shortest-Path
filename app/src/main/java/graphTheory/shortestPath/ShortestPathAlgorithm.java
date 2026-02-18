package graphTheory.shortestPath;

import graphTheory.graph.Graph;
import graphTheory.graph.Node;

public interface ShortestPathAlgorithm {

    /**
     * Executes the Dijkstra algorithm
     */
    void run(final Graph g, final Node source);
}
