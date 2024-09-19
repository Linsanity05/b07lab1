public class Polynomial{
    double [] coeff;

    public Polynomial(){
        coeff = new double[1];
    }

    public Polynomial(double[] arr){
        coeff = new double[arr.length];
        for(int i=0; i<arr.length; i++){
            coeff[i] = arr[i];
        }
    }

    public Polynomial add(Polynomial poly){
        int x = Math.max(coeff.length, poly.coeff.length);
        double [] array = new double[x];
        Polynomial result = new Polynomial(array);

        for(int i=0; i<x; i++){
            if(i >= coeff.length){
                result.coeff[i] = poly.coeff[i];
            }
            else if(i >= poly.coeff.length){
                result.coeff[i] = coeff[i];
            }
            else{
                result.coeff[i] = coeff[i] + poly.coeff[i];
            }
        }

        return result;  
    }

    public double evaluate(double val){
        double result = 0;
        for(int i=0; i<coeff.length; i++){
            result += coeff[i] * Math.pow(val, i);
        }   
        return result;
    }

    public boolean hasRoot(double val){
        return (evaluate(val) == 0);
    }
}