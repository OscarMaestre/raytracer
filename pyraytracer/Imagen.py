import unittest
import Vec3
import Rayo
from rich.progress import track
from Camara import Camara
from Objeto import Escena
from random import random, randint
from Materiales import MaterialLambertiano

class Imagen(object):
    def __init__(self, ancho, alto) -> None:
        self._ancho=ancho
        self._alto=alto
        self.puntos=[[Vec3.Vec3() for x in range(0, self.ancho)] for y in range(0, self._alto)]
    @property
    def ancho(self):
        return self._ancho
    @property
    def alto(self):
        return self._alto
    
    def procesar_pixeles(self, escena, funcion):
        """Para cada pixel (x, y) se ejecutará una funcion f(rayo, escena)
        que procese ambos pixeles de esa imagen y devuelva el color
        que corresponda a ese pixel"""
        camara=Camara()
        for cy in track(range(0, self.alto), "Generando imagen sin AA..."):
            for cx in range(0, self.ancho):
                u=cx/(self.ancho-1)
                v=cy/(self.alto-1)
                rayo=camara.get_rayo(u, v)
                color=funcion(rayo, escena)
                self.set_color(cx, cy, color)

    def perturbar_valor(self, coordenada, maximo):
        variacion=random()
        coordenada_perturbada=coordenada + randint(0, 1)
        return coordenada_perturbada/(maximo-1)

    def procesar_pixeles_con_aa(self, escena, funcion, num_muestras=30):
        """Para cada pixel (x, y) se ejecutará una funcion f(rayo, escena, 100)
        que procese el pixel de esa imagen y devuelva el color
        que corresponda a ese pixel. Esta variante incluye el
        antialiasing (suavizado de contornos) y una profundidad
        maxima de rebote de 100"""
        camara=Camara()
        for cy in track(range(0, self.alto), "Generando imagen con AA..."):
            for cx in range(0, self.ancho):
                color=Vec3.Vec3.get_negro()
                for _ in range(0, num_muestras):
                    u=self.perturbar_valor(cx, self.ancho)
                    v=self.perturbar_valor(cy, self.alto)
                    
                    rayo=camara.get_rayo(u, v)
                    color_punto=funcion(rayo, escena, 50)
                    color=Vec3.sumar_vectores(color, color_punto)
                #El bucle AA ha terminado, dividimos
                #el color por el número de muestras
                #y lo registramos
                color=Vec3.dividir_por_escalar(color, num_muestras)
                self.set_color(cx, cy, color)
    
    def set_color(self, cx, cy, color):
        """Cambia el color de un pixel"""
        if cx<0 or cx>=self.ancho:
            raise Exception()
        if cy<0 or cy>=self.alto:
            raise Exception()
        """Nuestro origen de coordenadas está abajo
        por eso hacemos self.alto-cy-y"""
        self.puntos[self.alto-cy-1][cx]=color
            
    def get_puntos_rgb(self):
        """Para cada pixel (x, y) se ejecutará una funcion f(x, y)
        que procese ambos pixeles"""
        cadena=[]
        for cy in range(0, self.alto):
            for cx in range(0, self.ancho):
                cadena.append(self.puntos[cy][cx].get_rgb())
        return "\n".join(cadena)

    def get_ppm(self):
        """Devuelve los puntos en formato PPM"""
        archivo_ppm=[]
        archivo_ppm.append("P3")
        archivo_ppm.append("{0} {1}".format(self.ancho, self.alto))
        archivo_ppm.append("255")
        archivo_ppm.append(self.get_puntos_rgb())
        return "\n".join(archivo_ppm)
    
    def guardar_imagen(self, nombre_archivo):
        """Guarda la imagen actual en un archivo PPM"""
        with open(nombre_archivo, "w") as fich:
            fich.write(self.get_ppm())

def imprimir_coordenadas(x, y, imagen):
    """Funcion usada solo en las pruebas para probar
    el recorrido de la imagen"""
    formato="<{0},{1}>".format(x, y)
    print(formato)

def generar_gradiente(x, y, imagen):
    """Función usada solo en las pruebas para
    probar la generación de archivos PPM"""
    rojo  = x/(imagen.ancho-1)
    verde = y / (imagen.alto-1)
    azul  = 0.5
    color = Vec3.Vec3(rojo, verde, azul)
    return color

#Usado solo en las pruebas, no es realista
def color_rayo(rayo, escena):
    registro_alcance=escena.es_alcanzado(rayo, 0.00001, 10000)
    if registro_alcance!=None:
        return Vec3.Vec3.get_blanco()
    return Vec3.Vec3.get_azul()

