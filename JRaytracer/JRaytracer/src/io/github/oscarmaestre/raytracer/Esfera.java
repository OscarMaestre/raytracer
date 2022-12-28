/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.oscarmaestre.raytracer;

/**
 *
 * @author usuario
 */
public class Esfera extends ObjetoRenderizable {
    private Punto3D centro;
    private double  radio;

    public Esfera(Punto3D centro, double radio) {
        this.centro = centro;
        this.radio = radio;
    }

    public Punto3D getCentro() {
        return centro;
    }

    public double getRadio() {
        return radio;
    }
    
    public double calcularSolucionConSignoPositivo(
            double a, double b, double discriminante)
    {
        double solucion=(-b + discriminante) / 2.0*a;
        return solucion;
    }
    
    public double calcularSolucionConSignoNegativo(
            double a, double b, double discriminante)
    {
        double solucion=(-b - discriminante) / 2.0*a;
        return solucion;
    }
    
    public boolean solucionEntraEnRango(double solucion, 
            double t_min, double t_max){
        if ( (solucion>=t_min) && (solucion<=t_max) ){
                return true;
            }
        return false;
    }
    
    public PuntoAlcanzadoPorRayo 
            calcularMejorSolucionRayoAlcanzaEsfera
            (Rayo rayo, double a, double b, 
                double discriminante, double t_min, double t_max)
    {
        PuntoAlcanzadoPorRayo puntoAlcance=null;
        //Empezamos intentando calcular la solucion
        //con signo negativo
        double solucionConNegativo=
                this.calcularSolucionConSignoNegativo(
                    a, b, discriminante);
        if (solucionEntraEnRango(solucionConNegativo, t_min, t_max)){
            double t=solucionConNegativo;
            puntoAlcance=PuntoAlcanzadoPorRayo.from(this, rayo, t);
            return puntoAlcance;
        }
        //Y si no probamos con el positivo
        double solucionConPositivo=
                this.calcularSolucionConSignoPositivo(
                    a, b, discriminante);
        if (solucionEntraEnRango(solucionConPositivo, t_min, t_max)){
            double t=solucionConPositivo;
            puntoAlcance=PuntoAlcanzadoPorRayo.from(this, rayo, t);
            return puntoAlcance;
        }
        //Si llegamos aquí se devolverá null
        return puntoAlcance;
    } //Fin del método calcularMejorSolucion
            
    public PuntoAlcanzadoPorRayo esAlcanzadaPorRayo(
            Rayo rayo, double t_minimo, double t_maximo){
        /* Recordemos que en la esfera queremos resolver la
        ecuación t^2*B*B + 2tB(A-C) + (A-C)+(A-C) - R^2 = 0 
        y que la solución es (b^2 - (4ac) / 2a )*/
        
        PuntoAlcanzadoPorRayo puntoAlcance=null;
        
        Vec3 a_menos_c;
        a_menos_c=Vec3.restarVectores(
                rayo.getOrigen(), this.centro);
        
        double a=Vec3.productoEscalar(
                rayo.getDireccion(), rayo.getDireccion());
        double b=2.0 * (Vec3.productoEscalar(
                a_menos_c, rayo.getDireccion()));
        double c=Vec3.productoEscalar(a_menos_c, a_menos_c) - 
                (this.radio * this.radio);
        double discriminante = (double) (b*b)-(4.0*a*c);
        if (discriminante>0){
            double t=(-b-Math.sqrt(discriminante) ) / (2.0*a);
            Punto3D puntoEsfera = rayo.getPosicion(t);
            Vec3 vectorNormalEsfera=Vec3.restarVectores(
                    puntoEsfera, this.centro);
            
            puntoAlcance=new PuntoAlcanzadoPorRayo(puntoEsfera,
                    vectorNormalEsfera, t, true);
        } 
        return puntoAlcance;
    }
    
    
    /* Colorea la superficie de la esfera según el vector normal perpendicular al
    punto donde la esfera es alcanzada por un rayo */
    public Color colorRayoSegunNormalPerpendicular(Rayo rayo, 
            PuntoAlcanzadoPorRayo puntoEsfera){
        double t = puntoEsfera.getT();
        Punto3D posicion = rayo.getPosicion(t);
        Vec3 vectorUnitarioNormal = puntoEsfera.vectorUnitarioNormal();
        double r = (1+ vectorUnitarioNormal.getV1() )  * 0.5;
        double g = (1+ vectorUnitarioNormal.getV2() )  * 0.5;
        double b = (1+ vectorUnitarioNormal.getV3() )  * 0.5;
        return new Color (r, g, b);
    }
}
