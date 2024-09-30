import java.io.File;

public class Driver {
    public static void main(String [] args) {
        double [] c1 = {1.0,3.2};
        int [] c2 = {0,1};
        double [] c3 = {5.3,-3.2,1.5};
        int [] c4 = {2,1,0};
            
        Polynomial p = new Polynomial(c1,c2);
        Polynomial p1 = new Polynomial(c3,c4);
        Polynomial p2 = p.add(p1);
        
        for(int i=0; i<p2.expo.length; i++){
            System.out.println("Exponent:" + p2.expo[i] + "   Coefficient: " + p2.coeff[i]);
        }

        Polynomial p3 = p.multiply(p1);

        for(int i=0; i<p3.expo.length; i++){
            System.out.println("Exponent:" + p3.expo[i] + "   Coefficient: " + p3.coeff[i]);
        }

        File input = new File("test.txt");
        System.out.println(input.exists());
        Polynomial p4 = new Polynomial(input);

        for(int i=0; i<p4.expo.length; i++){
            System.out.println("Exponent:" + p4.expo[i] + "   Coefficient: " + p4.coeff[i]);
        }

        p3.saveToFile("output");

        System.out.println(p1.evaluate(3));
        System.out.println(p1.hasRoot(3));
    }
}