def color_rayo_con_rebote(rayo, escena, profundidad):
    if profundidad<=0:
        return Vec3.Vec3.get_negro()
    
    registro_alcance=escena.es_alcanzado(rayo, 0.00001, 10000)
    if registro_alcance!=None:
        material=registro_alcance.material
        rayo_reflejado=material.refleja_rayo(rayo, registro_alcance)
        color_rayo=rayo_reflejado.color
        color_siguiente_rebote=color_rayo_con_rebote(
            rayo_reflejado, escena, profundidad-1)
        color_final =Vec3.producto_simple(color_siguiente_rebote, color_rayo)
        # print("Multiplicamos "+str(color_rayo) + " por " +str(color_siguiente_rebote)+
        #     " y sale " + str(color_final))
        # print("hubo alcance y el color es:"+color_final.get_rgb())
        return color_final
    return Vec3.Vec3.get_blanco()


class TestImagen(unittest.TestCase):
    def test_creacion(self):
        imagen=Imagen(400, 225)
        self.assertEqual(400, imagen.ancho)
        self.assertEqual(225, imagen.alto)
    
    def test_imagen_negro(self):
        imagen=Imagen(4, 3)
        imagen.guardar_imagen("negro.ppm")

    def test_imagen_variada(self):
        imagen=Imagen(4, 3)
        imagen.set_color(0, 0, Vec3.Vec3.get_blanco())
        imagen.set_color(3, 2, Vec3.Vec3.get_rojo())
        imagen.guardar_imagen("pixeles_variados.ppm")
    
    
    def test_escena_esfera_simple(self):
        mat_lambert=MaterialLambertiano(Vec3.Vec3(0.5, 0.6, 0.9))
        imagen=Imagen(400, 225)
        escena=Escena()
        centro_esfera=Vec3.Vec3(0, 0, -1)
        escena.addEsfera(centro_esfera, 0.5, mat_lambert)
        imagen.procesar_pixeles(escena, color_rayo)
        imagen.guardar_imagen("esfera.ppm")


    def test_escena_esfera_con_material(self):
        mat_lambert=MaterialLambertiano(Vec3.Vec3(0.30, 0.9, 0.9))
        imagen=Imagen(400, 225)
        escena=Escena()
        centro_esfera=Vec3.Vec3(0, 0, -1)
        escena.addEsfera(centro_esfera, 0.5, mat_lambert)
        imagen.procesar_pixeles_con_aa(escena, color_rayo_con_rebote)
        imagen.guardar_imagen("esfera_con_material.ppm")

    def test_escena_esfera_con_material_aa(self):
        mat_lambert=MaterialLambertiano(Vec3.Vec3(0.30, 0.9, 0.9))
        imagen=Imagen(400, 225)
        escena=Escena()
        centro_esfera=Vec3.Vec3(0, 0, -1)
        escena.addEsfera(centro_esfera, 0.5, mat_lambert)
        imagen.procesar_pixeles_con_aa(escena, color_rayo_con_rebote)
        imagen.guardar_imagen("esfera_con_material_aa.ppm")

    def test_rayo_simple(self):
        mat_lambert=MaterialLambertiano(Vec3.Vec3(0.30, 1.0, 0.9))
        imagen=Imagen(200, 112)
        escena=Escena()
        centro_esfera=Vec3.Vec3(0, 0, -1)
        escena.addEsfera(centro_esfera, 0.5, mat_lambert)
        rayo=Rayo.Rayo(Vec3.Vec3(0, 0, 0), Vec3.Vec3(0, 0.1, -1))
        rayo_reflejado=color_rayo_con_rebote(rayo, escena, 1)
        print(rayo_reflejado.get_rgb())
    def test_valor_perturbado(self):
        imagen=Imagen(200, 112)
        perturbado=imagen.perturbar_valor(199, 200)
        self.assertEqual(20, perturbado)

def generar_imagen_1():
    
    imagen=Imagen(400, 225)
    
    escena=Escena()
    #Esfera 1
    mat_lambert1=MaterialLambertiano(Vec3.Vec3(0.30, 0.9, 0.9))
    centro_esfera=Vec3.Vec3(0, 0, -1)
    escena.addEsfera(centro_esfera, 0.5, mat_lambert1)

    mat_lambert2=MaterialLambertiano(Vec3.Vec3(0.1, 0.25, 0.15))
    centro_esfera2=Vec3.Vec3(0, -100.5, -1)
    escena.addEsfera(centro_esfera2, 100, mat_lambert2)
    imagen.procesar_pixeles_con_aa(escena, color_rayo_con_rebote, num_muestras=20)
    imagen.guardar_imagen("esferas_imagen_1.ppm")


if __name__=="__main__":
    # unittest.main()
    generar_imagen_1()