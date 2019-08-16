package com.simulata.TrafficSimulation.vias

case class Sentido private (val tipo:String) {
  
}

object Sentido{
  def unaVia: Sentido={
    new Sentido("unaVia")
  }
  
  def dobleVia: Sentido={
    Sentido("dobleVia")
  }
}