package io.github.oscarmaestre.trazadorayos;

import java.util.ArrayList;


public class Escena {
    private ArrayList<ObjetoRenderizable> objetos;

    public Escena() {
        this.objetos=new ArrayList<>();
    }
    public void add(ObjetoRenderizable objeto){
        this.objetos.add(objeto);
    }
    public void addEsfera(double x, double y, double z, double radio, Material material){
        Vec3 centroEsfera=new Vec3(x, y, z);
        Esfera esfera=new Esfera(centroEsfera, radio, material);
        this.add(esfera);
    }
    public RegistroAlcance rayoGolpeaObjeto(Rayo rayo, double t_min, double t_max){
        RegistroAlcance punto=null;
        double masCercano=t_max;
        for (ObjetoRenderizable esfera : this.objetos){
            RegistroAlcance esAlcanzadaPorRayo;
            esAlcanzadaPorRayo = esfera.esAlcanzadaPorRayo(rayo,t_min, masCercano);
            if (esAlcanzadaPorRayo!=null){
                masCercano=esAlcanzadaPorRayo.getT();
                punto=esAlcanzadaPorRayo;
            }
        }
        return punto;
    }
    
    public static Escena getEscenaEjemploCamaraAerea(){
        Escena escena=new Escena();
        
        Material materialSuelo, materialCentro, materialIzq, materialDer;
        materialSuelo=new MaterialLambertiano(new Vec3(0.8, 0.8, 0.0));
        materialCentro=new MaterialLambertiano(new Vec3(0.7, 0.3, 0.3));
        materialIzq=new MaterialMetalico(new Vec3(0.8, 0.8, 0.8), 0.3);
        materialDer=new MaterialMetalico(new Vec3(0.8, 0.6, 0.2), 1.0);
        
        escena.addEsfera(0, -100.5, -1, 100, materialSuelo);
        escena.addEsfera(0,0,-1,0.5,materialCentro);
        escena.addEsfera(-1, 0, -1, 0.5, materialIzq);
        escena.addEsfera(1, 0, -1, 0.5, materialDer);
        return escena;
    }
}
