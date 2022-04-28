package computation;

import io.IOManager;

public class NonLinearCalculator {
    IOManager ioManager;
    OneDimensionalMath oneDimensionalMath;
    MultiDimensionalMath multiDimensionalMath;
    public NonLinearCalculator(IOManager ioManager){
        this.ioManager = ioManager;
    }

    public void solveOneDimensionalEquation() {
        int equationNumber = this.getEquationNumber();
        double a = ioManager.getDoubleDigit("Введите границу a");
        double b = ioManager.getDoubleDigit("Введите границу b");
        double epsilon = ioManager.getDoubleDigit("Введите погрешность e");
        this.oneDimensionalMath = new OneDimensionalMath(equationNumber);

        ResultWrapper newtonResultWrapper = this.newton(a, b, epsilon, oneDimensionalMath);
        ResultWrapper simpleIterationsResultWrapper = this.simpleIterations(a, b, epsilon, oneDimensionalMath);

        if (newtonResultWrapper.isValid() && simpleIterationsResultWrapper.isValid()){
            ioManager.printTableHeaders(new String[] {"Метод Ньютона", "Метод Простых Итераций"});

            ioManager.printTableRow(new String[] {"Корень:", newtonResultWrapper.getResult()+"", simpleIterationsResultWrapper.getResult()+""});

            ioManager.printTableRow(new String[] {"ΔF:", newtonResultWrapper.getError()+"", simpleIterationsResultWrapper.getError()+""});

            ioManager.printTableRow(new String[] {"Число итераций:", newtonResultWrapper.getIteration_counter()+"", simpleIterationsResultWrapper.getIteration_counter()+""});
        }
    }

    public void solveMultiDimensionalEquation() {
        int systemNumber = this.getSystemNumber();
        double epsilon = ioManager.getDoubleDigit("Введите погрешность e");
        this.multiDimensionalMath = new MultiDimensionalMath(systemNumber);

        ResultWrapper newtonResultWrapper = this.newton(epsilon, multiDimensionalMath);

        if (newtonResultWrapper.isValid() ){
            ioManager.printTableHeaders(new String[] {"Метод Ньютона"});
            for (int i = 0; i < newtonResultWrapper.getRoots().length; i++){
                ioManager.printTableRow(new String[] {"X"+i+":", newtonResultWrapper.getRoots()[i]+""});
            }
            ioManager.printTableRow(new String[] {"Число итераций:", newtonResultWrapper.getIteration_counter()+""});
            //ioManager.printTableRow(new String[] {"F1 =", ""+multiDimensionalMath.f1(newtonResultWrapper.getRoots()[0],newtonResultWrapper.getRoots()[1])});
            //ioManager.printTableRow(new String[] {"F2 =", ""+multiDimensionalMath.f2(newtonResultWrapper.getRoots()[0],newtonResultWrapper.getRoots()[1])});
        }

    }

    private ResultWrapper simpleIterations(double a, double b, double epsilon, OneDimensionalMath oneDimensionalMath) {
        double x_prev = 0, x_cur = (a+b)/2;
        int iteration_counter = 0;
        if (oneDimensionalMath.dfi( x_cur ) >= 1) {
            ioManager.writeErrorMessage("Не выполнено условие сходимости метода простых итераций!");
            return new ResultWrapper();
        }
        do {
            x_prev = x_cur;
            iteration_counter++;
            x_cur = oneDimensionalMath.fi(x_prev);
        } while ( Math.abs(x_prev - x_cur) >= epsilon && iteration_counter<100);
        return new ResultWrapper(iteration_counter, x_cur, oneDimensionalMath.f(x_cur));
    }

    private ResultWrapper newton(double a, double b, double epsilon, OneDimensionalMath oneDimensionalMath) {
        double x_prev = 0, x_cur = 0;
        int iteration_counter = 0;
        if (oneDimensionalMath.f(a) * oneDimensionalMath.ddf(a)>0){
            x_cur = a;
        } else if (oneDimensionalMath.f(b) * oneDimensionalMath.ddf(b)>0) {
            x_cur = b;
        } else {
            ioManager.writeErrorMessage("Не выполнено условие сходимости метода Ньютона!");
            return new ResultWrapper();
        }
        do {
            x_prev = x_cur;
            iteration_counter++;
            if (oneDimensionalMath.df(x_prev) == 0) {
                ioManager.writeErrorMessage("Не выполнено условие сходимости метода Ньютона - производная равна 0!");
                return new ResultWrapper();
            }
            x_cur = x_prev - oneDimensionalMath.f(x_prev) / oneDimensionalMath.df(x_prev);
        } while ( Math.abs(x_prev - x_cur) >= epsilon  && iteration_counter<100);
        return new ResultWrapper(iteration_counter, x_cur, oneDimensionalMath.f(x_cur));
    }

