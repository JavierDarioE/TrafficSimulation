package movimiento

import json._
import cartesiano._
import vias._
import simulacion.Simulacion

abstract class Vehiculo (val placa:String,val origen:Interseccion, val destino:Interseccion, private var _velocidad:Velocidad) 
extends Movil(origen, _velocidad) with MovimientoUniforme {
 private var _punto:Punto = origen
 Simulacion.vehiculos :+=this
}

object Vehiculo{
  
  val r = scala.util.Random
  
  val letras = ('A' to 'Z')
  
  val digitos = ('0' to '9')
  
  
  
  def crearVehiculo(vMin:Int, vMax:Int, proporciones:Array[String], intersecciones:Array[Interseccion]):Vehiculo={
    def definirTipo(n:String)= n match{
      //se usa el constructor que no recibe placa, en cada clase estará definido como se crean
      //y se envía una interseccion origen y una destino (no se verifica que sean diferentes)
      //el angulo de la velocidad es 0 por defecto
      case "carro" => new Carro(Carro.generarPlaca,
            intersecciones(r.nextInt(intersecciones.length)),
            intersecciones(r.nextInt(intersecciones.length)),
            new Velocidad(vMin+r.nextInt((vMax-vMin))))
      case "moto" => new Moto(Moto.generarPlaca,
            intersecciones(r.nextInt(intersecciones.length)),
            intersecciones(r.nextInt(intersecciones.length)),
            new Velocidad(vMin+r.nextInt((vMax-vMin))))
      case "mototaxi" => new Moto(Moto.generarPlaca,
            intersecciones(r.nextInt(intersecciones.length)),
            intersecciones(r.nextInt(intersecciones.length)),
            new Velocidad(vMin+r.nextInt((vMax-vMin))))
      case "camion" => new Moto(Moto.generarPlaca,
            intersecciones(r.nextInt(intersecciones.length)),
            intersecciones(r.nextInt(intersecciones.length)),
            new Velocidad(vMin+r.nextInt((vMax-vMin))))
      case "bus" => new Moto(Moto.generarPlaca,
            intersecciones(r.nextInt(intersecciones.length)),
            intersecciones(r.nextInt(intersecciones.length)),
            new Velocidad(vMin+r.nextInt((vMax-vMin))))
    }
  //se escoge un indice al azar y dependiendo del tipo de este se crea un vehículo
  definirTipo(proporciones(r.nextInt(1000)))
  }
}
