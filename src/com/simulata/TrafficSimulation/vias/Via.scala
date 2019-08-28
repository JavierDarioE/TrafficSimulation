package com.simulata.TrafficSimulation.vias

import com.simulata.TrafficSimulation.cartesiano._
//no estoy seguro de si poner la velocidad de la via como tipo int o tipo velocidad, creo que
//es mas facil dejandola tipo Int por como se le mandan los parámetros
case class Via(origenn:Interseccion,
               finn:Interseccion,
               v:Int ,
               tipo:TipoVia,
               sentido:Sentido,
               numero:String,
               nombre: Option[String] = None,
               var _camara: Option[CamaraFotoDeteccion] = None)
  extends Recta(){
  type T = Interseccion
  origen = origenn
  fin = finn
  //v está en kph y vmax en mps
  val vMax=Velocidad.kphTomps(v)
  def longitud: Double = {
    Punto.distancia(origenn,finn)
  } 
  var camara=_camara
  def camara_=(camara: CamaraFotoDeteccion): Unit = _camara = Some(camara)
}