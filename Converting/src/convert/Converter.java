package convert;

public class Converter {
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;

    private int imageWidth;
    private int imageHeight;

    public void setIntervalX(double xMin, double xMax){
        this.xMin = xMin;
        this.xMax = xMax;
    }

    public void setIntervalY(double yMin, double yMax){
        this.yMin = yMin;
        this.yMax = yMax;
    }

    public double getYMin() {
        return yMin;
    }

    public double getXMin() {
        return xMin;
    }

    public double getXMax() {
        return xMax;
    }

    public double getYMax() {
        return yMax;
    }

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

    public Converter(Converter converter){
        setIntervalX(converter.getXMin(),converter.getXMax());
        setIntervalY(converter.getYMin(), converter.getYMax());
        setImageWidth(converter.getImageWidth());
        setImageHeight(converter.getImageHeight());
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
