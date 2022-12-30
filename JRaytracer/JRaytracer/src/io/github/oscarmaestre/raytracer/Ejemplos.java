package io.github.oscarmaestre.raytracer;
public class Ejemplos {
    public static Imagen generarImagenAzul(){
        Camara viewportSimple = Camara.getCamaraSimple();
        final int anchoPx=viewportSimple.getAnchoPixelesReales();
        final int altoPx =viewportSimple.getAltoPixelesReales();
        Imagen imagen=new Imagen(anchoPx, altoPx);
        for (int cy=0; cy<altoPx; cy++){
            for (int cx=0; cx<anchoPx; cx++){
                Rayo rayoHacia = viewportSimple.getRayoHacia(cx, cy);
                Color c=Color.getAzul();
                imagen.setColor(cx, cy, c);
            } //Fin del for para cx
        } //Fin del for para cy
        return imagen;
    } //Fin del método generarImagenAzul
    public static Imagen generarGradiente(Color base){
        Camara viewportSimple = Camara.getCamaraSimple();
        final int anchoPx=viewportSimple.getAnchoPixelesReales();
        final int altoPx =viewportSimple.getAltoPixelesReales();
        Imagen imagen=new Imagen(anchoPx, altoPx);
        /*Dividimos el blanco en tantos grados como altura tenga
        la imagen. Ese "incremento de blanco, será lo que iremos sumando
        en cada fila*/
        Color incremento;
        incremento=Color.from(Color.dividirVectorPorEscalar(
                Color.getBlanco(), (double)altoPx));
        for (int cy=0; cy<altoPx; cy++){
            base=Color.sumar(base, incremento);
            for (int cx=0; cx<anchoPx; cx++){
                Rayo rayoHacia = viewportSimple.getRayoHacia(cx, cy);
                imagen.setColor(cx, cy, base);
            } //Fin del for para cx
        } //Fin del for para cy
        return imagen;
    }
    
    public static Imagen getEsferaColoreadaSegunNormal(){
        
        Camara viewportSimple = Camara.getCamaraSimple();
        final int anchoPx=viewportSimple.getAnchoPixelesReales();
        final int altoPx =viewportSimple.getAltoPixelesReales();
        Imagen imagen=new Imagen(anchoPx, altoPx);
        Punto3D centroEsfera=new Punto3D(0.0, 0.0, -1);
        Esfera esfera=new Esfera(centroEsfera, 0.5);
        for (int cy=0; cy<altoPx; cy++){
            for (int cx=0; cx<anchoPx; cx++){
                Color colorEnPixel;
                Rayo rayoHacia = viewportSimple.getRayoHacia(cx, cy);
                PuntoAlcanzadoPorRayo esAlcanzadaPorRayo;
                esAlcanzadaPorRayo = esfera.esAlcanzadaPorRayo(rayoHacia, 0, 50000);
                if (esAlcanzadaPorRayo!=null){
                    colorEnPixel=esfera.colorRayoSegunNormalPerpendicular(
                            rayoHacia, esAlcanzadaPorRayo);
                } else {
                    colorEnPixel=Color.getBlanco();
                }
                imagen.setColor(cx, cy, colorEnPixel);
            } //Fin del for para cx
        } //Fin del for para cy
        return imagen;
    }
}
