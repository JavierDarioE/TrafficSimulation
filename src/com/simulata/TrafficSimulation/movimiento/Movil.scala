package com.simulata.TrafficSimulation.movimiento

import com.simulata.TrafficSimulation.cartesiano._

abstract class Movil (
    private var _posicion: Punto, 
    private var _velocidadCrucero: Velocidad,
    private var _velocidadActual: Velocidad,
    private var _aceleracion: Double) {

  //setters y getters
  def posicion:Punto = _posicion
  def posicion_=(pos:Punto): Unit = _posicion = pos
  
  def velocidadCrucero: Velocidad = _velocidadCrucero
  def velocidadCrucero_=(newVelocidad: Velocidad): Unit = _velocidadCrucero = newVelocidad
  
  def velocidadActual: Velocidad =_velocidadActual
  def velocidadActual_=(newVelocidad: Velocidad): Unit = _velocidadActual = newVelocidad
  
  def aceleracion: Double =_aceleracion
  def aceleracion_=(newAceleracion: Double): Unit = _aceleracion = newAceleracion
}