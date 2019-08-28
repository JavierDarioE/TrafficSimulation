package com.simulata.TrafficSimulation.movimiento

import java.awt.Color
import com.simulata.TrafficSimulation.cartesiano._

class Carro (val pl:String,
             private var _vCrucero:Velocidad,
             private var _a: Double,
             val c : Color = Color.WHITE)
  extends Vehiculo(pl, _vCrucero, _a, c, "carro"){
}

object Carro{
  
  val r = scala.util.Random

  def generarPlaca:String={
    var a:String=""
    for(i<- 0 to 2) a=a+Vehiculo.letras(r.nextInt(26))
    for(i<- 0 to 2) a=a+Vehiculo.digitos(r.nextInt(10))
    a
  }
}