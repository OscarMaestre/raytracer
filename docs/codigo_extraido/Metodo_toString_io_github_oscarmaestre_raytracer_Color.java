public String toString() {
    /* En formato PPM hay que mostrar los valores entre 0 y 255*/
    int r = (int) (this.getV1() * 255.999);
    int g = (int) (this.getV2() * 255.999);
    int b = (int) (this.getV3() * 255.999);
    /* En formato PPM mostraremos los colores en formato
        R G B (con un espacio entre medias y en ese orden */
    String cadena = String.format("%d %d %d", r, g, b);
    return cadena;
}
