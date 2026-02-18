package graphTheory.graph;

import java.util.Set;

import com.google.common.graph.EndpointPair;

public interface Graph {
    /**
     * Adds a generic node to the graph
     * 
     * @param id      The id of the node
     * @param balance The balance of the node (b>0 = supply; b<0 = demand)
     */
    void addNode(final int id, final int balance);

    /**
     * Adds an edge to the graph
     * 
     * @param from The id of the source node
     * @param to   The id of the destination node
     * @param cost The cost of the edge
     * @param flow The max flow that the edge can handle
     */
    void addEdge(final int from, final int to, final int cost, final int flow);

    /**
     * Returns the node with the given id
     * 
     * @param id
     * @return A node object
     */
    Node getNodeFromId(final int id);

    /**
     * Returns an immutable set of nodes
     * 
     * @return A set of nodes
     */
    Set<Node> getNodes();

    /**
     * Returns an immutable set of edges
     * 
     * @return A set of edges
     */
    Set<EndpointPair<Node>> getEdges();

    /**
     * Edits the values of an edge given two nodes
     * 
     * @param n1   Source node
     * @param n2   Destination node
     * @param flow The new flow
     * @param cost The new cost
     */
    void editEdge(Node n1, Node n2, int flow, int cost);
}
