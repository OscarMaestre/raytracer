#!/usr/bin/python3

import time, math

ANSI_BORRADO_PANTALLA="\033[2J"
RATIO_ASPECTO               = 4
CARACTERES_ALTO_PANTALLA    =20
CARACTERES_ANCHO_PANTALLA   =CARACTERES_ALTO_PANTALLA * RATIO_ASPECTO


def muestra(x, y):
    if (x+y+int(time.time())) % 2 ==0:
        return "#"
    else:
        return " "

def renderizar_tablero():
    while True:
        caracteres=[]
        for y in range(0, 20):
            for x in range (0, 80):
                caracteres.append(muestra(x, y))
            caracteres.append("\n")
        pantalla="".join(caracteres)
        print(ANSI_BORRADO_PANTALLA)
        print(pantalla)
        time.sleep(1/30)

def dentro_de_circulo(x, y):
    radio=0.4
    distancia=math.sqrt( x**2 + y**2 )
    if distancia<=radio:
        return True
    return False

def dentro_de_esfera_3d(x, y , z):
    radio=0.4
    distancia=math.sqrt( x**2 + y**2 + z**2)
    if distancia<=radio:
        return True
    return False

def distancia_del_centro_de_la_esfera(x, y , z):
    radio=0.4
    distancia=math.sqrt( x**2 + y**2 + z**2)
    #Si esto es 0 o negativo es que el punto
    #está dentro de la esfera
    return distancia-radio



def dentro_de_donut(x, y):
    radio_exterior=0.4
    radio_interior=0.2
    distancia=math.sqrt( x**2 + y**2 )
    if distancia>=radio_interior and distancia<=radio_exterior:
        return True
    return False


def normalizar_horizontal(coordenada_x):
    #Coordenada_x=0 debería equivaler a -1
    #Coordenada_x=40 debería equivaler a -0
    #Coordenada_x=80 debería equivaler a -1
    #Con esto x queda entre 0 y 1
    normalizado_unidad=coordenada_x/CARACTERES_ANCHO_PANTALLA
    #Con esto queda entre 0 y 2
    normalizado_unidad=normalizado_unidad*2
    #Y ahora entre -1 y 1
    x_normalizada=normalizado_unidad - 1
    #print("x ="+str(coordenada_x)+" queda normalizada a "+str(x_normalizada))
    return x_normalizada


def normalizar_vertical(coordenada_y):
    #Coordenada_y también debería estar entre -1 y 1
    #para que nos quede algo redondo y no ovalado

    #Con esto ahora mide entre 0 y 1
    normalizado_unidad=coordenada_y/CARACTERES_ALTO_PANTALLA
    #Ampliamos la escala y ahora está entre 0 y 2
    normalizado_unidad=normalizado_unidad*2
    #Y ahora está entre -1 y 1
    y_normalizada=normalizado_unidad-1
    #Pero hay un problema, la pantalla es muy ancha, pero
    #es "poco alta", así que el rango [-1, 1] se queda
    #demasiado grande para tan poco altura, así que
    #tenemos que reducir y en lugar de [-1, 1] para la coordenada y habrá
    #que tener algo que sea proporcional
    #Si el x normalizado es [-1, 1] (hay 2 de distancia entre extremos)
    #para valores de 0 entre 0 y 80 esto significa
    #que tenemos una regla de tres
    #   Para 80 px -------------El rango es 2
    #   Para 20 px--------------El rango es x
    #Nuestro rango final debe ser x=(20*2)/80
    rango_final=(CARACTERES_ALTO_PANTALLA*2)/CARACTERES_ANCHO_PANTALLA
    #Entonces necesitamos 
    
    #El rango que está entre 0 y 1 lo multiplicamos por
    #el rango que nos ha salido y ahora quedará con el
    #valor apropiadamente corregido
    y_normalizada=y_normalizada*rango_final
    #print("y ="+str(coordenada_y)+" queda normalizada a "+str(y_normalizada))
    return y_normalizada

def renderizar():
    while True:
        caracteres=[]
        for y in range(0, CARACTERES_ALTO_PANTALLA):
            y_normalizada=normalizar_vertical(y)
            for x in range (0, CARACTERES_ANCHO_PANTALLA):
                x_normalizada=normalizar_horizontal(x)
                
                if dentro_de_donut(x_normalizada, y_normalizada):
                    caracteres.append("#")
                else:
                    caracteres.append(" ")
            caracteres.append("\n") 
        pantalla="".join(caracteres)
        print(ANSI_BORRADO_PANTALLA)
        print(pantalla)
        
        

if __name__=="__main__":
    renderizar()