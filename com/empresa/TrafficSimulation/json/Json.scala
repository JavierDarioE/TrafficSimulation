import scala.io.Source
import net.liftweb.json._

//Esta clase se encarga de leer y escribir los archivos .json con las configuraciones de la aplicación.

class Json(){
  val currentDirectory:String = new java.io.File(".").getCanonicalPath
  val jsonRaw = Source.fromFile(s"$currentDirectory/parámetros.json").getLines.mkString
  val jsonFile:JValue = parse(jsonRaw)

  implicit val formats = DefaultFormats

  case class ParametrosSimulacion(dt: Int,
                                  tRefresh: Int,
                                  vehiculos:Vehiculo,
                                  velocidad:Velocidad,
                                  proporciones:Proporciones)

  case class Vehiculo(minimo: Int, maximo: Int)

  case class Velocidad(minimo: Int, maximo: Int)

  case class Proporciones(carros: Double, motos: Double, buses: Double, camiones: Double, motoTaxis: Double)

  case class Data(parametrosSimulacion: ParametrosSimulacion)

  val dt = jsonFile.extract[Data].parametrosSimulacion.dt //ejemplo de la manera en la que se extraen los datos.
}