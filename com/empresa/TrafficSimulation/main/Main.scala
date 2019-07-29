package main

import simulacion._

object Main extends App{

  var sim = new Simulacion()
  var hilo = new Thread(sim)

  hilo.start()
}
