package polynomial;

import java.util.ArrayList;
import java.util.List;

public class InterpolatingPolynomialLagrange extends Polynomial {
    private final List<double[]> points;

    public InterpolatingPolynomialLagrange(){
        super();
        points = new ArrayList<>();
    }

    public InterpolatingPolynomialLagrange(List<double[]> points){
        super();
        this.points = new ArrayList<>();
        for(double[] point : points) {
            if (point.length != 2) throw new IllegalArgumentException("Points must have exactly two coordinates");
            this.points.add(point.clone());
        }
        updatePolynomial();
    }

    public List<double[]> getPoints() {
        List<double[]> copy = new ArrayList<>();
        for (double[] point : points) {
            copy.add(point.clone());
        }
        return copy;
    }

    private Polynomial computeBasisPolynomial(int j) {
        Polynomial basisPoly = new Polynomial(1.0); // Инициализация полинома единицей
        double x_j = points.get(j)[0];

        for (int i = 0; i < points.size(); i++) {
            if (i == j) continue; // Пропустить текущую точку
            double x_i = points.get(i)[0];
            Polynomial term = new Polynomial(-x_i, 1.0); // (x - x_i)
            basisPoly = basisPoly.times(term); // Умножить на текущий член
            basisPoly = basisPoly.timesValue(1.0 / (x_j - x_i)); // Разделить на (x_j - x_i)
        }

        return basisPoly;
    }

    private void updatePolynomial() {
        Polynomial lagrangePoly = new Polynomial(0.0); // Инициализация нулевым полиномом

        for (int j = 0; j < points.size(); j++) {
            double y_j = points.get(j)[1];
            Polynomial basisPoly = computeBasisPolynomial(j); // Базисный полином L_j(x)
            lagrangePoly = lagrangePoly.plus(basisPoly.timesValue(y_j)); // Добавить y_j * L_j(x)
        }

        this.coefficients.clear();
        this.coefficients.addAll(lagrangePoly.getCoefficients()); // Обновить коэффициенты
    }

    public void addPoint(double x, double y) {
        points.add(new double[]{x, y});
        updatePolynomial();
    }

    public void deletePoint(double x, double y) {
        points.removeIf(point -> point[0] == x && point[1] == y);
        updatePolynomial();
    }
}
