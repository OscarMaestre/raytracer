package io.github.oscarmaestre.raytracer;

public class Esfera extends ObjetoRenderizable {
    private final Punto3D centro;
    private final double  radio;

    public Esfera(Punto3D centro, double radio) {
        this.centro = centro.getCopia();
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
            
    @Override
    public PuntoAlcanzadoPorRayo esAlcanzadaPorRayo(
            Rayo rayo, double t_minimo, double t_maximo){
        /* Recordemos que en la esfera queremos resolver la
        ecuación t^2*B*B + 2tB(A-C) + (A-C)+(A-C) - R^2 = 0 
        y que la solución es (b^2 - (4ac) / 2a )*/
        
        PuntoAlcanzadoPorRayo puntoAlcance=null;
        
        Vector3D a_menos_c;
        a_menos_c=Vector3D.restarVectores(
                rayo.getOrigen(), this.centro);
        
        double a=Vector3D.productoEscalar(
                rayo.getDireccion(), rayo.getDireccion());
        double b=2.0 * (Vector3D.productoEscalar(
                a_menos_c, rayo.getDireccion()));
        double c=Vector3D.productoEscalar(a_menos_c, a_menos_c) - 
                (this.radio * this.radio);
        double discriminante = (double) (b*b)-(4.0*a*c);
        
        
        if (discriminante>0){
            puntoAlcance = this.calcularMejorSolucionRayoAlcanzaEsfera(rayo, a, b, discriminante, t_minimo, t_maximo);
        } 
        return puntoAlcance;
    }
    
    
    /* Colorea la superficie de la esfera según el vector normal perpendicular al
    punto donde la esfera es alcanzada por un rayo */
    public Color colorRayoSegunNormalPerpendicular(Rayo rayo, 
            PuntoAlcanzadoPorRayo puntoEsfera){
        double t = puntoEsfera.getT();
        Punto3D posicion = rayo.getPosicion(t);
        Vector3D vectorUnitarioNormal = puntoEsfera.vectorUnitarioNormal();
        double r = (1+ vectorUnitarioNormal.getV1() )  * 0.5;
        double g = (1+ vectorUnitarioNormal.getV2() )  * 0.5;
        double b = (1+ vectorUnitarioNormal.getV3() )  * 0.5;
        return new Color (r, g, b);
    }
}
