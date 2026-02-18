package graphTheory.graph;

public interface Edge {
    /*
     * Updates the cost of the edge
     */
    void setCost(final int newCost);

    /*
     * Updates the flow of the edge
     */
    void setFlow(final int newFlow);

    /*
     * Getter for the cost of the edge
     */
    int getCost();

    /*
     * Getter for the flow of the edge
     */
    int getFlow();
}
