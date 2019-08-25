package com.simulata.TrafficSimulation.cartesiano

import com.simulata.TrafficSimulation.grafico.GrafoVia
import com.simulata.TrafficSimulation.movimiento.Vehiculo
import com.simulata.TrafficSimulation.simulacion.Simulacion
import com.simulata.TrafficSimulation.vias.Via
import scala.collection.mutable.Queue

class Viaje(vehiculo: Vehiculo) {
  val random = scala.util.Random
  val interseccionOrigen = Simulacion.intersecciones(random.nextInt(Simulacion.intersecciones.length))
  var interseccionDestino = Simulacion.intersecciones(random.nextInt(Simulacion.intersecciones.length))
  while (interseccionOrigen==interseccionDestino){
    interseccionDestino = Simulacion.intersecciones(random.nextInt(Simulacion.intersecciones.length))
  }
  val nodoOrigen = GrafoVia.grafo.get(interseccionOrigen)
  val nodoDestino = GrafoVia.grafo.get(interseccionDestino)
  val recorrido = nodoOrigen.shortestPathTo(nodoDestino).get.edges.map(_.toOuter.label.asInstanceOf[Via]).toArray
  val colaVias = new Queue[Via]()
  recorrido.foreach(i => colaVias += i)

  //TODO gets y sets
}
