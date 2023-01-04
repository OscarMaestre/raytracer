package io.github.oscarmaestre.trazadorayos;

public class RegistroAlcance {
    private final Vec3 punto;
    private final Vec3 normal;
    private final double t;
    private final ObjetoRenderizable objeto;
    private boolean alcanzadoDesdeExterior;
    
    public RegistroAlcance(Vec3 punto, Vec3 normal, double t, ObjetoRenderizable objeto, boolean alcanzadoDesdeExterior) {
        this.punto = punto;
        this.normal = normal;
        this.t = t;
        this.objeto = objeto;
        this.alcanzadoDesdeExterior = alcanzadoDesdeExterior;
        /* Si el objeto ha sido alcanzado por un rayo 
            que estaba en el interior, la normal apunta
            hacia el interior del objeto, por eso invertimos*/
        if (!this.alcanzadoDesdeExterior){
            this.normal.cambiarSigno();
        }
    }

    public ObjetoRenderizable getObjeto() {
        return objeto;
    }


    public Vec3 getPunto() {
        return punto;
    }

    public Vec3 getNormal() {
        return normal;
    }

    public double getT() {
        return t;
    }
    
}
