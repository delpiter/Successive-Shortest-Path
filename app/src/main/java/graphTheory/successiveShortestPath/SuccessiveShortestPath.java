package graphTheory.successiveShortestPath;

public interface SuccessiveShortestPath {
    /**
     * Executes the algorithm
     */
    public void run();

    /**
     * Returns true if the algorithm is over
     * 
     * @return a boolean.
     */
    public boolean checkAlgorithmEnd();

    /**
     * Returns a string containing a readable format for the solution
     * 
     * @return
     */
    public String solutionToString();
}
