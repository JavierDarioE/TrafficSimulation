package com.simulata.TrafficSimulation.cartesiano

import com.simulata.TrafficSimulation.grafico.GrafoVia
import com.simulata.TrafficSimulation.movimiento.Vehiculo
import com.simulata.TrafficSimulation.simulacion.Simulacion
import com.simulata.TrafficSimulation.vias.{Interseccion, Via}
import com.simulata.TrafficSimulation.semaforo.Semaforo

import scala.collection.mutable.Queue
import scala.util.Random
import com.simulata.TrafficSimulation.json.Json

class Viaje(_vehiculo: Vehiculo) {
  //constructor:
  private val random: Random.type = scala.util.Random
  private val _interseccionOrigen: Interseccion = Simulacion.intersecciones(random.nextInt(Simulacion.intersecciones.length))
  private var _interseccionDestino = Simulacion.intersecciones(random.nextInt(Simulacion.intersecciones.length))
  while (_interseccionOrigen == _interseccionDestino) {
    _interseccionDestino = Simulacion.intersecciones(random.nextInt(Simulacion.intersecciones.length))
  }

  private val nodoOrigen = GrafoVia.grafo.get(_interseccionOrigen)//se genera con el companion object **neo4j**
  private val nodoDestino = GrafoVia.grafo.get(_interseccionDestino)
  private val recorrido = nodoOrigen.shortestPathTo(nodoDestino).get.edges.map(_.toOuter.label.asInstanceOf[Via]).toArray
  private val colaVias = new Queue[Via]()
  recorrido.foreach(i => colaVias += i) //hasta acá se genera con el companion object **neo4j**
  
  _vehiculo.posicion_=(new Punto(_interseccionOrigen.xx, _interseccionOrigen.yy)) //se reasigna con el companion object
  _vehiculo.color_=(_interseccionDestino.color)
  private var _direccion: Angulo = Angulo(0)
  private var _via: Via = colaVias.dequeue()
  private var _siguienteInterseccion: Interseccion = interseccionDestino
  private var _distanciaParaRecorrer = colaVias.map(_.longitud).sum
  var yaLlego = false
  var yaEstaFrenando = false
  // var estaEnPrimeraInterseccion = true

  Simulacion.viajes :+= this
  //fin del constructor

  //accesores:
  def interseccionOrigen: Interseccion = _interseccionOrigen
  def interseccionDestino: Interseccion = _interseccionDestino
  def vehiculo: Vehiculo = _vehiculo
  def direccion: Angulo = _direccion
  def via: Via = _via
  def siguienteInterseccion: Interseccion = _siguienteInterseccion
  def distanciaParaRecorrer: Double = _distanciaParaRecorrer
  
