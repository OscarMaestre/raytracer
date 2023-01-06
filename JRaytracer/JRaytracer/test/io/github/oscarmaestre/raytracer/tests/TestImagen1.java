package io.github.oscarmaestre.raytracer.tests;

import io.github.oscarmaestre.trazadorayos.Escena;
import io.github.oscarmaestre.trazadorayos.Esfera;
import io.github.oscarmaestre.trazadorayos.Imagen1;
import io.github.oscarmaestre.trazadorayos.Vec3;
import java.io.FileNotFoundException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author usuario
 */
public class TestImagen1 {
    
    public TestImagen1() {
    }

    @Test
    public void testSinAA() throws FileNotFoundException {
        boolean conaa=false;
        long antes = System.currentTimeMillis();
        Escena escena=Escena.getEscenaEjemploCamaraAerea();
        Imagen1 i = Imagen1.renderizar(conaa, escena);
        long despues = System.currentTimeMillis();
        long diferencia=despues-antes;
        System.out.println("Sin AA diferencia ms:"+diferencia);
        double ms=(double) ((despues-antes)/1000.0);
        System.out.println("Sin antialiasing:"+ms);
        i.guardarImagenComoPPM("otra1.ppm");
    }
    @Test
    public void testConAA() throws FileNotFoundException {
        Escena escena=Escena.getEscenaEjemploCamaraAerea();
        boolean conaa=true;
        long antes = System.currentTimeMillis();
        Imagen1 i = Imagen1.renderizar(conaa, escena);
        long despues = System.currentTimeMillis();
        long diferencia=despues-antes;
        System.out.println("Con AA diferencia ms:"+diferencia);
        double ms=(double) ((despues-antes)/1000);
        System.out.println("Con antialiasing:"+ms);
        i.guardarImagenComoPPM("otra1-conAA.ppm");
    }
    @Test
    public void pruebaColor(){
        Vec3 color=new Vec3(1, 0, 0);
        String colorRGB = Vec3.getColorRGB(color);
        System.out.println("El color era:"+colorRGB.toLowerCase());
    }
    
    @Test
    public void pruebaPromediadoColor(){
        Vec3 color=new Vec3(0.0, 0.0, 0.0);
        for (int i=0; i<100; i++){
            color.sumar(new Vec3(0.1, 0.1, 0.0));
        }
        color.dividir(100.0);
        System.out.println("El color era:"+color.toString());
        System.out.println("El color era:"+Vec3.getColorRGB(color));
    }
    @Test
    public void pruebaNormal(){
        Vec3 origen=new Vec3(0,0,0);
        double radio=1.0;
        Esfera e=new Esfera(origen, radio, null);
        Vec3 normalEnPunto = e.getNormalEnPunto(new Vec3(1, 1, 1));
        System.out.println("La normal es:"+normalEnPunto.toString());
    }
    
    @Test
    public void pruebaEsferaUnidad(){
        Vec3 puntoEnEsferaUnidad = Vec3.getPuntoEnEsferaUnidad();
        System.out.println("Punto en esfera unidad:"+puntoEnEsferaUnidad);
    }
}
