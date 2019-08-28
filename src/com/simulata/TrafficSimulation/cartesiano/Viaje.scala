package com.simulata.TrafficSimulation.cartesiano

import com.simulata.TrafficSimulation.grafico.GrafoVia
import com.simulata.TrafficSimulation.movimiento.Vehiculo
import com.simulata.TrafficSimulation.simulacion.Simulacion
import com.simulata.TrafficSimulation.vias.{Interseccion, Via}

import scala.collection.mutable.Queue
import scala.util.Random

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
    /*
    if (estaEnPrimeraInterseccion) {
      vehiculo.velocidad.magnitud = 0
      estaEnPrimeraInterseccion = false
    }
    * 
    */
    
    // Si no ha llegado a su destino la rapidez del vehiculo cambia segun la aceleracion
    if(!yaLlego) {
      _vehiculo.velocidadActual.magnitud += vehiculo.aceleracion * Simulacion.tRefresh
    }
    
    // Si la rapidez del vehiculo queda negativa o cero, la vuelve cero
    if(_vehiculo.velocidadActual.magnitud <= 0) vehiculo.velocidadActual.magnitud = 0
    
    // Si la rapidez del vehiculo queda igual o mayor a la crucero, la vuelve igual a la crucero
    else if(vehiculo.velocidadActual.magnitud >= vehiculo.velocidadCrucero.magnitud) {
      vehiculo.velocidadActual.magnitud = vehiculo.velocidadCrucero.magnitud
    }
    
    if (vehiculo.posicion == via.origenn.copy()) {
      direccion = Angulo(tangenteInversa(via.origenn.xx, via.finn.xx, via.origenn.yy, via.finn.yy))
      siguienteInterseccion = via.finn

    }
    else if (_vehiculo.posicion == _via.finn.copy()) {
      _direccion = Angulo(tangenteInversa(_via.finn.xx, _via.origenn.xx, _via.finn.yy, _via.origenn.yy))
      _siguienteInterseccion = _via.origenn
    }
    
    _vehiculo.velocidadActual.angulo_=(_direccion)
    val (px, py) = _vehiculo.movimiento(dt, _vehiculo.velocidadActual)
    _vehiculo.posicion_=(new Punto(_vehiculo.posicion.x + px, _vehiculo.posicion.y + py))
    
    val distancia = Punto.distancia(_vehiculo.posicion, _siguienteInterseccion.copy())
    
    // si la distancia a la interseccion es menor o igual a la distancia que recorrera el vehiculo en 
    // una unidad de tiempo mas uno, en metros todo
    if (distancia <= (_vehiculo.velocidadActual.magnitud * dt + 1)) {
      _vehiculo.posicion_=(Punto(_siguienteInterseccion.xx, _siguienteInterseccion.yy))
      _vehiculo.posicion.x = _siguienteInterseccion.xx
      _vehiculo.posicion.y = _siguienteInterseccion.yy
      if (colaVias.nonEmpty) {
        _via = colaVias.dequeue()
      }
      else {
        _vehiculo.velocidadActual.magnitud_=(0)
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
