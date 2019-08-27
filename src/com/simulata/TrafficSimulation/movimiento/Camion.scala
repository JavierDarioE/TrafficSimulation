package com.simulata.TrafficSimulation.movimiento

import java.awt.Color

import com.simulata.TrafficSimulation.cartesiano._
import com.simulata.TrafficSimulation.vias._

//falta definir el modelo para representarlos*****/

//para poner los get y los set se debe de copiar todo el código, no vi como dejarlo en vehículo

class Camion (val pl:String,
              private var _vCrucero:Velocidad,
              private var _a: Double,
              val c: Color = Color.WHITE)
  extends Vehiculo(pl, _vCrucero, _a, c){

}

object Camion{
val r = scala.util.Random

  def generarPlaca:String={
    var a:String="R"
    for(i<- 0 to 4) a=a+Vehiculo.digitos(r.nextInt(10))
    a
  }
}

