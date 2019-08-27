package com.simulata.TrafficSimulation.vias

import java.awt.Color

import com.simulata.TrafficSimulation.cartesiano.Punto
import com.simulata.TrafficSimulation.movimiento.Vehiculo
import com.simulata.TrafficSimulation.simulacion.Simulacion
import com.simulata.TrafficSimulation.semaforo._

class Interseccion (val xx:Double, val yy:Double, val nombre: Option[String] = None, val color: Color) extends Punto(xx, yy){
  
  // Agrupa los semaforos de la interseccion
  var nodoSemaforo: NodoSemaforo = new NodoSemaforo
  
  var origenes=Array[Vehiculo]()
  
  var fin=Array[Vehiculo]()
  
  def this (x:Int,y:Int){
    this(x, y, Some(""), Color.WHITE)
  }
  
  Simulacion.intersecciones:+=this
}