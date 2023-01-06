package io.github.oscarmaestre.trazadorayos;

import io.github.oscarmaestre.raytracer.Color;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;


public class Imagen1 {


    private int alto;
    private int ancho;

    Vec3 pixeles[][];
    boolean antialiasing=false;
    public final static int cantidadMuestras=100;
    public final static Random generadorAzar=new Random();
    protected final static int MAX_REBOTES_RAYO=50;
    public Imagen1(int ancho, int alto) {
        this.alto = alto;
        this.ancho = ancho;
        /* Almacenamos los colores de cada pixel 
        en formato (y,x). Esto ocurre porque luego hay
        que recorrer fila por fila y empezaremos
        recorriendo todas las X en la fila y=0*/
        this.pixeles=new Vec3[this.alto][this.ancho];
    }
    public void setColor(int x, int y, Vec3 c){
        /* Hacemos alto-1-y para que el origen de coordenadas
        esté en la esquina inferior izquierda. Esto
        concuerda más con el uso diario que hacemos de los ejes.
        Los ejes en gráficos por ordenador suelen tener el origen
        en la esquina superior izquierda de la imagen  */
        this.pixeles[alto-1-y][x]=c;
    }
    public void setColor(int x, int y, double v1, double v2, double v3){
        Vec3 c=new Vec3(v1, v2, v3);
        this.setColor(x, y, c);
    }
    public int getAncho() {
        return ancho;
    }
    public int getAlto(){
        return alto;
    }
    
    public static Vec3 getColorConAntialiasing(
            int cx, int cy, Camara camara, Escena escena,
            int ANCHO, int ALTO)
    {
        Vec3 color=new Vec3(0.0, 0.0, 0.0);
        double nuevoU, nuevoV;
        for(int i=0; i<Imagen1.cantidadMuestras; i++){

            nuevoU=obtenerNuevoEscalarPerturbado(cx, ANCHO);
            nuevoV=obtenerNuevoEscalarPerturbado(cy, ALTO);

            Rayo rayo = camara.getRayo(nuevoU, nuevoV );
            Vec3 colorPunto=getColor(rayo, escena, MAX_REBOTES_RAYO);

            color.sumar(colorPunto);

        }

        color.dividir(Imagen1.cantidadMuestras);
        
        return color;
    }
    public static double obtenerNuevoEscalarPerturbado(
            int coordenada, int valorMaximoCoordenada){
        double coordenadaDouble=(double) coordenada;
        double variacionAleatoria=generadorAzar.nextDouble();
        double valorMaximoAjustado=(double)(valorMaximoCoordenada-1);
        double escalarPerturbado;
        escalarPerturbado = 
                (coordenadaDouble+variacionAleatoria)/valorMaximoAjustado;
        return escalarPerturbado;
    }
    public static Imagen1 renderizar(boolean conAA, Escena escena){
        
        final int ANCHO =400;
        final int ALTO  =225;

        
        
        Camara camara = Camara.getCamara();
        
        double R=Math.cos(Math.PI/4);
        Imagen1 imagen=new Imagen1(ANCHO, ALTO);
        
        for (int coorY=0; coorY<ALTO; coorY++){
            
            for (int coorX=0; coorX<ANCHO; coorX++){
                double u=(double)coorX/(ANCHO-1);
                double v=(double)coorY/(ALTO-1);
                if (conAA){
                    Vec3 color;
                    color = getColorConAntialiasing(
                            coorX, coorY, 
                            camara, escena, ANCHO, ALTO);
                    imagen.setColor(coorX, coorY, color);
                } else {
                    Rayo rayo = camara.getRayo(u,v );
                    Vec3 color=getColor(rayo, escena, MAX_REBOTES_RAYO);
                    imagen.setColor(coorX, coorY, color);
                } //Fin del if con antialiasing
            }
        }
        return imagen;
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
                Vec3 colorEnPixel;
                colorEnPixel=this.pixeles[coorY][coorX];
                Vec3 colorCorregidoGamma = Vec3.aplicarGamma(colorEnPixel, Imagen1.cantidadMuestras);
                resultado.append(Vec3.getColorRGB(colorCorregidoGamma));
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

    private static Vec3 getColor(Rayo rayo, Escena escena, int profundidad) {
        /* Si hemos bajado demasiado en la recursividad salimos */
        if (profundidad<=0){
            return new Vec3(0, 0, 0);
        }
        RegistroAlcance registroAlcance;
        Vec3 blanco=new Vec3(1, 1, 1);
        registroAlcance=escena.rayoGolpeaObjeto(rayo, 0, Double.MAX_VALUE);
        
        if (registroAlcance!=null){
            Material material=registroAlcance.getObjeto().getMaterial();
            RayoReflejado rayoReflejado = material.dispersar(rayo, registroAlcance);
            if (rayoReflejado==null) {
                return new Vec3(0, 0, 0);
            }
            /* Si llegamos aquí hay reflejo, seguimos calculando
            recursivamente */
            Vec3 siguienteReflejo=getColor(rayoReflejado, escena, profundidad-1);
            Vec3 atenuacion=rayoReflejado.getAtenuacion();
            Vec3 colorFinal = Vec3.multiplicarVectores(atenuacion, siguienteReflejo);
            return colorFinal;
        } 
        Vec3 direccionUnidad = Vec3.vectorUnitario(rayo.getDireccion());
        double t=(direccionUnidad.getY()+1.0) * 0.5;
        blanco.producto((double)(1.0-t));
        Vec3 colorFondo=new Vec3(0.5, 0.7, 1.0);
        colorFondo.producto(t);
        Vec3 fondoMezclado = Vec3.sumarVectores(blanco, colorFondo);
        return fondoMezclado;
    }
    
    private static Vec3 getColorSinDispersion(Rayo rayo, Escena escena, int profundidad) {
        /* Si hemos bajado demasiado en la recursividad salimos */
        if (profundidad<=0){
            return new Vec3(0, 0, 0);
        }
        RegistroAlcance registroAlcance;
        Vec3 blanco=new Vec3(1, 1, 1);
        registroAlcance=escena.rayoGolpeaObjeto(rayo, 0, Double.MAX_VALUE);
        
        if (registroAlcance!=null){
            Vec3 puntoAlQueDesviamos;
            puntoAlQueDesviamos=Vec3.sumarVectores(
                    registroAlcance.getPunto(), 
                    registroAlcance.getNormal(),
                    Vec3.getEsferaNormalSegunHemisferio(registroAlcance.getNormal()));
            Vec3 nuevaDireccionDelRayo=
                    Vec3.restarVectores(puntoAlQueDesviamos, 
                            registroAlcance.getPunto());
            Rayo nuevoRayo=new Rayo(
                    registroAlcance.getPunto(), 
                    nuevaDireccionDelRayo);
            Vec3 colorDelNuevoRayo=getColor(
                    nuevoRayo, escena, profundidad-1);
            /* Este color se devuelve "debilitado" por el rebote*/
            colorDelNuevoRayo.dividir(2);
            return colorDelNuevoRayo;
        } 
        Vec3 direccionUnidad = Vec3.vectorUnitario(rayo.getDireccion());
        double t=(direccionUnidad.getY()+1.0) * 0.5;
        blanco.producto((double)(1.0-t));
        Vec3 colorFondo=new Vec3(0.5, 0.7, 1.0);
        colorFondo.producto(t);
        Vec3 fondoMezclado = Vec3.sumarVectores(blanco, colorFondo);
        return fondoMezclado;
    }
}
