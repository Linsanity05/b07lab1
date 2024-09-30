import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.math.BigDecimal;
import java.util.Scanner;

public class Polynomial{
    double [] coeff;
    int [] expo;

    public Polynomial(){
        coeff = null;
        expo = null;
    }

    public Polynomial(double[] coeff, int[] expo){
        this.coeff = coeff;
        this.expo = expo;
    }

    public Polynomial(File input){
        try{
            Scanner sc = new Scanner(input);
            String poly = sc.nextLine();
            String[] terms = poly.split("(?=-)|(?=\\+)");
            
            expo = new int[terms.length];
            coeff = new double[terms.length];
            String[] vals = new String[2];

            for(int i=0; i<terms.length; i++){
                if(terms[i].contains("x")){
                    vals = terms[i].split("x");
                    coeff[i] = Double.parseDouble(vals[0]);
                    expo[i] = Integer.parseInt(vals[1]);
                }
                else{
                    expo[i] = 0;
                    coeff[i] = Integer.parseInt(terms[i]);
                }
            }
        }   
        catch (FileNotFoundException e){
            System.out.println("Error Occured");
        }
    }

    public Polynomial add(Polynomial poly){
        HashMap<Integer, Double> exponent_to_coefficient = new HashMap<Integer, Double>();
        
        Polynomial p = new Polynomial(this.coeff, this.expo);
        add_to_hash(exponent_to_coefficient, p);
        add_to_hash(exponent_to_coefficient, poly);

        remove_zeros(exponent_to_coefficient);

        Polynomial result = convert(exponent_to_coefficient);
        
        return result;
    }

    public Polynomial multiply(Polynomial poly){
        HashMap<Integer, Double> exponent_to_coefficient = new HashMap<Integer, Double>();

        for(int i=0; i<expo.length; i++){
            for(int j=0; j<poly.expo.length; j++){
                int power = expo[i] + poly.expo[j];
                
                BigDecimal co1 = BigDecimal.valueOf(coeff[i]);
                BigDecimal co2 = BigDecimal.valueOf(poly.coeff[j]);
                BigDecimal prod = co1.multiply(co2);

                double product = prod.doubleValue();

                if(exponent_to_coefficient.containsKey(power)){
                    BigDecimal test = BigDecimal.valueOf(exponent_to_coefficient.get(power));
                    BigDecimal res = test.add(prod);
                    exponent_to_coefficient.put(power, res.doubleValue());
                }
                else{
                    exponent_to_coefficient.put(power, product);
                }
            }
        }

        remove_zeros(exponent_to_coefficient);
        
        Polynomial result = convert(exponent_to_coefficient);

        return result;
    }

    public double evaluate(double val){
        double result = 0;
        for(int i=0; i<coeff.length; i++){
            result += coeff[i] * Math.pow(val, expo[i]);
        }   
        return result;
    }

    public boolean hasRoot(double val){
        return (evaluate(val) == 0);
    }

    public void saveToFile(String name){
        try{
            File output = new File(name + ".txt");
            output.createNewFile();
            FileWriter fw = new FileWriter(output);

            for(int i=0; i<expo.length; i++){
                if(expo[i] == 0){
                    fw.write(Double.toString(coeff[i]));
                }
                else{
                    if(coeff[i] > 0 && i != 0){
                        fw.write("+");
                    }
                    fw.write(Double.toString(coeff[i]));
                    fw.write("x");
                    fw.write(Integer.toString(expo[i]));
                }
            }
            fw.close();

        }
        catch(IOException e){
            System.out.println("Error Occured");
        }
        
    }

    //Helpers
    private void add_to_hash(HashMap<Integer, Double> map, Polynomial poly){
        for(int i=0; i<poly.expo.length; i++){
            if(map.containsKey(poly.expo[i])){
                map.put(poly.expo[i], map.get(poly.expo[i]) + poly.coeff[i]);
            }
            else{
                map.put(poly.expo[i], poly.coeff[i]);
            }
        }
    }

    private void remove_zeros(HashMap<Integer, Double> map){
        Set<Integer> ints = new HashSet<Integer>(map.keySet());
        for(Integer i:ints){
            if(map.get(i) == 0.0){
                map.remove(i);
            }
        }
    }

    private Polynomial convert(HashMap<Integer, Double> map){
        int[] exponents = new int[map.size()];
        double[] coefficients = new double[map.size()];
        int counter = 0;

        for(Integer i:map.keySet()){
            exponents[counter] = i;
            coefficients[counter] = map.get(i);
            counter++;
        }

        Polynomial result = new Polynomial(coefficients, exponents);
        
        return result;
    }
}