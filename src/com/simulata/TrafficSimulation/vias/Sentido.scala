package com.simulata.TrafficSimulation.vias

class Sentido private (val tipo:String) {
  
}

object Sentido{
  def unaVia: Sentido={
    new Sentido("unaVia")
  }
  
  def dobleVia: Sentido={
    new Sentido("dobleVia")
  }
}