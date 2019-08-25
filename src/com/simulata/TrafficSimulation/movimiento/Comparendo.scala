package com.simulata.TrafficSimulation.movimiento
import com.simulata.TrafficSimulation.simulacion._
import com.simulata.TrafficSimulation.vias._


case class Comparendo(val vehiculo:Vehiculo,val vVehiculo:Double,val vMaxVia:Double,val camara:CamaraFotoDeteccion) {
  Simulacion.comparendos :+=this
}