package com.simulata.TrafficSimulation.movimiento
import com.simulata.TrafficSimulation.simulacion._
import com.simulata.TrafficSimulation.vias._


case class Comparendo(vehiculo:Vehiculo, vVehiculo:Double, vMaxVia:Double, camara:CamaraFotoDeteccion) {
  Simulacion.comparendos :+=this
}