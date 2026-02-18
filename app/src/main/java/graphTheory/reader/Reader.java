package graphTheory.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import graphTheory.graph.Graph;
import graphTheory.graph.GraphImpl;

public class Reader {

    /*
     * Reads from the resource folder a with filename name:
     * The file MUST have the following format:
     * numNodes
     * sourceNodeId
     * destinationNodeId
     * sourceNode1-[destinationNode=(cost,flow),...]
     * ...
     * sourceNoden-[destinationNode=(cost,flow),...]
     */
    public static Graph readFromFile(final String filename) throws IOException {
        Graph graph = new GraphImpl();

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);

        if (inputStream == null) {
            throw new FileNotFoundException("Resource file not found: " + filename);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        try {
            int n = Integer.parseInt(br.readLine().trim());

            for (int i = 0; i < n; i++) {
                String node = br.readLine().trim();
                String[] nodeData = node.split(",");
                graph.addNode(Integer.parseInt(nodeData[0]), Integer.parseInt(nodeData[1]));
            }

            for (int i = 1; i <= n; i++) {
                parseEdgeLine(br.readLine().trim(), graph);
            }
        } catch (NumberFormatException e) {
            // TODO: handle exception
        }
        return graph;
    }

    private static void parseEdgeLine(String line, Graph graph) {
        // System.out.println(line);
        int dashIndex = line.indexOf('-');
        int node = Integer.parseInt(line.substring(0, dashIndex));

        int bracketStart = line.indexOf('[');
        int bracketEnd = line.indexOf(']');

        if (bracketStart == -1 || bracketEnd == -1) {
            return; // No edges
        }

        String edgesStr = line.substring(bracketStart + 1, bracketEnd);

        // System.out.println(edgesStr);

        if (edgesStr.isEmpty()) {
            return;
        }

        String[] edges = edgesStr.split(";");

        for (String edge : edges) {
            edge = edge.trim();

            int equalIndex = edge.indexOf('=');
            int dest = Integer.parseInt(edge.substring(0, equalIndex));

            String valuesStr = edge.substring(equalIndex + 2, edge.length() - 1);
            String[] values = valuesStr.split(",");
            int cost = Integer.parseInt(values[0]);
            int flow = Integer.parseInt(values[1]);

            graph.addEdge(node, dest, cost, flow);
        }
    }
}
