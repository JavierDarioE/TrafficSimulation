package main

import simulacion._

object Main extends App{

  var hilo = new Thread(Simulacion)

  hilo.start()
}
