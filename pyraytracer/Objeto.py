import Vec3
import math
from RegistroAlcances import RegistroAlcances

class Objeto(object):
    def es_alcanzado(rayo, t_min, t_max, registro_alcance):
        pass

class Escena(object):
    def __init__(self) -> None:
        self._objetos=[]
    def addEsfera(self, centro, radio, material):
        esfera=Esfera(centro, radio, material)
        self._objetos.append(esfera)
    
    def es_alcanzado(self, rayo, t_min, t_max):
        for objeto in self._objetos:
            reg_alcance=objeto.es_alcanzado(rayo, t_min, t_max)
            if reg_alcance!=None:
                return reg_alcance
    
class Esfera(Objeto):
    def __init__(self, center, radio, material) -> None:
        self._center   = center
        self._radio    = radio
        self._material = material
    
    @property
    def radio(self):
        return self._radio
    
    @property
    def centro(self):
        return self._center

    @property
    def material(self):
        return self._material

    def get_normal_en_punto(self, punto):
        """Devuelve un vector con la normal en ese punto"""
        vector_diferencia = Vec3.restar_vectores(punto, self.centro)
        normal            = Vec3.dividir_por_escalar(vector_diferencia, self.radio)
        return normal

    def es_alcanzado(self, rayo, t_min, t_max):
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
        punto_alcance=rayo.punto_final(solucion)
        normal=self.get_normal_en_punto(punto_alcance)
        registro_alcances=RegistroAlcances(punto_alcance, normal, solucion)
        return registro_alcances