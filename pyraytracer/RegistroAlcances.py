import unittest
from Materiales import MaterialLambertiano, Material
from Rayo import Rayo
from Vec3 import producto_escalar, cambiar_signo

class RegistroAlcances(object):
    def __init__(self, punto, normal, t, material : Material ) -> None:
        self._punto    = punto
        self._normal   = normal
        self._t        = t
        self._material = material
    
    def calcular_normal(self, rayo : Rayo):
        """Queremos que la normal al punto donde el rayo golpeo
        apunte siempre 'hacia afuera' de la superficie """
        if producto_escalar(rayo.direccion, self._normal)<0:
            self._normal=cambiar_signo(self._normal)

    @property
    def punto(self):
        return self._punto

    @property
    def normal(self):
        return self._normal

    @property
    def t(self):
        return self._t

    @property
    def material(self):
        return self._material

    