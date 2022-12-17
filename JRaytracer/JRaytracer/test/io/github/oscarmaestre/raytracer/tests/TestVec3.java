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
    public void testVectorUnitario(){
        Vec3 v1=new Vec3(2, 3, 4);
        Vec3 vectorUnitario = Vec3.vectorUnitario(v1);
        System.out.println(vectorUnitario.toString());
    }
    @BeforeMethod
    public void setUpMethod() throws Exception {
    }
}
