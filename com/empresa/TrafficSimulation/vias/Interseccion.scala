package vias

import cartesiano.Punto


class Interseccion (val xx:Double, val yy:Double, val nombre:String ) extends Punto(xx, yy){
  
  def this (x:Int,y:Int){
    this(x, y, "")
  }
}