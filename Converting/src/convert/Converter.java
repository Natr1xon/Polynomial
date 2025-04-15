package convert;

public class Converter {
    private final double xMin;
    private final double xMax;
    private final double yMin;
    private final double yMax;

    private int imageWidth;
    private int imageHeight;

    public void setImageHeight(int imageHeight) { this.imageHeight = imageHeight; }

    public void setImageWidth(int imageWidth) { this.imageWidth = imageWidth; }

    public int getImageWidth() { return imageWidth; }

    public int getImageHeight() { return imageHeight; }

    public Converter(double xMin, double xMax, double yMin, double yMax,
                     int imageWidth, int imageHeight){
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public Converter(double xMin, double xMax, double yMin, double yMax){
        this(xMin,xMax,yMin,yMax,1,1);
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
