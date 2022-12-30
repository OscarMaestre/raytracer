/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package io.github.oscarmaestre.raytracer.tests;

import io.github.oscarmaestre.raytracer.Vec3;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author usuario
 */
public class TestVec3 {
    
    public TestVec3() {
    }

    @Test
    public void hello() {
        Vec3 v1=new Vec3(0.435, 0.173, 0.888);
        Assert.assertEquals(0.435, v1.getV1(), 0.001);
    }
    
    @Test
    public void sumaCuatroVectores() {
        Vec3 vector1=new Vec3(1.0, 1.0, 1.0);
        Vec3 suma = Vec3.sumarVectores(vector1, vector1, vector1, vector1);
        double v1 = suma.getV1();
        Assert.assertEquals(v1, 4.0, 0.005);
    }

    @Test 
    public void restaVectores() {
        Vec3 vector1=new Vec3(1.0, 1.0, 1.0);
        Vec3 resta = Vec3.restarVectores(vector1, vector1);
        double v1 = resta.getV1();
        double v2 = resta.getV2();
        double v3 = resta.getV3();
        Assert.assertEquals(v1, 0.0, 0.005);
        Assert.assertEquals(v2, 0.0, 0.005);
        Assert.assertEquals(v3, 0.0, 0.005);   
        
    }
    
    @Test
    public void tesMultiplicarVec3porEscalar(){
        Vec3 vector=new Vec3 (1.0, 2.0, 3.0);
        double escalar=2.0;
        vector.multiplicarPorEscalar(escalar);
        double v1 = vector.getV1();
        double v2 = vector.getV2();
        double v3 = vector.getV3();
        Assert.assertEquals(v1, 2.0, 0.005);
        Assert.assertEquals(v2, 4.0, 0.005);
        Assert.assertEquals(v3, 6.0, 0.005);   
    }
    
    
    @Test
    public void testDividirEstaticoVec3porEscalar(){
        Vec3 vector=new Vec3 (1.0, 2.0, 3.0);
        double escalar=2.0;
        Vec3 resultado = Vec3.dividirVectorPorEscalar(vector, escalar);
        double d1=resultado.getV1();
        double d2=resultado.getV2();
        double d3=resultado.getV3();
        Assert.assertEquals(d1, 0.5, 0.05);
        Assert.assertEquals(d2, 1.0, 0.05);
        Assert.assertEquals(d3, 1.5, 0.05);
    }
    @Test
    public void tesMultiplicarEstaticoVec3porEscalar(){
        Vec3 vector=new Vec3 (1.0, 2.0, 3.0);
        double escalar=2.0;
        Vec3 resultado = Vec3.multiplicarVectorPorEscalar(vector, escalar);
        
        double v1 = resultado.getV1();
        double v2 = resultado.getV2();
        double v3 = resultado.getV3();
        //Assert.assertEquals(v1, 2.0, 0.005);
        Assert.assertEquals(v2, 4.0, 0.005);
        Assert.assertEquals(v3, 6.0, 0.005);   
    }
    
    @Test
    public void testVectorUnidad(){
        double longitud;
        Vec3 vector=new Vec3 (1.0, 2.0, 3.0);
        longitud=vector.getLongitud();
        Assert.assertEquals(longitud, 3.74,0.005);
        Vec3 vectorUnitario = Vec3.vectorUnitario(vector);
        longitud = vectorUnitario.getLongitud();
        Assert.assertEquals(longitud, 1,0.005);
    }
    
    @Test 
    public void testProductoEscalar(){
        Vec3 v1=new Vec3 (1.0, 0.0, 0.0);
        Vec3 v2=new Vec3 (0.0, 1.0, 0.0);
        double productoEscalar = Vec3.productoEscalar(v1, v2);
        Assert.assertEquals(0.0,productoEscalar , 0.001);
    }
    
    @Test 
    public void testRestaMultiple(){
        Vec3 v1=new Vec3 (1.0, 0.0, 0.0);
        Vec3 v2=new Vec3 (0.0, 1.0, 0.0);
        Vec3 v3=new Vec3 (0.0, 1.0, 0.0);
        Vec3 resultado = Vec3.restarVectores(v1, v2, v3);
        Assert.assertEquals(1.0,resultado.getV1() , 0.001);
        Assert.assertEquals(-2.0,resultado.getV2() , 0.001);
        Assert.assertEquals(0.0,resultado.getV3() , 0.001);
    }
    
    @Test 
    public void testRestaMultiple2(){
        Vec3 v1=new Vec3 (1.0, 0.0, 0.0);
        Vec3 v2=new Vec3 (0.0, 1.0, 0.0);
        Vec3 v3=new Vec3 (0.0, 1.0, 0.0);
        Vec3 v4=new Vec3 (0.0, 1.0, -1.0);
        Vec3 resultado = Vec3.restarVectores(v1, v2, v3, v4);
        Assert.assertEquals(1.0,resultado.getV1() , 0.001);
        Assert.assertEquals(-3.0,resultado.getV2() , 0.001);
        Assert.assertEquals(1.0,resultado.getV3() , 0.001);
    }
    
    @Test
    public void testVectorUnitario(){
        Vec3 v1=new Vec3(2, 3, 4);
        Vec3 vectorUnitario = Vec3.vectorUnitario(v1);
        System.out.println(vectorUnitario.toString());
    }
    @BeforeMethod
    public void setUpMethod() throws Exception {
    }
}
