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
  
  
  
  def crearVehiculo(vMin:Int, vMax:Int, tipo: String, intersecciones:Array[Interseccion]):Vehiculo={
    def definirTipo(n:String): Vehiculo = n match{
      //se usa el constructor que no recibe placa, en cada clase estará definido como se crean
      //y se envía una interseccion origen y una destino (no se verifica que sean diferentes)
      //el angulo de la velocidad es 0 por defecto
      case "carro" => new Carro(Carro.generarPlaca,
            intersecciones(r.nextInt(intersecciones.length)),
            intersecciones(r.nextInt(intersecciones.length)),
            new Velocidad(vMin+r.nextInt(vMax-vMin)))
      case "moto" => new Moto(Moto.generarPlaca,
            intersecciones(r.nextInt(intersecciones.length)),
            intersecciones(r.nextInt(intersecciones.length)),
            new Velocidad(vMin+r.nextInt(vMax-vMin)))
      case "mototaxi" => new MotoTaxi(MotoTaxi.generarPlaca,
            intersecciones(r.nextInt(intersecciones.length)),
            intersecciones(r.nextInt(intersecciones.length)),
            new Velocidad(vMin+r.nextInt(vMax-vMin)))
      case "camion" => new Camion(Camion.generarPlaca,
            intersecciones(r.nextInt(intersecciones.length)),
            intersecciones(r.nextInt(intersecciones.length)),
            new Velocidad(vMin+r.nextInt(vMax-vMin)))
      case "bus" => new Bus(Bus.generarPlaca,
            intersecciones(r.nextInt(intersecciones.length)),
            intersecciones(r.nextInt(intersecciones.length)),
            new Velocidad(vMin+r.nextInt(vMax-vMin)))
    }
  //se genera un vehículo dependiendo del tipo.
  definirTipo(tipo)
  }
}
