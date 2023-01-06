import Vec3
import math

class Objeto(object):
    def es_alcanzado(rayo, t_min, t_max, registro_alcance):
        pass

class Esfera(Objeto):
    def __init__(self, center, radio) -> None:
        self._center = center
        self._radio  = radio
    
    @property
    def radio(self):
        return self._radio
    
    @property
    def centro(self):
        return self._center

    def es_alcanzado(self, rayo, t_min, t_max, registro_alcances):
        oc     = Vec3.restar_vectores(rayo.origen, self.centro)
        a      = rayo.direccion.get_longitud_cuadrado()
        semi_b = Vec3.producto_escalar(oc, rayo.direccion)
        c      = oc.get_longitud_cuadrado() - (self.radio*self.radio)

        discriminante=(semi_b*semi_b) - (a*c)
        if discriminante<0:
            return None
        raiz_cuadrada=math.sqrt(discriminante)

        solucion=(-semi_b-raiz_cuadrada) / a
        if solucion<t_min or t_max < solucion:
            #Si llegamos aquí la solución se ha pasado de los
            #limites, así que probamos la otra
            solucion=(-semi_b+raiz_cuadrada) / a
            if solucion<t_min or t_max < solucion:
                return None
        #Si llegamos aquí es que se encontró una
        #solución
        