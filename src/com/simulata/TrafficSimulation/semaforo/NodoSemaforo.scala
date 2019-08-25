package com.simulata.TrafficSimulation.semaforo

class NodoSemaforo {
  
  //habrá minimo 2 semaforos, máximo 12
  var semaforos: Array[Semaforo] = Array()
  
  def agregarSemaforo(semaforo: Semaforo): Unit =
    semaforos :+= semaforo
  
  // TODO Lo siguiente es la secuencia de estados de los semaforos
  
}