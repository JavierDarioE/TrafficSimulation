package com.simulata.TrafficSimulation.json

import java.io._
import scala.io.Source
import net.liftweb.json._
import net.liftweb.json.JsonDSL._


//Esta clase se encarga de leer y escribir los archivos .json con las configuraciones de la aplicación.

object Json{

  /* cargar parámetros **/

  val currentDirectory:String = new java.io.File(".").getCanonicalPath //obetnemos el directorio de la app
  val jsonRaw: String = Source.fromFile(s"$currentDirectory/parámetros.json").getLines.mkString //obtenemos el Json
  val jsonFile:JValue = parse(jsonRaw)

  implicit val formats:Formats = DefaultFormats

  val parametros:ParametrosSimulacion = jsonFile.extract[Data].parametrosSimulacion //convertimos el Json a case class

  //métodos de acceso:
  def motos: Double = parametros.proporciones.motos
  def carros: Double = parametros.proporciones.carros
  def camiones: Double = parametros.proporciones.camiones
  def buses: Double = parametros.proporciones.buses
  def motoTaxis: Double = parametros.proporciones.motoTaxis
  def dt: Int = parametros.dt
  def tRefresh: Int = parametros.tRefresh
  def maximo: Int = parametros.vehiculos.maximo
  def minimo: Int = parametros.vehiculos.minimo
  def velMax: Int = parametros.velocidad.maximo
  def velMin: Int = parametros.velocidad.minimo
  def tasaAcelMax: Double = parametros.tasaAceleracion.maximo
  def tasaAcelMin: Double = parametros.tasaAceleracion.minimo
  def minTiempoVerde: Int = parametros.semaforos.minTiempoVerde
  def maxTiempoVerde: Int = parametros.semaforos.maxTiempoVerde
  def tiempoAmarillo: Int = parametros.semaforos.tiempoAmarillo

  /* guardar resultados **/

  def saveResults(total: Int,
                  carros: Int,
                  motos: Int,
                  buses: Int,
                  camiones: Int,
                  motoTaxis: Int,
                  vias: Int,
                  intersecciones: Int,
                  viasUnSentido: Int,
                  viasDobleSentido: Int,
                  velocidadMinima: Int,
                  velocidadMaxima: Int,
                  longitudPromedio: Int,
                  promedioOrigen: Int,
                  promedioDestino: Int,
                  sinOrigen: Int,
                  sinDestino: Int,
                  simulacion: Int,
                  realidad: Int,
                  velMinima: Int,
                  velMaxima: Int,
                  velPromedio: Int,
                  distMinima: Int,
                  distMaxima: Int,
                  distPromedio: Int,
                  //TODO añadir al metodo jsonSave
                  cantidad: Int,
                  promedioPorcentajeExceso: Double
                 ): Unit ={

    println("Guardando datos... ")

    val jsonSave: JObject = "resultadosSimulacion" -> (
      ("vehiculos" -> (
        ("total" -> total) ~
          ("carros" -> carros) ~
          ("motos" -> motos) ~
          ("buses" -> buses) ~
          ("camiones" -> camiones) ~
          ("motoTaxis" -> motoTaxis)
        )) ~
        ("mallaVial" -> (
          ("vias" -> vias) ~
            ("intersecciones" -> intersecciones) ~
            ("viasUnSentido" -> viasUnSentido) ~
            ("viasDobleSentido" -> viasDobleSentido) ~
            ("velocidadMinima" -> velocidadMinima) ~
            ("velocidadMaxima" -> velocidadMaxima) ~
            ("longitudPromedio" -> longitudPromedio) ~
            ("vehiculosEnInterseccion" ->
              ("promedioOrigen" -> promedioOrigen) ~
                ("promedioDestino" -> promedioDestino) ~
                ("sinOrigen" -> sinOrigen) ~
                ("sinDestino"-> sinDestino)
              )
          )) ~
        ("tiempos" -> (
          ("simulacion" -> simulacion) ~
            ("realidad" -> realidad)
          )) ~
        ("velocidades" -> (
          ("minima" -> velMinima) ~
            ("maxima" -> velMaxima)
          )) ~
        ("distancias"-> (
            ("minima" -> distMinima) ~
              ("maxima" -> distMaxima) ~
              ("promedio" -> distPromedio)
          )) ~
        ("comparendos" -> (
          ("cantidad" -> cantidad) ~
            ("promedioPorcentajeExceso" -> promedioPorcentajeExceso)
          ))
      )

    val FileRaw: String = prettyRender(jsonSave)
    val pw = new PrintWriter(new File(s"$currentDirectory/resultados.json"))
    pw.write(FileRaw)
    pw.close()

    println("Datos guardados.")
  }

  /** case classes* */

  //para convertir un Json a case class:

  case class ParametrosSimulacion(dt: Int,
                                  tRefresh: Int,
                                  vehiculos:Vehiculo,
                                  velocidad:Velocidad,
                                  tasaAceleracion: TasaAceleracion,
                                  proporciones:Proporciones,
                                  semaforos: Semaforos)

  case class Vehiculo(minimo: Int, maximo: Int)

  case class Velocidad(minimo: Int, maximo: Int)
  
  case class TasaAceleracion(minimo: Double, maximo: Double)

  case class Proporciones(carros: Double, motos: Double, buses: Double, camiones: Double, motoTaxis: Double)
  
  case class Semaforos(minTiempoVerde: Int, maxTiempoVerde: Int, tiempoAmarillo: Int)

  case class Data(parametrosSimulacion: ParametrosSimulacion)
}
