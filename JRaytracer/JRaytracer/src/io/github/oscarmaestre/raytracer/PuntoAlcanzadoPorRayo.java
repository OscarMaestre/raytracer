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
    private final Vector3D    normal;
    private final double  t;

    public PuntoAlcanzadoPorRayo(Punto3D punto, Vector3D normal, double t) {
        this.punto = punto;
        this.normal = normal;
        this.t = t;
    }

    public Punto3D getPunto() {
        return punto;
    }

    public Vector3D getNormal() {
        return normal;
    }

    public double getT() {
        return t;
    }

    
    public Vector3D vectorUnitarioNormal(){
        return this.normal.vectorUnitario();
    }    
    /**
     * Calcula si el alcance se da por el exterior de la esfera
     * o por el interior
     * @param rayo Rayo que alcanza la esfera
     * @param normal Vector normal a la esfera
     * @return true si el alcance se da en el exterior o false si
     * se da por dentro de la esfera */
    private boolean alcanceOcurreEnExterior(Rayo rayo, Vector3D normal){
        double productoEscalar = Vector3D.productoEscalar(rayo.getDireccion(), normal);
        if (productoEscalar>0.0){
            return false;
        }
        return true;
    }
    private Vector3D rectificarNormalSiProcede(Rayo rayo, Vector3D normal){
        if (this.alcanceOcurreEnExterior(rayo, normal)){
            return normal;
        } 
        normal=Vector3D.cambiarSigno(normal);
        return normal;
    }
    public static PuntoAlcanzadoPorRayo from (Esfera esfera, Rayo rayo, double t){
        Punto3D puntoEnEsfera = rayo.getPosicion(t);
        Vector3D vectorNormalEsfera=Vector3D.restarVectores(
                    puntoEnEsfera, esfera.getCentro());
        
        /* En principio asumimos que el punto está
        en el exterior de la esfera */
        PuntoAlcanzadoPorRayo puntoAlcance;
            puntoAlcance=new PuntoAlcanzadoPorRayo(puntoEnEsfera,
                    vectorNormalEsfera, t);
        /* Si es necesario, la normal se invierte*/
        puntoAlcance.rectificarNormalSiProcede(rayo, vectorNormalEsfera);
        return puntoAlcance;
    }
}
