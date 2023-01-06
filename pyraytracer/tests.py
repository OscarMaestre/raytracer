import unittest
from Imagen import TestImagen
from Vec3 import TestVectores

if __name__=="__main__":
    suite=unittest.TestSuite()
    suite.addTest(TestImagen())
    suite.addTest(TestVectores())
    unittest.main()