package vias

import cartesiano.Punto
import movimiento.Vehiculo
import simulacion.Simulacion

class Interseccion (val xx:Double, val yy:Double, val nombre:String ) extends Punto(xx, yy){
  
  var origenes=Array[Vehiculo]()
  
  var fin=Array[Vehiculo]()
  
  def this (x:Int,y:Int){
    this(x, y, "")
  }
  
  Simulacion.intersecciones:+=this
}