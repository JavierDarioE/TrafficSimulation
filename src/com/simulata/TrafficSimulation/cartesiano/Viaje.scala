package com.simulata.TrafficSimulation.cartesiano

import com.simulata.TrafficSimulation.grafico.GrafoVia
import com.simulata.TrafficSimulation.movimiento.Vehiculo
import com.simulata.TrafficSimulation.simulacion.Simulacion
import com.simulata.TrafficSimulation.vias.{Interseccion, Via}

import scala.collection.mutable.Queue
import scala.util.Random

class Viaje(vehiculo: Vehiculo) {
  val random: Random.type = scala.util.Random
  val interseccionOrigen = Simulacion.intersecciones(random.nextInt(Simulacion.intersecciones.length))
  var interseccionDestino = Simulacion.intersecciones(random.nextInt(Simulacion.intersecciones.length))
  while (interseccionOrigen == interseccionDestino) {
    interseccionDestino = Simulacion.intersecciones(random.nextInt(Simulacion.intersecciones.length))
  }
  val nodoOrigen = GrafoVia.grafo.get(interseccionOrigen)
  val nodoDestino = GrafoVia.grafo.get(interseccionDestino)
  val recorrido = nodoOrigen.shortestPathTo(nodoDestino).get.edges.map(_.toOuter.label.asInstanceOf[Via]).toArray
  val colaVias = new Queue[Via]()
  recorrido.foreach(i => colaVias += i)

  vehiculo.posicion_=(new Punto(interseccionOrigen.xx, interseccionOrigen.yy))
  vehiculo.color_=(interseccionDestino.color)
  var direccion: Angulo = Angulo(0)
  var via: Via = colaVias.dequeue()
  var siguienteInterseccion: Interseccion = interseccionDestino
  var distanciaParaRecorrer = colaVias.map(_.longitud).sum
  var yaLlego = false
  // var estaEnPrimeraInterseccion = true

  Simulacion.viajes :+= this

  def mover(dt: Double): Unit = {
    
    /*
    if (estaEnPrimeraInterseccion) {
      vehiculo.velocidad.magnitud = 0
      estaEnPrimeraInterseccion = false
    }
    * 
    */
    
    // Si no ha llegado a su destino la rapidez del vehiculo cambia segun la aceleracion
    if(!yaLlego) {
      vehiculo.velocidadActual.magnitud += vehiculo.aceleracion * Simulacion.tRefresh
    }
    
    // Si la rapidez del vehiculo queda negativa o cero, la vuelve cero
    if(vehiculo.velocidadActual.magnitud <= 0) vehiculo.velocidadActual.magnitud = 0
    
    // Si la rapidez del vehiculo queda igual o mayor a la crucero, la vuelve igual a la crucero
    else if(vehiculo.velocidadActual.magnitud >= vehiculo.velocidadCrucero.magnitud) {
      vehiculo.velocidadActual.magnitud = vehiculo.velocidadCrucero.magnitud
    }
    
    if (vehiculo.posicion == via.origenn.copy()) {
      direccion = Angulo(tangenteInversa(via.origenn.xx, via.finn.xx, via.origenn.yy, via.finn.yy))
      siguienteInterseccion = via.finn
    }
    else if (vehiculo.posicion == via.finn.copy()) {
      direccion = Angulo(tangenteInversa(via.finn.xx, via.origenn.xx, via.finn.yy, via.origenn.yy))
      siguienteInterseccion = via.origenn
    }
    vehiculo.velocidadActual.angulo_=(direccion)
    val (px, py) = vehiculo.movimiento(dt, vehiculo.velocidadActual)
    vehiculo.posicion_=(new Punto(vehiculo.posicion.x + px, vehiculo.posicion.y + py))
    
    val distancia = Punto.distancia(vehiculo.posicion, siguienteInterseccion.copy())
    
    // si la distancia a la interseccion es menor o igual a la distancia que recorrera el vehiculo en 
    // una unidad de tiempo mas uno, en metros todo
    if (distancia <= (vehiculo.velocidadActual.magnitud * dt + 1)) {
      vehiculo.posicion_=(Punto(siguienteInterseccion.xx, siguienteInterseccion.yy))
      vehiculo.posicion.x = siguienteInterseccion.xx
      vehiculo.posicion.y = siguienteInterseccion.yy
      if (colaVias.nonEmpty) {
        via = colaVias.dequeue()
      }
      else {
        vehiculo.velocidadActual.magnitud_=(0)
        yaLlego = true
      }
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
