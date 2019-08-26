

package com.simulata.TrafficSimulation.semaforo

import com.simulata.TrafficSimulation.vias._
import com.simulata.TrafficSimulation.json._

class Semaforo(via: Via) {
  
  // Tiempo en verde al azar dentro los limites establecidos
  val tiempoVerde: Double = {
    
    val random: scala.util.Random = scala.util.Random
    
    Semaforo.minTiempoVerde + random.nextInt(Semaforo.maxTiempoVerde - 
        Semaforo.minTiempoVerde) 
  }
  
  private var _estado: String = "rojo"
  
  def estado=_estado
  
  def estado_=(newEstado: String): Unit = {
    // Antes de cambiarlo verifica que sea verde, amarillo o rojo
    if(newEstado=="verde" || newEstado=="amarillo" || newEstado=="rojo") {
      _estado = newEstado
    }
  }
  
  // Segundos que lleva el semaforo en cada color, excepto en rojo
  var segundosEnVerde: Int = 0
  var segundosEnAmarillo: Int = 0
  
  // Cambia o no el estado del semaforo siguiendo ciertas reglas expuestas en el issue #41
  def estadoFluye: Unit = estado match {
    
    case "verde" => {
      if(segundosEnVerde < tiempoVerde) segundosEnVerde += 1
      else {
        estado = "amarillo" 
        segundosEnVerde = 0
      }
    }
    case "amarillo" => {
      if(segundosEnAmarillo < Semaforo.tiempoAmarillo) segundosEnAmarillo += 1
      else {
        estado = "rojo"
        segundosEnAmarillo = 0
      }
    }
    case "rojo" => estado = "verde"    
  }
}

object Semaforo {
  
  var tiempoAmarillo: Int = Json.tiempoAmarillo
  var minTiempoVerde: Int = Json.minTiempoVerde
  var maxTiempoVerde: Int = Json.maxTiempoVerde
}
