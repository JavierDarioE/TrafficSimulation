package com.simulata.TrafficSimulation.cartesiano

trait Recta {
  type T <: Punto
  
  var origen: T = _
  
  var fin: T = _
}