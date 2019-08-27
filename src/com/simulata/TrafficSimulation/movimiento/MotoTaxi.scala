package com.simulata.TrafficSimulation.movimiento

import java.awt.geom.Ellipse2D
import java.awt.{Color, Shape}

import com.simulata.TrafficSimulation.cartesiano._
import com.simulata.TrafficSimulation.vias._
//falta definir el modelo para representarlos*****/

//para poner los get y los set se debe de copiar todo el código, no vi como dejarlo en vehículo

class MotoTaxi (val pl:String,
                private var _v:Velocidad,
                private var _tasaA: Double,
                val c:Color = Color.WHITE)
  extends Vehiculo(pl, _v, _tasaA, c){

}

object MotoTaxi{
  val r = scala.util.Random

  def generarPlaca:String={
    var a:String=""
    for(i<- 0 to 2) a=a+Vehiculo.digitos(r.nextInt(10))
    for(i<-0 to 2) a=a+Vehiculo.letras(r.nextInt(26))
    a
  }
}

