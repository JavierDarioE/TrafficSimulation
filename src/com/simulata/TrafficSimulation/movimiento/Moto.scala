package com.simulata.TrafficSimulation.movimiento

import java.awt.Color

import com.simulata.TrafficSimulation.cartesiano._
import com.simulata.TrafficSimulation.vias._

class Moto (val pl:String,
            private var _v:Velocidad,
            val c : Color = Color.WHITE)
  extends Vehiculo(pl, _v, c){

}

object Moto{
  val r = scala.util.Random

  def generarPlaca:String={
    var a:String=""
    for(i<- 0 to 2) a=a+Vehiculo.digitos(r.nextInt(10))
    for(i<- 0 to 1) a=a+Vehiculo.letras(r.nextInt(26))
    a=a+Vehiculo.digitos(r.nextInt(10))
    a
  }
}
