package io.github.oscarmaestre.raytracer;

public class Punto3D extends Vector3D {    
    public Punto3D(double d1, double d2, double d3) {
        super(d1, d2, d3);
    }
    
    
    public static Punto3D fromVec3(Vector3D v){
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
