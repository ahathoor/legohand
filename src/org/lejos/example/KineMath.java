package org.lejos.example;

public class KineMath {

    static double r2 = 11.85*0.8;
    static double r1 = 16;
    
    public static void main(String[] args) {
    	double k[] = etsikulmat(12,12);
    	System.out.println(k[0] + "   " + k[1]);

    	k = etsikulmat(0,r1+r2);
    	System.out.println(k[0] + "   " + k[1]);
    	
    	double x = r1+r2;
    	x = Math.sqrt(x*x/2);
    	k = etsikulmat(x,x);
    	System.out.println(k[0] + "   " + k[1]);
    	

    	k = etsikulmat(-r2,r1);
    	System.out.println(k[0] + "   " + k[1]);
    	

    	k = etsikulmat(-r2-1,r1+1);
    	System.out.println(k[0] + "   " + k[1]);
    }
    
    public static double[] etsikulmat(double x, double y) {

    	System.out.println("x : " + x + "   y : " + y);
        //kulma (x,y) (0,0)
        double min = Math.PI - Math.atan2(y, x);
        double max = min - 2*Math.PI;

        double search = 0;

        for (int i = 0; i < 70; i++) {
            if(Math.abs(min - max) == 0)
                break;
            search = (min + max) / 2;
            double k1 = Math.abs(distToOrigo(kärki(x, y, r1, (search + min) / 2)) - r2);
            double k2 = Math.abs(distToOrigo(kärki(x, y, r1, (search + max) / 2)) - r2);
            if (k1 < k2) {
                max = search;
            } else {
                min = search;
            }
        }
        double[] betweenPoint = kärki(x, y, r1, search);
//        System.out.println("+++" + distToOrigo(betweenPoint));
        double k1 = Math.atan2(betweenPoint[1], betweenPoint[0]);
//        k1 = cleanAngle(k1);
        
        double k2 = Math.atan2(y-betweenPoint[1], x-betweenPoint[0])-k1;
//        k2 = cleanAngle(k2);
//        System.out.println("Eka kulma= " + Math.toDegrees(cleanAngle(k1)));
//        System.out.println("Toka kulma= " + Math.toDegrees(cleanAngle(k2)));
    	return new double[] {Math.toDegrees(k1),Math.toDegrees(k2)};
    }
    
    public static double cleanAngle(double a) {
    	while (a<-Math.PI/4) a+= Math.PI*2;
    	return a;
    }

    public static double distToOrigo(double[] piste) {
        return Math.sqrt(piste[0] * piste[0] + piste[1] * piste[1]);
    }

    public static double[] kärki(double x, double y, double r, double angle) {
        double rx = x + Math.cos(angle) * r;
        double ry = y - Math.sin(angle) * r;
        return new double[]{rx, ry};
    }
}