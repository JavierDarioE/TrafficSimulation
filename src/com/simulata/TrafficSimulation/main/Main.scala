package com.simulata.TrafficSimulation.main

import com.simulata.TrafficSimulation.simulacion._

object Main extends App{

  var hilo = new Thread(Simulacion)

  hilo.start()
}
