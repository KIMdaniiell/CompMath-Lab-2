package computation;

public class ResultWrapper {
    private int iteration_counter;
    private double result;
    private double error;
    private double[] roots;
    private boolean isValid;

    public ResultWrapper() {
        this.isValid = false;
    }

    public ResultWrapper(int iteration_counter, double result, double error) {
        this.iteration_counter = iteration_counter;
        this.result = result;
        this.error = error;
        this.isValid = true;
    }

    public ResultWrapper(int iteration_counter, double[] roots) {
        this.iteration_counter = iteration_counter;
        this.roots = roots;
        this.isValid = true;
    }

    public int getIteration_counter() {
        return iteration_counter;
    }

    public double getResult() {
        return result;
    }

    public double getError() {
        return error;
    }

    public double[] getRoots() { return roots; }

    public boolean isValid() {
        return isValid;
    }

}
