package com.simulata.TrafficSimulation.vias

import com.simulata.TrafficSimulation.cartesiano.Recta
import com.simulata.TrafficSimulation.cartesiano.Punto

//no estoy seguro de si poner la velocidad de la via como tipo int o tipo velocidad, creo que
//es mas facil dejandola tipo Int por como se le mandan los parámetros
case class Via(val origenn:Interseccion, val finn:Interseccion, vMax:Int , tipo:TipoVia, val sentido:Sentido, numero:String, val nombre:String)
  extends Recta(){
  type T = Interseccion
  origen = origenn
  fin = finn
  
  def longitud: Double = {
    Punto.distancia(origenn,finn)
  } 
}