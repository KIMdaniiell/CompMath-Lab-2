import computation.NonLinearCalculator;
import io.IOManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IOManager console = new IOManager(new Scanner(System.in));
        NonLinearCalculator nonLinearCalculator = new NonLinearCalculator(console);
        nonLinearCalculator.solveOneDimensionalEquation();
        //nonLinearCalculator.solveMultiDimensionalEquation();
    }
}
