import org.junit.jupiter.api.Test;
import polynomial.Polynomial;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
class PolynomialTest {

    @Test
    void testPolynomialCreation() {
        Polynomial p1 = new Polynomial(3.0, 2.0, 1.0); // x^2 + 2x + 3
        assertEquals(List.of(3.0, 2.0, 1.0), p1.getCoefficients(), "Создание из списка коэффициентов");

        Polynomial p2 = new Polynomial(List.of(4.0, 0.0, -2.0)); // -2x^2 + 4
        assertEquals(List.of(4.0, 0.0, -2.0), p2.getCoefficients(), "Создание из списка List");

        Polynomial p3 = new Polynomial(new double[]{1.0, -2.0, 3.0}); // 3x^2 - 2x + 1
        assertEquals(List.of(1.0, -2.0, 3.0), p3.getCoefficients(), "Создание из массива");

        Polynomial p4 = new Polynomial();
        assertEquals(List.of(0.0), p4.getCoefficients(), "Создание пустой полином");
    }

    // Проверка метода degree()
    @Test
    void testDegree() {
        Polynomial p1 = new Polynomial(3.0, 2.0, 1.0); // x^2 + 2x + 3
        assertEquals(2, p1.degree(), "Степень полинома p1");

        Polynomial p2 = new Polynomial(0.0, 0.0, 0.0); // Нулевой полином
        assertEquals(0, p2.degree(), "Степень нулевого полинома");
    }

    // Проверка арифметических операций
    @Test
    void testArithmeticOperations() {
        Polynomial p1 = new Polynomial(3.0, 2.0, 1.0); // x^2 + 2x + 3
        Polynomial p2 = new Polynomial(5.0, -1.0);  // -x + 5

        assertEquals(List.of(8.0, 1.0, 1.0), p1.plus(p2).getCoefficients(), "Сложение");
        assertEquals(List.of(-2.0, 3.0, 1.0), p1.minus(p2).getCoefficients(), "Вычитание");
        assertEquals(List.of(15.0, 7.0, 3.0, -1.0), p1.times(p2).getCoefficients(), "Умножение");

        Polynomial p3 = new Polynomial(5.0, -2.0, 1.0); // x^2 + 2x + 3
        Polynomial p4 = new Polynomial(5.0, -1.0);

        assertEquals(List.of(10.0, -3.0, 1.0), p4.plus(p3).getCoefficients(), "Сложение");
        assertEquals(List.of(0.0, 1.0, -1.0), p4.minus(p3).getCoefficients(), "Вычитание");
    }

    // Проверка умножения и деления на число
    @Test
    void testScalarMultiplicationAndDivision() {
        Polynomial p1 = new Polynomial(3.0, 2.0, 1.0); // x^2 + 2x + 3
        assertEquals(List.of(6.0, 4.0, 2.0), p1.timesValue(2).getCoefficients(), "Умножение на 2");
        assertEquals(List.of(1.5, 1.0, 0.5), p1.div(2).getCoefficients(), "Деление на 2");

        Exception exception = assertThrows(ArithmeticException.class, () -> p1.div(0));
        assertEquals("Division by zero", exception.getMessage(), "Деление на ноль должно вызывать исключение");
    }

    // Проверка вычисления значения полинома
    @Test
    void testPolynomialEvaluation() {
        Polynomial p1 = new Polynomial(3.0, 2.0, 1.0); // x^2 + 2x + 3
        assertEquals(11.0, p1.calc(2), "Значение полинома в x=2");
    }

    // Проверка метода equals() и hashCode()
    @Test
    void testEqualityAndHashCode() {
        Polynomial p1 = new Polynomial(3.0, 2.0, 1.0);
        Polynomial p2 = new Polynomial(3.0, 2.0, 1.0);
        Polynomial p3 = new Polynomial(1.0, 2.0, 3.0);

        Objects obj = null;
        assertEquals(p1, p2, "Равные полиномы должны быть равны");
        assertEquals(p1, p1,"Ссылки одинаковы");
        assertFalse(p1.equals(obj));
        assertFalse(p1.equals("124"));
        assertNotEquals(p1, p3, "Разные полиномы не должны быть равны");
        assertEquals(p1.hashCode(), p2.hashCode(), "Хеш-коды равных полиномов должны совпадать");
    }

    // Проверка toString()
    @Test
    void testToString() {
        Polynomial p1 = new Polynomial(3.0, 2.0, 1.0);
        assertEquals("x^2 + 2.0x + 3.0", p1.toString(), "Корректное представление полинома");

        Polynomial p2 = new Polynomial(-3.0, 0.0, 2.0, -1.0);
        assertEquals("-x^3 + 2.0x^2 - 3.0", p2.toString(), "Корректное представление полинома");

        Polynomial p3 = new Polynomial(0.0);
        assertEquals("0", p3.toString(), "Нулевой полином");

        Polynomial p4 = new Polynomial(1.0);
        assertEquals("1.0", p4.toString());
    }
}