package com.simulata.TrafficSimulation.movimiento

import com.simulata.TrafficSimulation.cartesiano._

abstract class Movil (private var _posicion: Punto, private var _velocidad: Velocidad) {

  //setters y getters
  def posicion:Punto = _posicion
  def posicion_=(pos:Punto): Unit = _posicion = pos
  
  def velocidad:Velocidad = _velocidad
  def velocidad_=(vel:Velocidad):Unit = _velocidad = velocidad

}