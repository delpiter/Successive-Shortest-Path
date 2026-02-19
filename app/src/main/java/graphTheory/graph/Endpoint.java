package graphTheory.graph;

public record Endpoint(Node destination, Edge value) {
    @Override
    public final String toString() {
        return "\nDestination=>" + this.destination() + "=>" + this.value();
    }
}