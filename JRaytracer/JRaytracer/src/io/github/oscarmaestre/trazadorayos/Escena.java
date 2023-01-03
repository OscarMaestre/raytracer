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
    public void addEsfera(double x, double y, double z, double radio, Vec3 color){
        Vec3 centroEsfera=new Vec3(x, y, z);
        Esfera esfera=new Esfera(centroEsfera, radio, color);
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
}
