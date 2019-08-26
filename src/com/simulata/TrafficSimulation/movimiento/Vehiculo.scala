package com.simulata.TrafficSimulation.movimiento

import java.awt.{Color, Shape}

import com.simulata.TrafficSimulation.cartesiano._
import com.simulata.TrafficSimulation.vias._
import com.simulata.TrafficSimulation.simulacion.Simulacion
import com.simulata.TrafficSimulation.grafico.GrafoVia

import scala.collection.immutable.NumericRange
import scala.collection.mutable.Queue
import scala.util.Random


abstract case class Vehiculo (placa:String,
                              private var _vel:Velocidad,
                              private var _color: Color)
extends Movil(Punto(0,0), _vel) with MovimientoUniforme {

  def color: Color =_color
  def color_=(color: Color): Unit = _color = color

  // accedemos a la lista de vehiculos en el objeto Simulacion y agregamos este vahiculo a la lista
  Simulacion.vehiculos :+=this
}

object Vehiculo{
  val r: Random.type = scala.util.Random
  
  val letras: NumericRange.Inclusive[Char] = 'A' to 'Z'
  
  val digitos: NumericRange.Inclusive[Char] = '0' to '9'
  
  def crearVehiculo(vMin:Double, vMax:Double, tipo: String):Vehiculo={
    def definirTipo(n:String):Vehiculo= n match{
      //se usa el constructor que no recibe placa, en cada clase estará definida la manera de crearlas
      //el angulo de la velocidad es 0 por defecto
      case "carro" => new Carro(Carro.generarPlaca,
            Velocidad(vMin+r.nextInt((vMax-vMin).toInt))
            )
      case "moto" => new Moto(Moto.generarPlaca,
            Velocidad(vMin+r.nextInt((vMax-vMin).toInt))
            )
      case "mototaxi" => new MotoTaxi(MotoTaxi.generarPlaca,
            Velocidad(vMin+r.nextInt((vMax-vMin).toInt))
            )
      case "camion" => new Camion(Camion.generarPlaca,
            Velocidad(vMin+r.nextInt((vMax-vMin).toInt))
            )
      case "bus" => new Bus(Bus.generarPlaca,
            Velocidad(vMin+r.nextInt((vMax-vMin).toInt))
            )
    }
  //se crea un vehiculo dependiendo del tipo
    definirTipo(tipo)
  }

  //TODO simplificar gets y sets (suponiendo que sea posible)

}
