package com.simulata.TrafficSimulation.semaforo

import com.simulata.TrafficSimulation.vias._

class Semaforo(via: Via, tiempoVerde: Double) {
  
  private var _estado: String = "rojo"
  
  def estado=_estado
  
  def estado_=(newEstado: String): Unit = {
    // Antes de cambiarlo verifica que sea verde, amarillo o rojo
    if(newEstado=="verde" || newEstado=="amarillo" || newEstado=="rojo") {
      _estado = newEstado
    }
  }
  
  
  
}

object Semaforo {
  
  var tiempoAmarillo: Double = _
  var minTiempoVerde: Double = _
  var maxTiempoVerde: Double = _
}