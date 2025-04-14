package ui;

import convert.Converter;

import java.awt.*;

public class CartesianPainter implements Painter {
    private Converter converter;

    public CartesianPainter(Converter converter){
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

    public void paint(Graphics g){
        int width = converter.getImageWidth();
        int height = converter.getImageHeight();
        int centerX = (int)converter.xCrt2Scr(0);
        int centerY = (int)converter.yCrt2Scr(0);

        g.drawLine(0, centerY, width, centerY); // Ось X
        g.drawLine(centerX, 0, centerX, height); // Ось Y

        drawAxes(g, centerX, width, centerY, height);
    }

    private void drawAxes(Graphics g, int centerX, int width, int centerY, int height) {
        double scaleX = converter.divisionDensityX();
        double scaleY = converter.divisionDensityY();

        // Малые деления (0.1 ед.)
        g.setColor(Color.RED);
        drawMinorTicks(g, centerX, width, centerY, scaleX / 10, true);  // Ось X
        drawMinorTicks(g, centerY, height, centerX, scaleY / 10, false); // Ось Y

        // Промежуточные деления (0.5 ед.)
        g.setColor(Color.BLUE);
        drawIntermediateTicks(g, centerX, width, centerY, scaleX, true);
        drawIntermediateTicks(g, centerY, height, centerX, scaleY, false);

        // Основные деления (1 ед.)
        g.setColor(Color.BLACK);
        drawMajorTicks(g, centerX, width, centerY, scaleX,true);
        drawMajorTicks(g, centerY, height, centerX, scaleY,false);
    }

    // Отрисовка малых делений (0.1)
    private void drawMinorTicks(Graphics g, int center, int max, int fixedPos, double step, boolean isXAxis) {
        for (double pos = center + step; pos < max; pos += step) {
            drawTick(g, (int)Math.round(pos), fixedPos, 2, isXAxis);
        }

        for (double pos = center - step; pos > 0; pos -= step) {
            drawTick(g, (int)Math.round(pos), fixedPos, 2, isXAxis);
        }
    }

    // Отрисовка промежуточных делений (0.5)
    private void drawIntermediateTicks(Graphics g, int center, int max, int fixedPos, double scale, boolean isXAxis) {
        double step = scale / 2;

        for (double pos = center + step; pos < max; pos += scale) {
            drawTick(g, (int)Math.round(pos), fixedPos, 4, isXAxis);
        }

        for (double pos = center - step; pos > 0; pos -= scale) {
            drawTick(g, (int)Math.round(pos), fixedPos, 4, isXAxis);
        }
    }

    // Отрисовка основных делений (1.0) для оси X
    private void drawMajorTicks(Graphics g, int center, int max, int fixedPos, double scale, boolean isAxis) {
        for (double pos = center + scale; pos < max; pos += scale) {
            int value = (int)Math.round((pos - center)/scale);
            if(!isAxis) value *= -1;
            drawTick(g, (int)Math.round(pos), fixedPos, 6, isAxis);
            drawTickLabel(g, (int)Math.round(pos), fixedPos, value, isAxis);
        }

        for (double pos = center - scale; pos > 0; pos -= scale) {
            int value = (int)Math.round((pos - center)/scale);
            if(!isAxis) value *= -1;
            drawTick(g, (int)Math.round(pos), fixedPos, 6, isAxis);
            drawTickLabel(g, (int)Math.round(pos), fixedPos, value, isAxis);
        }
    }

    private void drawTick(Graphics g, int pos, int fixedPos, int size, boolean isXAxis) {
        if (isXAxis) {
            g.drawLine(pos, fixedPos - size, pos, fixedPos + size);
        } else {
            g.drawLine(fixedPos - size, pos, fixedPos + size, pos);
        }
    }

    private void drawTickLabel(Graphics g, int pos, int fixedPos, int value, boolean isXAxis) {
        String str = String.valueOf(value);
        var m = g.getFontMetrics();
        var strRect = m.getStringBounds(str, g);
        if (isXAxis) {
            g.drawString(
                    str,
                    (int)(pos - strRect.getWidth() / 2),
                    (int)(fixedPos + 3 + strRect.getHeight())
            );
        } else {
            g.drawString(
                    str,
                    (int)(fixedPos + 2 + strRect.getWidth()),
                    (int)(pos + strRect.getWidth() / 2)
            );
        }
    }
}