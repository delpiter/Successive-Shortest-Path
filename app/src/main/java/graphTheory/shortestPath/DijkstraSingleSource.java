package graphTheory.shortestPath;

import java.util.Comparator;
import java.util.PriorityQueue;

import graphTheory.graph.Graph;
import graphTheory.graph.Node;

/**
 * Generic Implementation of the Dijkstra algorithm
 */
public class DijkstraSingleSource implements ShortestPathAlgorithm {

    private PriorityQueue<Node> heap;

    public DijkstraSingleSource() {
        this.heap = new PriorityQueue<Node>(Comparator.comparingInt(Node::getDistance));
    }

    private void initializeSingleSource(final Graph g, final Node source) {
        heap.clear();
        g.getNodes().forEach(node -> {
            node.reset();
        });
        source.setDistance(0);
        heap.add(source);
    }

    @Override
    public void run(final Graph g, final Node source) {
        initializeSingleSource(g, source);
        // System.err.println("----------INITIALIZED DIJKSTRA-----------");
        // System.err.println(heap);
        while (!heap.isEmpty()) {
            Node u = heap.poll();
            // System.err.println("EXPLORING NODE: " + u.getNodeId());
            u.setExplored();
            // System.err.println("ADJACENT NODES: " + u.getEdges() + "\n-----CHECKING
            // ADJACENT NODES---------");

            u.getEdges().forEach(ep -> {
                // System.err.println(
                // "is node " + ep.destination() + " explored? " + ep
                // .destination().isExplored() + "\nflow = " + ep.value().getFlow());
                if (ep.value().getFlow() != 0 && !ep.destination().isExplored()) {
                    // System.err.println("RELAXING EDGE: {" + u.getNodeId() + ", " +
                    // ep.destination().getNodeId() + "}");
                    int potentialCost = u.getDistance()
                            + ep.value().getCost()
                            - u.getPotential()
                            + ep.destination().getPotential();

                    if (ep.destination().getDistance() > potentialCost) {
                        ep.destination().setDistance(potentialCost);
                        ep.destination().setPredecessor(u);
                        // System.err.println("Prev flow: " + ep.destination().getFlow());
                        ep.destination().updateFlow(ep.value().getFlow());
                        // System.err.println("Updated flow: " + ep.destination().getFlow());
                        if (!heap.contains(ep.destination())) {
                            heap.add(ep.destination());
                        }
                    }
                }
            });
            // System.err.println("Heap Log: is Empty? " + heap.isEmpty());
        }
    }
}
