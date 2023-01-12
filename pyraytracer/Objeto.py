import Vec3
import math
from RegistroAlcances import RegistroAlcances
from Materiales import MaterialLambertiano
import unittest

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
        mas_cercano_hasta_ahora=t_max
        mejor_reg_alcance=None
        for objeto in self._objetos:
            reg_alcance=objeto.es_alcanzado(rayo, t_min, mas_cercano_hasta_ahora)
            if reg_alcance!=None:
                #print("Encontrado alcance")
                mejor_reg_alcance=reg_alcance
                mas_cercano_hasta_ahora=reg_alcance.t
            #Fin del if
        #Fin del for que recorre los objetos de la escena
        return mejor_reg_alcance
    
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
        registro_alcances=RegistroAlcances(punto_alcance, normal, solucion, self._material)
        return registro_alcances

class TestEsfera(unittest.TestCase):
    def test_normal(self):
        mat_lambert=MaterialLambertiano(Vec3.Vec3(0.5, 0.6, 0.9))
        
        centro_esfera=Vec3.Vec3(0, 0, -1)
        e=Esfera(centro_esfera, 0.5, mat_lambert)
        normal=e.get_normal_en_punto(Vec3.Vec3(0.1, 0.1, -1))
        print("Normal"+str(normal))



if __name__=="__main__":
    unittest.main()