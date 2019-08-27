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
  private var _direccion = Angulo(0)
  private var _via = colaVias.dequeue() //un while hasta que la via sea igual a la de la cola :)
  private var _siguienteInterseccion = _interseccionDestino
  private val _distanciaParaRecorrer = colaVias.map(_.longitud).sum

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
    if (_vehiculo.posicion == _via.origenn.copy()) {
      _direccion = Angulo(tangenteInversa(_via.origenn.xx, _via.finn.xx, _via.origenn.yy, _via.finn.yy))
      _siguienteInterseccion = _via.finn
    }
    else if (_vehiculo.posicion == _via.finn.copy()) {
      _direccion = Angulo(tangenteInversa(_via.finn.xx, _via.origenn.xx, _via.finn.yy, _via.origenn.yy))
      _siguienteInterseccion = _via.origenn
    }
    _vehiculo.velocidad.angulo_=(_direccion)
    val (px, py) = _vehiculo.movimiento(dt, _vehiculo.velocidad)
    _vehiculo.posicion_=(new Punto(_vehiculo.posicion.x + px, _vehiculo.posicion.y + py))
    val distancia = Punto.distancia(_vehiculo.posicion, _siguienteInterseccion.copy())
    if (distancia <= (_vehiculo.velocidad.magnitud * dt + 1)) {
      _vehiculo.posicion_=(Punto(_siguienteInterseccion.xx, _siguienteInterseccion.yy))
      _vehiculo.posicion.x = _siguienteInterseccion.xx
      _vehiculo.posicion.y = _siguienteInterseccion.yy
      if (colaVias.nonEmpty) {
        _via = colaVias.dequeue()
      }
      else {
        _vehiculo.velocidad.magnitud_=(0)
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
