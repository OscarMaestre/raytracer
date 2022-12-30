package io.github.oscarmaestre.raytracer;

import java.util.ArrayList;

public class Escena {
    private ArrayList<ObjetoRenderizable> objetos;

    public Escena() {
        this.objetos=new ArrayList<>();
    }
    public void add(ObjetoRenderizable objeto){
        this.objetos.add(objeto);
    }
    public void addEsfera(double x, double y, double z, double radio){
        Punto3D centroEsfera=new Punto3D(x, y, z);
        Esfera esfera=new Esfera(centroEsfera, radio);
        this.add(esfera);
    }
    public PuntoAlcanzadoPorRayo rayoGolpeaObjeto(Rayo rayo, double t_min, double t_max){
        PuntoAlcanzadoPorRayo punto=null;
        double masCercano=t_max;
        for (ObjetoRenderizable esfera : this.objetos){
            PuntoAlcanzadoPorRayo esAlcanzadaPorRayo;
            esAlcanzadaPorRayo = esfera.esAlcanzadaPorRayo(rayo,t_min, masCercano);
            if (esAlcanzadaPorRayo!=null){
                masCercano=esAlcanzadaPorRayo.getT();
                punto=esAlcanzadaPorRayo;
            }
        }
        return punto;
    }
}
