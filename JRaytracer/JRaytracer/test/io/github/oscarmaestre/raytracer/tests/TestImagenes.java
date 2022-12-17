/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package io.github.oscarmaestre.raytracer.tests;

import io.github.oscarmaestre.raytracer.Imagen;
import java.io.FileNotFoundException;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestImagenes {
    
    public TestImagenes() {
    }

    @Test
    public void hello() throws FileNotFoundException {
        Imagen i=Imagen.getPrimeraImagen();
        i.guardarImagenComoPPM("primera_imagen.ppm");
    }

}
