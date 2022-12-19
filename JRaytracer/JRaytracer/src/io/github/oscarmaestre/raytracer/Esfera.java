/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.oscarmaestre.raytracer;

/**
 *
 * @author usuario
 */
public class Esfera extends ObjetoRenderizable {
    private Punto3D centro;
    private double  radio;

    public Esfera(Punto3D centro, double radio) {
        this.centro = centro;
        this.radio = radio;
    }

    public Punto3D getCentro() {
        return centro;
    }

    public double getRadio() {
        return radio;
    }
    
    public PuntoAlcanzadoPorRayo esAlcanzadaPorRayo(Rayo rayo){
        /* Recordemos que en la esfera queremos resolver la
        ecuación t^2*B*B + 2tB(A-C) + (A-C)+(A-C) - R^2 = 0 
        y que la solución es (b^2 - (4ac) / 2a )*/
        
        PuntoAlcanzadoPorRayo puntoAlcance=null;
        
        Vec3 a_menos_c;
        a_menos_c=Vec3.restarVectores(
                rayo.getOrigen(), this.centro);
        
        double a=Vec3.productoEscalar(
                rayo.getDireccion(), rayo.getDireccion());
        double b=2.0 * (Vec3.productoEscalar(
                a_menos_c, rayo.getDireccion()));
        double c=Vec3.productoEscalar(a_menos_c, a_menos_c) - 
                (this.radio * this.radio);
        double discriminante = (double) (b*b)-(4.0*a*c);
        if (discriminante>0){
            puntoAlcance=new PuntoAlcanzadoPorRayo(centro, a_menos_c, 1.0, true);
        } 
        return puntoAlcance;
    }
}
