import Vec3
import unittest
class Rayo(object):
    def __init__(self, origen, direccion) -> None:
        self._origen    = origen
        self._direccion = direccion
    
    @property
    def origen(self):
        return self._origen
    
    @property
    def direccion(self):
        return self._direccion
    
    def punto_final(self, t):
        incremento = Vec3.multiplicar_por_escalar(self.direccion, t)
        resultado  = Vec3.sumar_vectores(self.origen, incremento)
        return resultado

class RayoReflejado(Rayo):
    def __init__(self, origen, direccion, color, registro_alcance) -> None:
        super().__init__(origen, direccion)
        self._color            = color
        self._registro_alcance = registro_alcance
    
    @property
    def color(self):
        return self._color

    @property
    def registro_alcance(self):
        return self._registro_alcance

class TestRayo(unittest.TestCase):
    def test_punto_final(self):
        origen    = Vec3.Vec3()
        direccion = Vec3.Vec3(1, 1, 1)
        rayo      = Rayo(origen, direccion)
        final     = rayo.punto_final(2)
        self.assertEqual(final.x, 2)
        self.assertEqual(final.y, 2)
        self.assertEqual(final.z, 2)


if __name__=="__main__":
    unittest.main()