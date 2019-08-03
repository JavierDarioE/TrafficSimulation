package simulacion

import java.awt.Color

import grafico.{Grafico, GrafoVia}
import resultadosSimulacion._
import json.Json
import vias._
import movimiento._
import grafico._
import scala.collection.mutable.ArrayBuffer

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Simulacion extends Runnable {

  def start(): Int = 1

  def restart(): Int = {
    0
    //vehiculos.restart
  }

  def pause(): Int = 0

  def mover(movil: Array[Vehiculo]): Unit = {
    for (m <- movil) m.mover(dt)
  }

  //Parámetros de la Simulación

  val motos: Double = Json.motos
  val carros: Double = Json.carros
  val camiones: Double = Json.camiones
  val buses: Double = Json.buses
  val motoTaxis: Double = Json.motoTaxis
  val dt = Json.dt
  var t = 0
  val tRefresh = Json.tRefresh * 1000
  val maximo: Int = Json.maximo
  val minimo: Int = Json.minimo
  val velMax: Int = Json.velMax
  val velMin: Int = Json.velMin


  val random: scala.util.Random = scala.util.Random
  val porcentaje: Int = minimo + random.nextInt(maximo-minimo)

  var intersecciones: Array[Interseccion] = Array[Interseccion]()

  //Se instancian las Intersecciones(se añadirán a intersecciones al crearse):
  val niquia = new Interseccion(300, 12000, "Niquia", Color.BLUE)
  val lauraAuto = new Interseccion(2400, 11400, "M. Laura Auto", Color.RED)
  val lauraReg = new Interseccion(2400, 12600, "M. Laura Reg", Color.MAGENTA)
  val ptoCero = new Interseccion(5400, 12000, "Pto 0", Color.CYAN)
  val mino = new Interseccion(6300, 15000, "Minorista", Color.DARK_GRAY)
  val villa = new Interseccion(6300, 19500, "Villanueva", Color.GRAY)
  val ig65 = new Interseccion(5400, 10500, "65 Igu", Color.GREEN)
  val robledo = new Interseccion(5400, 1500, "Exito Rob", Color.ORANGE)
  val colReg = new Interseccion(8250, 12000, "Col Reg", Color.PINK)
  val colFerr = new Interseccion(8250, 15000, "Col Ferr", Color.YELLOW)
  val col65 = new Interseccion(8250, 10500, "Col 65", Color.BLACK)
  val col80 = new Interseccion(8250, 1500, "Col 80", Color.BLUE)
  val juanOr = new Interseccion(10500, 19500, "Sn Juan Ori", Color.RED)
  val maca = new Interseccion(10500, 12000, "Macarena", Color.MAGENTA)
  val expo = new Interseccion(12000, 13500, "Exposiciones", Color.CYAN)
  val reg30 = new Interseccion(13500, 15000, "Reg 30", Color.DARK_GRAY)
  val monte = new Interseccion(16500, 15000, "Monterrey", Color.GRAY)
  val agua = new Interseccion(19500, 15000, "Aguacatala", Color.GREEN)
  val viva = new Interseccion(21000, 15000, "Viva Env", Color.ORANGE)
  val mayor = new Interseccion(23400, 15000, "Mayorca", Color.PINK)
  val ferrCol = new Interseccion(8250, 15000, "Ferr Col", Color.YELLOW)
  val ferrJuan = new Interseccion(10500, 15000, "Alpujarra", Color.BLACK)
  val sanDiego = new Interseccion(12000, 19500, "San Diego", Color.BLUE)
  val premium = new Interseccion(13500, 19500, "Premium", Color.RED)
  val pp = new Interseccion(16500, 19500, "Parque Pob", Color.MAGENTA)
  val santafe = new Interseccion(19500, 18750, "Santa Fe", Color.CYAN)
  val pqEnv = new Interseccion(21000, 18000, "Envigado", Color.DARK_GRAY)
  val juan65 = new Interseccion(10500, 10500, "Juan 65", Color.GRAY)
  val juan80 = new Interseccion(10500, 1500, "Juan 80", Color.GREEN)
  val _33_65 = new Interseccion(12000, 10500, "33 con 65", Color.ORANGE)
  val bule = new Interseccion(12000, 7500, "Bulerias", Color.PINK)
  val gema = new Interseccion(12000, 1500, "St Gema", Color.YELLOW)
  val _30_65 = new Interseccion(13500, 10500, "30 con 65", Color.BLACK)
  val _30_70 = new Interseccion(13500, 4500, "30 con 70", Color.BLUE)
  val _30_80 = new Interseccion(13500, 1500, "30 con 80", Color.RED)
  val bol65 = new Interseccion(11100, 10500, "Boliv con 65", Color.MAGENTA)
  val gu10 = new Interseccion(16500, 12000, "Guay con 10", Color.CYAN)
  val terminal = new Interseccion(16500, 10500, "Term Sur", Color.GREEN)
  val gu30 = new Interseccion(13500, 12000, "Guay 30", Color.ORANGE)
  val gu80 = new Interseccion(19500, 12000, "Guay 80", Color.PINK)
  val _65_80 = new Interseccion(19500, 10500, "65 con 30", Color.YELLOW)
  val gu_37S = new Interseccion(21000, 12000, "Guay con 37S", Color.BLACK)


  //Instanciamos la lista de vias:
  val vias: ArrayBuffer[Via] = ArrayBuffer(
    Via(niquia, lauraAuto, 80, TipoVia("Carrera"), Sentido.dobleVia, "64C", "Auto Norte"),
    Via(niquia, lauraReg, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    Via(lauraAuto, lauraReg, 60, TipoVia("Calle"), Sentido.dobleVia, "94", "Pte Madre Laura"),
    Via(lauraAuto, ptoCero, 80, TipoVia("Carrera"), Sentido.dobleVia, "64C", "Auto Norte"),
    Via(lauraReg, ptoCero, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    Via(ptoCero, mino, 60, TipoVia("Calle"), Sentido.dobleVia, "58", "Oriental"),
    Via(mino, villa, 60, TipoVia("Calle"), Sentido.dobleVia, "58", "Oriental"),
    Via(ptoCero, ig65, 60, TipoVia("Calle"), Sentido.dobleVia, "55", "Iguaná"),
    Via(ig65, robledo, 60, TipoVia("Calle"), Sentido.dobleVia, "55", "Iguaná"),
    Via(ptoCero, colReg, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    Via(colReg, maca, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    Via(maca, expo, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    Via(expo, reg30, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    Via(reg30, monte, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    Via(monte, agua, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    Via(agua, viva, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    Via(viva, mayor, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    Via(mino, ferrCol, 60, TipoVia("Carrera"), Sentido.dobleVia, "55", "Ferrocarril"),
    Via(ferrCol, ferrJuan, 60, TipoVia("Carrera"), Sentido.dobleVia, "55", "Ferrocarril"),
    Via(ferrJuan, expo, 60, TipoVia("Carrera"), Sentido.dobleVia, "55", "Ferrocarril"),
    Via(villa, juanOr, 60, TipoVia("Carrera"), Sentido.dobleVia, "46", "Oriental"),
    Via(juanOr, sanDiego, 60, TipoVia("Carrera"), Sentido.dobleVia, "46", "Oriental"),
    Via(sanDiego, premium, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", "Av Pob"),
    Via(premium, pp, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", "Av Pob"),
    Via(pp, santafe, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", "Av Pob"),
    Via(santafe, pqEnv, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", "Av Pob"),
    Via(pqEnv, mayor, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", "Av Pob"),
    Via(ferrCol, colReg, 60, TipoVia("Calle"), Sentido.dobleVia, "450", "Colombia"),
    Via(colReg, col65, 60, TipoVia("Calle"), Sentido.dobleVia, "450", "Colombia"),
    Via(col65, col80, 60, TipoVia("Calle"), Sentido.dobleVia, "450", "Colombia"),
    Via(juanOr, ferrJuan, 60, TipoVia("Calle"), Sentido.dobleVia, "44", "Sn Juan"),
    Via(ferrJuan, maca, 60, TipoVia("Calle"), Sentido.dobleVia, "44", "Sn Juan"),
    Via(maca, juan65, 60, TipoVia("Calle"), Sentido.dobleVia, "44", "Sn Juan"),
    Via(juan65, juan80, 60, TipoVia("Calle"), Sentido.dobleVia, "44", "Sn Juan"),
    Via(sanDiego, expo, 60, TipoVia("Calle"), Sentido.dobleVia, "33", "33"),
    Via(expo, _33_65, 60, TipoVia("Calle"), Sentido.dobleVia, "33", "33"),
    Via(_33_65, bule, 60, TipoVia("Calle"), Sentido.dobleVia, "33", "33"),
    Via(bule, gema, 60, TipoVia("Calle"), Sentido.dobleVia, "33", "33"),
    Via(premium, reg30, 60, TipoVia("Calle"), Sentido.dobleVia, "30", "30"),
    Via(reg30, _30_65, 60, TipoVia("Calle"), Sentido.dobleVia, "30", "30"),
    Via(_30_65, _30_70, 60, TipoVia("Calle"), Sentido.dobleVia, "30", "30"),
    Via(_30_70, _30_80, 60, TipoVia("Calle"), Sentido.dobleVia, "30", "30"),
    Via(maca, bol65, 60, TipoVia("Diagonal"), Sentido.dobleVia, "74B", "Boliv"),
    Via(bol65, bule, 60, TipoVia("Diagonal"), Sentido.dobleVia, "74B", "Boliv"),
    Via(bule, _30_70, 60, TipoVia("Diagonal"), Sentido.dobleVia, "74B", "Boliv"),
    Via(juan80, bule, 60, TipoVia("Transversal"), Sentido.dobleVia, "39B", "Nutibara"),
    Via(pp, monte, 60, TipoVia("Calle"), Sentido.dobleVia, "10", "10"),
    Via(monte, gu10, 60, TipoVia("Calle"), Sentido.dobleVia, "10", "10"),
    Via(gu10, terminal, 60, TipoVia("Calle"), Sentido.dobleVia, "10", "10"),
    Via(expo, gu30, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", "Av Guay"),
    Via(gu30, gu10, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", "Av Guay"),
    Via(gu10, gu80, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", "Av Guay"),
    Via(gu80, gu_37S, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", "Av Guay"),
    Via(lauraAuto, ig65, 60, TipoVia("Carrera"), Sentido.dobleVia, "65", "65"),
    Via(ig65, col65, 60, TipoVia("Carrera"), Sentido.dobleVia, "65", "65"),
    Via(juan65, col65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", "65"),
    Via(bol65, juan65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", "65"),
    Via(_33_65, bol65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", "65"),
    Via(_30_65, _33_65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", "65"),
    Via(_30_65, terminal, 60, TipoVia("Carrera"), Sentido.dobleVia, "65", "65"),
    Via(terminal, _65_80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "65"),
    Via(robledo, col80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
    Via(col80, juan80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
    Via(juan80, gema, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
    Via(gema, _30_80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
    Via(_30_80, _65_80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
    Via(_65_80, gu80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
    Via(gu80, agua, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
    Via(agua, santafe, 60, TipoVia("Calle"), Sentido.dobleVia, "12S", "80"),
    Via(viva, pqEnv, 60, TipoVia("Calle"), Sentido.dobleVia, "37S", "37S"),
    Via(viva, gu_37S, 60, TipoVia("Calle"), Sentido.dobleVia, "63", "37S"))

  val grafoVia = grafico.GrafoVia.construir(vias)

  var vehiculos: Array[Vehiculo] = Array[Vehiculo]()

  //Se crean arreglos de strings que indican el tipo de vehiculo, el tamaño depende de la
  //proporción de cada vehiculo, cada lista indica la cantidad de cada vehiculo que habrá en la simulación
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

  //Se instancian los vehículos
  for (p <- proporciones) Vehiculo.crearVehiculo(velMin, velMax, p, intersecciones)
  //falta hacer que verifique que el origen no sea igual al destino

  GrafoVia.construir(vias)
  Grafico.graficarVias(vias.toArray)
  Thread.sleep(5000)

  var Running = 0
  Running = start()

  override def run(): Unit = {
    
    /* testing class ResultadosSimulacion */
    val resultados = new ResultadosSimulacion
    while (true) {
      Running match {
        case 1 =>
          mover(vehiculos)

          //Grafico.graficarMovil(vehiculos)
          t += dt
          println("thread is running")
          Thread.sleep(tRefresh)


        case 0 =>
          println("paused")


        case _ =>
          println("restarted")
        /* poner codigo para reiniciar posición de vehiculos*/
          Running = 1


      }
    }

    def n(outer:Interseccion):Simulacion.grafoVia.NodeT=Simulacion.grafoVia get outer

    resultados.buses_=(vehiculos.count(_.isInstanceOf[Bus]))
    resultados.camiones_=(vehiculos.count(_.isInstanceOf[Camion]))
    resultados.carros_=(vehiculos.count(_.isInstanceOf[Carro]))
    resultados.distMaxima_=(vehiculos.map(_.distanciaRecorrida.toInt).max)//
    resultados.distMinima_=((vehiculos.map(_.distanciaRecorrida.toInt).min))//
    resultados.distPromedio_=((vehiculos.map(_.distanciaRecorrida.toInt).sum/vehiculos.length))//
    resultados.intersecciones_=(intersecciones.length)
    resultados.longitudPromedio_=(vias.map(_.longitud.toInt).sum/vias.length)
    resultados.motos_=(vehiculos.count(_.isInstanceOf[Moto]))
    resultados.motoTaxis_=(vehiculos.count(_.isInstanceOf[MotoTaxi]))
    resultados.promedioDestino_=(intersecciones.map(_.fin.length).reduce(_+_)/intersecciones.length)//
    resultados.promedioOrigen_=(intersecciones.map(_.origenes.length).reduce(_+_)/intersecciones.length)//
    resultados.realidad_=(6)//
    resultados.simulacion_=(t)//
    resultados.sinDestino_=(intersecciones.length-(vehiculos.map(_.destino).length))//
    resultados.sinOrigen_=((intersecciones.length-(vehiculos.map(_.origen).length)))//
    resultados.total_=(vehiculos.length)
    resultados.viasUnSentido_=(vias.count(_.sentido.tipo == "unaVia"))
    resultados.viasDobleSentido_=(vias.count(_.sentido.tipo == "dobleVia"))
    resultados.velPromedio_=((vehiculos.map(_.velocidad.magnitud.toInt).reduce(_+_))/vehiculos.length)
    resultados.velMinima_=(vias.map(_.vMax).min)
    resultados.velMaxima_=(vias.map(_.vMax).max)
    resultados.vias_=(vias.length)
    resultados.velocidadMaxima_=(vehiculos.map(_.velocidad.magnitud.toInt).max)
    resultados.velocidadMinima_=(vehiculos.map(_.velocidad.magnitud.toInt).min)

    resultados.guardar()
  }
}
