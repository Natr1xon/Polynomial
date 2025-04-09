import org.junit.jupiter.api.Test;
import polynomial.InterpolatingPolynomialLagrange;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InterpolatingPolynomialLagrangeTest {

    @Test
    void testDefaultConstructor() {
        InterpolatingPolynomialLagrange poly = new InterpolatingPolynomialLagrange();
        assertTrue(poly.getPoints().isEmpty(), "Конструктор по умолчанию должен инициализировать пустой список точек");
        assertEquals(1, poly.getCoefficients().size(), "Полином по умолчанию должен быть 0");
        assertEquals(0.0, poly.getCoefficients().get(0), "Полином по умолчанию должен быть 0");
    }

    @Test
    void testConstructorWithPoints() {
        List<double[]> points = new ArrayList<>();
        points.add(new double[]{1.0, 2.0});
        points.add(new double[]{2.0, 3.0});
        points.add(new double[]{3.0, 5.0});

        InterpolatingPolynomialLagrange poly = new InterpolatingPolynomialLagrange(points);
        assertEquals(3, poly.getPoints().size(), "Конструктор должен инициализировать переданные точки");
        assertEquals(3, poly.getCoefficients().size(), "Полином должен иметь 3 коэффициента для 3 точек");
    }

    @Test
    void testAddPoint() {
        InterpolatingPolynomialLagrange poly = new InterpolatingPolynomialLagrange();
        poly.addPoint(1.0, 2.0);
        poly.addPoint(2.0, 3.0);

        assertEquals(2, poly.getPoints().size(), "Добавление точек должно обновлять список точек");
        assertEquals(2, poly.getCoefficients().size(), "Полином должен иметь 2 коэффициента для 2 точек");
    }

    @Test
    void testDeletePoint() {
        List<double[]> points = new ArrayList<>();
        points.add(new double[]{1.0, 2.0});
        points.add(new double[]{2.0, 3.0});
        points.add(new double[]{3.0, 5.0});

        InterpolatingPolynomialLagrange poly = new InterpolatingPolynomialLagrange(points);
        poly.deletePoint(2.0, 3.0);

        assertEquals(2, poly.getPoints().size(), "Удаление точки должно удалять её из списка");
        assertEquals(2, poly.getCoefficients().size(), "Полином должен иметь 2 коэффициента после удаления");
    }

    @Test
    void testPolynomialCalculation() {
        List<double[]> points = new ArrayList<>();
        points.add(new double[]{1.0, 2.0});
        points.add(new double[]{2.0, 3.0});
        points.add(new double[]{3.0, 5.0});

        InterpolatingPolynomialLagrange poly = new InterpolatingPolynomialLagrange(points);

        // Ожидаемый полином: P(x) = 2 + (x - 1) * 1 + (x - 1)(x - 2) * 0.5
        // Упрощённо: P(x) = 0.5x^2 - 0.5x + 2
        List<Double> expectedCoefficients = List.of(2.0, -0.5, 0.5);
        assertEquals(expectedCoefficients, poly.getCoefficients(), "Коэффициенты полинома должны соответствовать ожидаемым значениям");
    }

    @Test
    void testIllegalArgumentException() {
        List<double[]> invalidPoints = new ArrayList<>();
        invalidPoints.add(new double[]{1.0}); // Некорректная точка (только одна координата)

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new InterpolatingPolynomialLagrange(invalidPoints));
        assertEquals("Points must have exactly two coordinates", exception.getMessage(), "Конструктор должен выбрасывать IllegalArgumentException для некорректных точек");
    }
}