import unittest
import math
from random import random

class Vec3(object):
    def __init__(self, x=0, y=0 , z=0) -> None:
        self._x=x
        self._y=y
        self._z=z

    @property
    def x(self):
        return self._x
    
    @property
    def y(self):
        return self._y

    @property
    def z(self):
        return self._z

    def copiar(self):
        copia=Vec3(self._x, self._y, self._z)
        return copia
    
    def get_longitud_cuadrado(self):
        """Devuelve la longitud al cuadrado"""
        cuadrado_x=self._x*self._x
        cuadrado_y=self._y*self._y
        cuadrado_z=self._z*self._z
        return cuadrado_x+cuadrado_y+cuadrado_z
    
    def get_longitud(self):
        """Devuelve la longitud del vector"""
        longitud_cuadrado=self.get_longitud_cuadrado()
        return math.sqrt(longitud_cuadrado)

    def recortar(self, valor):
        """Limita el valor a [0.0, 1.0] recortando si es necesario"""    
        if valor<0:
            return 0
        if valor>=1.0:
            return 1.0
        return valor

    def get_rgb(self):
        """Devuelve el RGB correspondiente a este vector"""
        rojo = int(self.recortar(self.x)*255.999)
        verde= int(self.recortar(self.y)*255.999)
        azul = int(self.recortar(self.z)*255.999)
        formato="{0} {1} {2}".format(rojo, verde, azul)
        return formato
    
    def get_rgb_con_gamma_corregido(self):
        """Devuelve el RGB correspondiente a este vector
        pero con el gamma corregido"""
        rojo  = math.sqrt(self.x)
        verde = math.sqrt(self.x)
        azul  = math.sqrt(self.x)

        rojo  = self.recortar(rojo)  * 256
        verde = self.recortar(verde) * 256
        azul  = self.recortar(azul)  * 256

        rojo  = int(rojo)
        verde = int(verde)
        azul  = int(azul)
        
        formato="{0} {1} {2}".format(rojo, verde, azul)
        return formato

    def __str__(self) -> str:
        cadena="<{0}, {1}, {2}>".format(self.x, self.y, self.z)
        return cadena

    @staticmethod
    def get_vector_azar():
        """Genera un vector al azar con componentes entre -1 y 1"""
        x=(2*random())-1
        y=(2*random())-1
        z=(2*random())-1
        return Vec3(x, y, z)

    @staticmethod
    def get_vector_en_esfera_unidad():
        """Genera vectores al azar hasta que uno est?? dentro
        de la esfera de radio unidad"""
        while True:
            vector_azar=Vec3.get_vector_azar()
            if vector_azar.get_longitud_cuadrado()<=1:
                return vector_azar
        #Fin del while
    
    @staticmethod
    def get_vector_azar_en_hemisferio(normal):
        vector_en_esfera=Vec3.get_vector_en_esfera_unidad()
        if producto_escalar(vector_en_esfera, normal)>0.0:
            return vector_en_esfera
        return cambiar_signo(vector_en_esfera)

    @staticmethod
    def get_azul():
        return Vec3(0.0, 0.0, 1.0)
    
    @staticmethod
    def get_rojo():
        return Vec3(1.0, 0.0, 0.0)
    
    @staticmethod
    def get_verde():
        return Vec3(0.0, 1.0, 0.0)
    
    @staticmethod
    def get_negro():
        return Vec3(0.0, 0.0, 0.0)

    @staticmethod
    def get_blanco():
        return Vec3(1.0, 1.0, 1.0)

def sumar_vectores(v1, v2):
    """Devuelve la suma de los vectores"""
    suma=Vec3(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z)
    return suma

def cambiar_signo(v1):
    """Cambia el signo del vector"""
    invertido=Vec3(-v1.x, -v1.y, -v1.z)
    return invertido

def restar_vectores(v1, v2):
    """Devuelve el resultado v1 - v2"""
    v2_invertido=cambiar_signo(v2)
    resta=sumar_vectores(v1,v2_invertido)
    return resta

def multiplicar_por_escalar(v, t):
    """Multiplica el vector por un escalar"""
    copia=v.copiar()
    resultado=Vec3(t*copia.x, t*copia.y, t*copia.z)
    return resultado

def dividir_por_escalar(v, t):
    """Divide el vector por un escalar"""
    copia=v.copiar()
    resultado=multiplicar_por_escalar(v, 1/t)
    return resultado

def producto_simple(v1, v2):
    """Producto simple de los componentes"""
    nuevo_x = v1.x * v2.x
    nuevo_y = v1.y * v2.y
    nuevo_z = v1.z * v2.z
    return Vec3(nuevo_x, nuevo_y, nuevo_z)

def producto_escalar(v1, v2):
    """Producto escalar de los vectores"""
    suma_x    = v1.x * v2.x
    suma_y    = v1.y * v2.y
    suma_z    = v1.z * v2.z
    resultado = suma_x + suma_y + suma_z
    return resultado

