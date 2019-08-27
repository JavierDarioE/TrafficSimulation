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
  val tasaAcelMax: Double = Json.tasaAcelMax
  val tasaAcelMin: Double = Json.tasaAcelMin

  // TODO la instanciación de vias e intersecciones van dentro de un método
  var intersecciones: Array[Interseccion] = Array[Interseccion]()

  val niquia = new Interseccion(300, 12000, Some("Niquia"), Color.BLUE)
  val lauraAuto = new Interseccion(2400, 11400, Some("M. Laura Auto"), Color.RED)
  val lauraReg = new Interseccion(2400, 12600, Some("M. Laura Reg"), Color.MAGENTA)
  val ptoCero = new Interseccion(5400, 12000, Some("Pto 0"), Color.CYAN)
  val mino = new Interseccion(6300, 15000, Some("Minorista"), Color.DARK_GRAY)
  val villa = new Interseccion(6300, 19500, Some("Villanueva"), Color.GRAY)
  val ig65 = new Interseccion(5400, 10500, Some("65 Igu"), Color.GREEN)
  val robledo = new Interseccion(5400, 1500, Some("Exito Rob"), Color.ORANGE)
  val colReg = new Interseccion(8250, 12000, Some("Col Reg"), Color.PINK)
  val colFerr = new Interseccion(8250, 15000, Some("Col Ferr"), Color.YELLOW)
  val col65 = new Interseccion(8250, 10500, Some("Col 65"), Color.BLACK)
  val col80 = new Interseccion(8250, 1500, Some("Col 80"), Color.BLUE)
  val juanOr = new Interseccion(10500, 19500, Some("Sn Juan Ori"), Color.RED)
  val maca = new Interseccion(10500, 12000, Some("Macarena"), Color.MAGENTA)
  val expo = new Interseccion(12000, 13500, Some("Exposiciones"), Color.CYAN)
  val reg30 = new Interseccion(13500, 15000, Some("Reg 30"), Color.DARK_GRAY)
  val monte = new Interseccion(16500, 15000, Some("Monterrey"), Color.GRAY)
  val agua = new Interseccion(19500, 15000, Some("Aguacatala"), Color.GREEN)
  val viva = new Interseccion(21000, 15000, Some("Viva Env"), Color.ORANGE)
  val mayor = new Interseccion(23400, 15000, Some("Mayorca"), Color.PINK)
  val ferrCol = new Interseccion(8250, 15000, Some("Ferr Col"), Color.YELLOW)
  val ferrJuan = new Interseccion(10500, 15000, Some("Alpujarra"), Color.BLACK)
  val sanDiego = new Interseccion(12000, 19500, Some("San Diego"), Color.BLUE)
  val premium = new Interseccion(13500, 19500, Some("Premium"), Color.RED)
  val pp = new Interseccion(16500, 19500, Some("Parque Pob"), Color.MAGENTA)
  val santafe = new Interseccion(19500, 18750, Some("Santa Fe"), Color.CYAN)
  val pqEnv = new Interseccion(21000, 18000, Some("Envigado"), Color.DARK_GRAY)
  val juan65 = new Interseccion(10500, 10500, Some("Juan 65"), Color.GRAY)
  val juan80 = new Interseccion(10500, 1500, Some("Juan 80"), Color.GREEN)
  val _33_65 = new Interseccion(12000, 10500, Some("33 con 65"), Color.ORANGE)
  val bule = new Interseccion(12000, 7500, Some("Bulerias"), Color.PINK)
  val gema = new Interseccion(12000, 1500, Some("St Gema"), Color.YELLOW)
  val _30_65 = new Interseccion(13500, 10500, Some("30 con 65"), Color.BLACK)
  val _30_70 = new Interseccion(13500, 4500, Some("30 con 70"), Color.BLUE)
  val _30_80 = new Interseccion(13500, 1500, Some("30 con 80"), Color.RED)
  val bol65 = new Interseccion(11100, 10500, Some("Boliv con 65"), Color.MAGENTA)
  val gu10 = new Interseccion(16500, 12000, Some("Guay con 10"), Color.CYAN)
  val terminal = new Interseccion(16500, 10500, Some("Term Sur"), Color.GREEN)
  val gu30 = new Interseccion(13500, 12000, Some("Guay 30"), Color.ORANGE)
  val gu80 = new Interseccion(19500, 12000, Some("Guay 80"), Color.PINK)
  val _65_80 = new Interseccion(19500, 10500, Some("65 con 30"), Color.YELLOW)
  val gu_37S = new Interseccion(21000, 12000, Some("Guay con 37S"), Color.BLACK)

  //Instanciamos la lista de vias:
  val vias: ArrayBuffer[Via] = ArrayBuffer(
    Via(niquia, lauraAuto, 80, TipoVia("Carrera"), Sentido.dobleVia, "64C", Some("Auto Norte"), None),
    Via(niquia, lauraReg, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"), None),
    Via(lauraAuto, lauraReg, 60, TipoVia("Calle"), Sentido.dobleVia, "94", Some("Pte Madre Laura"), None),
    Via(lauraAuto, ptoCero, 80, TipoVia("Carrera"), Sentido.dobleVia, "64C", Some("Auto Norte"), None),
    Via(lauraReg, ptoCero, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"), None),
    Via(ptoCero, mino, 60, TipoVia("Calle"), Sentido.dobleVia, "58", Some("Oriental"), None),
    Via(mino, villa, 60, TipoVia("Calle"), Sentido.dobleVia, "58", Some("Oriental"), None),
    Via(ptoCero, ig65, 60, TipoVia("Calle"), Sentido.dobleVia, "55", Some("Iguaná"), None),
    Via(ig65, robledo, 60, TipoVia("Calle"), Sentido.dobleVia, "55", Some("Iguaná"), None),
    Via(ptoCero, colReg, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"), None),
    Via(colReg, maca, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"), None),
    Via(maca, expo, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"), None),
    Via(expo, reg30, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"), None),
    Via(reg30, monte, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"), None),
    Via(monte, agua, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"), None),
    Via(agua, viva, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"), None),
    Via(viva, mayor, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"), None),
    Via(mino, ferrCol, 60, TipoVia("Carrera"), Sentido.dobleVia, "55", Some("Ferrocarril"), None),
    Via(ferrCol, ferrJuan, 60, TipoVia("Carrera"), Sentido.dobleVia, "55", Some("Ferrocarril"), None),
    Via(ferrJuan, expo, 60, TipoVia("Carrera"), Sentido.dobleVia, "55", Some("Ferrocarril"), None),
    Via(villa, juanOr, 60, TipoVia("Carrera"), Sentido.dobleVia, "46", Some("Oriental"), None),
    Via(juanOr, sanDiego, 60, TipoVia("Carrera"), Sentido.dobleVia, "46", Some("Oriental"), None),
    Via(sanDiego, premium, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", Some("Av Pob"), None),
    Via(premium, pp, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", Some("Av Pob"), None),
    Via(pp, santafe, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", Some("Av Pob"), None),
    Via(santafe, pqEnv, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", Some("Av Pob"), None),
    Via(pqEnv, mayor, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", Some("Av Pob"), None),
    Via(ferrCol, colReg, 60, TipoVia("Calle"), Sentido.dobleVia, "450", Some("Colombia"), None),
    Via(colReg, col65, 60, TipoVia("Calle"), Sentido.dobleVia, "450", Some("Colombia"), None),
    Via(col65, col80, 60, TipoVia("Calle"), Sentido.dobleVia, "450", Some("Colombia"), None),
    Via(juanOr, ferrJuan, 60, TipoVia("Calle"), Sentido.dobleVia, "44", Some("Sn Juan"), None),
    Via(ferrJuan, maca, 60, TipoVia("Calle"), Sentido.dobleVia, "44", Some("Sn Juan"), None),
    Via(maca, juan65, 60, TipoVia("Calle"), Sentido.dobleVia, "44", Some("Sn Juan"), None),
    Via(juan65, juan80, 60, TipoVia("Calle"), Sentido.dobleVia, "44", Some("Sn Juan"), None),
    Via(sanDiego, expo, 60, TipoVia("Calle"), Sentido.dobleVia, "33", Some("33"), None),
    Via(expo, _33_65, 60, TipoVia("Calle"), Sentido.dobleVia, "33", Some("33"), None),
    Via(_33_65, bule, 60, TipoVia("Calle"), Sentido.dobleVia, "33", Some("33"), None),
    Via(bule, gema, 60, TipoVia("Calle"), Sentido.dobleVia, "33", Some("33"), None),
    Via(premium, reg30, 60, TipoVia("Calle"), Sentido.dobleVia, "30", Some("30"), None),
    Via(reg30, _30_65, 60, TipoVia("Calle"), Sentido.dobleVia, "30", Some("30"), None),
    Via(_30_65, _30_70, 60, TipoVia("Calle"), Sentido.dobleVia, "30", Some("30"), None),
    Via(_30_70, _30_80, 60, TipoVia("Calle"), Sentido.dobleVia, "30", Some("30"), None),
    Via(maca, bol65, 60, TipoVia("Diagonal"), Sentido.dobleVia, "74B", Some("Boliv"), None),
    Via(bol65, bule, 60, TipoVia("Diagonal"), Sentido.dobleVia, "74B", Some("Boliv"), None),
    Via(bule, _30_70, 60, TipoVia("Diagonal"), Sentido.dobleVia, "74B", Some("Boliv"), None),
    Via(juan80, bule, 60, TipoVia("Transversal"), Sentido.dobleVia, "39B", Some("Nutibara"), None),
    Via(pp, monte, 60, TipoVia("Calle"), Sentido.dobleVia, "10", Some("10"), None),
    Via(monte, gu10, 60, TipoVia("Calle"), Sentido.dobleVia, "10", Some("10"), None),
    Via(gu10, terminal, 60, TipoVia("Calle"), Sentido.dobleVia, "10", Some("10"), None),
    Via(expo, gu30, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", Some("Av Guay"), None),
    Via(gu30, gu10, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", Some("Av Guay"), None),
    Via(gu10, gu80, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", Some("Av Guay"), None),
    Via(gu80, gu_37S, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", Some("Av Guay"), None),
    Via(lauraAuto, ig65, 60, TipoVia("Carrera"), Sentido.dobleVia, "65", Some("65"), None),
    Via(ig65, col65, 60, TipoVia("Carrera"), Sentido.dobleVia, "65", Some("65"), None),
    Via(juan65, col65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", Some("65"), None),
    Via(bol65, juan65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", Some("65"), None),
    Via(_33_65, bol65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", Some("65"), None),
    Via(_30_65, _33_65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", Some("65"), None),
    Via(_30_65, terminal, 60, TipoVia("Carrera"), Sentido.dobleVia, "65", Some("65"), None),
    Via(terminal, _65_80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", Some("65"), None),
    Via(robledo, col80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", Some("80"), None),
    Via(col80, juan80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", Some("80"), None),
    Via(juan80, gema, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", Some("80"), None),
    Via(gema, _30_80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", Some("80"), None),
    Via(_30_80, _65_80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", Some("80"), None),
    Via(_65_80, gu80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", Some("80"), None),
    Via(gu80, agua, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", Some("80"), None),
    Via(agua, santafe, 60, TipoVia("Calle"), Sentido.dobleVia, "12S", Some("80"), None),
    Via(viva, pqEnv, 60, TipoVia("Calle"), Sentido.dobleVia, "37S", Some("37S"), None),
    Via(viva, gu_37S, 60, TipoVia("Calle"), Sentido.dobleVia, "63", Some("37S"), None))

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
  var camaras:Array[CamaraFotoDeteccion]=Array[CamaraFotoDeteccion]()

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
    proporciones.foreach(Vehiculo.crearVehiculo(velMin, velMax, tasaAcelMin, tasaAcelMax, _))

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
      case 1 => Neo4J.guardarEstado()
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
    
    //TODO cambiar la logica para que las operaciones de los case se hagan desde los metodos start, stop, restart

    while (active) {
      running match {
        case 0 => print(".")
          Thread.sleep(100)

        case 1 => for (v <- viajes) v.mover(dt)
          Grafico.moverVehiculos(vehiculos)
          t += dt
          Thread.sleep(tRefresh*100)

        case 2 => print(".")
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
