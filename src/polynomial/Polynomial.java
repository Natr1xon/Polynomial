package polynomial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Polynomial {
    protected final List<Double> coefficients;

    public Polynomial() {
        coefficients = new ArrayList<>();
        coefficients.add(0.0);
    }

    public Polynomial(List<Double> coefficients) {
        this.coefficients = new ArrayList<>(coefficients);
        removeLeadingZeroes();
    }

    public Polynomial(Double... coefficients) {
        this.coefficients = new ArrayList<>(Arrays.asList(coefficients));
        removeLeadingZeroes();
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = new ArrayList<>();
        for (double coefficient : coefficients) {
            this.coefficients.add(coefficient);
        }
        removeLeadingZeroes();
    }

    public List<Double> getCoefficients(){
        return new ArrayList<>(coefficients);
    }

    @Override
    public String toString() {
        if (coefficients.size() == 1 && coefficients.getFirst() == 0.0) {
            return "0";
        }

        StringBuilder polynomialString = new StringBuilder();
        for (int i = coefficients.size() - 1; i >= 0; i--) {
            double coefficient = coefficients.get(i);
            if (coefficient == 0) continue;

            if (!polynomialString.isEmpty()) {
                polynomialString.append(coefficient > 0 ? " + " : " - ");
            } else if (coefficient < 0) {
                polynomialString.append("-");
            }

            if (!(Math.abs(coefficient) == 1 && i > 0)) {
                polynomialString.append(Math.abs(coefficient));
            }

            if (i > 0) {
                polynomialString.append("x");
                if (i > 1) {
                    polynomialString.append("^").append(i);
                }
            }
        }
        return polynomialString.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Polynomial that = (Polynomial) obj;
        return Objects.equals(this.coefficients, that.coefficients);
    }

    @Override
    public int hashCode(){
        return 31 * coefficients.hashCode();
    }

    private void removeLeadingZeroes() {
        int i = coefficients.size() - 1;
        while(i > 0 && coefficients.get(i) == 0){
            coefficients.remove(i);
            i--;
        }
    }

    public int degree(){
        return coefficients.size()-1;
    }

    public Polynomial plus(Polynomial other){
        int maxDegree = Math.max(this.degree(), other.degree());
        List<Double> polynomial = new ArrayList<>();

        for(int i = 0; i <= maxDegree; i++) {
            double coefficient1 = (i < this.coefficients.size() ? this.coefficients.get(i) : 0);
            double coefficient2 = (i < other.coefficients.size() ? other.coefficients.get(i) : 0);
            polynomial.add(coefficient1 + coefficient2);
        }
        return new Polynomial(polynomial);
    }

    public Polynomial minus(Polynomial other){
        int maxDegree = Math.max(this.degree(), other.degree());
        List<Double> polynomial = new ArrayList<>();

        for(int i = 0; i <= maxDegree; i++) {
            double coefficient1 = (i < this.coefficients.size() ? this.coefficients.get(i) : 0);
            double coefficient2 = (i < other.coefficients.size() ? other.coefficients.get(i) : 0);
            polynomial.add(coefficient1 - coefficient2);
        }
        return new Polynomial(polynomial);
    }

    public Polynomial times(Polynomial other){
        List<Double> polynomial = new ArrayList<>();

        int coopSize = this.coefficients.size() + other.coefficients.size() - 1;
        for(int i = 0; i < coopSize; i++)
            polynomial.add(0.0);

        for(int i = 0; i < this.coefficients.size(); i++){
            for (int j = 0; j< other.coefficients.size();j++){
                double product = this.coefficients.get(i) * other.coefficients.get(j);
                polynomial.set(i + j, polynomial.get(i + j) + product);
            }
        }
        return new Polynomial(polynomial);
    }

    public Polynomial timesValue(double value){
        List<Double> polynomial = new ArrayList<>();
        for (Double coefficient : coefficients) {
            polynomial.add(coefficient * value);
        }
        return new Polynomial(polynomial);
    }

    public Polynomial div(double value){
        List<Double> polynomial = new ArrayList<>();
        if(value == 0) throw new ArithmeticException("Division by zero");
        else{
            for (Double coefficient : coefficients) {
                polynomial.add(coefficient / value);
            }
        }
        return new Polynomial(polynomial);
    }

    public double calc(double x){
        double result = 0;
        for(int i = 0; i < coefficients.size(); i++){
            result += coefficients.get(i) * Math.pow(x,i);
        }
        return result;
    }

    public Polynomial derivative(){
        if (coefficients.size() <= 1) return new Polynomial();

        List<Double> polynomial = new ArrayList<>();
        for(int i = 1; i < coefficients.size(); i++){
            polynomial.add(coefficients.get(i) * i);
        }

        return new Polynomial(polynomial);
    }
}
