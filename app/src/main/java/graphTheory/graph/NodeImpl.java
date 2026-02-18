package graphTheory.graph;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

public class NodeImpl implements Node {

    private int nodeId;
    private Optional<Node> predecessor;
    private int distance;
    private int balance;
    private Set<Endpoint> adjacencyList;
    private boolean isExplored;
    private int flow;
    private int potential;

    /**
     * Creates a node.
     * 
     * @param n        The node number identifier
     * @param distance The distance from the source node
     */
    public NodeImpl(final int n, final int distance, final int balance) {
        this.nodeId = n;
        this.predecessor = Optional.empty();
        this.distance = distance;
        this.balance = balance;
        this.adjacencyList = new HashSet<>();
        this.isExplored = false;
        this.flow = Integer.MAX_VALUE;
        this.potential = 0;
    }

    /**
     * Creates a node.
     * 
     * @param n The node number identifier
     */
    public NodeImpl(final int n, final int balance) {
        this(n, Integer.MAX_VALUE, balance);
    }

    @Override
    public void setPredecessor(final Node p) {
        this.predecessor = Optional.of(p);
    }

    @Override
    public void setDistance(final int d) {
        this.distance = d;
    }

    @Override
    public Optional<Node> getPredecessor() throws NoSuchElementException {
        // if (predecessor.isEmpty()) {
        // throw new NoSuchElementException("Predecessor is not yet set");
        // }
        return predecessor;
    }

    @Override
    public int getDistance() {
        return this.distance;
    }

    @Override
    public int getNodeId() {
        return this.nodeId;
    }

    @Override
    public void setBalance(int b) {
        this.balance = b;
    }

    @Override
    public int getBalance() {
        return this.balance;
    }

    @Override
    public String toString() {
        return Integer.toString(nodeId) + "={"
                + (this.balance > 0 ? "Supply: " : this.balance < 0 ? "Demand: " : "Transit: ")
                + this.balance + "}";
    }

    @Override
    public void reset() {
        this.distance = Integer.MAX_VALUE;
        this.predecessor = Optional.empty();
        this.isExplored = false;
        this.flow = Integer.MAX_VALUE;
    }

    @Override
    public void addEdge(Node to, int cost, int flow) {
        this.adjacencyList.add(new Endpoint(to, new EdgeImpl(cost, flow)));
    }

    @Override
    public Set<Endpoint> getEdges() {
        return this.adjacencyList;
    }

    @Override
    public void setExplored() {
        this.isExplored = true;
    }

    @Override
    public boolean isExplored() {
        return this.isExplored;
    }

    @Override
    public void updateFlow(int minFlow) {
        this.flow = Math.min(this.flow, minFlow);
    }

    @Override
    public int getFlow() {
        return this.flow;
    }

    @Override
    public void updatePotential() {
        this.potential -= this.distance;
    }

    @Override
    public int getPotential() {
        return this.potential;
    }
}
