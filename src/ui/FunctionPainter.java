package ui;

import convert.Converter;
import polynomial.InterpolatingPolynomialNewton;
import polynomial.Polynomial;

import java.awt.*;

public class FunctionPainter implements Painter{
    private Converter converter;
    private InterpolatingPolynomialNewton polynomialNewton = new InterpolatingPolynomialNewton();

    public FunctionPainter(Converter converter){
        this.converter = converter;
    }

    public Dimension getSize(){
        return new Dimension(converter.getImageWidth(),converter.getImageHeight());
    }

    public void setSize(Dimension d){
        converter.setImageWidth(d.width);
        converter.setImageHeight(d.height);
    }

    public void setSize(int width, int height){
        converter.setImageWidth(width);
        converter.setImageHeight(height);
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    public boolean addPointToPolynomial(int x, int y){
        double x1 = converter.xScr2Crt(x);
        double y1 = converter.yScr2Crt(y);
        if(checkDot(x1)) {
            polynomialNewton.addPoint(x1, y1);
            return true;
        }
        else return false;
    }

    private boolean checkDot(double x){
        double minDiff = 0.3;

        for(var polynomial : polynomialNewton.getPoints()){
            if(Math.abs(x - polynomial[0]) < minDiff) return false;
        }
        return true;
    }

    public void clearGraph(){
        polynomialNewton = new InterpolatingPolynomialNewton();
    }

    public void deleteNearestPoint(int screenX, int screenY) {
        double targetX = converter.xScr2Crt(screenX);
        double targetY = converter.yScr2Crt(screenY);
        final double PIXEL_TOLERANCE = 10.0 * Math.max(
                converter.xScr2Crt(1) - converter.xScr2Crt(0),
                converter.yScr2Crt(0) - converter.yScr2Crt(1)
        );

        double[] nearestPoint = null;
        double minDistance = Double.MAX_VALUE;

        for (double[] point : polynomialNewton.getPoints()) {
            double dx = point[0] - targetX;
            double dy = point[1] - targetY;
            double distance = dx*dx + dy*dy; // Квадрат расстояния

            if (distance < minDistance && distance < PIXEL_TOLERANCE*PIXEL_TOLERANCE) {
                minDistance = distance;
                nearestPoint = point;
            }
        }

        if (nearestPoint != null) {
            polynomialNewton.deletePoint(nearestPoint[0], nearestPoint[1]);
        }
    }

    public void paintDot(Graphics g, Color color){
        g.setColor(color);
        int sz = 8;
        for(var polynomial : polynomialNewton.getPoints()){
            int x1 = (int)converter.xCrt2Scr(polynomial[0]);
            int y1 = (int)converter.yCrt2Scr(polynomialNewton.calc(polynomial[0]));
            g.fillOval(x1 - sz/2,y1 - sz/2,sz,sz);
        }
    }

    public void paintGraph(Graphics g, Color color){
        Graphics2D g2b = (Graphics2D)g;
        g2b.setStroke(new BasicStroke(2));

        g.setColor(color);

        int prevX = -1, prevY = -1;
        for (int x = 0; x < converter.getImageWidth(); x++) {
            double y = polynomialNewton.calc(converter.xScr2Crt(x));
            int scrY = (int)converter.yCrt2Scr(y);

            if (prevX != -1 && !polynomialNewton.getPoints().isEmpty()) {
                g.drawLine(prevX, prevY, x, scrY);
            }
            prevX = x;
            prevY = scrY;
        }
    }

    public void paintDerivativeGraph(Graphics g, Color color){
        Polynomial derivativePolynomial = polynomialNewton.derivative();

        Graphics2D g2b = (Graphics2D)g;
        g2b.setStroke(new BasicStroke(2));

        g.setColor(color);
        int prevX = -1, prevY = -1;
        for (int x = 0; x < converter.getImageWidth(); x++) {
            double y = derivativePolynomial.calc(converter.xScr2Crt(x));
            int scrY = (int)converter.yCrt2Scr(y);

            if (prevX != -1 && polynomialNewton.getPoints().size() >= 2) {
                g.drawLine(prevX, prevY, x, scrY);
            }
            prevX = x;
            prevY = scrY;
        }

    }

    public void paint(Graphics g) {

    }
}
