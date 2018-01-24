package com.dell.sped;
/**
 * Created by Dell on 21.01.2018.
 */

public class GPSDist    {
    double x1;
    double y1;
    double x2;
    double y2;

    public GPSDist(double x1, double y1, double x2, double y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    public Double getDist(){
        double wynik1 = Math.sqrt(Math.pow((x2-x1), 2)+Math.pow(Math.cos((x1*Math.PI)/180) * (y2-y1), 2));
        double wynik2 = wynik1 * (40075.704/360);
        return round(wynik2, 2);
    }

    public static double round(double value, int places){
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
