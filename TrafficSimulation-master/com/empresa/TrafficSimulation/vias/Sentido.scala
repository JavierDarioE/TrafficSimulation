package vias

class Sentido private (val tipo:String) {
  
}

object Sentido{
  def unaVia: Sentido={
    new Sentido("unaVia")
  }
  
  def dobleVia(tipo:String): Sentido={
    new Sentido("dobleVia")
  }
}