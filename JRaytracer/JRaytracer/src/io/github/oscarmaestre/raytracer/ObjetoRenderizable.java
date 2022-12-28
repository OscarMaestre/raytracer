/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.oscarmaestre.raytracer;

/**
 *
 * @author usuario
 */
public abstract class ObjetoRenderizable {
    public  abstract PuntoAlcanzadoPorRayo esAlcanzadaPorRayo(
            Rayo rayo, double t_minimo, double t_maximo);
    
    
}
