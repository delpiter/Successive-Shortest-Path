package graphTheory.successiveShortestPath;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import graphTheory.graph.Graph;
import graphTheory.graph.Node;
import graphTheory.shortestPath.DijkstraMultipleSources;
import graphTheory.shortestPath.ShortestPathAlgorithm;

public class SSPMultipleSource implements SuccessiveShortestPath {

    private final Graph graph;
    private final ShortestPathAlgorithm shortestPathAlgorithm;
    // private int flow;
    private int lastSum = Integer.MAX_VALUE;

    public SSPMultipleSource(final Graph g) {
        this.graph = g;
        this.shortestPathAlgorithm = new DijkstraMultipleSources();
        // this.flow = 0;
    }

    @Override
    public void run() {
        while (checkAlgorithmEnd()) {
            shortestPathAlgorithm.run(graph, null);
            Set<Node> endNodes = this.graph.getNodes().stream()
                    .filter(node -> node.getBalance() < 0)
                    .collect(Collectors.toSet());
            endNodes.forEach(node -> {
                updateResidualGraph(node);
            });
        }
    }

    @Override
    public boolean checkAlgorithmEnd() {
        Set<Node> sourceNodes = this.graph.getNodes().stream().filter(node -> node.getBalance() > 0)
                .collect(Collectors.toSet());
        if (sourceNodes.isEmpty()) {
            return true;
        }

        /* Check if anything was modified since last path */
        int sum = sourceNodes.stream().map(Node::getBalance).reduce(0, Integer::sum);
        if (sum == lastSum) {
            return true;
        }
        this.lastSum = sum;
        return false;
    }

    private void updateResidualGraph(final Node endNode) {
        Optional<Node> oRoot = recursivePathClimb(endNode);
        if (oRoot.isEmpty()) {
            throw new NoSuchElementException("There is no path to the receiver");
        }
        int _flow = Math.min(endNode.getFlow(), Math.min(oRoot.get().getBalance(), Math.abs(endNode.getBalance())));
        oRoot.get().setBalance(oRoot.get().getBalance() - _flow);

    }

    /**
     * Climbs the shortest path tree to the nearest supplier
     * 
     * @param n the current node
     * @return Null if the root of the tree is not a supplier else the supplier
     */
    private Optional<Node> recursivePathClimb(final Node n) {
        if (n.getPredecessor().isEmpty()) {
            if (n.getBalance() > 0)
                return Optional.of(n);
            else
                return Optional.empty();
        }
        return recursivePathClimb(n);
    }

    @Override
    public String solutionToString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'solutionToString'");
    }
}
