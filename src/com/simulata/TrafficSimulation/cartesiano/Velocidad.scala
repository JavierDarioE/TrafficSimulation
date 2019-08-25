package com.simulata.TrafficSimulation.cartesiano

//están como variables por si luego ponen que no es constante la velocidad
//las velocidades serán en m/s, sólo se usará km/h cuando se necesite devolver resultados

case class Velocidad (private var _magnitud:Double, private var _angulo:Angulo = Angulo(0)){

  //getters y setters
  def magnitud:Double = _magnitud //getter
  def magnitud_=(magnitud:Double):Unit = _magnitud = magnitud //setter
  
  def angulo: Angulo = _angulo //getter
  def angulo_=(angulo:Angulo):Unit = _angulo = angulo //setter
}

object Velocidad{
  def kphTomps(v:Double):Double={
    v/3.6
  }
  def mpsTokph(v:Double):Int={
    (v*3.6).toInt
  }
}