    private ResultWrapper newton(double epsilon, MultiDimensionalMath multiDimensionalMath) {
        double[] x_prev = {0,0}, x_cur = {2,1.1}; // 2,1    1.5,-0.5
        int iteration_counter = 0;
        do {
            iteration_counter++;
            for (int i = 0 ; i < 2 ; i++) { x_prev[i] = x_cur[i]; }
            double jacobian = multiDimensionalMath.getJacobian(x_prev[0], x_prev[1]);
            ioManager.writeDebug("Итерация "+iteration_counter+": "+"X = "+x_prev[0]+"   Y = "+x_prev[1]);
            x_cur[0] = x_prev[0] - 0.1*(multiDimensionalMath.dyf2(x_prev[0],x_prev[1]) * multiDimensionalMath.f1(x_prev[0],x_prev[1])
                    -multiDimensionalMath.dyf1(x_prev[0],x_prev[1]) * multiDimensionalMath.f2(x_prev[0],x_prev[1])) / jacobian;
            x_cur[1] = x_prev[1] - 0.1*(-multiDimensionalMath.dxf2(x_prev[0],x_prev[1]) * multiDimensionalMath.f1(x_prev[0],x_prev[1])
                    -multiDimensionalMath.dxf1(x_prev[0],x_prev[1]) * multiDimensionalMath.f2(x_prev[0],x_prev[1])) / jacobian;

        } while ( (Math.abs(x_prev[0] - x_cur[0]) >= epsilon || Math.abs(x_prev[1] - x_cur[1]) >= epsilon) && iteration_counter<1000);

        return new ResultWrapper(iteration_counter, x_cur);
    }

    private int getEquationNumber() {
        int number = 0;
        int options = OneDimensionalMath.getStringRepresentation().length;

        StringBuilder requestTextBuilder = new StringBuilder("Выберите нелинейное уравнение с одной переменной:\n");
        for (int i = 0; i < options-1; i++) {
            requestTextBuilder.append("\t").append(i+1).append(") ").append(OneDimensionalMath.getStringRepresentation()[i]).append("\n");
        }
        requestTextBuilder.append("\t").append(options).append(") ").append(OneDimensionalMath.getStringRepresentation()[options-1]);
        ioManager.writeMessage(requestTextBuilder.toString());

        boolean valid = false;
        while (!valid) {
            number = ioManager.getIntDigit("Номер уравнения");
            if ((number >=1) && (number <= options)) {
                valid = true;
            } else {
                ioManager.writeErrorMessage("Уравнение с таким номером не найдено!");
            }
        }
        return number;
    }

    private int getSystemNumber() {
        int number = 0;
        int options = MultiDimensionalMath.getStringRepresentation().length/2;

        StringBuilder requestTextBuilder = new StringBuilder("Выберите систему нелинейных алгебраических уравнений:\n");
        for (int i = 0; i < options-1; i++) {
            requestTextBuilder.append("\t").append("Система №").append(i+1).append(":\n");
            requestTextBuilder.append("\t\t•").append(MultiDimensionalMath.getStringRepresentation()[2*i]).append("\n");
            requestTextBuilder.append("\t\t•").append(MultiDimensionalMath.getStringRepresentation()[2*i+1]).append("\n");
        }
        requestTextBuilder.append("\t").append("Система №").append(options).append(":\n");
        requestTextBuilder.append("\t\t•").append(MultiDimensionalMath.getStringRepresentation()[2*options-2]).append("\n");
        requestTextBuilder.append("\t\t•").append(MultiDimensionalMath.getStringRepresentation()[2*options-1]);
        ioManager.writeMessage(requestTextBuilder.toString());

        boolean valid = false;
        while (!valid) {
            number = ioManager.getIntDigit("Номер системы");
            if ((number >=1) && (number <= options)) {
                valid = true;
            } else {
                ioManager.writeErrorMessage("Уравнение с таким номером не найдено!");
            }
        }
        return number;
    }

}
