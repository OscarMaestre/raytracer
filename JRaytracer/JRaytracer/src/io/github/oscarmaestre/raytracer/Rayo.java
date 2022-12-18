/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.oscarmaestre.raytracer;

/**
 *
 * @author usuario
 */
public class Rayo {
    private Punto3D origen;
    private Vec3    direccion;

    public Rayo(Punto3D origen, Vec3 direccion) {
        this.origen = origen.getCopia();
        this.direccion = direccion.getCopia();
    }
    public Punto3D getPosicion(double t){
        Vec3 incremento=Vec3.multiplicarVectorPorEscalar(direccion, t);
        /* Pequeño hack: Vec3 y Punto3D son casi iguales, así que
        posFinal puede ser Vec3*/
        Vec3 posFinal = Vec3.sumarVectores(this.origen, incremento);
        /* Pero por supuesto, devolvemos el tipo correcto*/
        return Punto3D.fromVec3(posFinal);
    }

    public Punto3D getOrigen() {
        return origen;
    }

    public Vec3 getDireccion() {
        return direccion;
    }
    
    
}
