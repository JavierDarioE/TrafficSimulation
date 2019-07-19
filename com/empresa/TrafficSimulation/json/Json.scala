package json

import scala.io.Source
import net.liftweb.json._


//Esta clase se encarga de leer y escribir los archivos .json con las configuraciones de la aplicación.

object Json{
  val currentDirectory:String = new java.io.File(".").getCanonicalPath
  val jsonRaw = Source.fromFile(s"$currentDirectory/parámetros.json").getLines.mkString
  val jsonFile:JValue = parse(jsonRaw)

  implicit val formats:Formats = DefaultFormats

  val parametros:ParametrosSimulacion = jsonFile.extract[Data].parametrosSimulacion

  case class ParametrosSimulacion(dt: Int,
                                  tRefresh: Int,
                                  vehiculos:Vehiculo,
                                  velocidad:Velocidad,
                                  proporciones:Proporciones)

  case class Vehiculo(minimo: Int, maximo: Int)

  case class Velocidad(minimo: Int, maximo: Int)

  case class Proporciones(carros: Double, motos: Double, buses: Double, camiones: Double, motoTaxis: Double)

  case class Data(parametrosSimulacion: ParametrosSimulacion)

  def getMotos:Double = parametros.proporciones.motos
  def getCarros:Double = parametros.proporciones.carros
  def getCamiones:Double = parametros.proporciones.camiones
  def getMotoTaxis:Double = parametros.proporciones.motoTaxis
  def getDt:Int = parametros.dt
  def getTRefresh:Int = parametros.tRefresh
  def getMaximo:Int = parametros.vehiculos.maximo
  def getMinimo:Int = parametros.vehiculos.minimo
  def getVelMax:Int = parametros.velocidad.maximo
  def getVelMin:Int = parametros.velocidad.minimo
}