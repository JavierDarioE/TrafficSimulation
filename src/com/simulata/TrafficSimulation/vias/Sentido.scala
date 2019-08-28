package com.simulata.TrafficSimulation.vias

case class Sentido private (tipo:String) {
  
}

object Sentido{
  def unaVia: Sentido={
    new Sentido("unaVia")
  }
  
  def dobleVia: Sentido={
    Sentido("dobleVia")
  }
}