import unittest
import Vec3
from rich.progress import track

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
    
    def procesar_pixeles(self, funcion):
        """Para cada pixel (x, y) se ejecutará una funcion f(x, y, imagen)
        que procese ambos pixeles de esa imagen y devuelva el color
        que corresponda a ese pixel"""
        for cy in track(range(0, self.alto), "Lineas..."):
            for cx in range(0, self.ancho):
                color=funcion(cx, cy, self)
                self.set_color(cx, cy, color)
    
    def set_color(self, cx, cy, color):
        if cx<0 or cx>=self.ancho:
            raise Exception()
        if cy<0 or cy>=self.alto:
            raise Exception()
        self.puntos[self.alto-cy-1][cx]=color
            
    def get_puntos_rgb(self):
        cadena=[]
        """Para cada pixel (x, y) se ejecutará una funcion f(x, y)
        que procese ambos pixeles"""
        for cy in range(0, self.alto):
            for cx in range(0, self.ancho):
                cadena.append(self.puntos[cy][cx].get_rgb())
        return "\n".join(cadena)
    def get_ppm(self):
        archivo_ppm=[]
        archivo_ppm.append("P3")
        archivo_ppm.append("{0} {1}".format(self.ancho, self.alto))
        archivo_ppm.append("255")
        archivo_ppm.append(self.get_puntos_rgb())
        return "\n".join(archivo_ppm)
    
    def guardar_imagen(self, nombre_archivo):
        with open(nombre_archivo, "w") as fich:
            fich.write(self.get_ppm())

def imprimir_coordenadas(x, y, imagen):
    formato="<{0},{1}>".format(x, y)
    print(formato)

def generar_gradiente(x, y, imagen):
    rojo  = y / (imagen.alto-1)
    verde = x/(imagen.ancho-1)
    azul  = 0.5
    color = Vec3.Vec3(rojo, verde, azul)
    return color

class TestImagen(unittest.TestCase):
    def test_creacion(self):
        imagen=Imagen(400, 225)
        self.assertEqual(400, imagen.ancho)
        self.assertEqual(225, imagen.alto)
    def test_recorrido(self):
        imagen=Imagen(4, 3)
        imagen.procesar_pixeles(imprimir_coordenadas)

    def test_imagen_negro(self):
        imagen=Imagen(4, 3)
        imagen.guardar_imagen("negro.ppm")

    def test_imagen_variada(self):
        imagen=Imagen(4, 3)
        imagen.set_color(0, 0, Vec3.Vec3.get_blanco())
        imagen.set_color(3, 2, Vec3.Vec3.get_rojo())
        imagen.guardar_imagen("pixeles_variados.ppm")
    
    def test_gradiente(self):
        imagen=Imagen(256, 256)
        imagen.procesar_pixeles(generar_gradiente)
        imagen.guardar_imagen("gradiente.ppm")
if __name__=="__main__":
    unittest.main()