  //métodos de clase
  def mover(dt: Double): Unit = {
    
    // La rapidez del vehiculo cambia segun la aceleracion
    _vehiculo.velocidadActual.magnitud += _vehiculo.aceleracion * Simulacion.tRefresh
    
    // Si la rapidez del vehiculo queda negativa o cero, la vuelve cero
    if(vehiculo.velocidadActual.magnitud <= 0) vehiculo.velocidadActual.magnitud = 0
    
    // Si la rapidez del vehiculo queda igual o mayor a la crucero, la vuelve igual a la crucero
    else if(vehiculo.velocidadActual.magnitud >= vehiculo.velocidadCrucero.magnitud) {
      vehiculo.velocidadActual.magnitud = vehiculo.velocidadCrucero.magnitud
    }
    
    // A este if y al else if que le sigue se entra si el vehiculo esta justo 
    // en una interseccion
    if (vehiculo.posicion == via.origenn.copy()) {
      _direccion = Angulo(tangenteInversa(via.origenn.xx, via.finn.xx, via.origenn.yy, via.finn.yy))
      _siguienteInterseccion = via.finn
      
      // El semaforo que afecta al vehiculo en este momento
      val semaforo = {
        via.origenn.nodoSemaforo.semaforos.find(_.via.equals(via)).getOrElse({
          val semaforoApano = new Semaforo(via)
          via.origenn.nodoSemaforo.agregarSemaforo(semaforoApano)
          semaforoApano
        })
      }
      // println(semaforo.estado)
            
      semaforo.estado match {
        
        // Si esta en rojo el vehiculo no se mueve        
        case "rojo" => {
          vehiculo.velocidadActual.magnitud = 0
          vehiculo.aceleracion = 0
          //yaEstaParado = true
        }        
        case "amarillo" => {
          
          // Si no estuviera frenando es porque se va a pasar el semaforo
          if(yaEstaFrenando) {
            vehiculo.velocidadActual.magnitud = 0
            vehiculo.aceleracion = 0
          }
        }
        
        // Si esta en verde la aceleracion vuelve a ser la definida
        case "verde" => {
          vehiculo.aceleracion = vehiculo.aceleracionDefinida
          //yaEstaParado = false
          yaEstaFrenando = false
        }
      }
          }
    else if (vehiculo.posicion == via.finn.copy()) {
      _direccion = Angulo(tangenteInversa(via.finn.xx, via.origenn.xx, via.finn.yy, via.origenn.yy))
      _siguienteInterseccion = via.origenn
      
      // El semaforo que afecta al vehiculo en este momento
      var semaforo = {
        via.finn.nodoSemaforo.semaforos.find(_.via.equals(via)).getOrElse({
          val semaforoApano = new Semaforo(via)
          via.finn.nodoSemaforo.agregarSemaforo(semaforoApano)
          semaforoApano
        })
      }
      
      semaforo.estado match {
        
        // Si esta en rojo el vehiculo no se mueve    
        case "rojo" => {
          vehiculo.velocidadActual.magnitud = 0
          vehiculo.aceleracion = 0
          //yaEstaParado = true
        }        
        case "amarillo" => {
          
          // Si no estuviera frenando es porque se va a pasar el semaforo
          if(yaEstaFrenando) {
            vehiculo.velocidadActual.magnitud = 0
            vehiculo.aceleracion = 0
          }
        }
        
        // Si esta en verde la aceleracion vuelve a ser la definida
        case "verde" => {
          vehiculo.aceleracion = vehiculo.aceleracionDefinida
          //yaEstaParado = false
          yaEstaFrenando = false
        }
      }      
    }
    
    vehiculo.velocidadActual.angulo_=(direccion)
    val (px, py) = vehiculo.movimiento(dt, vehiculo.velocidadActual)
    vehiculo.posicion_=(new Punto(vehiculo.posicion.x + px, vehiculo.posicion.y + py))
    
    val distancia = Punto.distancia(vehiculo.posicion, siguienteInterseccion.copy())
    
    // El semaforo con el que se va a encontrar el vehiculo
    val semaforo = {
      siguienteInterseccion.nodoSemaforo.semaforos.find(_.via.equals(via)).get
    }
    
    
    // Para asegurarme de que si esta en verde tenga la aceleracion definida
    if (semaforo.estado == "verde") vehiculo.aceleracion = vehiculo.aceleracionDefinida
    
    semaforo.estado match {
      
      case "rojo" => 
      
      case "amarillo" => {
        
        // Si cambio de repente a amarillo y esta cerca, siga
        if (distancia < Viaje.xSemaforoAmarilloContinuar && !yaEstaFrenando) {
          vehiculo.aceleracion = vehiculo.aceleracionDefinida
        }
        else if (distancia < Viaje.xSemaforoFrenar) {
          vehiculo.aceleracion = vehiculo.aceleracionDefinida * (-1)
          yaEstaFrenando = true
        }
      }
      
      // Si esta en verde que la aceleracion sea la definida, no se si es necesario
      case "verde" => vehiculo.aceleracion = vehiculo.aceleracionDefinida
    }
    
    // si la distancia a la interseccion es menor o igual a la distancia que recorrera el vehiculo en 
    // una unidad de tiempo mas uno, en metros todo
    if (distancia <= (vehiculo.velocidadActual.magnitud * dt + 1)) {
      vehiculo.posicion_=(Punto(siguienteInterseccion.xx, siguienteInterseccion.yy))
      vehiculo.posicion.x = siguienteInterseccion.xx
      vehiculo.posicion.y = siguienteInterseccion.yy
      if (colaVias.nonEmpty) {
        _via = colaVias.dequeue()
      }
      else {
        vehiculo.velocidadActual.magnitud_=(0)
        vehiculo.aceleracion = 0
        yaLlego = true
      }
    }
    
    // Si ya llego al destino, se asegura que no se mueva
    if(yaLlego) {
      vehiculo.velocidadActual.magnitud = 0
      vehiculo.aceleracion = 0
    }
  }

  def tangenteInversa(x1: Double, x2: Double, y1: Double, y2: Double): Double = {
    val diferenciaX = x2 - x1
    val diferenciaY = y2 - y1

    if (diferenciaX == 0) {
      if (diferenciaY > 0) {
        90
      } else {
        270
      }
    } else if (diferenciaY == 0) {
      if (diferenciaX > 0) {
        0
      } else {
        180
      }
    } else {
      val m = diferenciaY / diferenciaX
      val a = scala.math.atan(m).toDegrees
      if (diferenciaX > 0 && diferenciaY > 0) {
        a
      } else if (diferenciaX < 0 && diferenciaY > 0) {
        a + 180
      } else if (diferenciaX < 0 && diferenciaY < 0) {
        a + 180
      } else if (diferenciaX > 0 && diferenciaY < 0) {
        360 + a
      } else {
        0
      }
    }
  }
}

object Viaje {
  
  var xSemaforoFrenar: Double = Json.xSemaforoFrenar
  var xSemaforoAmarilloContinuar: Double = Json.xSemaforoAmarilloContinuar
}
