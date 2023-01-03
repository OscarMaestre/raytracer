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
    private Vector3D    direccion;

    public Rayo(Punto3D origen, Vector3D direccion) {
        this.origen = origen.getCopia();
        this.direccion = direccion.getCopia();
    }
    public Punto3D getPosicion(double t){
        Vector3D incremento=Vector3D.multiplicarVectorPorEscalar(direccion, t);
        /* Pequeño hack: Vector3D y Punto3D son casi iguales, así que
        posFinal puede ser Vector3D*/
        Vector3D posFinal = Vector3D.sumarVectores(this.origen, incremento);
        /* Pero por supuesto, devolvemos el tipo correcto*/
        return Punto3D.fromVec3(posFinal);
    }

    public Punto3D getOrigen() {
        return origen;
    }

    public Vector3D getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return "Rayo{" + "origen=" + origen + ", direccion=" + direccion + '}';
    }
    
    
    
    
}
