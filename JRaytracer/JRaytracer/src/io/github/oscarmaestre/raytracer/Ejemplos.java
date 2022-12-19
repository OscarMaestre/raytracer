package io.github.oscarmaestre.raytracer;
public class Ejemplos {
    public static Imagen generarImagenAzul(){
        Viewport viewportSimple = Viewport.getViewportSimple();
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
}
