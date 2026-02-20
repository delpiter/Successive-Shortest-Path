package graphTheory.successiveShortestPath;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.MinMaxPriorityQueue;

import graphTheory.graph.Edge;
import graphTheory.graph.Graph;
import graphTheory.graph.Node;
import graphTheory.shortestPath.DijkstraSingleSource;
import graphTheory.shortestPath.ShortestPathAlgorithm;

public class SSPSingleSource implements SuccessiveShortestPath {

    private static final String CORRECT = "The problem has a solution";
    private static final String NO_SOLUTION = "The problem has no solution";

    private final Graph graph;
    private final ShortestPathAlgorithm shortestPathAlgorithm;
    // private int flow;
    MinMaxPriorityQueue<Node> suppliers;
    private int lastSum = Integer.MAX_VALUE;
    private String conclusions;

    public SSPSingleSource(final Graph g) {
        this.graph = g;
        this.shortestPathAlgorithm = new DijkstraSingleSource();
        // this.flow = 0;
        this.suppliers = MinMaxPriorityQueue.orderedBy(Comparator.comparingInt(Node::getBalance)).create();
        this.suppliers
                .addAll(this.graph.getNodes()
                        .stream()
                        .filter(n -> n.getBalance() > 0)
                        .collect(Collectors.toSet()));
        // System.out.println(suppliers);
        // demanders = this.graph.getNodes().stream().filter(n -> n.getBalance() <
        // 0).collect(Collectors.toSet());
    }

    @Override
    public void run() {
        // int debug = 0;
        boolean close = false;
        while (!checkAlgorithmEnd() && !close) {
            Node source = this.suppliers.pollLast();
            if (source != null) {
                // System.out.println("Iteration: " + debug + ", Source node: " +
                // source.getNodeId() + "=" +
                // source.getBalance());

                this.shortestPathAlgorithm.run(graph, source);
                Optional<Node> endNode = this.graph.getNodes().stream()
                        .filter(node -> node.getBalance() < 0 && node.getFlow() < Integer.MAX_VALUE)
                        .max(Comparator.comparingInt(Node::getFlow));

                // System.err.println(this.graph);
                // System.err.println("endNode? " + endNode.isEmpty());
                if (!endNode.isEmpty()) {
                    this.graph.getNodes().forEach(n -> {
                        n.updatePotential();
                    });
                    // System.out.println("Original Balance: " + source.getBalance());
                    updateResidualGraph(source, endNode.get());
                    // System.out.println("Updated Balance: " + source.getBalance());
                    if (source.getBalance() != 0)
                        this.suppliers.add(source);
                } else
                    close = true;
            } else
                close = true;
            // debug++;
        }
    }

    @Override
    public boolean checkAlgorithmEnd() {
        Set<Node> sourceNodes = this.graph.getNodes().stream().filter(node -> node.getBalance() > 0)
                .collect(Collectors.toSet());
        // System.out.println("Source node remaining? " + (sourceNodes.isEmpty() ? "no"
        // : "yes"));
        if (sourceNodes.isEmpty()) {
            this.conclusions = CORRECT;
            return true;
        }

        /* Check if anything was modified since last path */
        int sum = sourceNodes.stream().map(Node::getBalance).reduce(0, Integer::sum);
        // System.out.println("Has something been edited since last loop? " + sum + "=="
        // + lastSum + "? "
        // + (sum == lastSum ? "no" : "yes"));
        if (sum == lastSum) {
            this.conclusions = NO_SOLUTION;
            return true;
        }
        this.lastSum = sum;
        return false;
    }

    private void updateResidualGraph(final Node root, final Node endNode) {
        int _flow = Math.min(
                endNode.getFlow(),
                Math.min(root.getBalance(),
                        Math.abs(endNode.getBalance())));
        root.setBalance(root.getBalance() - _flow);
        endNode.setBalance(endNode.getBalance() + _flow);
        recursiveUpdate(endNode, _flow);
    }

    /**
     * Climbs the shortest path tree to the nearest supplier
     * 
     * @param n the current node
     */
    private void recursiveUpdate(final Node n, final int flow) {
        if (!n.getPredecessor().isEmpty()) {
            // System.err.println("Node: " + n + ", predecessor: " + n.getPredecessor());
            Edge e = n.getPredecessor()
                    .get()
                    .getEdges()
                    .stream()
                    .filter(ep -> ep.destination().getNodeId() == n.getNodeId())
                    .findFirst().get().value();
            e.setFlow(e.getFlow() - flow);
            Edge eRev = n.getEdges().stream()
                    .filter(ep -> ep.destination().getNodeId() == n.getPredecessor().get().getNodeId())
                    .findFirst().get().value();
            eRev.setFlow(eRev.getFlow() + flow);
            recursiveUpdate(n.getPredecessor().get(), flow);
        }
    }

    @Override
    public String toString() {
        return "--------- Final Graph -----------\n" + this.graph.toString();
    }

    @Override
    public String solutionToString() {
        StringBuilder solution = new StringBuilder();
        solution.append("Conclusions: " + this.conclusions + "\n");
        solution.append(this.toString() + "\n");
        if (this.conclusions != NO_SOLUTION) {
            this.graph.getNodes().forEach(n -> {
                // solution.append("From node " + n.getNodeId() + " to:\n");
                n.getEdges().forEach(ep -> {
                    if (ep.value().getCost() < 0 && ep.value().getFlow() > 0) {
                        solution.append("From node " + ep.destination().getNodeId()
                                + " to node:" + n.getNodeId() + " => Flow: " + ep.value().getFlow() + "\n");
                    }
                });
            });
        }
        return solution.toString();
    }
}
