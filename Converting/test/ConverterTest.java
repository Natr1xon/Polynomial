import convert.Converter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {
    @Test
    void testConverterCreation() {
        Converter convert1 = new Converter(-5, 5, -5, 5);
        Converter convert2 = new Converter(-5, 5, -5, 5, 800, 600);

        assertNotNull(convert1, "Object should not be null");
        assertNotNull(convert2, "Object should not be null");
    }

    @Test
    void testCoordinateTransformation() {
        Converter converter = new Converter(-5, 5, -5, 5, 800, 600);

        // Test center point
        assertEquals(0.0, converter.xScr2Crt(400));
        assertEquals(0.0, converter.yScr2Crt(300));
        assertEquals(400.0, converter.xCrt2Scr(0));
        assertEquals(300.0, converter.yCrt2Scr(0));

        // Test edges
        assertEquals(-5.0, converter.xScr2Crt(0));
        assertEquals(5.0, converter.xScr2Crt(800));
        assertEquals(5.0, converter.yScr2Crt(0));
        assertEquals(-5.0, converter.yScr2Crt(600));

        assertEquals(0.0, converter.xCrt2Scr(-5));
        assertEquals(800.0, converter.xCrt2Scr(5));
        assertEquals(0.0, converter.yCrt2Scr(5));
        assertEquals(600.0, converter.yCrt2Scr(-5));

        // Test random points
        assertEquals(2.5, converter.xScr2Crt(600));
        assertEquals(-2.5, converter.yScr2Crt(450));
        assertEquals(640.0, converter.xCrt2Scr(3));
        assertEquals(150.0, converter.yCrt2Scr(2.5));
    }
}