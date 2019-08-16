

package com.simulata.TrafficSimulation.cartesiano
//El gráfico estará en unidades de m y el tiempo dt en seg

case class Punto (val x:Double, val y:Double) {
}
object Punto{
  def distancia(a:Punto,b:Punto):Double={
    scala.math.sqrt(scala.math.pow(b.x - a.x, 2) + scala.math.pow(b.y - a.y, 2))
  }
}