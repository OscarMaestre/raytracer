/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package io.github.oscarmaestre.raytracer.tests;

import io.github.oscarmaestre.raytracer.Color;
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

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }
}
