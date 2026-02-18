package graphTheory.graph;

import java.util.Optional;
import java.util.Set;

public interface Node {

    /**
     * Returns the integer that identifies the node.
     */
    int getNodeId();

    /*
     * Sets the predecessor of the node in the shortest path.
     */
    void setPredecessor(final Node p);

    /*
     * Sets the distance from the source in the path.
     */
    void setDistance(final int d);

    /*
     * Returns the predecessor of the node in the path.
     */
    Optional<Node> getPredecessor();

    /*
     * Returns the distance of the node from the origin.
     */
    int getDistance();

    /**
     * Sets the balance of the node
     * 
     * @param b can be > 0 (Supply) or b < 0 (Demand)
     */
    void setBalance(int b);

    /**
     * Returns the current availability of the node
     * 
     * @return
     */
    int getBalance();

    /**
     * Adds an element to the adjacency List
     * 
     * @param to   The destination node
     * @param cost The cost of the edge
     * @param flow The max flow of the edge can handle
     */
    void addEdge(final Node to, final int cost, final int flow);

    /**
     * Returns all the adjacent nodes and relative costs
     * 
     * @return
     */
    Set<Endpoint> getEdges();

    /**
     * Sets the node explored, needed for the dijkstra algorithm
     */
    void setExplored();

    /**
     * @return Returns true if the node has already been explored in the dijkstra
     *         algorithm
     */
    boolean isExplored();

    /**
     * Sets the current minimum flow in the augmenting path
     * 
     * @param minFlow
     */
    void updateFlow(final int minFlow);

    /**
     * @return The minimum edge value for the flow
     */
    int getFlow();

    /**
     * Updates the potential for the node
     */
    void updatePotential();

    /**
     * @return The value of the current potential
     */
    int getPotential();

    /**
     * Resets the nodes for the next iteration
     */
    void reset();
}
