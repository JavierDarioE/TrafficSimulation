package com.simulata.TrafficSimulation.grafico

import com.simulata.TrafficSimulation.vias._
import scala.collection.mutable.ArrayBuffer
import scalax.collection.mutable.Graph
import scalax.collection.edge.WLDiEdge

object GrafoVia {

  val grafo: Graph[Interseccion, WLDiEdge] = Graph[Interseccion, WLDiEdge]()
  val listaAristas = new ArrayBuffer[Interseccion]()

  def construir(arregloVias: ArrayBuffer[Via]): Unit={
    arregloVias.foreach { v => {
      if ((v.sentido.tipo).equalsIgnoreCase("unaVia")){
        grafo += WLDiEdge(v.origenn, v.finn)(v.longitud, v)
      }
      else if ((v.sentido.tipo).equalsIgnoreCase("dobleVia")){
        grafo += WLDiEdge(v.origenn, v.finn)(v.longitud, v)
        grafo += WLDiEdge(v.finn, v.origenn)(v.longitud, v)
      }
    }
}
    grafo.nodes.foreach(v => listaAristas += v.value)
  }

  def interseccion(nodo: Interseccion):grafo.NodeT = grafo.get(nodo)
}