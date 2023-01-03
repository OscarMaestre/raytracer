package io.github.oscarmaestre.trazadorayos;

public abstract class ObjetoRenderizable {
    protected Vec3 color;
    public  abstract RegistroAlcance esAlcanzadaPorRayo(
            Rayo rayo, double t_minimo, double t_maximo);

    public Vec3 getColor() {
        return color;
    }
    
    public boolean rayoGolpeaPorElExterior(Rayo r, Vec3 normal){
        double productoEscalar = 
                Vec3.productoEscalar(r.getDireccion(), normal);
        if (productoEscalar>0.0 ){
            return false;
        }
        return true;
    }
    
}
