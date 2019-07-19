package simulacion

import json.Json

class Simulacion extends Runnable{
  override def run(): Unit = {
    var t = Json.getTRefresh
    val dt = Json.getDt
    println(s"camiones: ${Json.getCamiones}")
    println(s"carros: ${Json.getCarros}")
    println(s"t: $t")
    println(s"dt: $dt")

    while (true) {
      //t += td
      //Grafico.graficar
      println("thread is running")
      Thread.sleep(1000)
    }
  }
}