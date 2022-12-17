package io.github.oscarmaestre.raytracer;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Imagen {

    private int alto;
    private int ancho;

    Color pixeles[][];

    public Imagen(int alto, int ancho) {
        this.alto = alto;
        this.ancho = ancho;
        /* Almacenamos los colores de cada pixel en formato (x,y)*/
        this.pixeles=new Color[this.ancho][this.alto];
    }
    public void setColor(int x, int y, Color c){
        this.pixeles[x][y]=c;
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
        
        Imagen imagen=new Imagen(256, 256);
        for (int cy=ALTO-1; cy>=0; --cy){
            for (int cx=0; cx<ALTO; cx++){
                double r=cx/(ANCHO-1);
                double g=cy/(ALTO-1);
                double b=0.25;
                imagen.setColor(cx, cy, r, g, b);
            }
        }
        return imagen;
    }
    public String getImagenComoPPM(){
        StringBuilder resultado=new StringBuilder();
        
        resultado.append("P3\n");
        /* En el formato PPM aparece primero la altura y
        luego la anchura */
        resultado.append(this.alto + " " + this.ancho+"\n");
        resultado.append("255\n");
        final int ANCHO=this.getAncho();
        final int ALTO =this.getAlto();
        for (int coordenadaX=0; coordenadaX<ANCHO; coordenadaX++){
            for (int coordenadaY=0; coordenadaY<ALTO; coordenadaY++){
                Color colorEnPixel;
                colorEnPixel=this.pixeles[coordenadaX][coordenadaY];
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
