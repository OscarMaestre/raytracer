package io.github.oscarmaestre.raytracer;

public class Vector3D implements IVec3 {
    
    protected final double v1, v2, v3;
    public Vector3D(double d1, double d2, double d3) {
        v1=d1;
        v2=d2;
        v3=d3;
    }

    public  IVec3 productoCruzado(Vector3D v2){
        double d1=( this.getV2()*v2.getV3() -
                            this.getV3()*v2.getV2() );
        double d2=( this.getV3()*v2.getV1() -
                            this.getV1()*v2.getV3() );
        double d3=( this.getV1()*v2.getV2() -
                            this.getV2()*v2.getV1() );
        
        return new Vector3D(d1,d2,d3);
    }
    
    public Vector3D vectorUnitario(){
        double longitud = this.getLongitud();
        Vector3D resultado = (Vector3D) this.dividirPorEscalar(longitud);
        return resultado;
    }
    
    public double getLongitudAlCuadrado(){
        /* Se usa el cuadrado para evitar problemas con
        los negativos */
        double resultado;
        resultado=(v1*v1)+(v2*v2)+ (v3*v3);
        return resultado;
    }
    
    public double getLongitud(){
        double longitudCuadrado=this.getLongitudAlCuadrado();
        return Math.sqrt(longitudCuadrado);
    }
    
    public Vector3D getCopia(){
        return new Vector3D(v1,v2, v3);
    }
    
    public double getV1() {
        return this.v1;
    }

    public double getV2() {
        return this.v2;
    }
    public double getV3() {
        return this.v3;
    }
    
    public static Vector3D sumarVectores(Vector3D v1, Vector3D v2){
        double resultado1=v1.getV1()+v2.getV1();
        double resultado2=v1.getV2()+v2.getV2();
        double resultado3=v1.getV3()+v2.getV3();
        return new Vector3D(resultado1,resultado2,resultado3);
    }
    
    public static Vector3D sumarVectores(Vector3D v1, Vector3D v2, Vector3D v3){
        Vector3D sumaParcial = Vector3D.sumarVectores(v1, v2);
        Vector3D suma = Vector3D.sumarVectores(sumaParcial, v3);
        return suma;
    }
    
    public static Vector3D sumarVectores(Vector3D v1, Vector3D v2, Vector3D v3, Vector3D v4){
        Vector3D sumaParcial=Vector3D.sumarVectores(v1, v2, v3);
        Vector3D suma=Vector3D.sumarVectores(sumaParcial, v4);
        return suma;
    }
    
    public static Vector3D restarVectores(Vector3D v1, Vector3D v2){
        double resultado1=v1.getV1()-v2.getV1();
        double resultado2=v1.getV2()-v2.getV2();
        double resultado3=v1.getV3()-v2.getV3();
        return new Vector3D(resultado1,resultado2,resultado3);
    }
    /**
     * Resta al primer vector los otros dos
     * @param v1 Vector original
     * @param v2 Vector que se resta a v1
     * @param v3 Vector que se resta a v2
     * @return 
     */
    public static Vector3D restarVectores(Vector3D v1, Vector3D v2, Vector3D v3){
        Vector3D sumaVectoresParaRestar=Vector3D.sumarVectores(v2, v3);
        return Vector3D.restarVectores(v1, sumaVectoresParaRestar);
    }
    
    /**
     * Resta al primer vector los otros tres
     * @param v1 Vector original
     * @param v2 Vector que se resta al original
     * @param v3 Vector que se resta al original
     * @param v4 Vector que se resta al original
     * @return 
     */
    public static Vector3D restarVectores(Vector3D v1, Vector3D v2, Vector3D v3, Vector3D v4){
        Vector3D sumaVectoresParaRestar=Vector3D.sumarVectores(v2, v3, v4);
        return Vector3D.restarVectores(v1, sumaVectoresParaRestar);
    }
    
    public static Vector3D multiplicarVectores(Vector3D v1, Vector3D v2){
        double resultado1=v1.getV1()*v2.getV1();
        double resultado2=v1.getV2()*v2.getV2();
        double resultado3=v1.getV3()*v2.getV3();
        return new Vector3D(resultado1,resultado2,resultado3);
    }
    
    public static Vector3D multiplicarVectorPorEscalar(Vector3D v, double t){
//        System.out.println("Multiplicando vector:"+v.toString());
//        System.out.println("Multiplicando por:"+t);
        double resultado1=v.getV1()*t;
        double resultado2=v.getV2()*t;
        double resultado3=v.getV3()*t;
        Vector3D resultado=new Vector3D(resultado1,resultado2,resultado3);
//        System.out.println("Obtenemos: "+resultado.toString());
        return resultado;
    }
    
    public static Vector3D dividirVectorPorEscalar(Vector3D v, double t){
        Vector3D resultado;
        resultado=multiplicarVectorPorEscalar(v, 1/t);
        return resultado;
    }
    

    public static double productoEscalar(Vector3D v1, Vector3D v2){
        double resultado1=v1.getV1()*v2.getV1();
        double resultado2=v1.getV2()*v2.getV2();
        double resultado3=v1.getV3()*v2.getV3();
        double suma=resultado1+resultado2+resultado3;
        return suma;
    }
    
    public static Vector3D productoCruzado(Vector3D v1, Vector3D v2){
        double d1=( v1.getV2()*v2.getV3() -
                            v1.getV3()*v2.getV2() );
        double d2=( v1.getV3()*v2.getV1() -
                            v1.getV1()*v2.getV3() );
        double d3=( v1.getV1()*v2.getV2() -
                            v1.getV2()*v2.getV1() );
        
        return new Vector3D(d1,d2,d3);
    }
    
    public static Vector3D cambiarSigno(Vector3D vector){
        return Vector3D.multiplicarVectorPorEscalar(vector, -1.0);
    }
    public static boolean sonIguales(Vector3D v1, Vector3D v2){
        if (v1.getV1()!=v2.getV1()){
            return false;
        }
        if (v1.getV2()!=v2.getV2()){
            return false;
        }
        if (v1.getV3()!=v2.getV3()){
            return false;
        }
        /* Si llegamos aquí no se encontraron diferencias*/
        return true;
    }

    @Override
    public String toString() {
        double v1 = this.getV1();
        double v2 = this.getV2();
        double v3 = this.getV3();
        String str="<"+v1+","+v2+","+v3+">";
        return str;
    }

    @Override
    public IVec3 from(double d1, double d2, double d3) {
        return new Vector3D(d1, d2, d3);
    }
    
} // Fin de la clase vec3
