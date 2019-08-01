package json

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
                  distPromedio: Int
                 ): Unit ={

    println("Guardando datos... ")

    val jsonSave: JObject = ("resultadosSimulacion" -> (
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
              ("promedio" -> distPromedio)))))

    val FileRaw: String = prettyRender(jsonSave)
    val pw = new PrintWriter(new File(s"$currentDirectory/resultados.json"))
    pw.write(FileRaw)
    pw.close

    println("Datos guardados.")
  }




  /** case classes* */

  //para convertir un Json a case class:

  case class ParametrosSimulacion(dt: Int,
                                  tRefresh: Int,
                                  vehiculos:Vehiculo,
                                  velocidad:Velocidad,
                                  proporciones:Proporciones)

  case class Vehiculo(minimo: Int, maximo: Int)

  case class Velocidad(minimo: Int, maximo: Int)

  case class Proporciones(carros: Double, motos: Double, buses: Double, camiones: Double, motoTaxis: Double)

  case class Data(parametrosSimulacion: ParametrosSimulacion)

  //para serializar los datos en un archivo Json con case classes: (No prestar atención a esta parte)

  /*

  case class SerializeData(resultadosSimulacion: ResultadosSimulacion)

  case class ResultadosSimulacion(vehiculos: Vehiculos,
                                  mallaVial: MallaVial,
                                  tiempos: Tiempos,
                                  velocidades: Velocidades,
                                  istancias: Distancias)

  case class Vehiculos(total: Int, carros: Int, motos: Int, buses: Int, camiones: Int, motoTaxis: Int)

  case class MallaVial(vias: Int,
                       intersecciones: Int,
                       viasUnSentido: Int,
                       viasDobleSentido: Int,
                       velocidadMinima : Int,
                       velocidadMaxima: Int,
                       longitudPromedio: Int,
                       vehiculosEnInterseccion: VehiculosEnIterseccion)

  case class VehiculosEnIterseccion(promedioOrigen: Int, promedioDestino: Int, sinOrigen: Int, sinDestino: Int)

  case class Tiempos(simulacion: Int, realidad: Int)

  case class Velocidades(minima: Int, maxima: Int, promedio: Int)

  case class Distancias(minima: Int, maxima: Int, promedio: Int)

   */

}
