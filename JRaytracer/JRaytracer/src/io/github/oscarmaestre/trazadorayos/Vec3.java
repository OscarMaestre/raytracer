package io.github.oscarmaestre.trazadorayos;

import io.github.oscarmaestre.raytracer.Vector3D;
import java.util.Random;

public class Vec3 {
    double d1, d2, d3;

    public Vec3(double d1, double d2, double d3) {
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }
    public Vec3(){
        this.d1 = 0.0;
        this.d2 = 0.0;
        this.d3 = 0.0;
    }
    public Vec3 getCopia(){
        d1 = this.getX();
        d2 = this.getY();
        d3 = this.getZ();
        return new Vec3(d1, d2, d3);
    }

    public double getX() {
        return d1;
    }

    public double getY() {
        return d2;
    }

    public double getZ() {
        return d3;
    }
    public void cambiarSigno(){
        this.d1=-this.d1;
        this.d2=-this.d2;
        this.d3=-this.d3;
    }
    public void sumar(Vec3 vector){
        this.d1+=vector.getX();
        this.d2+=vector.getY();
        this.d3+=vector.getZ();
    }
    public void restar(Vec3 vector){
        this.d1-=vector.getX();
        this.d2-=vector.getY();
        this.d3-=vector.getZ();
    }

    public void producto(double t){
        this.d1=this.d1*t;
        this.d2=this.d2*t;
        this.d3=this.d3*t;
    }
    public void dividir(double t){
        this.producto(1/t);
    }
    public double getLongitudCuadrado(){
        return (d1*d1)+(d2*d2)+(d3*d3);
    }
    public double getLongitud(){
        return Math.sqrt(getLongitudCuadrado());
    }
    
    public boolean esCasiCero(){
        double delta=1e-8;
        boolean xCasiCero=this.getX()<delta;
        boolean yCasiCero=this.getY()<delta;
        boolean zCasiCero=this.getZ()<delta;
        boolean todosCasiCero=xCasiCero && yCasiCero && zCasiCero;
        if (todosCasiCero){
            return true;
        } 
        return false;
    }
    /* Funciones de utilidad*/
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append(d1);
        sb.append(" ");
        sb.append(d2);
        sb.append(" ");
        sb.append(d3);
        return sb.toString();
    }
    public static Vec3 sumarVectores(Vec3 v1, Vec3 v2){
        double x = v1.getX()+v2.getX();
        double y = v1.getY()+v2.getY();
        double z = v1.getZ()+v2.getZ();
        return new Vec3(x, y, z);
    }
    
    public static Vec3 sumarVectores(Vec3 v1, Vec3 v2, Vec3 v3){
        double x = v1.getX()+v2.getX()+v3.getX();
        double y = v1.getY()+v2.getY()+v3.getY();
        double z = v1.getZ()+v2.getZ()+v3.getZ();
        return new Vec3(x, y, z);
    }
    
    public static Vec3 restarVectores(Vec3 v1, Vec3 v2){
        double x = v1.getX()-v2.getX();
        double y = v1.getY()-v2.getY();
        double z = v1.getZ()-v2.getZ();
        return new Vec3(x, y, z);
    }
    
    public static Vec3 multiplicarVectores(Vec3 v1, Vec3 v2){
        double x = v1.getX()*v2.getX();
        double y = v1.getY()*v2.getY();
        double z = v1.getZ()*v2.getZ();
        return new Vec3(x, y, z);
    }
    
    public static Vec3 multiplicarPorDouble(Vec3 v1, double t){
        double x = v1.getX()* t;
        double y = v1.getY()* t;
        double z = v1.getZ()* t;
        return new Vec3(x, y, z);
    }
    
    public static Vec3 dividirPorDouble(Vec3 v1, double t){
        return Vec3.multiplicarPorDouble(v1, 1/t);
    }
    
    public static double productoEscalar(Vec3 v1, Vec3 v2){
        double resultado1=v1.getX()*v2.getX();
        double resultado2=v1.getY()*v2.getY();
        double resultado3=v1.getZ()*v2.getZ();
        double suma=resultado1+resultado2+resultado3;
        return suma;
    }
    
    public static Vec3 productoCruzado(Vec3 v1, Vec3 v2){
        double trozo1 = v1.getY()*v2.getZ() - v1.getZ()*v2.getY();
        double trozo2 = v1.getZ()*v2.getX() - v1.getX()*v2.getZ();
        double trozo3 = v1.getX()*v2.getY() - v1.getY()*v1.getX();
        return new Vec3 (trozo1, trozo2, trozo3);
    }
    
    public static Vec3 vectorUnitario(Vec3 v){
        double longitudCuadrado = v.getLongitudCuadrado();
        return Vec3.dividirPorDouble(v, longitudCuadrado);
    }
    
    public static String getColorRGB(Vec3 color){
        int r = (int) (255.999*color.getX());
        int g = (int) (255.999*color.getY());
        int b = (int) (255.999*color.getZ());
        StringBuilder sb=new StringBuilder();
        sb.append(r).append(" ").append(g).append(" ").append(b);
        return sb.toString();
    }
    
    private static Double getDoubleAleatorio(Random generadorAzar){
        
        /* Esto genera entre 0 y 1.0*/
        double valor=generadorAzar.nextDouble();
        /* Ahora está entre 0 y 2*/
        valor= (double) valor*2.0;
        /* Y ahora entre -1 y 1*/
        valor=(double) valor-1.0;
        return valor;
    }
    
    public static Vec3 getPuntoEnEsferaUnidad(){ 
        Random generadorAzar=new Random();
        Vec3 punto;
        do {
            double x=getDoubleAleatorio(generadorAzar);
            double y=getDoubleAleatorio(generadorAzar);
            double z=getDoubleAleatorio(generadorAzar);
            punto=new Vec3(x, y, z);
        }while (punto.getLongitudCuadrado()>=1.0);
        return punto;
    }
    
    public static Vec3 getVectorUnitarioAleatorio(){
        Vec3 puntoAlAzar=Vec3.getPuntoEnEsferaUnidad();
        return Vec3.vectorUnitario(puntoAlAzar);
    }
    public static Vec3 getEsferaNormalSegunHemisferio(Vec3 normal){
        Vec3 puntoEnEsferaUnidad = Vec3.getPuntoEnEsferaUnidad();
        double productoEscalar;
        productoEscalar = Vec3.productoEscalar(puntoEnEsferaUnidad, normal);
        if (productoEscalar>0.0){
            return puntoEnEsferaUnidad;
        } else {
            puntoEnEsferaUnidad.cambiarSigno();  
        }   
        return puntoEnEsferaUnidad;
    }
    
    public static double limitar(double valor, double min, double max){
        if (valor<min){
            return min;
        } 
        if (valor>max){
            return max;
        }
        return valor;
    }
    public static Vec3 aplicarGamma(Vec3 color, int numMuestras){
        double r=color.getX();
        double g=color.getY();
        double b=color.getZ();
        
        r=Math.sqrt(r);
        g=Math.sqrt(g);
        b=Math.sqrt(b);
        r=limitar(r, 0, 0.999);
        g=limitar(g, 0, 0.999);
        b=limitar(b, 0, 0.999);
        return new Vec3(r, g, b);
    }
    public static Vec3 getReflejo(Vec3 v, Vec3 normal){
        double producto = Vec3.productoEscalar(v, normal);
        producto=(double)(producto*2.0);
        Vec3 aux = Vec3.multiplicarPorDouble(normal, producto);
        Vec3 reflejo=Vec3.restarVectores(v, aux);
        return reflejo;
    }
}
