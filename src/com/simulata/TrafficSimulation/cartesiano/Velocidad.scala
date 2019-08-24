package com.simulata.TrafficSimulation.cartesiano

//están como variables por si luego ponen que no es constante la velocidad
//las velocidades serán en m/s, sólo se usará km/h cuando se necesite devolver resultados

case class Velocidad (private var _magnitud:Double, private var _angulo:Angulo = Angulo(0)){
  //constructor auxiliar (no funciona)
  def this(magnitud:Double){
    this(magnitud, Angulo(0))
  }
  //accesores y mutadores (?)
  def magnitud:Double = _magnitud
  
  def magnitud_=(magnitud:Double):Unit = _magnitud = magnitud
  
  def angulo: Angulo = _angulo
  
  def angulo_=(angulo:Angulo):Unit = _angulo = angulo
}

object Velocidad{
  def kphTomps(v:Double):Double={
    v/3.6
  }
  def mpsTokph(v:Double):Int={
    (v*3.6).toInt
  }
}
