package vias

import cartesiano.Punto
import simulacion.Simulacion

class Interseccion (val xx:Double, val yy:Double, val nombre:String ) extends Punto(xx, yy){
  
  def this (x:Int,y:Int){
    this(x, y, "")
  }
  
  Simulacion.intersecciones:+=this
}