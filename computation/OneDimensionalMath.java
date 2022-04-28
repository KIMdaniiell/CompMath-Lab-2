package computation;


import io.IOManager;

public class OneDimensionalMath {
    private static final String[] stringRepresentation = {
            "x − sin x = 0.25",
            "x^3 = e^x - 1",
            "lg x - 7/(2x + 6) = 0",
    };
    private int equationNumber;

    public OneDimensionalMath(int equationNumber){
        this.equationNumber = equationNumber;
    }

    public double f(double x) {
        switch (equationNumber) {
            case 1:
                return f1(x);
            case 2:
                return f2(x);
            case 3:
                return f3(x);
            default:
                return 0;
        }
    }
    public double df(double x) {
        switch (equationNumber) {
            case 1:
                return df1(x);
            case 2:
                return df2(x);
            case 3:
                return df3(x);
            default:
                return 0;
        }
    }
    public double ddf(double x) {
        switch (equationNumber) {
            case 1:
                return ddf1(x);
            case 2:
                return ddf2(x);
            case 3:
                return ddf3(x);
            default:
                return 0;
        }
    }
    public double fi(double x) {
        switch (equationNumber) {
            case 1:
                return fi1(x);
            case 2:
                return fi2(x);
            case 3:
                return fi3(x);
            default:
                return 0;
        }
    }
    public double dfi(double x) {
        switch (equationNumber) {
            case 1:
                return dfi1(x);
            case 2:
                return dfi2(x);
            case 3:
                return dfi3(x);
            default:
                return 0;
        }
    }

    private double fi1(double x) { return 0.25 + Math.sin(x);}
    private double fi2(double x) { return Math.pow( Math.exp(x) - 1 ,-3);}
    private double fi3(double x) { return Math.exp(7/(2*x + 6));}

    private double dfi1(double x) { return Math.cos(x);}
    private double dfi2(double x) { return -3*Math.exp(x)/Math.pow((Math.exp(x) - 1),4);}
    private double dfi3(double x) { return Math.exp(7/(2*x + 6)) * -14/Math.pow(2*x+6,2);}

    private double f1( double x ){ return  x - Math.sin(x) - 0.25;}
    private double f2( double x ){ return  Math.pow(x,3) - Math.exp(x) + 1;}
    private double f3( double x ){ return  Math.log(x) - 7/(2*x + 6);}

    private double df1( double x ){ return  1 - Math.cos(x);}
    private double df2( double x ){ return  3*Math.pow(x,2) - Math.exp(x);}
    private double df3( double x ){ return  Math.pow(x,-1) + 14/Math.pow(2*x +6,2);}

    private double ddf1( double x ){ return  Math.sin(x);}
    private double ddf2( double x ){ return  6*x - Math.exp(x);}
    private double ddf3( double x ){ return  -Math.pow(x,-2) -7/Math.pow(x+3,3);}

    public static String[] getStringRepresentation() {
        return stringRepresentation;
    }

}
