/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package io.github.oscarmaestre.raytracer.tests;

import io.github.oscarmaestre.raytracer.Color;
import io.github.oscarmaestre.raytracer.Ejemplos;
import io.github.oscarmaestre.raytracer.Imagen;
import java.io.FileNotFoundException;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestImagenes {
    
    public TestImagenes() {
    }

    @Test
    public void imagenAzul() throws FileNotFoundException {
        Imagen i=Ejemplos.generarImagenAzul();
        i.guardarImagenComoPPM("imagen_azul.ppm");
    }
    
    @Test
    public void imagenGradienteVerde() throws FileNotFoundException {
        Imagen i=Ejemplos.generarGradiente(new Color(0.0, 1.0, 0.0));
        i.guardarImagenComoPPM("gradiente_verde.ppm");
    }
    
    @Test
    public void imagen1() throws FileNotFoundException {
        Imagen i=Imagen.getPrimeraImagen();
        i.guardarImagenComoPPM("primera_imagen.ppm");
    }
    
    @Test
    public void imagen2() throws FileNotFoundException {
        Imagen i=Imagen.getSegundaImagen();
        i.guardarImagenComoPPM("segunda_imagen.ppm");
    }
    @Test
    public void imagen3() throws FileNotFoundException {
        Imagen i=Imagen.getTerceraImagen();
        i.guardarImagenComoPPM("tercera_imagen.ppm");
    }

}
