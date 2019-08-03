package com.simulata.TrafficSimulation.movimiento

import com.simulata.TrafficSimulation.cartesiano._

abstract class Movil (private var _posicion: Punto, private var _velocidad: Velocidad) {
  
  def posicion = _posicion
  
  def posicion_=(posicion:Punto): Unit = _posicion = posicion
  
  def velocidad = _velocidad
  
  def velocidad_=(velocidad:Velocidad):Unit = _velocidad = velocidad
  
  //Funcion que aumenta la posicion en un dt, está definida en la clase vehículo, en la clase MovimientoUniforme se define
  //una función que representa como cambia la posición con una velocidad y en un tiempo dt
  def mover(dt:Double):Unit
  
  def angulo = this.velocidad.angulo.angulo
}