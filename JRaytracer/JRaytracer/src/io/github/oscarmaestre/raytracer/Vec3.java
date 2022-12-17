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
    
    public void dividirPorEscalar(double t){
        this.multiplicarPorEscalar(1/t);
    }
    
    public void cambiarSigno(){
        this.valores[0]=-this.valores[0];
        this.valores[1]=-this.valores[1];
        this.valores[2]=-this.valores[2];
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
    
    public static Vec3 sumarVectores(Vec3 v1, Vec3 v2){
        double resultado1=v1.getV1()+v2.getV1();
        double resultado2=v1.getV2()+v2.getV2();
        double resultado3=v1.getV3()+v2.getV2();
        return new Vec3(resultado1,resultado2,resultado3);
    }
    public static Vec3 restarVectores(Vec3 v1, Vec3 v2){
        double resultado1=v1.getV1()-v2.getV1();
        double resultado2=v1.getV2()-v2.getV2();
        double resultado3=v1.getV3()-v2.getV2();
        return new Vec3(resultado1,resultado2,resultado3);
    }
    
    public static Vec3 multiplicarVectores(Vec3 v1, Vec3 v2){
        double resultado1=v1.getV1()*v2.getV1();
        double resultado2=v1.getV2()*v2.getV2();
        double resultado3=v1.getV3()*v2.getV2();
        return new Vec3(resultado1,resultado2,resultado3);
    }
    
    
} // Fin de la clase vec3
