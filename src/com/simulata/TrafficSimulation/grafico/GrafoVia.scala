package com.simulata.TrafficSimulation.grafico

import com.simulata.TrafficSimulation.vias._

import scala.collection.mutable.Queue
import scala.collection.mutable.ArrayBuffer
import scalax.collection.mutable.Graph
import scalax.collection.GraphPredef._
import scalax.collection.GraphEdge._
import scalax.collection.edge.WLDiEdge
import scalax.collection.edge.Implicits._
import scala.collection.mutable

object GrafoVia {

  val grafo = Graph[Interseccion, WLDiEdge]()
  val listaAristas = new ArrayBuffer[Interseccion]()

  def construir(arregloVias: ArrayBuffer[Via]): Unit={
    arregloVias.foreach {
      case v@Via(_, _, _, _, Sentido.unaVia, _, _) =>
        grafo += WLDiEdge(v.origenn, v.finn)(v.longitud, v)
      case v@Via(_, _, _, _, Sentido.dobleVia, _, _) =>
        grafo += WLDiEdge(v.origenn, v.finn)(v.longitud, v)
        grafo += WLDiEdge(v.finn, v.origenn)(v.longitud, v)
    }
    grafo.nodes.foreach(v => listaAristas += v.value)
  }

  def interseccion(nodo: Interseccion):grafo.NodeT = grafo.get(nodo)

    

    


  /*var queueAristas = new mutable.Queue[Interseccion]()
val grafo = Graph[Interseccion, WLDiEdge]()

def n(interseccion: Interseccion): grafo.NodeT = grafo.get(interseccion)

def construir(arregloVias: ArrayBuffer[Via]):

  /*Graph[Interseccion, WLDiEdge]*/ Unit = {
  arregloVias.foreach(v => {
    v match {
      case Via(_,_,_,_,Sentido.unaVia,_,_) => {
        grafo += WLDiEdge(v.origenn, v.finn)(v.longitud,v)
    }

      case Via(_,_,_,_,Sentido.dobleVia,_,_) =>{
        grafo += WLDiEdge(v.origenn, v.finn)(v.longitud,v)
        grafo += WLDiEdge(v.finn, v.origenn)(v.longitud,v)
      }
    }
  })
  grafo.nodes.foreach(v => queueAristas+= v.value)
*/
  /*val queueAristas = new mutable.Queue[Interseccion]()
val nodos = new ArrayBuffer[]

val arregloViasUnSentido = arregloVias.filter(_.sentido.tipo == "unaVia")
val arregloViasDobleSentido = arregloVias.filter(_.sentido.tipo == "dobleVia")

arregloViasUnSentido.foreach(via => queueAristas.enqueue(WLDiEdge(via.origenn, via.finn)(via.longitud, via)))

arregloViasDobleSentido.foreach(via => queueAristas.enqueue(WLDiEdge(via.origenn, via.finn)(via.longitud, via)))
arregloViasDobleSentido.foreach(via => queueAristas.enqueue(WLDiEdge(via.finn, via.origenn)(via.longitud, via)))

val grafo = Graph[Interseccion, WLDiEdge](nodos: _*)
*/
}