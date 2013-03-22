/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package käsimath;

import java.util.Arrays;

/**
 *
 * @author ahathoor
 */
public class Käsimath {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double r1 = 16;
        double r2 = 15;

        double x = 7;
        double y = 17;

        //kulma (x,y) (0,0)
        double min = Math.PI - Math.atan2(y, x);
        System.out.println("minikulma " + Math.toDegrees(min));
        double max = min - 2*Math.PI;
        System.out.println("maksikulma " + Math.toDegrees(max));

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
        
        System.out.println(Math.toDegrees(search));
        double[] k = kärki(x, y, r1, search);
        System.out.println(Arrays.toString(k));
        System.out.println(distToOrigo(k));
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
