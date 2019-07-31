package vias

import cartesiano.Punto
import scala.collection.mutable.ArrayBuffer

class Interseccion (val xx:Double, val yy:Double, val nombre:String ) extends Punto(xx, yy){
  
  Interseccion.intersecciones.append(this)
  
  def this (x:Int,y:Int){
    this(x, y, "")
  }
}

object Interseccion{
  var intersecciones:ArrayBuffer[Interseccion]=ArrayBuffer()
}