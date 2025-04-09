package convert;

public class Converter {
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;

    private int imageWidth;
    private int imageHeight;

    public Converter(double xMin, double xMax, double yMin, double yMax){
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    public Converter(double xMin, double xMax, double yMin, double yMax,
                     int imageWidth, int imageHeight){
        this(xMin, xMax, yMin, yMax);
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public double divisionDensityX(){
        return imageWidth / (xMax - xMin);
    }

    public double divisionDensityY(){
        return imageHeight / (yMax - yMin);
    }

    public double xScr2Crt(double xScr){
        return xMin + (xScr / imageWidth) * (xMax - xMin);
    }

    public double xCrt2Scr(double xCrt){
        return ((xCrt - xMin) / (xMax - xMin)) * imageWidth;
    }

    public double yScr2Crt(double yScr){ return yMax - (yScr / imageHeight) * (yMax - yMin); }

    public double yCrt2Scr(double yCrt){
        return ((yMax - yCrt) / (yMax - yMin)) * imageHeight;
    }
}
