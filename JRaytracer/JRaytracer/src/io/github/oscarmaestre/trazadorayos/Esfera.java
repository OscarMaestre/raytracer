package io.github.oscarmaestre.trazadorayos;

import java.util.Random;


public class Esfera extends ObjetoRenderizable {
    private final Vec3 centro;
    private final double  radio;
    
    public Esfera(Vec3 centro, double radio, Vec3 color, Material material) {
        this.centro = centro;
        this.radio = radio;
        this.color = color;
        this.material=material;
    }
    
    public Vec3 getCentro() {
        return centro;
    }
    
    public double getRadio() {
        return radio;
    }
    public Vec3 getNormalEnPunto(Vec3 punto){
        Vec3 resta = Vec3.restarVectores(punto, centro);
        resta.dividir(radio);
        //System.out.println("La normal en:"+resta.toString()+" es "+resta.toString());
        return resta;
        
    }

    
    @Override
    public RegistroAlcance esAlcanzadaPorRayo(
            Rayo rayo, double t_minimo, double t_maximo){
        /* Recordemos que en la esfera queremos resolver la
        ecuación t^2*B*B + 2tB(A-C) + (A-C)+(A-C) - R^2 = 0 
        y que la solución es (b^2 - (4ac) / 2a )*/
        
        RegistroAlcance puntoAlcance=null;
        
        Vec3 a_menos_c;
        a_menos_c=Vec3.restarVectores(
                rayo.getOrigen(), this.centro);
        
        double a=Vec3.productoEscalar(
                rayo.getDireccion(), rayo.getDireccion());
        double semib=Vec3.productoEscalar(a_menos_c, rayo.getDireccion());
        double c=a_menos_c.getLongitudCuadrado() - 
                (this.radio * this.radio);
        double discriminante = 
                (semib*semib)-(a*c);
        if (discriminante<0) return null;
        double sqrtd=Math.sqrt(discriminante);
        
        double root=(-semib-sqrtd) / a;
        if (root < t_minimo || t_maximo < root) {
            root = (-semib + sqrtd) / a;
            if (root < t_minimo || t_maximo < root)
                return null;
        }
        Vec3 puntoFinal = rayo.puntoFinal(root);
        Vec3 normal=Vec3.restarVectores(puntoFinal, centro);
        normal.dividir(radio);
        boolean rayoGolpeaPorElExterior = this.rayoGolpeaPorElExterior(rayo, normal);
        puntoAlcance=new RegistroAlcance(
                puntoFinal, normal, root,
                this, rayoGolpeaPorElExterior);
        return puntoAlcance;
        
    }
    
    
    /* Colorea la superficie de la esfera según el vector normal perpendicular al
    punto donde la esfera es alcanzada por un rayo */
    public Vec3 colorRayoSegunNormalPerpendicular(Rayo rayo, 
            RegistroAlcance alcance){
        
        
        Vec3 vectorUnitarioNormal = alcance.getNormal();
        double r = (1+ vectorUnitarioNormal.getX() )  * 0.5;
        double g = (1+ vectorUnitarioNormal.getY() )  * 0.5;
        double b = (1+ vectorUnitarioNormal.getZ() )  * 0.5;
        return new Vec3 (r, g, b);
    }
    
    
}
