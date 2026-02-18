package graphTheory.graph;

public class EdgeImpl implements Edge {

    private int cost;
    private int flow;

    public EdgeImpl(final int c, final int f) {
        this.cost = c;
        this.flow = f;
    }

    @Override
    public void setCost(final int newCost) {
        this.cost = newCost;
    }

    @Override
    public void setFlow(final int newFlow) {
        this.flow = newFlow;
    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public int getFlow() {
        return this.flow;
    }

    @Override
    public String toString() {
        return "{" + cost + ", " + flow + "}";
    }
}
