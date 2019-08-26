package com.simulata.TrafficSimulation.semaforo

import java.util.LinkedList

class NodoSemaforo {
  
  var semaforos: Array[Semaforo] = Array()
  
  def agregarSemaforo(semaforo: Semaforo): Unit =
    semaforos :+= semaforo
  
  // Hace un paso en el flujo de estados de los semaforos de este nodo 
  def estadosFluyen: Unit = {

    // Si todos los semaforos del nodo estan en rojo, pone el primero en verde
    if(semaforos.forall(_.estado=="rojo")) {
      semaforos(0).estado = "verde"
    }
    else {
      
      // Si hay mas de un semaforo que no esta en rojo, ponerlos todos en rojo
      if(semaforos.count(_.estado != "rojo") > 1) semaforos.foreach(_.estado = "rojo")
      else {
        
        // El semaforo que no esta en rojo
        var semaforoACambiar = semaforos.find(_.estado != "rojo").get

        semaforoACambiar.estadoFluye
        
        // Si el semaforo quedo en rojo
        if(semaforoACambiar.estado == "rojo") {

          // Poner en verde al semaforo que le sigue en la lista
          // o al primero si ya era el ultimo de la lista
          try {
            semaforos(semaforos.indexOf(semaforoACambiar) + 1).estado = "verde"
          } catch {
            case e: Exception => semaforos(0).estado = "verde"
          }
        }
      }      
    }
  }
}

object NodoSemaforo {
  
  var listaDeNodoSemaforo: LinkedList[NodoSemaforo] = new LinkedList()
}