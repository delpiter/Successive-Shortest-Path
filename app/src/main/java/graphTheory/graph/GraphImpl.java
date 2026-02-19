package graphTheory.graph;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class GraphImpl implements Graph {
    private MutableValueGraph<Node, Edge> graph;

    public GraphImpl() {
        graph = ValueGraphBuilder.directed().build();
    }

    @Override
    public void addNode(final int id, final int balance) {
        graph.addNode(new NodeImpl(id, balance));
    }

    @Override
    public void addEdge(final int from, final int to, final int cost, final int flow) {
        Node source = getNodeFromId(from);
        Node end = getNodeFromId(to);

        this.graph.putEdgeValue(EndpointPair.ordered(source, end), new EdgeImpl(cost, flow));
        source.addEdge(end, cost, flow);

        this.graph.putEdgeValue(EndpointPair.ordered(getNodeFromId(to), getNodeFromId(from)), new EdgeImpl(-cost, 0));
        end.addEdge(source, -cost, 0);
    }

    @Override
    public Node getNodeFromId(int id) {
        final Set<Node> allNodes = graph.nodes();
        Optional<Node> node = allNodes.stream().filter(n -> n.getNodeId() == id).findFirst();
        if (node.isEmpty())
            throw new NoSuchElementException();
        return node.get();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        this.getNodes().forEach(n -> {
            out.append("Node " + n.getNodeId() + ": " + n.getEdges().toString() + "\n");
        });
        return out.toString();
        // "Nodes: " + this.graph.nodes() + "\n Edges" + this.graph.edges();
    }

    @Override
    public Set<Node> getNodes() {
        return this.graph.nodes();
    }

    @Override
    public Set<EndpointPair<Node>> getEdges() {
        return this.graph.edges();
    }

    @Override
    public void editEdge(Node n1, Node n2, int flow, int cost) {
        Optional<Edge> e = this.graph.edgeValue(n1, n2);
        if (e.isEmpty())
            throw new NoSuchElementException();
        e.get().setCost(cost);
        e.get().setFlow(flow);
    }
}
