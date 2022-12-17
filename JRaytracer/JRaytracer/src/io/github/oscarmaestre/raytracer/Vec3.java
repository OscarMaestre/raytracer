package io.github.oscarmaestre.raytracer;

/**
 *
 * @author usuario
 */
public class Vec3 {
    
    double valores[];
    public Vec3(double d1, double d2, double d3) {
        this.valores=new double[3];
        this.valores[0]=d1;
        this.valores[1]=d2;
        this.valores[2]=d3;
    }

    public void sumarVector(Vec3 vector){
        this.valores[0]+=vector.getV1();
        this.valores[1]+=vector.getV2();
        this.valores[2]+=vector.getV3();
    }

    public void multiplicarPorEscalar(double t){
        this.valores[0]*=t;
        this.valores[1]*=t;
        this.valores[2]*=t;        
    }
    
    public double getLongitudAlCuadrado(){
        /* Se usa el cuadrado para evitar problemas con
        los negativos */
        double resultado;
        resultado=(this.valores[0]*this.valores[0])+
                (this.valores[1]*this.valores[1])+
                (this.valores[2]*this.valores[2]);
        return resultado;
    }
    
    public double getLongitud(){
        double longitudCuadrado=this.getLongitudAlCuadrado();
        return Math.sqrt(longitudCuadrado);
    }
    
    
    public double getV1() {
        return this.valores[0];
    }

    public double getV2() {
        return this.valores[1];
    }
    public double getV3() {
        return this.valores[2];
    }
    
} // Fin de la clase vec3
