import convert.Converter;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        Converter convert = new Converter(-5,5,-5,5,800,600);

        System.out.println(convert.xCrt2Scr(0));
        System.out.println(convert.yCrt2Scr(0));
        System.out.println(convert.xScr2Crt(400));
        System.out.println(convert.yScr2Crt(300));

    }
}