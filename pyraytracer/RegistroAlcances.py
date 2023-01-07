import unittest

class RegistroAlcances(object):
    def __init__(self, punto, normal, t) -> None:
        self._punto  = punto
        self._normal = normal
        self._t      = t
    
    @property
    def punto(self):
        return self._punto

    @property
    def normal(self):
        return self._normal

    @property
    def t(self):
        return self._t

    