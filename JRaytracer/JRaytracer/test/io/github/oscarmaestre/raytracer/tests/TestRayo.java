package io.github.oscarmaestre.raytracer.tests;

import io.github.oscarmaestre.raytracer.Punto3D;
import io.github.oscarmaestre.raytracer.Rayo;
import io.github.oscarmaestre.raytracer.Vector3D;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author usuario
 */
public class TestRayo {
    
    public TestRayo() {
    }

    @Test
    public void rayo() {
        Punto3D origen=new Punto3D(0,0,0);
        Vector3D    direccion=new Vector3D(1,1,1);
        Rayo r=new Rayo(origen, direccion);
        Punto3D posicion = r.getPosicion(2.0);
        Assert.assertEquals(posicion.getV1(), 2.0, 0.04);
        Assert.assertEquals(posicion.getV2(), 2.0, 0.04);
        Assert.assertEquals(posicion.getV3(), 2.0, 0.04);
    }
}
