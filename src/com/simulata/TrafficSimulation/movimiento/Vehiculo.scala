package com.simulata.TrafficSimulation.movimiento

import java.awt.{Color, Shape}
import com.simulata.TrafficSimulation.cartesiano._
import com.simulata.TrafficSimulation.vias._
import com.simulata.TrafficSimulation.simulacion.Simulacion
import com.simulata.TrafficSimulation.grafico.GrafoVia
import scala.collection.mutable.Queue

abstract case class Vehiculo (placa:String,
                              private var _vel:Velocidad,
                              var color: Color)
extends Movil(Punto(0,0), _vel) with MovimientoUniforme {

  val random = scala.util.Random
  val viaje = new Viaje(this)
  var via = viaje.colaVias.dequeue()
  var siguienteInterseccion = viaje.interseccionDestino
  posicion_=(new Punto(viaje.interseccionOrigen.xx, viaje.interseccionOrigen.yy))
  println(viaje.interseccionOrigen.nombre)
  var direccion:Angulo = Angulo(0)
  var distanciaParaRecorrer = viaje.colaVias.map(_.longitud).sum//aqui

  def move(dt:Double){
    if (posicion == via.origenn.copy()){
      direccion = Angulo(tangenteInversa(via.origenn.xx,via.finn.xx,via.origenn.yy, via.finn.yy))
      siguienteInterseccion = via.finn
    }
    else if (posicion == via.finn.copy()){
      direccion = Angulo(tangenteInversa(via.finn.xx,via.origenn.xx,via.finn.yy, via.origenn.yy))
      siguienteInterseccion = via.origenn
    }
    velocidad.angulo_=(direccion)
    //velocidad_=(Velocidad(velocidad.magnitud, direccion))
    val (px, py) = movimiento(dt, velocidad)
    posicion_= (new Punto(posicion.x + px, posicion.y + py))
    val dist = Punto.distancia(posicion, siguienteInterseccion.copy())
    //val hip = math.abs(math.sqrt(math.pow(posicion.x-siguienteInterseccion.xx,2) + math.pow(posicion.x-siguienteInterseccion.xx, 2)))
    if(dist <= (velocidad.magnitud*dt+1)){
      posicion_=(Punto(siguienteInterseccion.xx, siguienteInterseccion.yy))
      posicion.x = siguienteInterseccion.xx
      posicion.y = siguienteInterseccion.yy
      if(viaje.colaVias.nonEmpty){
        via = viaje.colaVias.dequeue()
      }
      else{
        velocidad.magnitud_=(0)
      }
    }
  }

  def tangenteInversa(x1:Double,x2:Double,y1:Double,y2:Double):Double ={
    val diferenciaX = x2 - x1
    val diferenciaY = y2 - y1

    if (diferenciaX == 0){
      if(diferenciaY > 0){
        90
      }else{
        270
      }
    }else if(diferenciaY == 0){
      if(diferenciaX > 0){
        0
      }else{
        180
      }
    }else{
      val m = diferenciaY/diferenciaX
      val a = scala.math.atan(m).toDegrees
      if(diferenciaX > 0 && diferenciaY > 0){
        a
      }else if(diferenciaX < 0 && diferenciaY >0){
        a + 180
      }else if(diferenciaX < 0 && diferenciaY <0){
        a + 180
      }else if (diferenciaX > 0 && diferenciaY<0){
        360 + a
      }else{
        0
      }
    }
  }

  /* accedemos a la lista de vehiculos en el objeto Simulacion */
 
  Simulacion.vehiculos :+=this
}

object Vehiculo{
  val r = scala.util.Random
  
  val letras = ('A' to 'Z')
  
  val digitos = ('0' to '9')  
  
  def crearVehiculo(vMin:Double, vMax:Double, tipo: String/*, intersecciones:Array[Interseccion]*/):Vehiculo={
    def definirTipo(n:String)= n match{
      //se usa el constructor que no recibe placa, en cada clase estará definido como se crean
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
}
