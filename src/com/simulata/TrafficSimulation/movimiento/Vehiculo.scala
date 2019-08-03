package com.simulata.TrafficSimulation.movimiento

import java.awt.{Color, Shape}

import com.simulata.TrafficSimulation.cartesiano._
import com.simulata.TrafficSimulation.vias._
import com.simulata.TrafficSimulation.simulacion.Simulacion

abstract class Vehiculo (val placa:String,
                         val origen:Interseccion,
                         val destino:Interseccion,
                         private var _velocidad:Velocidad,
                         var color: Color,
                         val forma: java.awt.geom.Rectangle2D.Double)
extends Movil(origen, _velocidad) with MovimientoUniforme {
 
 private var _punto:Punto = origen
 
 def punto=_punto

 private var _distanciaRecorrida:Double=0
  
  def distanciaRecorrida=_distanciaRecorrida
  
  def distanciaRecorrida_=(d:Double): Unit = _distanciaRecorrida = d
 //para obtener la ruta
  def n(outer:Interseccion):Simulacion.grafoVia.NodeT=Simulacion.grafoVia get outer
  //ruta es una lista de intersecciones
  var ruta =(n(origen) shortestPathTo n(destino)).get.nodes.toList
  
  def actualizarAngulo(a:Punto,b:Punto,v:Velocidad):Unit={
   //se haya el ángulo entre el vector (b-a) y el vector (0,1) siendo a el punto actual y b al que se quiere ir
    val vector = new Punto(b.x-a.x,b.y-a.y)
    if(vector.y>0){ 
    v.angulo=new Angulo(scala.math.acos(vector.x/Punto.distancia(vector,Punto(0,0)))*180/math.Pi)
    }
    //se le suma 180 grados en caso de que el vector esté en los cuadrantes 3 o 4
    else{
      v.angulo=new Angulo((scala.math.acos(vector.x/Punto.distancia(vector,Punto(0,0)))*180/math.Pi)+180)
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
  
  def crearVehiculo(vMin:Int, vMax:Int, tipo: String, intersecciones:Array[Interseccion]):Vehiculo={
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
      case "mototaxi" => new MotoTaxi(MotoTaxi.generarPlaca,
            intersecciones(r.nextInt(intersecciones.length)),
            intersecciones(r.nextInt(intersecciones.length)),
            new Velocidad(vMin+r.nextInt((vMax-vMin))))
      case "camion" => new Camion(Camion.generarPlaca,
            intersecciones(r.nextInt(intersecciones.length)),
            intersecciones(r.nextInt(intersecciones.length)),
            new Velocidad(vMin+r.nextInt((vMax-vMin))))
      case "bus" => new Bus(Bus.generarPlaca,
            intersecciones(r.nextInt(intersecciones.length)),
            intersecciones(r.nextInt(intersecciones.length)),
            new Velocidad(vMin+r.nextInt((vMax-vMin))))
    }
  //se escoge un indice al azar y dependiendo del tipo de este se crea un vehículo
  definirTipo(tipo)
  }
}
