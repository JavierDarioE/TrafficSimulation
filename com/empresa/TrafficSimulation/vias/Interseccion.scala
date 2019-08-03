package vias

import java.awt.Color

import cartesiano.Punto
import simulacion.Simulacion

class Interseccion (val xx:Double, val yy:Double, val nombre:String, color: Color) extends Punto(xx, yy){
  
  def this (x:Int,y:Int){
    this(x, y, "", Color.WHITE)
  }
  
  Simulacion.intersecciones:+=this
}