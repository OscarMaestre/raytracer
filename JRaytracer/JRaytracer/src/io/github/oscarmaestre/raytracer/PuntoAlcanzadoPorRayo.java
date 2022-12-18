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
    private Punto3D punto;
    private Vec3    normal;
    private double  t;
    private boolean estaEnExterior;

    public PuntoAlcanzadoPorRayo(Punto3D punto, Vec3 normal, double t, boolean estaEnExterior) {
        this.punto = punto;
        this.normal = normal;
        this.t = t;
        this.estaEnExterior = estaEnExterior;
    }
    
}