def producto_vectorial(v1, v2):
    """Producto escalar de los vectores"""
    
    nuevo_x = (v1.y * v2.z) - (v1.z * v2.y)
    nuevo_y = (v1.z * v2.x) - (v1.x * v2.z)
    nuevo_z = (v1.x * v2.y) - (v1.y * v2.x)
    return Vec3(nuevo_x, nuevo_y, nuevo_z)
    
def vector_unitario(vector):
    """Devuelve el vector unitario correspondiente"""
    longitud  = vector.get_longitud()
    resultado = dividir_por_escalar(vector, longitud)
    return resultado

def vector_es_casi_cero(vector : Vec3):
    x_es_casi_cero=(vector.x<1e-8)
    y_es_casi_cero=(vector.y<1e-8)
    z_es_casi_cero=(vector.z<1e-8)
    casi_todo_es_cero=x_es_casi_cero and y_es_casi_cero and z_es_casi_cero
    return casi_todo_es_cero

def apuntan_en_la_misma_direccion (v1 : Vec3, v2 : Vec3):
    prod_escalar=producto_escalar(v1, v2)
    return prod_escalar>0

class TestVectores(unittest.TestCase):
    def test_creacion(self):
        v1=Vec3(2.0, 4.0, -5.0)
        self.assertEqual(2.0, v1._x)
        self.assertEqual(4.0, v1._y)
        self.assertEqual(-5.0, v1._z)
    
    def test_sumar_vectores(self):
        v1=Vec3(2.0, 4.0, -5.0)
        v2=Vec3(4, 6, -2)
        suma=sumar_vectores(v1, v2)
        self.assertEqual(6.0, suma._x)
        self.assertEqual(10.0, suma._y)
        self.assertEqual(-7.0, suma._z)
    
    def test_restar_vectores(self):
        v1=Vec3(2.0, 4.0, -5.0)
        v2=Vec3(4, 6, -2)
        resta=restar_vectores(v1, v2)
        self.assertEqual(-2.0, resta._x)
        self.assertEqual(-2.0, resta._y)
        self.assertEqual(-3.0, resta._z)
    
    def test_multiplicar(self):
        v1=Vec3(2.0, 4.0, -5.0)
        resultado=multiplicar_por_escalar(v1, 0.5)
        self.assertEqual(1.0, resultado._x)
        self.assertEqual(2.0, resultado._y)
        self.assertEqual(-2.5, resultado._z)
    
    def test_multiplicar(self):
        v1=Vec3(2.0, 4.0, -5.0)
        resultado=dividir_por_escalar(v1, 0.5)
        self.assertEqual(4.0, resultado._x)
        self.assertEqual(8.0, resultado._y)
        self.assertEqual(-10, resultado._z)
    
    def test_cuadrado(self):
        v1=Vec3(2.0, 4.0, -5.0)
        longitud=v1.get_longitud_cuadrado()
        self.assertEqual(longitud, 45)
    
    def test_rgb(self):
        azul=Vec3.get_azul()
        rgb=azul.get_rgb()
        self.assertEqual("0 0 255", rgb)
    
    def test_producto_simple(self):
        v1=Vec3(2.0, 4.0, -5.0)
        v2=Vec3(1.5, 0.6, -2)
        resultado=producto_simple(v1, v2)
        self.assertEqual(3.0, resultado._x)
        self.assertEqual(2.4, resultado._y)
        self.assertEqual(10, resultado._z)

    def test_producto_escalar(self):
        v1=Vec3(2.0, 4.0, -5.0)
        v2=Vec3(1.5, 0.6, -2)
        resultado_esperado = 3+2.4+10
        resultado_obtenido = producto_escalar(v1, v2)
        self.assertEqual(resultado_esperado, resultado_obtenido)

    def test_producto_vectorial(self):
        v1=Vec3(1, 2, 3)
        v2=Vec3(2.5, 3.5, -4.5)
        resultado=producto_vectorial(v1, v2)
        self.assertEqual(-19.5, resultado._x)
        self.assertEqual(12, resultado._y)
        self.assertEqual(-1.5, resultado._z)

    def test_producto_unitario(self):
        v1=Vec3(2, -1.5, 3.25)
        resultado=vector_unitario(v1)
        self.assertAlmostEqual(0.48777, resultado._x, places=5)
        self.assertAlmostEqual(-0.36583, resultado._y, places=5)
        self.assertAlmostEqual(0.79262, resultado._z, places=5)
        
    def test_vector_unitario_azar(self):
        vector_unitario=Vec3.get_vector_en_esfera_unidad()
        self.assertTrue(vector_unitario.get_longitud_cuadrado()<=1)

    def test_vector_casi_cero(self):
        v=Vec3(1e-10, 1e-10, 1e-10)
        self.assertTrue(vector_es_casi_cero(v))

        v=Vec3(1e-10, 1e-10, 2)
        self.assertFalse(vector_es_casi_cero(v))

    def test_apuntan_misma_direccion(self):
        v1=Vec3(1, 2, 3)
        v2=Vec3(1, 2, -3)
        debe_dar_falso=apuntan_en_la_misma_direccion(v1, v2)
        self.assertEqual(debe_dar_falso, False)

if __name__=="__main__":
    unittest.main()