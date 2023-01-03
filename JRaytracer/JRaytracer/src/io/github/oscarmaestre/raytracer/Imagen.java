package io.github.oscarmaestre.raytracer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Imagen {

    private static Color getColorConAntialiasing(
            Camara camara, Escena escena,int cx, int cy,
            int muestrasParaAntialiasing) 
    {
        Random generadorAzar=new Random();
        /*Inicialmente, el color de fondo es este*/
        Color colorFondo=Color.getBlanco();    
        for (int i = 0; i < muestrasParaAntialiasing; i++) {
            /* Cuidado: es obligatorio poner 2 para que Java
            elija aleatorios entre 0 y 1. Si quisiéramos entre
            0 y 4 habría que poner nextInt(5) */
            int variacionDeX=generadorAzar.nextInt(2);
            int variacionDeY=generadorAzar.nextInt(2);
            int nuevoX=cx+variacionDeX;
            int nuevoY=cy+variacionDeY;
            Rayo rayoHacia = camara.getRayoHacia(nuevoX, nuevoY);
            if (escena.rayoGolpeaObjeto(rayoHacia, 0, 25.0)!=null){
                Color colorEnPunto=Color.getRojo();
                colorFondo = (Color) colorFondo.sumar(colorEnPunto);
                
            }
            
        } //Fin del bucle que toma muchas muestras de color alrededor del punto
        /* Ahora pueden pasar dos cosas:
            1.-El rayo no golpeó ningún objeto y 
                el color calculado es negro. Aún así devolveremos un
                color de fondo cualquiera
            2.-El rayo sí golpeó algo y entonces hay que
                promediar el color*/
        /*Si llegamos aquí es que el rayo golpeó algo.
        Dividimos el color entre el número de muestras lo
        que nos da un "color promediado con respecto a los de su alrededor */
        colorFondo = (Color) colorFondo.dividirPorEscalar(muestrasParaAntialiasing);
        
        return colorFondo;
    }

    private int alto;
    private int ancho;

    Color pixeles[][];
    boolean antialiasing=false;
    public Imagen(int ancho, int alto) {
        this.alto = alto;
        this.ancho = ancho;
        /* Almacenamos los colores de cada pixel 
        en formato (y,x). Esto ocurre porque luego hay
        que recorrer fila por fila y empezaremos
        recorriendo todas las X en la fila y=0*/
        this.pixeles=new Color[this.alto][this.ancho];
    }
    public void setColor(int x, int y, Color c){
        /* Hacemos alto-1-y para que el origen de coordenadas
        esté en la esquina inferior izquierda. Esto
        concuerda más con el uso diario que hacemos de los ejes.
        Los ejes en gráficos por ordenador suelen tener el origen
        en la esquina superior izquierda de la imagen  */
        this.pixeles[alto-1-y][x]=c;
    }
    public void setColor(int x, int y, double v1, double v2, double v3){
        Color c=new Color(v1, v2, v3);
        this.setColor(x, y, c);
    }
    public int getAncho() {
        return ancho;
    }
    public int getAlto(){
        return alto;
    }
    
    public static Imagen getPrimeraImagen(){
        final int ANCHO =256;
        final int ALTO  =256;
        
        Imagen imagen=new Imagen(ANCHO, ALTO);
        for (int coorY=0; coorY<ALTO; coorY++){
            for (int coorX=0; coorX<ANCHO; coorX++){
                double r=(double)coorX/(ANCHO-1);
                double g=(double)coorY/(ALTO-1);
                double b=(double)0.25;
                imagen.setColor(coorX, coorY, r, g, b);
            }
        }
        return imagen;
    }
    
    public static Punto3D calcularEsqInfIzq(Punto3D origen,
            Vector3D vHorizontal, Vector3D vVertical,
            double distanciaFocal ){
        
        /* Calculamos la mitad de los vectores
        de ancho y alto  */
        Vector3D semiVertical = 
                Vector3D.dividirVectorPorEscalar(vVertical, 2);
        Vector3D semiHorizontal = 
                Vector3D.dividirVectorPorEscalar(vHorizontal, 2);
        Vector3D profundidad = new Vector3D(0, 0, distanciaFocal);
        
        /* Y ahora al origen le restamos la semihorizontal,
        la semivertical y toda la profundidad (lo que nos hace 
        "retroceder" desde la pantalla)*/
        Vector3D esquinaInfIzq=Vector3D.restarVectores(origen, 
                semiHorizontal, semiVertical, profundidad);
        
        return Punto3D.fromVec3(esquinaInfIzq);
    }
    public static Punto3D calcularEsqInfIzq(Punto3D origen,
            Vector3D vHorizontal, Vector3D vVertical,
            Vector3D profundidad ){
        
        /* Igual que antes: calculamos la mitad de los vectores
        de ancho y alto  */
        Vector3D semiVertical = 
                Vector3D.dividirVectorPorEscalar(vVertical, 2);
        Vector3D semiHorizontal = 
                Vector3D.dividirVectorPorEscalar(vHorizontal, 2);
        
        
        /* Y ahora al origen le restamos la semihorizontal,
        la semivertical y toda la profundidad (lo que nos hace 
        "retroceder" desde la pantalla)*/
        Vector3D esquinaInfIzq=Vector3D.restarVectores(origen, 
                semiHorizontal, semiVertical, profundidad);
        
        return Punto3D.fromVec3(esquinaInfIzq);
    }
    
    public static Color calcularColorRayoEnSegundaImagen(Rayo rayo){
        Vector3D direccion = rayo.getDireccion();
        Vector3D vectorUnitario = direccion.vectorUnitario();
        /* Esto está entre 0 y 1 cuando el rayo está por encima del centro
        y entre -1 y 0 cuando está por debajo, lo que ayuda a hacer un
        gradiente*/
        double coordenadaY = vectorUnitario.getV2();
        /* Y esto valdrá siempre entre 0 y menos 1, lo usaremos
        para saber la "intensidad" de azul*/
        double t=0.5*(coordenadaY+1.0);
        /* Y esto para saber la "intensidad de blanco"*/
        double intensidadBlanco=1.0-t;
        
        /* El color final del rayo será una "mezcla" de estos dos colores*/
        Color colorBlanco   =   new Color(1, 1, 1);
        Color tonoAzul      =   new Color(0.5, 0.7, 1.0);
        
        /* Modificamos ambos colores*/
        Color parteBlanca = (Color) colorBlanco.multiplicarPorEscalar(intensidadBlanco);
        Color parteAzul   = (Color) tonoAzul.multiplicarPorEscalar(t);
        
        Color colorFinal =  (Color) parteBlanca.sumar(parteAzul);
        Color devolver=Color.from(colorFinal);
        return devolver;
    }
    
    public static Imagen getSegundaImagen(){
        final double    ASPECT_RATIO= 16.0/9.0;
        final int       ANCHO       = 400;
        final int       ALTO        = (int) (ANCHO / ASPECT_RATIO);
        Imagen          imagenResultado;
        final double    distancia_focal = 1.0;
        final Punto3D   origen          = new Punto3D(0.0, 0.0, 0.0);
        
        final double    alturaViewport=2.0;
        final double    anchuraViewport= ASPECT_RATIO * alturaViewport ;
        final Vector3D      vHorizontal;
        final Vector3D      vVertical;
        vHorizontal  =      new Vector3D(anchuraViewport, 0.0, 0.0);
        vVertical    =      new Vector3D(0.0, alturaViewport, 0.0);
        
        Punto3D esqInfIzq = 
                Imagen.calcularEsqInfIzq(origen, 
                        vHorizontal, 
                        vVertical, 
                        distancia_focal);
        
        System.out.println("La esq inf izq está en :"+esqInfIzq.toString());
        imagenResultado=new Imagen(ANCHO, ALTO);
        /*Esto se puede optimizar bastante, por ejemplo
        sacando algunas cosas del bucle interior*/
        for (int cy=0; cy<ALTO; cy++){
            for (int cx=0; cx<ANCHO; cx++){
                double escalaX=(double)cx/(ANCHO-1);
                double escalaY=(double)cy/(ALTO-1);
                Vector3D desplazamientoX = 
                        Vector3D.multiplicarVectorPorEscalar(
                                vHorizontal, escalaX);
                Vector3D desplazamientoY = 
                        Vector3D.multiplicarVectorPorEscalar(
                                vVertical, escalaY);
                Vector3D desplazamientoZ=origen.getCopia();
                
                /* Z se invierte porque el Z positivo está
                "alejándonos de la pantalla */
                desplazamientoZ=Vector3D.cambiarSigno(desplazamientoZ);
                
                Vector3D direccion = Vector3D.sumarVectores(esqInfIzq, 
                        desplazamientoX,
                        desplazamientoY,
                        desplazamientoZ);
                Rayo r=new Rayo(origen, direccion);
                //System.out.println(r);
                Color colorEnPunto = Imagen.calcularColorRayoEnSegundaImagen(r);
                imagenResultado.setColor(cx, cy, colorEnPunto);
            }
        }
        return imagenResultado;
    }
    
    private static boolean golpeaEsferaRoja(Esfera esfera,  Rayo r){
        PuntoAlcanzadoPorRayo esAlcanzadaPorRayo;
        esAlcanzadaPorRayo = esfera.esAlcanzadaPorRayo(r, 0, 10000);
        if (esAlcanzadaPorRayo!=null){
            return true;
        }
        return false;
    }
    public static Imagen getTerceraImagen(){
        final double    ASPECT_RATIO= 16.0/9.0;
        final int       ANCHO       = 400;
        final int       ALTO        = (int) (ANCHO / ASPECT_RATIO);
        Imagen          imagenResultado;
        final double    distancia_focal = 1.0;
        final Punto3D   origen          = new Punto3D(0.0, 0.0, 0.0);
        
        final double    alturaViewport=2.0;
        final double    anchuraViewport= ASPECT_RATIO * alturaViewport ;
        final Vector3D      vHorizontal;
        final Vector3D      vVertical;
        vHorizontal  =      new Vector3D(anchuraViewport, 0.0, 0.0);
        vVertical    =      new Vector3D(0.0, alturaViewport, 0.0);
        
        Punto3D esqInfIzq = 
                Imagen.calcularEsqInfIzq(origen, 
                        vHorizontal, 
                        vVertical, 
                        distancia_focal);
        
        System.out.println("La esq inf izq está en :"+esqInfIzq.toString());
        imagenResultado=new Imagen(ANCHO, ALTO);
        /* La esfera está justo en el centro de nuestra pantalla,
        a una unidad de distancia del centro de la pantalla */
        Punto3D centroEsfera=new Punto3D(0.0, 0.0, -1);
        Esfera esfera=new Esfera(centroEsfera, 0.5);
        /*Esto se puede optimizar bastante, por ejemplo
        sacando algunas cosas del bucle interior*/
        for (int cy=0; cy<ALTO; cy++){
            for (int cx=0; cx<ANCHO; cx++){
                double escalaX=(double)cx/(ANCHO-1);
                double escalaY=(double)cy/(ALTO-1);
                Vector3D desplazamientoX = 
                        Vector3D.multiplicarVectorPorEscalar(
                                vHorizontal, escalaX);
                Vector3D desplazamientoY = 
                        Vector3D.multiplicarVectorPorEscalar(
                                vVertical, escalaY);
                Vector3D desplazamientoZ=origen.getCopia();
                /* Z se invierte porque el Z positivo está
                "alejándonos de la pantalla */
                desplazamientoZ=Vector3D.cambiarSigno(desplazamientoZ);
                Vector3D direccion = Vector3D.sumarVectores(esqInfIzq, 
                        desplazamientoX,
                        desplazamientoY,
                        desplazamientoZ);
                Rayo r=new Rayo(origen, direccion);
                Color colorEnPunto;
                if (golpeaEsferaRoja(esfera, r)){
                    colorEnPunto=Color.getRojo();
                }
                else {
                    colorEnPunto = Imagen.calcularColorRayoEnSegundaImagen(r);
                }
                imagenResultado.setColor(cx, cy, colorEnPunto);
            }
        }
        return imagenResultado;
    }
    
    public static Imagen getCuartaImagen(boolean conAntialiasing, int numMuestras){
        Camara viewportSimple  = Camara.getCamaraSimple();
        Vector3D vHorizontal         = viewportSimple.getVectorHorizontal();
        Vector3D vVertical           = viewportSimple.getVectorVertical();
        Vector3D vProfundidad        = viewportSimple.getVectorProfundiad();
        Punto3D esqInfIzq = 
                Imagen.calcularEsqInfIzq(viewportSimple.getOrigenRayos(), 
                        vHorizontal, 
                        vVertical, 
                        vProfundidad);
        
        Imagen imagenResultado = new Imagen(viewportSimple.getAnchoPixelesReales(), 
                viewportSimple.getAltoPixelesReales());
        /* La esfera está justo en el centro de nuestra pantalla,
        a una unidad de distancia del centro de la pantalla */
        Escena escena=new Escena();
        escena.addEsfera(0, 0, -1, 0.5);
        escena.addEsfera(0,-99.5,-2, 100);
        
        /*Esto se puede optimizar bastante, por ejemplo
        sacando algunas cosas del bucle interior*/
        
        int muestrasParaAntialiasing=100;
        for (int cy=0; cy<viewportSimple.getAltoPixelesReales(); cy++){
            for (int cx=0; cx<viewportSimple.getAnchoPixelesReales(); cx++){
                Color colorEnPunto;
                colorEnPunto = getColorConAntialiasing(
                        viewportSimple, escena, 
                        cx, cy, muestrasParaAntialiasing);
                imagenResultado.setColor(cx, cy, colorEnPunto);
            }
        }
        return imagenResultado;
    }
    
    public String getImagenComoPPM(){
        StringBuilder resultado=new StringBuilder();
        
        resultado.append("P3\n");
        /* En el formato PPM aparece primero la anchura y
        luego la altura */
        resultado.append(this.ancho + " " + this.alto+"\n");
        resultado.append("255\n");
        final int ANCHO=this.getAncho();
        final int ALTO =this.getAlto();
        for (int coorY=0; coorY<ALTO; coorY++){
            for (int coorX=0; coorX<ANCHO; coorX++){
                Color colorEnPixel;
                colorEnPixel=this.pixeles[coorY][coorX];
                resultado.append(colorEnPixel.toString());
                resultado.append("\n");
            }
        }
        return resultado.toString();
    }
    
    public void guardarImagenComoPPM(String rutaFichero) throws FileNotFoundException{
        PrintWriter escritor=new PrintWriter(rutaFichero);
        String datosPPM=this.getImagenComoPPM();
        escritor.print(datosPPM);
        escritor.close();
    }
    
}
