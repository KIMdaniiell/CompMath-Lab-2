package computation;

public class MultiDimensionalMath {
    private static final String[] stringRepresentation = {
            "sin (x+1) - y = 1.2",
            "2x + cos y = 2",
            "x + x * y^3 = 9",
            "x * y + x * y^2 = 6", // "sin y + 2x = 2", "y + cos (x-1) = 0.7",
    };
    private int equationSystemNumber;

    public MultiDimensionalMath(int equationSystemNumber) {
        this.equationSystemNumber = equationSystemNumber;
    }

    public double getJacobian(double x, double y) {
        switch (equationSystemNumber){
            case 1:
                return dxf11(x,y)*dyf12(x,y) - dxf12(x,y)*dyf11(x,y);
            case 2:
                return dxf21(x,y)*dyf22(x,y) - dxf22(x,y)*dyf21(x,y);
            default:
                return 0;
        }
    }
    public double f1(double x, double y) {
        switch (equationSystemNumber){
            case 1:
                return f11(x,y);
            case 2:
                return f21(x,y);
            default:
                return 0;
        }
    }
    public double f2(double x, double y) {
        switch (equationSystemNumber){
            case 1:
                return f12(x,y);
            case 2:
                return f22(x,y);
            default:
                return 0;
        }
    }
    public double dxf1(double x, double y) {
        switch (equationSystemNumber){
            case 1:
                return dxf11(x,y);
            case 2:
                return dxf21(x,y);
            default:
                return 0;
        }
    }
    public double dyf1(double x, double y) {
        switch (equationSystemNumber){
            case 1:
                return dyf11(x,y);
            case 2:
                return dyf21(x,y);
            default:
                return 0;
        }
    }
    public double dxf2(double x, double y) {
        switch (equationSystemNumber){
            case 1:
                return dxf12(x,y);
            case 2:
                return dxf22(x,y);
            default:
                return 0;
        }
    }
    public double dyf2(double x, double y) {
        switch (equationSystemNumber){
            case 1:
                return dyf12(x,y);
            case 2:
                return dyf22(x,y);
            default:
                return 0;
        }
    }

    private double f11(double x, double y) { return Math.sin(x+1) - y - 1.2;}
    private double f12(double x, double y) { return 2*x + Math.cos(y) -2;}
    private double f21(double x, double y) { return x + x*Math.pow(y,3) - 9;}     // Math.sin(y) + 2*x -2
    private double f22(double x, double y) { return x*y + x*Math.pow(y,2) - 6;}   // y + Math.cos(x-1) - 0.7

    private double dxf11(double x, double y) { return Math.cos(x+1);}
    private double dxf12(double x, double y) { return 2;}
    private double dxf21(double x, double y) { return 1 + Math.pow(y,3) ;}    // 2
    private double dxf22(double x, double y) { return y + Math.pow(y,2) ;}    // -Math.sin(x-1)

    private double dyf11(double x, double y) { return -1;}
    private double dyf12(double x, double y) { return -Math.sin(y);}
    private double dyf21(double x, double y) { return x*3*Math.pow(y,2);}   // Math.cos(y)
    private double dyf22(double x, double y) { return x + x*2*y;}           // 1

    public static String[] getStringRepresentation() {
        return stringRepresentation;
    }
}
