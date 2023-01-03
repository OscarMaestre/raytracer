package io.github.oscarmaestre.raytracer.tests;

import io.github.oscarmaestre.trazadorayos.Esfera;
import io.github.oscarmaestre.trazadorayos.Vec3;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author usuario
 */
public class TestNuevaEsfera {
    
    public TestNuevaEsfera() {
    }

    @Test
    public void hello() {
        Vec3 origen=new Vec3(0, 0, -1.0);
        Esfera e=new Esfera(origen, 105, new Vec3 (1, 0, 0));
    }
}
