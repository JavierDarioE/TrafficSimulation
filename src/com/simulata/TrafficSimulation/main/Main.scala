package com.simulata.TrafficSimulation.main

import com.simulata.TrafficSimulation.simulacion._
import com.simulata.TrafficSimulation.procesos._

object Main extends App{

  var hilo = new Thread(Simulacion)
  var hiloSemaforos = new Thread(ProcesoSemaforos)

  hilo.start()
  hiloSemaforos.start()
}
