package vias

import java.awt.Color

import cartesiano.Punto
import movimiento.Vehiculo
import simulacion.Simulacion

class Interseccion (val xx:Double, val yy:Double, val nombre:String, color: Color) extends Punto(xx, yy){
  
  var origenes=Array[Vehiculo]()
  
  var fin=Array[Vehiculo]()
  
  def this (x:Int,y:Int){
    this(x, y, "", Color.WHITE)
  }
  
  Simulacion.intersecciones:+=this
}