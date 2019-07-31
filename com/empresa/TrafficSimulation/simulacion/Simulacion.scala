package simulacion

import json.Json

class Simulacion extends Runnable{
  override def run(): Unit = {
    var t = Json.tRefresh
    val dt = Json.dt
    println(s"camiones: ${Json.camiones}")
    println(s"carros: ${Json.carros}")
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
