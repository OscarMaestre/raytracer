/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package io.github.oscarmaestre.raytracer.tests;

import io.github.oscarmaestre.raytracer.Vector3D;
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
        Vector3D v1=new Vector3D(0.435, 0.173, 0.888);
        Assert.assertEquals(0.435, v1.getV1(), 0.001);
    }
    
    @Test
    public void sumaCuatroVectores() {
        Vector3D vector1=new Vector3D(1.0, 1.0, 1.0);
        Vector3D suma = Vector3D.sumarVectores(vector1, vector1, vector1, vector1);
        double v1 = suma.getV1();
        Assert.assertEquals(v1, 4.0, 0.005);
    }

    @Test 
    public void restaVectores() {
        Vector3D vector1=new Vector3D(1.0, 1.0, 1.0);
        Vector3D resta = Vector3D.restarVectores(vector1, vector1);
        double v1 = resta.getV1();
        double v2 = resta.getV2();
        double v3 = resta.getV3();
        Assert.assertEquals(v1, 0.0, 0.005);
        Assert.assertEquals(v2, 0.0, 0.005);
        Assert.assertEquals(v3, 0.0, 0.005);   
        
    }
    
    @Test
    public void tesMultiplicarVec3porEscalar(){
        Vector3D vector=new Vector3D (1.0, 2.0, 3.0);
        double escalar=2.0;
        vector=Vector3D.multiplicarVectorPorEscalar(vector, escalar);
        double v1 = vector.getV1();
        double v2 = vector.getV2();
        double v3 = vector.getV3();
        Assert.assertEquals(v1, 2.0, 0.005);
        Assert.assertEquals(v2, 4.0, 0.005);
        Assert.assertEquals(v3, 6.0, 0.005);   
    }
    
    
    @Test
    public void testDividirEstaticoVec3porEscalar(){
        Vector3D vector=new Vector3D (1.0, 2.0, 3.0);
        double escalar=2.0;
        Vector3D resultado = Vector3D.dividirVectorPorEscalar(vector, escalar);
        double d1=resultado.getV1();
        double d2=resultado.getV2();
        double d3=resultado.getV3();
        Assert.assertEquals(d1, 0.5, 0.05);
        Assert.assertEquals(d2, 1.0, 0.05);
        Assert.assertEquals(d3, 1.5, 0.05);
    }
    @Test
    public void tesMultiplicarEstaticoVec3porEscalar(){
        Vector3D vector=new Vector3D (1.0, 2.0, 3.0);
        double escalar=2.0;
        Vector3D resultado = Vector3D.multiplicarVectorPorEscalar(vector, escalar);
        
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
        Vector3D vector=new Vector3D (1.0, 2.0, 3.0);
        longitud=vector.getLongitud();
        Assert.assertEquals(longitud, 3.74,0.005);
        Vector3D vectorUnitario = vector.vectorUnitario();
        longitud = vectorUnitario.getLongitud();
        Assert.assertEquals(longitud, 1,0.005);
    }
    
    @Test 
    public void testProductoEscalar(){
        Vector3D v1=new Vector3D (1.0, 0.0, 0.0);
        Vector3D v2=new Vector3D (0.0, 1.0, 0.0);
        double productoEscalar = Vector3D.productoEscalar(v1, v2);
        Assert.assertEquals(0.0,productoEscalar , 0.001);
    }
    
    @Test 
    public void testRestaMultiple(){
        Vector3D v1=new Vector3D (1.0, 0.0, 0.0);
        Vector3D v2=new Vector3D (0.0, 1.0, 0.0);
        Vector3D v3=new Vector3D (0.0, 1.0, 0.0);
        Vector3D resultado = Vector3D.restarVectores(v1, v2, v3);
        Assert.assertEquals(1.0,resultado.getV1() , 0.001);
        Assert.assertEquals(-2.0,resultado.getV2() , 0.001);
        Assert.assertEquals(0.0,resultado.getV3() , 0.001);
    }
    
    @Test 
    public void testRestaMultiple2(){
        Vector3D v1=new Vector3D (1.0, 0.0, 0.0);
        Vector3D v2=new Vector3D (0.0, 1.0, 0.0);
        Vector3D v3=new Vector3D (0.0, 1.0, 0.0);
        Vector3D v4=new Vector3D (0.0, 1.0, -1.0);
        Vector3D resultado = Vector3D.restarVectores(v1, v2, v3, v4);
        Assert.assertEquals(1.0,resultado.getV1() , 0.001);
        Assert.assertEquals(-3.0,resultado.getV2() , 0.001);
        Assert.assertEquals(1.0,resultado.getV3() , 0.001);
    }
    
    @Test
    public void testVectorUnitario(){
        Vector3D v1=new Vector3D(2, 3, 4);
        Vector3D vectorUnitario;
        vectorUnitario = v1.vectorUnitario();
        System.out.println(vectorUnitario.toString());
    }
    @BeforeMethod
    public void setUpMethod() throws Exception {
    }
}
