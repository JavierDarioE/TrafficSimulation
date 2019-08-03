package com.simulata.TrafficSimulation.vias

import java.awt.Color

import com.simulata.TrafficSimulation.cartesiano.Punto
import com.simulata.TrafficSimulation.movimiento.Vehiculo
import com.simulata.TrafficSimulation.simulacion.Simulacion

class Interseccion (val xx:Double, val yy:Double, val nombre:String, val color: Color) extends Punto(xx, yy){
  
  var origenes=Array[Vehiculo]()
  
  var fin=Array[Vehiculo]()
  
  def this (x:Int,y:Int){
    this(x, y, "", Color.WHITE)
  }
  
  Simulacion.intersecciones:+=this
}