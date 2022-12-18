Esferas
=============

Ecuación de la esfera
--------------------------------------------------------------------------------

Para una esfera de radio *R* situada en el origen *(0,0,0)* se sabe que un punto cualquiera *(x,y,z)* está en la esfera si *x^2 + y^2 + z^2 < R^2*. Si la esfera está en cualquier otro punto del espacio *(cx, cy, cz)* necesitaremos "moverla" al punto *(0,0,0)* y entonces la ecuación será *(x-cx)^2 + (y-cy)^2 + (z-cz)^2 < R^2*.

Como disponemos de código para trabajar con vectores podemos decir que si queremos comprobar si un punto *P* está dentro de una esfera de radio *R* y con centro en *C* podemos decir que *P* estará dentro si *(P-C)^2 < R^2* o lo que es igual *(P-C) * (P-C) < R^2* 

Como sabemos que nuestros rayos siguen la ecuación vectorial *P(t)=A+tB* donde A es el punto de origen y B el vector de dirección nuestra fórmula queda así: *(A+tB-C) * (A+tB-C) < R^2*

Si desarrollamos dicha ecuación nos queda *t^2*B*B + 2tB(A-C) + (A-C)*(A-C) - R^2 = 0*. En esta ecuación ocurre que:

* A, B y C son conocidos y constantes.
* R es conocidos
* t es una incógnita.

Por lo tanto, aquí tenemos una simple ecuación de segundo grado de tipo *ax^2 + bx + c*. Si la resolvemos podemos encontrarnos que:

* No hay solución: en ese caso el rayo no golpea la esfera.
* Hay una solución: el rayo alcanza la esfera exactamente en un punto.
* Hay dos soluciones: el rayo alcanza la esfera en dos puntos. En este caso, y si la esfera por ejemplo es de cristal, ocurre que el rayo atraviesa la esfera.

Volviendo a la ecuación *t^2*B*B + 2tB(A-C) + (A-C)*(A-C) - R^2 = 0* tenemos que:

* A es el punto de origen del rayo.
* B es la dirección del rayo.
* C es el centro de la esfera.
* R es el radio de la esfera.
* t es nuestra incógnita.

Si en *ax^2 + bx + c* la solución es *-b +- raíz(b^2-4ac) / 2a* y queremos ver las soluciones habrá que comprobar si el discriminante es mayor de 0. Los coeficientes en nuestra ecuación sería:

* a sería B*B. Es decir, la dirección del rayo multiplicada por sí misma.
* b sería 2B(A-C). O sea 2 por la dirección del rayo multiplicada por la resta del origen del rayo menos el centro de la esfera.
* c sería la (A-C) multiplicado por sí mismo y restando despues el radio al cuadrado.
