package io.github.oscarmaestre.raytracer;


/**
 *
 * @author usuario
 */
public interface IVec3 {
    public IVec3 from(double d1, double d2, double d3);
    
    public double getV1() ;
    public double getV2() ;
    public double getV3() ;

    
    public default IVec3 cambiarSigno(){
        double v1=this.getV1();
        double v2=this.getV2();
        double v3=this.getV3();
        return from(-v1, -v2, -v3);
    }
    
    public default IVec3 sumar(IVec3 sumando){
        double resultado1=getV1()+sumando.getV1();
        double resultado2=getV2()+sumando.getV2();
        double resultado3=getV3()+sumando.getV3();
        return from(resultado1,resultado2,resultado3);
    };
    
    public default IVec3 sumarVectores(IVec3 sumando1, IVec3 sumando2){
        IVec3 resultado;
        resultado = this.sumar(
                sumando1).sumar(sumando2);
        return from(resultado.getV1(), 
                resultado.getV2(), 
                resultado.getV3());
    };
    
    
    public default IVec3 restarVector(IVec3 v1){
        IVec3 v1Cambiado = v1.cambiarSigno();
        IVec3 suma = this.sumar(v1Cambiado);
        return suma;
    }
    
    public default IVec3 multiplicarPorEscalar(double d){
        double d1=d*this.getV1();
        double d2=d*this.getV2();
        double d3=d*this.getV3();
        return from(d1, d2, d3);
    }
    
    public default IVec3 dividirPorEscalar(double d){
        double d1=this.getV1()/d;
        double d2=this.getV2()/d;
        double d3=this.getV3()/d;
        return from(d1, d2, d3);
    }
    
    public static IVec3 dividirVectorPorEscalar(IVec3 v, double d){
        double d1=v.getV1()/d;
        double d2=v.getV2()/d;
        double d3=v.getV3()/d;
        return v.from(d1, d2, d3);
    }
    
    

    public static Vector3D cambiarSigno(Vector3D vector){
        return Vector3D.multiplicarVectorPorEscalar(vector, -1.0);
    }
    
    public default IVec3 productoCruzado(IVec3 v1, IVec3 v2){
        double d1=( v1.getV2()*v2.getV3() -
                            v1.getV3()*v2.getV2() );
        double d2=( v1.getV3()*v2.getV1() -
                            v1.getV1()*v2.getV3() );
        double d3=( v1.getV1()*v2.getV2() -
                            v1.getV2()*v2.getV1() );
        
        return from(d1,d2,d3);
    }
    
    
    public default boolean sonIguales(IVec3 v1, IVec3 v2){
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

}
