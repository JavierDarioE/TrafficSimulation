package com.simulata.TrafficSimulation.simulacion

import com.simulata.TrafficSimulation.neo4J.Neo4J
import java.awt.Color
import java.awt.event.{KeyEvent, KeyListener, WindowEvent, WindowListener}

import com.simulata.TrafficSimulation.grafico.{Grafico, GrafoVia}
import com.simulata.TrafficSimulation.resultadosSimulacion._
import com.simulata.TrafficSimulation.json.Json
import com.simulata.TrafficSimulation.vias._
import com.simulata.TrafficSimulation.movimiento._
import com.simulata.TrafficSimulation.grafico._
import com.simulata.TrafficSimulation.cartesiano._
import com.simulata.TrafficSimulation.semaforo._
import com.simulata.TrafficSimulation.procesos._
import scalax.collection.Graph
import scalax.collection.edge.WLDiEdge

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import java.util.LinkedList

object Simulacion extends Runnable {
  //primero obtenemos los parámetros necesarios desde el json:
  val motos: Double = Json.motos
  val carros: Double = Json.carros
  val camiones: Double = Json.camiones
  val buses: Double = Json.buses
  val motoTaxis: Double = Json.motoTaxis
  val dt:Int = Json.dt
  var t = 0
  val tRefresh: Int = Json.tRefresh
  val maximo: Int = Json.maximo
  val minimo: Int = Json.minimo
  val velMax: Double = Velocidad.kphTomps(Json.velMax)
  val velMin: Double = Velocidad.kphTomps(Json.velMin)
  val acelMax: Double = Json.acelMax
  val acelMin: Double = Json.acelMin

  // TODO la instanciación de vias e intersecciones van dentro de un método
  
  var intersecciones: Array[Interseccion] = Array[Interseccion]()
  val vias: ArrayBuffer[Via] = Neo4J.leerMallaVial
  
  val viasBackup: ArrayBuffer[Via] = vias //un backup de las vias, lol.
  
  // Se crean los semaforos
  vias.foreach(via => {
    
    // Se crea semaforo en el nodo final de la via
    via.finn.nodoSemaforo.agregarSemaforo(new Semaforo(via))
    
    // Si este nodoSemaforo no esta en la lista se lo agrega
    if(!NodoSemaforo.listaDeNodoSemaforo.contains(via.finn.nodoSemaforo)) {
      NodoSemaforo.listaDeNodoSemaforo.add(via.finn.nodoSemaforo)
    }
    
    // Si la via es de doble sentido se crea semaforo en el nodo origen de la misma 
    if(via.sentido.tipo.equals("dobleVia")){
      via.origenn.nodoSemaforo.agregarSemaforo(new Semaforo(via))
      
      // Si este nodoSemaforo no esta en la lista se lo agrega
      if(!NodoSemaforo.listaDeNodoSemaforo.contains(via.origenn.nodoSemaforo)) {
        NodoSemaforo.listaDeNodoSemaforo.add(via.origenn.nodoSemaforo)
      }
    }
  })
  // Hasta aqui fué la creacion de los semaforos
  
  ProcesoSemaforos.semaforosCompletos = true
  
  //TODO instanciar cámaras desde neo4j
  val camaras:Array[CamaraFotoDeteccion]=Array[CamaraFotoDeteccion]()

  var comparendos: Array[Comparendo] = Array[Comparendo]()

  var viajes: Array[Viaje] = Array[Viaje]()

  var vehiculos: Array[Vehiculo] = Array[Vehiculo]() //un array donde estarán todos los vehiculos de la simulación.

  GrafoVia.construir(vias) //construir el grafo representando el sistema de vías.
  Grafico.graficarVias(vias.toArray) //graficar la vías en la ventana.

  var running = 2
  var active = true

  def prepararSimulacion(): Unit ={
    val random: scala.util.Random = scala.util.Random
    val porcentaje: Int = minimo + random.nextInt(maximo-minimo) //cantidad aleatoria de autos dentro de los límites.

    //Se crean arreglos de strings que indican el tipo de vehiculo, el tamaño depende del porcentaje del tipo de vehiculo
    //presente en la simulación, cada lista indica la cantidad de cada vehiculo que habrá en la simulación.
    val proporcionCarros: Array[String] = Array.fill((carros * porcentaje).toInt)("carro")
    val proporcionMotos: Array[String] = Array.fill((motos * porcentaje).toInt)("moto")
    val proporcionMotoTaxis: Array[String] = Array.fill((motoTaxis * porcentaje).toInt)("mototaxi")
    val proporcionCamion: Array[String] = Array.fill((camiones * porcentaje).toInt)("camion")
    val proporcionBus: Array[String] = Array.fill((buses * porcentaje).toInt)("bus")

    //Se concatenan los arrays creando un array de
    //la cantidad total de vehiculos que habrá
    val proporciones: Array[String] = {
      proporcionCarros ++
        proporcionMotos ++
        proporcionMotoTaxis ++
        proporcionCamion ++
        proporcionBus
    }

    //Se instancian los vehículos con las proporciones definidas más arriba:
    proporciones.foreach(Vehiculo.crearVehiculo(velMin, velMax, acelMin, acelMax, _))

    vehiculos.foreach(new Viaje(_)) //a cada vehiculo se le asigna un viaje

  }

