package io.github.oscarmaestre.raytracer;

public class Color implements IVec3 {
    public static final Color NEGRO     =new Color(0,0,0);
    public static final Color BLANCO    =new Color(255,255,255);
    
    public static final Color ROJO      =new Color(255,0,0);
    public static final Color VERDE     =new Color(0,255,0);
    public static final Color AZUL      =new Color(0,0,255);
    
    private double d1, d2, d3;

    public Color(double d1, double d2, double d3) {
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }
    
    
    
    public boolean esNegro(){
        boolean esNegro=this.sonIguales(this, Color.NEGRO);
        return esNegro;
    }
    public static Color getRojo(){
        return new Color(1.0, 0.0, 0.0);
    }
    public static Color getVerde(){
        return new Color(0.0, 1.0, 1.0);
    }
    
    public static Color getAzul(){
        return new Color(0.0, 0.0, 1.0);
    }
    
    public static Color getBlanco(){
        return new Color(1.0, 1.0, 1.0);
    }
    
    public static Color getNegro(){
        return new Color(0.0, 0.0, 0.0);
    }
    
    public String toString(){
        /* En formato PPM hay que mostrar los valores entre 0 y 255*/
        int r=(int) (this.getV1()*255.999);
        int g=(int) (this.getV2()*255.999);
        int b=(int) (this.getV3()*255.999);
        /* En formato PPM mostraremos los colores en formato
        R G B (con un espacio entre medias y en ese orden */
        String cadena=String.format("%d %d %d", r, g, b);
        return cadena;
    }
    
    public String toStringWithDouble(){
        String cadena=String.format("%f %f %f", 
                this.getV1(), this.getV2(), 
                this.getV3());
        return cadena;
    }
    
    public static Color from(IVec3 v){
        double d1 = v.getV1();
        double d2 = v.getV2();
        double d3 = v.getV3();
        Color c=new Color(d1, d2, d2);
        return new Color(d1, d2, d3);
    }
    
    public double limitarRango(
            double valor, double min_aceptado, double max_aceptado)
    {
        if (valor<min_aceptado) return min_aceptado;
        if (valor>max_aceptado) return max_aceptado;
        return valor;
    } //Fin de limitarRango

    @Override
    public IVec3 from(double d1, double d2, double d3) {
        return new Color(d1, d2, d3);
    }

    @Override
    public double getV1() {
        return this.d1;
    }

    @Override
    public double getV2() {
        return this.d2;
    }

    @Override
    public double getV3() {
        return this.d3;
    }
}
