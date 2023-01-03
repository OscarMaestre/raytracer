package io.github.oscarmaestre.trazadorayos;

public class Rayo {
    private final Vec3 origen;
    private final Vec3 direccion;

    public Rayo(Vec3 origen, Vec3 direccion) {
        this.origen = origen;
        this.direccion = direccion;
    }

    public Vec3 getOrigen() {
        return origen;
    }

    public Vec3 getDireccion() {
        return direccion;
    }
    
    public Vec3 puntoFinal(double t){
        Vec3 incremento = Vec3.multiplicarPorDouble(direccion, t);
        Vec3 resultado = Vec3.sumarVectores(origen, incremento);
        return resultado;
    }

    @Override
    public String toString() {
        return "Rayo{" + "origen=" + origen + ", direccion=" + direccion + '}';
    }
    
}
