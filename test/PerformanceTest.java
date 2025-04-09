import org.junit.jupiter.api.Test;
import polynomial.InterpolatingPolynomialLagrange;
import polynomial.InterpolatingPolynomialNewton;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PerformanceTest {
    private static final int NUM_POINTS = 1000; // Количество точек для интерполяции

    private List<double[]> generateTestPoints() {
        List<double[]> points = new ArrayList<>();
        for (int i = 0; i < PerformanceTest.NUM_POINTS; i++) {
            double y = Math.sin(i); // Пример функции
            points.add(new double[]{(double) i, y});
        }
        return points;
    }

    @Test
    void testLagrangePolynomialBuildTime() {
        List<double[]> points = generateTestPoints();

        long startTime = System.nanoTime();
        InterpolatingPolynomialLagrange lagrange = new InterpolatingPolynomialLagrange(points);
        long endTime = System.nanoTime();

        System.out.println("Время построения полинома Лагранжа: " + (endTime - startTime) / 1e6 + " мс");
        assertFalse(lagrange.getPoints().isEmpty(), "Полином Лагранжа должен быть построен");
    }

    @Test
    void testNewtonPolynomialBuildTime() {
        List<double[]> points = generateTestPoints();

        long startTime = System.nanoTime();
        InterpolatingPolynomialNewton newton = new InterpolatingPolynomialNewton(points);
        long endTime = System.nanoTime();

        System.out.println("Время построения полинома Ньютона: " + (endTime - startTime) / 1e6 + " мс");
        assertFalse(newton.getPoints().isEmpty(), "Полином Ньютона должен быть построен");
    }

    @Test
    void testLagrangePolynomialAddPointTime() {
        List<double[]> points = generateTestPoints();
        InterpolatingPolynomialLagrange lagrange = new InterpolatingPolynomialLagrange(points);

        long startTime = System.nanoTime();
        lagrange.addPoint(NUM_POINTS + 1, Math.sin(NUM_POINTS + 1));
        long endTime = System.nanoTime();

        System.out.println("Время добавления точки в полином Лагранжа: " + (endTime - startTime) / 1e6 + " мс");
        assertEquals(NUM_POINTS + 1, lagrange.getPoints().size(), "Точка должна быть добавлена в полином Лагранжа");
    }

    @Test
    void testNewtonPolynomialAddPointTime() {
        List<double[]> points = generateTestPoints();
        InterpolatingPolynomialNewton newton = new InterpolatingPolynomialNewton(points);

        long startTime = System.nanoTime();
        newton.addPoint(NUM_POINTS + 1, Math.sin(NUM_POINTS + 1));
        long endTime = System.nanoTime();

        System.out.println("Время добавления точки в полином Ньютона: " + (endTime - startTime) / 1e6 + " мс");
        assertEquals(NUM_POINTS + 1, newton.getPoints().size(), "Точка должна быть добавлена в полином Ньютона");
    }
}