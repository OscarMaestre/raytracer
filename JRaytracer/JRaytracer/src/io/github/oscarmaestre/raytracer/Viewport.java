package io.github.oscarmaestre.raytracer;

public class Viewport {
    private final double anchoVirtual;
    private final double altoVirtual;
    
    private final int anchoPixelesReales;
    private final int altoPixelesReales;
    
    private final Vec3 vectorAncho;
    private final Vec3 vectorAlto;
    /* Origen de los ejes de coordenadas*/
    private Punto3D esquinaInferiorIzquierda;
    /* Este punto suele ser el (0, 0, 0)*/
    private final Punto3D origenRayos;
    private final double distanciaFocal;
    public Viewport(double anchoVirtual, double altoVirtual, 
            int anchoPixelesReales, int altoPixelesReales,
            Punto3D origenRayos, double distanciaFocal) {
        this.anchoVirtual       = anchoVirtual;
        this.altoVirtual        = altoVirtual;
        this.anchoPixelesReales = anchoPixelesReales;
        this.altoPixelesReales  = altoPixelesReales;
        
        this.origenRayos        = origenRayos;
        this.distanciaFocal     = distanciaFocal;
        
        
        this.vectorAncho        =new Vec3(this.anchoVirtual, 0.0, 0.0);
        this.vectorAlto         =new Vec3(0.0, this.altoVirtual, 0.0);
        
        this.calcularEsquinaInferiorIzquierda();
    }
    private void calcularEsquinaInferiorIzquierda(){
        /* Calculamos la mitad de los vectores
        de ancho y alto  */
        Vec3 semiVertical = 
                Vec3.dividirVectorPorEscalar(this.vectorAlto, 2);
        Vec3 semiHorizontal = 
                Vec3.dividirVectorPorEscalar(this.vectorAncho, 2);
        Vec3 profundidad = new Vec3(0, 0, distanciaFocal);
        /* Y ahora al origen le restamos la semihorizontal,
        la semivertical y toda la profundidad (lo que hace 
        "retroceder" desde la pantalla*/
        Vec3 resultadoParcial1 = 
                Vec3.restarVectores(this.origenRayos, semiHorizontal);
        Vec3 resultadoParcial2 = 
                Vec3.restarVectores(resultadoParcial1, semiVertical);
        Vec3 esquinaInfIzq = 
                Vec3.restarVectores(resultadoParcial2, profundidad);
        this.esquinaInferiorIzquierda =  Punto3D.fromVec3(esquinaInfIzq);
    }
    
    /* Devuelve un viewport de 2x1.77 a una distancia focal de 1.0
    que se proyecta sobre una imagen de 400x225 con un origen en 0.0  */
    public static Viewport getViewportSimple(){
        final double    ASPECT_RATIO= 16.0/9.0;
        final int       ANCHO       = 400;
        final int       ALTO        = (int) (ANCHO / ASPECT_RATIO);
        
        final double    distanciaFocal = 1.0;
        
        final double    ALTURA_VIEWPORT     =2.0;
        final double    ANCHURA_VIEWPORT    = ASPECT_RATIO * ALTURA_VIEWPORT ;
        
        final Punto3D   origen  =   new Punto3D(0.0, 0.0, 0.0);
        
        Viewport viewport=new Viewport(ANCHURA_VIEWPORT, ALTURA_VIEWPORT,
            ANCHO, ALTO, origen, distanciaFocal);
        return viewport;
    }

    public Rayo getRayoHacia(int cx, int cy){
        double escalaX=(double)cx/(this.anchoPixelesReales-1);
        double escalaY=(double)cy/(this.altoPixelesReales-1);
        Vec3 desplazamientoX = 
                Vec3.multiplicarVectorPorEscalar(
                        this.vectorAncho, escalaX);
        Vec3 desplazamientoY = 
                Vec3.multiplicarVectorPorEscalar(
                        this.vectorAncho, escalaY);
        Vec3 desplazamientoZ=this.origenRayos.getCopia();
        /* Z se invierte porque el Z positivo está
        "alejándonos de la pantalla */
        desplazamientoZ.cambiarSigno();
        Vec3 direccion = Vec3.sumarVectores(this.esquinaInferiorIzquierda, 
                desplazamientoX,
                desplazamientoY,
                desplazamientoZ);
        Rayo r=new Rayo(this.origenRayos, direccion);
        return r;
    }
    public int getAnchoPixelesReales() {
        return anchoPixelesReales;
    }

    public int getAltoPixelesReales() {
        return altoPixelesReales;
    }
    
}
