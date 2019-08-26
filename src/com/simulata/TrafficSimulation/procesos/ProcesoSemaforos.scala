package com.simulata.TrafficSimulation.procesos

import com.simulata.TrafficSimulation.simulacion._
import com.simulata.TrafficSimulation.semaforo._

import java.util.LinkedList
import java.util.Iterator
import java.util.ListIterator

object ProcesoSemaforos extends Runnable{
  
  var semaforosCompletos = false
  
  override def run(): Unit = {
    
    // Un while vacio que espera a que todos los semaforos sean creados
    while(!semaforosCompletos) {}
    
    while (Simulacion.active){
      
      var iteradorNodosSemaforo: Iterator[NodoSemaforo] = NodoSemaforo.listaDeNodoSemaforo.listIterator()
      
      // Hace fluir a todos los semaforos
      while( iteradorNodosSemaforo.hasNext () ) {
        iteradorNodosSemaforo.next().estadosFluyen
      }
      
      // Funciona bien si esta asi, durmiendo un segundo
      Thread.sleep(1000)
    }
  }
  
  // Los siguientes tres metodos los deje como estaban porque ni se para que son :p
  def start(): Int = 1

  def restart(): Int = 0

  def pause(): Int = 0
}