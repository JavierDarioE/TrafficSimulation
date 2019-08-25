package com.simulata.TrafficSimulation.cartesiano

trait MovimientoUniforme {
  
  //Devuelve el punto en el que termina un objeto en un tiempo dt con una velocidad v si comienza desde el origen;
  //en la clase vehiculo se usa este método para calcular la nueva posición


  def movimiento(dt:Double, v:Velocidad): (Double, Double)={
    val angulo = v.angulo.angulo
    val magnitud = v.magnitud
    val px = magnitud*dt*scala.math.cos(scala.math.toRadians(angulo))
    val py = magnitud*dt*scala.math.sin(scala.math.toRadians(angulo))
    (px,py)
  }
}