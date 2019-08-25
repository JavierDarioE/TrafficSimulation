package com.simulata.TrafficSimulation.movimiento

import java.awt.{Color, Shape}
import com.simulata.TrafficSimulation.cartesiano._
import com.simulata.TrafficSimulation.vias._
import com.simulata.TrafficSimulation.simulacion.Simulacion
import com.simulata.TrafficSimulation.grafico.GrafoVia
import scala.collection.mutable.Queue

abstract case class Vehiculo (val placa:String,
                         val origen:Interseccion,
                         val destino:Interseccion,
                         private var _velocidad:Velocidad,
                         var color: Color,
                         val forma: java.awt.geom.Rectangle2D.Double,
                         val viaje:Option[Viaje])
extends Movil(origen, _velocidad) with MovimientoUniforme {

  println(origen.nombre)
  println(destino.nombre)
  val caminoMasCorto = (GrafoVia.interseccion(origen)).shortestPathTo(GrafoVia.interseccion(destino))
    
  val interseccionesCaminoMasCorto = caminoMasCorto.get.nodes.map(_.value).toArray
    
  val colaIntersecciones = new Queue[Interseccion]()
    interseccionesCaminoMasCorto.foreach(i => colaIntersecciones += i)
   
  val viasCaminoMasCorto = caminoMasCorto.get.edges.map(_.label.asInstanceOf[Via]).toArray
  
  val colaVias = new Queue[Via]()
    viasCaminoMasCorto.foreach(i => colaVias += i)
  //via inicial
  private var _via:Via = colaVias.dequeue
 
  def via=_via
 
  def via_=(vi:Via): Unit = _via = vi
  //posicion inicial
  private var _punto:Punto = origen//hay que revisar si get.nodes incluye el nodo origen, si si lo incluye entonces es => colaIntersecciones.dequeue
 
  def punto=_punto
 
  def punto_=(p:Punto): Unit = _punto = p
 
  def velocidad = _velocidad
  
  def velocidad_=(v:Velocidad):Unit = _velocidad = v

  private var _distanciaRecorrida:Double=0
  
  def distanciaRecorrida=_distanciaRecorrida
  
  def distanciaRecorrida_=(d:Double): Unit = _distanciaRecorrida = d

  def actualizarAngulo(viaActual:Via):Unit={
    velocidad_=(Velocidad(velocidad.magnitud,viaActual.angulo()))
  }
  //Indica el ángulo inicial
  actualizarAngulo(via)

  def move(dt:Double):Unit={
    //se verifica que aún no se haya llegado a la via final
    if (colaVias.length>1){
      //se verifica que la distancia entre el vehículo y la intersección objetivo actual sea mayor que la distancia que se
      //mueve el vehículo en dt, si es menor se corrige poniendo el vehículo en la posición de la intersección objetivo actual
      //y como se alcanzó se actualiza el ángulo y se remueven tanto la intersección actual como la via actual de sus respectivas colas
      if(Punto.distancia(punto, colaIntersecciones.head)<=(Punto.distancia(Punto(0,0),movimiento(dt,velocidad))+10)){
        actualizarAngulo(colaVias.dequeue)
        punto_=(colaIntersecciones.dequeue)
        }
    }
    //Verifica que aún no haya llegado a la intersección final
    if(colaIntersecciones.length>0){
      val dp = movimiento(dt,velocidad)
      val nuevox = dp.x+punto.x
      val nuevoy = dp.y+punto.y
      punto_=(Punto(nuevox,nuevoy))
      distanciaRecorrida_=(distanciaRecorrida+Punto.distancia(dp,Punto(0,0)))
      Simulacion.camaras.foreach(x=>
      //Verifica que la distancia entre la posición actual y la de la cámara sea menor al movimiento 
      //y que la velocidad sea menor a la velocidad permitida
        if(Punto.distancia(x.posicion, punto)<=Punto.distancia(dp,Punto(0,0))&&(velocidad.magnitud>via.vMax))
          (x.crearComparendo(this/*el vehiculo*/)))
    }
  }

 
  Simulacion.vehiculos :+=this
 
  origen.origenes :+=this
 
  destino.fin:+=this

}

object Vehiculo{
  val r = scala.util.Random
  
  val letras = ('A' to 'Z')
  
  val digitos = ('0' to '9')  
  
  def crearVehiculo(vMin:Double, vMax:Double, tipo: String, intersecciones:Array[Interseccion]):Vehiculo={
    //Para verificar que el origen y el destino no sean iguales
    val a:Int = r.nextInt(intersecciones.length)
    var b:Int = r.nextInt(intersecciones.length)
    while(b == a) b=r.nextInt(intersecciones.length)
    def definirTipo(n:String)= n match{
      //se usa el constructor que no recibe placa, en cada clase estará definido como se crean
      //y se envía una interseccion origen y una destino 
      //el angulo de la velocidad es 0 por defecto
      case "carro" => new Carro(Carro.generarPlaca,
            intersecciones(a),
            intersecciones(b),
            Velocidad(vMin+r.nextInt((vMax-vMin).toInt))
            )
      case "moto" => new Moto(Moto.generarPlaca,
            intersecciones(a),
            intersecciones(b),
            Velocidad(vMin+r.nextInt((vMax-vMin).toInt))
            )
      case "mototaxi" => new MotoTaxi(MotoTaxi.generarPlaca,
            intersecciones(a),
            intersecciones(b),
            Velocidad(vMin+r.nextInt((vMax-vMin).toInt))
            )
      case "camion" => new Camion(Camion.generarPlaca,
            intersecciones(a),
            intersecciones(b),
            Velocidad(vMin+r.nextInt((vMax-vMin).toInt))
            )
      case "bus" => new Bus(Bus.generarPlaca,
            intersecciones(a),
            intersecciones(b),
            Velocidad(vMin+r.nextInt((vMax-vMin).toInt))
            )
    }
  //se escoge un indice al azar y dependiendo del tipo de este se crea un vehículo
    definirTipo(tipo)
  }
}
