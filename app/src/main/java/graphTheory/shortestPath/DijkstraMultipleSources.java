package graphTheory.shortestPath;

import java.util.Comparator;
import java.util.PriorityQueue;

import graphTheory.graph.Graph;
import graphTheory.graph.Node;

/**
 * Specific implementation for the SSP algorithm.
 * Set all the nodes with positive balance as sources
 */
public class DijkstraMultipleSources implements ShortestPathAlgorithm {

    private PriorityQueue<Node> heap;

    public DijkstraMultipleSources() {
        this.heap = new PriorityQueue<>(Comparator.comparingInt(Node::getDistance));
        // heap =
        // MinMaxPriorityQueue.orderedBy(Comparator.comparingInt(Node::getDistance)).create();
    }

    private void initializeMultipleSources(final Graph g) {
        heap.clear();
        g.getNodes().forEach(node -> {
            node.reset();
            if (node.getBalance() > 0) {
                node.setDistance(0);
                heap.add(node);
            }
        });
    }

    @Override
    public void run(final Graph g, final Node source) {
        initializeMultipleSources(g);
        while (!heap.isEmpty()) {
            Node u = heap.poll();
            u.setExplored();
            u.getEdges().forEach(ep -> {
                if (ep.value().getFlow() > 0) {
                    if (!ep.destination().isExplored()) {
                        int newDistance = u.getDistance() + ep.value().getCost();
                        // TODO: update to use the potential instead of the direct cost
                        if (ep.destination().getDistance() > newDistance) {
                            ep.destination().setDistance(newDistance);
                            ep.destination().setPredecessor(u);
                            ep.destination().updateFlow(ep.value().getCost());
                            // at the destination node, the flow will be the minimum
                            if (!heap.contains(ep.destination())) {
                                heap.add(ep.destination());
                            }
                        }
                    }
                }
            });
        }
    }
}
