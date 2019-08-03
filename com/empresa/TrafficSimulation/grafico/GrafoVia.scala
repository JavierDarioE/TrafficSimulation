package grafico

import vias._

import scala.collection.mutable.Queue
import scala.collection.mutable.ArrayBuffer
import scalax.collection.Graph
import scalax.collection.GraphPredef._
import scalax.collection.GraphEdge._
import scalax.collection.edge.WLDiEdge
import scalax.collection.edge.Implicits._

import scala.collection.mutable

object GrafoVia {
  
  def construir(arregloVias: ArrayBuffer[Via]):
    Graph[Interseccion, WLDiEdge] = {
    
    var queueAristas = new mutable.Queue[WLDiEdge[Interseccion]]()
    
    val arregloViasUnSentido = arregloVias.filter(_.sentido.tipo == "unaVia")
    val arregloViasDobleSentido = arregloVias.filter(_.sentido.tipo == "dobleVia")
    
    arregloViasUnSentido.foreach(via => queueAristas.enqueue(WLDiEdge(via.origenn, via.finn)(via.longitud, via.nombre)))
    
    arregloViasDobleSentido.foreach(via => queueAristas.enqueue(WLDiEdge(via.origenn, via.finn)(via.longitud, via.nombre)))
    arregloViasDobleSentido.foreach(via => queueAristas.enqueue(WLDiEdge(via.finn, via.origenn)(via.longitud, via.nombre)))      
    
    Graph[Interseccion, WLDiEdge](queueAristas: _*)
  }      
}