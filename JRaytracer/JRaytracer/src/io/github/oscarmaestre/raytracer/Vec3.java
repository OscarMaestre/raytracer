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
    
    public Vec3 getCopia(){
        Vec3 copia;
        double v1 = this.getV1();
        double v2 = this.getV2();
        double v3 = this.getV3();
        return new Vec3(v1,v2, v3);
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
        double resultado3=v1.getV3()+v2.getV3();
        return new Vec3(resultado1,resultado2,resultado3);
    }
    
    public static Vec3 sumarVectores(Vec3 v1, Vec3 v2, Vec3 v3){
        Vec3 sumaParcial = Vec3.sumarVectores(v1, v2);
        Vec3 suma = Vec3.sumarVectores(sumaParcial, v3);
        return suma;
    }
    
    public static Vec3 sumarVectores(Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4){
        Vec3 sumaParcial=Vec3.sumarVectores(v1, v2, v3);
        Vec3 suma=Vec3.sumarVectores(sumaParcial, v4);
        return suma;
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
    
    public static Vec3 multiplicarVectorPorEscalar(Vec3 v, double t){
        double resultado1=v.getV1()*t;
        double resultado2=v.getV2()*t;
        double resultado3=v.getV3()*t;
        return new Vec3(resultado1,resultado2,resultado3);
    }
    
    public static Vec3 dividirVectorPorEscalar(Vec3 v, double t){
        Vec3 resultado;
        resultado=multiplicarVectorPorEscalar(v, 1/t);
        return resultado;
    }
    
    public static double productoEscalar(Vec3 v1, Vec3 v2){
        double resultado1=v1.getV1()*v2.getV1();
        double resultado2=v1.getV2()*v2.getV2();
        double resultado3=v1.getV3()*v2.getV2();
        double suma=resultado1+resultado3+resultado3;
        return suma;
    }
    
    public static Vec3 productoCruzado(Vec3 v1, Vec3 v2){
        double d1=( v1.getV2()*v2.getV3() -
                            v1.getV3()*v2.getV2() );
        double d2=( v1.getV3()*v2.getV1() -
                            v1.getV1()*v2.getV3() );
        double d3=( v1.getV1()*v2.getV2() -
                            v1.getV2()*v2.getV1() );
        
        return new Vec3(d1,d2,d3);
    }
    
    public static Vec3 vectorUnitario(Vec3 v){
        double longitud = v.getLongitud();
        Vec3 resultado = dividirVectorPorEscalar(v, longitud);
        return resultado;
    }

    @Override
    public String toString() {
        double v1 = this.getV1();
        double v2 = this.getV2();
        double v3 = this.getV3();
        String str="("+v1+","+v2+","+v3+")";
        return str;
    }
    
} // Fin de la clase vec3