  def borrarDatosSimulacion(): Unit ={
    comparendos = Array[Comparendo]()
    viajes = Array[Viaje]()
    vehiculos = Array[Vehiculo]()
  }

  def eventoF5(): Unit = {
    running match {
      case 2 => prepararSimulacion() //instanciar vehiculos y
        Grafico.graficarVehiculos(vehiculos) //agregar un método que carga los datos desce cero
        running = 1
      case 0 => running = 1
      case 1 => running = 0
      case _ => println("\nno es posible esta acción en estos momentos")
    }
  }

  def eventoF6(): Unit = {
    running match {
      case 1 | 0 => running = 3
      case _ => println("\nno es posible esta acción en estos momentos")
    }
  }

  def eventoF2(): Unit = {
    running match {
      case 1 => Neo4J.guardarEstado(viajes)
        Grafico.limpiarVehiculos(vehiculos)
        running = 2
      case _ => println("\nno es posible realizar esta acción en estos momentos")
    }
  }

  def eventoF1(): Unit = {
    running match {
      case 2 => //cargar los datos desde neo4j
        Grafico.graficarVehiculos(vehiculos)
        running = 1
      case _ => println("\nno es posible realizar esta acción en estos momentos")
    }
  }

  override def run(): Unit = {
    while (active) {
      running match {
        case 0 => println("Pausado")
          Thread.sleep(100)

        case 1 => for (v <- viajes) v.mover(dt)
          Grafico.moverVehiculos(vehiculos)
          t += dt
          Thread.sleep(tRefresh*100)

        case 2 => println("Pausado")
          Thread.sleep(100)

        case 3 => Grafico.limpiarVehiculos(vehiculos)
          borrarDatosSimulacion()
          running = 2

        case _ => println("\nvalor de la variable running inválido")
      }
    }
/*
    val resultados = new ResultadosSimulacion

    resultados.buses_=(vehiculos.count(_.isInstanceOf[Bus]))
    resultados.camiones_=(vehiculos.count(_.isInstanceOf[Camion]))
    resultados.carros_=(vehiculos.count(_.isInstanceOf[Carro]))
    resultados.distMaxima_=(vehiculos.map(_.distanciaRecorrida.toInt).max)
    resultados.distMinima_=(vehiculos.map(_.distanciaRecorrida.toInt).min)
    resultados.distPromedio_=(vehiculos.map(_.distanciaRecorrida.toInt).sum/vehiculos.length)
    resultados.intersecciones_=(intersecciones.length)
    resultados.longitudPromedio_=(vias.map(_.longitud.toInt).sum/vias.length)
    resultados.motos_=(vehiculos.count(_.isInstanceOf[Moto]))
    resultados.motoTaxis_=(vehiculos.count(_.isInstanceOf[MotoTaxi]))
    resultados.promedioDestino_=(intersecciones.map(_.fin.length).reduce(_+_)/intersecciones.length)
    resultados.promedioOrigen_=(intersecciones.map(_.origenes.length).reduce(_+_)/intersecciones.length)
    resultados.realidad_=(6)
    resultados.simulacion_=(t)
    resultados.sinDestino_=(intersecciones.length-vehiculos.map(_.destino).length)
    resultados.sinOrigen_=(intersecciones.length-vehiculos.map(_.origen).length)
    resultados.total_=(vehiculos.length)
    resultados.viasUnSentido_=(vias.count(_.sentido.tipo == "unaVia"))
    resultados.viasDobleSentido_=(vias.count(_.sentido.tipo == "dobleVia"))
    resultados.velPromedio_=(vehiculos.map(_.velocidad.magnitud.toInt).reduce(_+_)/vehiculos.length)
    resultados.velMinima_=(vias.map(_.v).min)
    resultados.velMaxima_=(vias.map(_.v).max)
    resultados.vias_=(vias.length)
    resultados.velocidadMaxima_=(Velocidad.mpsTokph(vehiculos.map(_.velocidad.magnitud.toInt).max))
    resultados.velocidadMinima_=(Velocidad.mpsTokph(vehiculos.map(_.velocidad.magnitud.toInt).min))
    resultados.cantidad_=(comparendos.length)
    resultados.promedioPorcentajeExceso_=(comparendos.map(x=>(x.vVehiculo*100/x.vMaxVia)-100).sum/comparendos.length)

    resultados.guardar()
 */
  }
}
