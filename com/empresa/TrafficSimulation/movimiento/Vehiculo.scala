package movimiento

import json._
import cartesiano._
import vias._
import scala.collection.mutable.ArrayBuffer

abstract class Vehiculo (val placa:String,val origen:Interseccion, val destino:Interseccion, private var _velocidad:Velocidad) 
extends Movil(origen, _velocidad) with MovimientoUniforme {
 private var _punto:Punto = origen
 Vehiculo.Vehiculos + (this.placa->this)
}

object Vehiculo{
  var Vehiculos:Map[String,Vehiculo]=Map() 
  
  val r = scala.util.Random
  
  val letras = ('A' to 'Z')
  
  val digitos = ('0' to '9')
  
  val proporcionCarros= Array.fill(Json.carros.toInt*100)("carro")
  val proporcionMotos= Array.fill(Json.motos.toInt*100)("moto")
  val proporcionMotoTaxis= Array.fill(Json.motoTaxis.toInt*100)("mototaxi")
  val proporcionCamion= Array.fill(Json.camiones.toInt*100)("camion")
  val proporcionBus= Array.fill(Json.buses.toInt*100)("bus")
  //Se crea una lista de 100 tipos de automovil de acuerdo a las proporciones, para elegir el tipo de la instancia a crear
  //se escoge un indice al azar y dependiendo del tipo de este se crea un vehículo
  val proporciones= proporcionCarros++proporcionMotos++proporcionMotoTaxis++proporcionCamion++proporcionBus
  
  def crearVehiculo:Vehiculo={
    def definirTipo(n:String)= n match{
      //se usa el constructor que no recibe placa, en cada clase estará definido como se crean
      //y se envía una interseccion origen y una destino (no se verifica que sean diferentes)
      //el angulo de la velocidad es 0 por defecto
      case "carro" => new Carro(Carro.generarPlaca,
            Interseccion.intersecciones(r.nextInt(Interseccion.intersecciones.length)),
            Interseccion.intersecciones(r.nextInt(Interseccion.intersecciones.length)),
            new Velocidad(Json.velMax+r.nextInt(Json.velMax-Json.velMin)))
      case "moto" => new Moto(Moto.generarPlaca,
            Interseccion.intersecciones(r.nextInt(Interseccion.intersecciones.length)),
            Interseccion.intersecciones(r.nextInt(Interseccion.intersecciones.length)),
            new Velocidad(Json.velMax+r.nextInt(Json.velMax-Json.velMin)))
      case "mototaxi" => new Moto(Moto.generarPlaca,
            Interseccion.intersecciones(r.nextInt(Interseccion.intersecciones.length)),
            Interseccion.intersecciones(r.nextInt(Interseccion.intersecciones.length)),
            new Velocidad(Json.velMax+r.nextInt(Json.velMax-Json.velMin)))
      case "camion" => new Moto(Moto.generarPlaca,
            Interseccion.intersecciones(r.nextInt(Interseccion.intersecciones.length)),
            Interseccion.intersecciones(r.nextInt(Interseccion.intersecciones.length)),
            new Velocidad(Json.velMax+r.nextInt(Json.velMax-Json.velMin)))
      case "bus" => new Moto(Moto.generarPlaca,
            Interseccion.intersecciones(r.nextInt(Interseccion.intersecciones.length)),
            Interseccion.intersecciones(r.nextInt(Interseccion.intersecciones.length)),
            new Velocidad(Json.velMax+r.nextInt(Json.velMax-Json.velMin)))
    }
  definirTipo(proporciones(r.nextInt(100)))
  }
}
