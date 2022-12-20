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
    
    public PuntoAlcanzadoPorRayo rayoGolpeaObjeto(){
        PuntoAlcanzadoPorRayo punto=null;
        
        return punto;
    }
}
