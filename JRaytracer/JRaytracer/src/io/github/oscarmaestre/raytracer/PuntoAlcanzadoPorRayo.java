/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.oscarmaestre.raytracer;

/**
 *
 * @author usuario
 */
public class PuntoAlcanzadoPorRayo {
    private final Punto3D punto;
    private final Vec3    normal;
    private final double  t;
    private final boolean estaEnExterior;

    public PuntoAlcanzadoPorRayo(Punto3D punto, Vec3 normal, double t, boolean estaEnExterior) {
        this.punto = punto;
        this.normal = normal;
        this.t = t;
        this.estaEnExterior = estaEnExterior;
    }

    public Punto3D getPunto() {
        return punto;
    }

    public Vec3 getNormal() {
        return normal;
    }

    public double getT() {
        return t;
    }

    public boolean isEstaEnExterior() {
        return estaEnExterior;
    }
    
    public Vec3 vectorUnitarioNormal(){
        return Vec3.vectorUnitario(this.normal);
    }    
    public static PuntoAlcanzadoPorRayo from (Esfera esfera, Rayo rayo, double t){
        Punto3D puntoEnEsfera = rayo.getPosicion(t);
        Vec3 vectorNormalEsfera=Vec3.restarVectores(
                    puntoEnEsfera, esfera.getCentro());
        PuntoAlcanzadoPorRayo puntoAlcance;
            puntoAlcance=new PuntoAlcanzadoPorRayo(puntoEnEsfera,
                    vectorNormalEsfera, t, true);
        return puntoAlcance;
    }
}
