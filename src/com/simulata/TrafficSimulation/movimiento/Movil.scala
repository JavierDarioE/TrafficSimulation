package com.simulata.TrafficSimulation.movimiento

import com.simulata.TrafficSimulation.cartesiano._

abstract class Movil (private var _pos: Punto, private var _vel: Velocidad) {
  
  def pos = _pos
  
  def pos_=(pos:Punto): Unit = _pos = pos
  
  def vel = _vel
  
  def vel_=(vel:Velocidad):Unit = _vel = vel
  
  //Funcion que aumenta la posicion en un dt, está definida en la clase vehículo, en la clase MovimientoUniforme se define
  //una función que representa como cambia la posición con una velocidad y en un tiempo dt
  def mover(dt:Double):Unit
  
  def angulo = this.vel.angulo.angulo
}