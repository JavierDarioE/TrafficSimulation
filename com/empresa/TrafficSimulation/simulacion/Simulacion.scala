package simulacion

import json.Json
import resultadosSimulacion.ResultadosSimulacion

class Simulacion extends Runnable{
  override def run(): Unit = {


    /* testing class ResultadosSimulacion */
    val resultados = new ResultadosSimulacion

    resultados.buses_=(1)
    resultados.camiones_=(2)
    resultados.carros_=(3)
    resultados.distMaxima_=(4)
    resultados.distMinima_=(6)
    resultados.distPromedio_=(7)
    resultados.intersecciones_=(8)
    resultados.longitudPromedio_=(7)
    resultados.motos_=(4)
    resultados.motoTaxis_=(7)
    resultados.promedioDestino_=(10)
    resultados.promedioOrigen_=(2)
    resultados.realidad_=(6)
    resultados.simulacion_=(10)
    resultados.sinDestino_=(20)
    resultados.sinOrigen_=(20)
    resultados.total_=(20)
    resultados.viasUnSentido_=(10)
    resultados.viasDobleSentido_=(10)
    resultados.velPromedio_=(90)
    resultados.velMinima_=(90)
    resultados.velMaxima_=(20)
    resultados.vias_=(4)
    resultados.velocidadMaxima_=(50)
    resultados.velocidadMinima_=(20)

    resultados.guardar()
    //end of test

    /*test json loader*/
    var t = Json.tRefresh
    val dt = Json.dt
    println(s"camiones: ${Json.camiones}")
    println(s"carros: ${Json.carros}")
    println(s"t: $t")
    println(s"dt: $dt")
    //end of test

    while (true) {
      //t += td
      //Grafico.graficar
      println("thread is running")
      Thread.sleep(1000)
    }
  }
}
