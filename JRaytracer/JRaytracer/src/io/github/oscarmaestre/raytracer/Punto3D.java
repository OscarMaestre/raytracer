package io.github.oscarmaestre.raytracer;

public class Punto3D extends Vec3 {    
    public Punto3D(double d1, double d2, double d3) {
        super(d1, d2, d3);
    }
    /* Construye un punto a partir de un 
    vec3 */
    public static Punto3D fromVec3(Vec3 v){
        double d1 = v.getV1();
        double d2 = v.getV2();
        double d3 = v.getV3();
        return new Punto3D(d1, d2, d3);
    }

    @Override
    public Punto3D getCopia() {
        double d1 = this.getV1();
        double d2 = this.getV2();
        double d3 = this.getV3();
        return new Punto3D(d1, d2, d3);
    }
    
}
