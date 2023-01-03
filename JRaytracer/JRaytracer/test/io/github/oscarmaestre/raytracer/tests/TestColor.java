/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package io.github.oscarmaestre.raytracer.tests;

import io.github.oscarmaestre.raytracer.Color;
import io.github.oscarmaestre.raytracer.IVec3;
import io.github.oscarmaestre.raytracer.Vector3D;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author usuario
 */
public class TestColor {
    
    public TestColor() {
    }

    @Test
    public void hello() {
        Color c=new Color(0.051, 0.002, 0.003);
        System.out.println(c.toString());
    }

    @Test
    public void sumaColores() {
        Color c=new Color(0.051, 0.002, 0.003);
        Color suma = (Color) c.sumar(c);
        double v1 = suma.getV1();
        Assert.assertEquals(v1, 0.102, 0.005);
    }
    
    @Test
    public void blancoDividido(){
        int altoPx=100;
        Color incremento = Color.from(
                IVec3.dividirVectorPorEscalar(
                    Color.getBlanco(), (double)altoPx) );
        Assert.assertEquals(incremento.getV1(), 0.01, 0.005);
    }
    @Test
    public void divisionBlanco(){
        int altoPx=225;
        Color incremento=(Color) IVec3.dividirVectorPorEscalar(
                Color.getBlanco(), (double)altoPx);
        System.out.println("El color resultado de dividir blanco es:"+incremento.toString());
        Color inc=Color.from(incremento);
        System.out.println("El incremento  de color es:"+inc.toStringWithDouble());
    }
    
    @Test
    public void testRGB(){
        Color blanco=Color.getBlanco();
        String toString = blanco.toString();
        System.out.println(blanco.toString());
    }
}
