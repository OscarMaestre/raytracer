package io.github.oscarmaestre.raytracer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Imagen {

    private int alto;
    private int ancho;

    Color pixeles[][];

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
            Vec3 vHorizontal, Vec3 vVertical,
            double distanciaFocal ){
        
        /* Calculamos la mitad de los vectores
        de ancho y alto  */
        Vec3 semiVertical = 
                Vec3.dividirVectorPorEscalar(vVertical, 2);
        Vec3 semiHorizontal = 
                Vec3.dividirVectorPorEscalar(vHorizontal, 2);
        Vec3 profundidad = new Vec3(0, 0, distanciaFocal);
        System.out.println("La semihorizontal es:"+semiHorizontal.toString());
        System.out.println("La semivertical es:"+semiVertical.toString());
        System.out.println("La profundidad es:"+profundidad.toString());
        
        /* Y ahora al origen le restamos la semihorizontal,
        la semivertical y toda la profundidad (lo que hace 
        "retroceder" desde la pantalla*/
        Vec3 resultadoParcial1 = 
                Vec3.restarVectores(origen, semiHorizontal);
        Vec3 resultadoParcial2 = 
                Vec3.restarVectores(resultadoParcial1, semiVertical);
        Vec3 esquinaInfIzq = 
                Vec3.restarVectores(resultadoParcial2, profundidad);
        return Punto3D.fromVec3(esquinaInfIzq);
    }
    
    public static Color calcularColorRayoEnSegundaImagen(Rayo rayo){
        Vec3 direccion = rayo.getDireccion();
        Vec3 vectorUnitario = Vec3.vectorUnitario(direccion);
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
        colorBlanco.multiplicarPorEscalar(intensidadBlanco);
        tonoAzul.multiplicarPorEscalar(t);
        Vec3 colorFinal = 
                Vec3.sumarVectores(colorBlanco, tonoAzul);
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
        final Vec3      vHorizontal;
        final Vec3      vVertical;
        vHorizontal  =      new Vec3(anchuraViewport, 0.0, 0.0);
        vVertical    =      new Vec3(0.0, alturaViewport, 0.0);
        
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
                Vec3 desplazamientoX = 
                        Vec3.multiplicarVectorPorEscalar(
                                vHorizontal, escalaX);
                Vec3 desplazamientoY = 
                        Vec3.multiplicarVectorPorEscalar(
                                vVertical, escalaY);
                Vec3 desplazamientoZ=origen.getCopia();
                /* Z se invierte porque el Z positivo está
                "alejándonos de la pantalla */
                desplazamientoZ.cambiarSigno();
                Vec3 direccion = Vec3.sumarVectores(esqInfIzq, 
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
        esAlcanzadaPorRayo = esfera.esAlcanzadaPorRayo(r);
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
        final Vec3      vHorizontal;
        final Vec3      vVertical;
        vHorizontal  =      new Vec3(anchuraViewport, 0.0, 0.0);
        vVertical    =      new Vec3(0.0, alturaViewport, 0.0);
        
        Punto3D esqInfIzq = 
                Imagen.calcularEsqInfIzq(origen, 
                        vHorizontal, 
                        vVertical, 
                        distancia_focal);
        
        System.out.println("La esq inf izq está en :"+esqInfIzq.toString());
        imagenResultado=new Imagen(ANCHO, ALTO);
        /* La esfera está justo en el centro de nuestra pantalla,
        a una unidad de distancia del centro de la pantalla */
        Punto3D centroEsfera=new Punto3D(0.0, 0.0, -1.0);
        Esfera esfera=new Esfera(centroEsfera, 0.5);
        /*Esto se puede optimizar bastante, por ejemplo
        sacando algunas cosas del bucle interior*/
        for (int cy=0; cy<ALTO; cy++){
            for (int cx=0; cx<ANCHO; cx++){
                double escalaX=(double)cx/(ANCHO-1);
                double escalaY=(double)cy/(ALTO-1);
                Vec3 desplazamientoX = 
                        Vec3.multiplicarVectorPorEscalar(
                                vHorizontal, escalaX);
                Vec3 desplazamientoY = 
                        Vec3.multiplicarVectorPorEscalar(
                                vVertical, escalaY);
                Vec3 desplazamientoZ=origen.getCopia();
                /* Z se invierte porque el Z positivo está
                "alejándonos de la pantalla */
                desplazamientoZ.cambiarSigno();
                Vec3 direccion = Vec3.sumarVectores(esqInfIzq, 
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
