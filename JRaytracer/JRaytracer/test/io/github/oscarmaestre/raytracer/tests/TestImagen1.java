package io.github.oscarmaestre.raytracer.tests;

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
        Imagen1 i = Imagen1.getPrimeraImagen(conaa);
        i.guardarImagenComoPPM("otra1.ppm");
    }
    @Test
    public void testConAA() throws FileNotFoundException {
        boolean conaa=true;
        Imagen1 i = Imagen1.getPrimeraImagen(conaa);
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
        Esfera e=new Esfera(origen, radio, new Vec3(0, 1, 0), null);
        Vec3 normalEnPunto = e.getNormalEnPunto(new Vec3(1, 1, 1));
        System.out.println("La normal es:"+normalEnPunto.toString());
    }
    
    @Test
    public void pruebaEsferaUnidad(){
        Vec3 puntoEnEsferaUnidad = Vec3.getPuntoEnEsferaUnidad();
        System.out.println("Punto en esfera unidad:"+puntoEnEsferaUnidad);
    }
}
