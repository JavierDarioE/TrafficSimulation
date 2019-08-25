package com.simulata.TrafficSimulation.vias
import com.simulata.TrafficSimulation.cartesiano._
import com.simulata.TrafficSimulation.movimiento._
import com.simulata.TrafficSimulation.simulacion._


//Todas las cámaras estarán ubicadas en la mitad de la vía

class CamaraFotoDeteccion (val via:Via){
  val posicion:Punto=via.puntoMedio()
  val distanciaOrigen:Double=Punto.distancia(via.origen,posicion)
  
  def crearComparendo(vehiculo:Vehiculo)={
    val camara=this
    var bool=true
    //Verifica que esta camara no le haya realizado otro comparendo al mismo vehiculo
   def verificarComparendo (comparendo:Comparendo)= comparendo match{
     case Comparendo(vehiculo,_,_,camara)=> 
       bool=false
   }
   Simulacion.comparendos.foreach(verificarComparendo(_))
   //si entre los comparendos no hay ninguno que cumpla crea la fotomulta
   if (bool) new Comparendo(vehiculo,vehiculo.velocidad.magnitud,via.vMax, this)
  }
}
