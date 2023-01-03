package io.github.oscarmaestre.trazadorayos;

public class Camara {
    private final double anchura;
    private final double altura;
    private final double ratio_aspecto;
    private final double distancia_focal;
    private final Vec3  origen;
    private final Vec3 vectorHorizontal;
    private final Vec3 vectorVertical;
    private final Vec3 vectorProfundidad;
    private final Vec3 esquinaInferiorIzquierda;
    public static final double RATIO_ESTANDAR=16.0/9.0;
    public Camara(double altura, double ratio_aspecto, Vec3 origen, double df) {
        this.altura = altura;
        this.ratio_aspecto = ratio_aspecto;
        this.origen = origen;
        this.distancia_focal = df;
        this.anchura = this.altura * this.ratio_aspecto;
        this.vectorHorizontal=new Vec3(this.anchura, 0.0, 0.0);
        this.vectorVertical = new Vec3(0.0, this.altura, 0.0);
        this.vectorProfundidad=new Vec3(0.0, 0.0, df);
        this.esquinaInferiorIzquierda=this.getEsquinaInferiorIzquierda();
    }
    
    private Vec3 getEsquinaInferiorIzquierda(){
        Vec3 esquina = origen.getCopia();
        Vec3 semiHorizontal = Vec3.dividirPorDouble(this.vectorHorizontal, 2.0);
        Vec3 semiVertical = Vec3.dividirPorDouble(this.vectorVertical, 2.0);
        
        esquina.restar(semiHorizontal);
        esquina.restar(semiVertical);
        esquina.restar(vectorProfundidad);
        
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
        return new Camara(2.0, Camara.RATIO_ESTANDAR, origen, 1.0);
    }
}
