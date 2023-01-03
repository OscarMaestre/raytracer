package io.github.oscarmaestre.raytracer;

public class Camara {
    private final double anchoVirtual;
    private final double altoVirtual;
    
    private final int anchoPixelesReales;
    private final int altoPixelesReales;
    
    private final Vector3D vectorAncho;
    private final Vector3D vectorAlto;
    /* Origen de los ejes de coordenadas*/
    private Punto3D esquinaInferiorIzquierda;
    /* Este punto suele ser el (0, 0, 0)*/
    private final Punto3D origenRayos;
    private final double distanciaFocal;
    public Camara(double anchoVirtual, double altoVirtual, 
            int anchoPixelesReales, int altoPixelesReales,
            Punto3D origenRayos, double distanciaFocal) {
        this.anchoVirtual       = anchoVirtual;
        this.altoVirtual        = altoVirtual;
        this.anchoPixelesReales = anchoPixelesReales;
        this.altoPixelesReales  = altoPixelesReales;
        
        this.origenRayos        = origenRayos;
        this.distanciaFocal     = distanciaFocal;
        
        
        this.vectorAncho        =new Vector3D(this.anchoVirtual, 0.0, 0.0);
        this.vectorAlto         =new Vector3D(0.0, this.altoVirtual, 0.0);
        
        this.calcularEsquinaInferiorIzquierda();
    }
    private void calcularEsquinaInferiorIzquierda(){
        /* Calculamos la mitad de los vectores
        de ancho y alto  */
        Vector3D semiVertical = 
                Vector3D.dividirVectorPorEscalar(this.vectorAlto, 2);
        Vector3D semiHorizontal = 
                Vector3D.dividirVectorPorEscalar(this.vectorAncho, 2);
        Vector3D profundidad = new Vector3D(0, 0, distanciaFocal);
        /* Y ahora al origen le restamos la semihorizontal,
        la semivertical y toda la profundidad (lo que hace 
        "retroceder" desde la pantalla*/
        Vector3D resultadoParcial1 = 
                Vector3D.restarVectores(this.origenRayos, semiHorizontal);
        Vector3D resultadoParcial2 = 
                Vector3D.restarVectores(resultadoParcial1, semiVertical);
        Vector3D esquinaInfIzq = 
                Vector3D.restarVectores(resultadoParcial2, profundidad);
        this.esquinaInferiorIzquierda =  Punto3D.fromVec3(esquinaInfIzq);
    }
    
    /* Devuelve un viewport de 2x1.77 a una distancia focal de 1.0
    que se proyecta sobre una imagen de 400x225 con un origen en 0.0  */
    public static Camara getCamaraSimple(){
        final double    ASPECT_RATIO= 16.0/9.0;
        final int       ANCHO       = 400;
        final int       ALTO        = (int) (ANCHO / ASPECT_RATIO);
        
        final double    distanciaFocal = 1.0;
        
        final double    ALTURA_VIEWPORT     =2.0;
        final double    ANCHURA_VIEWPORT    = ASPECT_RATIO * ALTURA_VIEWPORT ;
        
        final Punto3D   origen  =   new Punto3D(0.0, 0.0, 0.0);
        
        Camara viewport=new Camara(ANCHURA_VIEWPORT, ALTURA_VIEWPORT,
            ANCHO, ALTO, origen, distanciaFocal);
        return viewport;
    }

    public Rayo getRayoHacia(int cx, int cy){
        double escalaX=(double)cx/(this.anchoPixelesReales-1);
        double escalaY=(double)cy/(this.altoPixelesReales-1);
        Vector3D desplazamientoX = 
                Vector3D.multiplicarVectorPorEscalar(
                        this.vectorAncho, escalaX);
        Vector3D desplazamientoY = 
                Vector3D.multiplicarVectorPorEscalar(
                        this.vectorAlto, escalaY);
        Vector3D desplazamientoZ=this.origenRayos.getCopia();
        /* Z se invierte porque el Z positivo está
        "alejándonos de la pantalla */
        desplazamientoZ = Vector3D.cambiarSigno(desplazamientoZ);
        
        Vector3D direccion = Vector3D.sumarVectores(this.esquinaInferiorIzquierda, 
                desplazamientoX,
                desplazamientoY,
                desplazamientoZ);
        Rayo r=new Rayo(this.origenRayos, direccion);
        return r;
    }

    public Punto3D getOrigenRayos() {
        return origenRayos;
    }
    
    
    public int getAnchoPixelesReales() {
        return anchoPixelesReales;
    }

    public int getAltoPixelesReales() {
        return altoPixelesReales;
    }
    
    public Vector3D getVectorHorizontal(){
         return new Vector3D(this.anchoVirtual, 0.0, 0.0);
    }
    public Vector3D getVectorVertical(){
         return new Vector3D(0.0, this.altoVirtual, 0.0);
    }
    
    public Vector3D getVectorProfundiad(){
         return new Vector3D(0.0, 0.0, this.distanciaFocal);
    }
    
    
    
}
