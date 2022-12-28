/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.oscarmaestre.raytracer;

/**
 *
 * @author usuario
 */
public class Color extends Vec3 {

    public Color(double d1, double d2, double d3) {
        super(d1, d2, d3);
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
    
    public static Color from(Vec3 v){
        double d1 = v.getV1();
        double d2 = v.getV2();
        double d3 = v.getV3();
        Color c=new Color(d1, d2, d2);
        return new Color(d1, d2, d3);
    }
    
    public static Color sumar(Color c1, Color c2){
        Vec3 suma = Vec3.sumarVectores(c1, c2);
        double r=suma.getV1();
        double g=suma.getV2();
        double b=suma.getV3();
        if (r>1.0){
            r=1.0;
        }
        if (g>1.0){
            g=1.0;
        }
        if (b>1.0){
            b=1.0;
        }
        return new Color(r, g, b);       
    }
    
    public double limitarRango(
            double valor, double min_aceptado, double max_aceptado)
    {
        if (valor<min_aceptado) return min_aceptado;
        if (valor>max_aceptado) return max_aceptado;
        return valor;
    } //Fin de limitarRango
}
