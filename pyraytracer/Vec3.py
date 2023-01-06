import unittest
import math

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
        longitud_cuadrado=self.get_longitud_cuadrado
        return math.sqrt(longitud_cuadrado)
    
    def get_rgb(self):
        """Devuelve el RGB correspondiente a este vector"""
        rojo = int(self.x*255.999)
        verde= int(self.y*255.999)
        azul = int(self.z*255.999)
        formato="{0} {1} {2}".format(rojo, verde, azul)
        return formato

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

if __name__=="__main__":
    unittest.main()