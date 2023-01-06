package io.github.oscarmaestre.trazadorayos;

public class Camara {
    private final double anchura;
    private final double altura;
    private final double ratio_aspecto;
    
    private final Vec3  origen;
    private final Vec3 vectorHorizontal;
    private final Vec3 vectorVertical;
    
    private final Vec3 esquinaInferiorIzquierda;
    public static final double RATIO_ESTANDAR=16.0/9.0;
    public Camara(double ratio_aspecto, Vec3 origen, 
            double vfov_en_grados, Vec3 lookfrom, Vec3 lookat, Vec3 vup) {
        double radianes_en_vertical = Math.toRadians(vfov_en_grados);
        System.out.println("Los radianes son:"+radianes_en_vertical);
        double h=Math.tan(radianes_en_vertical/2.0);
        
        this.ratio_aspecto = ratio_aspecto;
        this.origen = lookfrom.getCopia();
        
        this.altura=(double)(2.0*h);
        this.anchura = this.altura * this.ratio_aspecto;
        
        Vec3 w=this.getW(lookfrom, lookat);
        Vec3 u=this.getU(vup, w);
        Vec3 v=this.getV(w, u);
        
        
        this.vectorHorizontal=Vec3.multiplicarVectores(new Vec3(this.anchura, 0.0, 0.0), u);        
        this.vectorVertical = Vec3.multiplicarVectores(new Vec3(0.0, this.altura, 0.0), v);
        
        this.esquinaInferiorIzquierda=this.getEsquinaInferiorIzquierda(w);
        
    }
    
    private Vec3 getEsquinaInferiorIzquierda(Vec3 w){
        Vec3 esquina = origen.getCopia();
        Vec3 semiHorizontal = Vec3.dividirPorDouble(this.vectorHorizontal, 2.0);
        Vec3 semiVertical = Vec3.dividirPorDouble(this.vectorVertical, 2.0);
        
        esquina.restar(semiHorizontal);
        esquina.restar(semiVertical);
        esquina.restar(w);
        
        return esquina;       
    }
    
    public Rayo getRayo(double u, double v){
        Vec3 direccionRayo=this.esquinaInferiorIzquierda.getCopia();
        Vec3 parteHorizontal=Vec3.multiplicarPorDouble(vectorHorizontal, u);
        Vec3 parteVertical=Vec3.multiplicarPorDouble(vectorVertical, v);
        direccionRayo.sumar(parteHorizontal);
        direccionRayo.sumar(parteVertical);
        direccionRayo.restar(origen);
        
        Vec3 origenRayo=origen.getCopia();
        return new Rayo (origenRayo, direccionRayo);
    }
    public static Camara getCamara(){
        Vec3 origen=new Vec3(0.0, 0.0, 0.0);
        Vec3 lookfrom=new Vec3(-2, 2, 1);
        Vec3 lookat=new Vec3(0, 0, -1);
        Vec3 vup=new Vec3(0, 1, 0);
        return new Camara(Camara.RATIO_ESTANDAR, origen, 90.0,
        lookfrom, lookat, vup);
    }

    private Vec3 getW(Vec3 lookfrom, Vec3 lookat) {
        Vec3 copiaFrom=lookfrom.getCopia();
        copiaFrom.restar(lookat);
        Vec3 w = Vec3.vectorUnitario(copiaFrom);
        return w;
    }

    private Vec3 getU(Vec3 vup, Vec3 w) {
        Vec3 copia = vup.getCopia();
        Vec3 productoCruzado = Vec3.productoCruzado(copia, w);
        Vec3 u = Vec3.vectorUnitario(productoCruzado);
        return u;
    }
    public Vec3 getV(Vec3 w, Vec3 u){
        Vec3 v = Vec3.productoCruzado(w, u);
        return v;
    }
}
