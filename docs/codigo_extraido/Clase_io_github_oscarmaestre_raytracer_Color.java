/**
 *
 * @author usuario
 */
public class Color extends Vec3 {

    public Color(double d1, double d2, double d3) {
        super(d1, d2, d3);
    }

    public String toString() {
        /* En formato PPM hay que mostrar los valores entre 0 y 255*/
        int r = (int) (this.getV1() * 255.999);
        int g = (int) (this.getV2() * 255.999);
        int b = (int) (this.getV3() * 255.999);
        /* En formato PPM mostraremos los colores en formato
        R G B (con un espacio entre medias y en ese orden */
        String cadena = String.format("%d %d %d", r, g, b);
        return cadena;
    }

    public static Color from(Vec3 v) {
        double d1 = v.getV1();
        double d2 = v.getV2();
        double d3 = v.getV3();
        return new Color(d1, d2, d3);
    }
}
