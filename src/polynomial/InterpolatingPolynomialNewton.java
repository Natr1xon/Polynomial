package polynomial;

import java.util.ArrayList;
import java.util.List;

public class InterpolatingPolynomialNewton extends Polynomial {
    private static final double EPSILON = 1e-10;
    private final List<double[]> points;

    public InterpolatingPolynomialNewton() {
        super();
        points = new ArrayList<>();
    }

    public InterpolatingPolynomialNewton(List<double[]> points) {
        super();
        this.points = new ArrayList<>();
        for (double[] point : points) {
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

    private double[][] computeDividedDifferences() {
        int size = points.size();
        double[][] table = new double[size][size];

        for (int i = 0; i < size; i++) {
            table[i][0] = points.get(i)[1];
        }

        for (int i = 1; i < size; i++) {
            for (int j = 0; j < size - i; j++) {
                table[j][i] = (table[j + 1][i - 1] - table[j][i - 1]) / (points.get(j + i)[0] - points.get(j)[0]);
            }
        }
        return table;
    }

    private void updatePolynomial() {
        double[][] divDiffs = computeDividedDifferences();
        Polynomial newtonPoly = new Polynomial(divDiffs[0][0]);

        Polynomial term = new Polynomial(1.0); // (x - x0)(x - x1)...

        for (int i = 1; i < points.size(); i++) {
            Polynomial factor = new Polynomial(-points.get(i - 1)[0], 1.0); // (x - x_i)
            term = term.times(factor); // (x - x_i)
            newtonPoly = newtonPoly.plus(term.timesValue(divDiffs[0][i])); // умножение на разделенну разность и плюсование
        }

        this.coefficients.clear();
        this.coefficients.addAll(newtonPoly.getCoefficients());
    }

    public void addPoint(double x, double y) {
        points.add(new double[]{x, y});
        updatePolynomial();
    }

    public void deletePoint(double x, double y) {
        points.removeIf(point ->
                Math.abs(point[0] - x) < EPSILON && Math.abs(point[1] - y) < EPSILON);
        updatePolynomial();
    }
}
