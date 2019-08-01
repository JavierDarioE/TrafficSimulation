package simulacion

import resultadosSimulacion._
import json.Json
import vias._
import movimiento._

object Simulacion extends Runnable{
  //Parámetros Simulación
  val motos: Double = Json.motos
  val carros: Double = Json.carros
  val camiones: Double = Json.camiones
  val buses: Double = Json.buses
  val motoTaxis: Double = Json.motoTaxis
  val dt: Int = Json.dt
  val tRefresh: Int = Json.tRefresh
  val maximo: Int = Json.maximo
  val minimo: Int = Json.minimo
  val velMax: Int = Json.velMax
  val velMin: Int = Json.velMin
  
  val random=scala.util.Random
  
  var intersecciones=Array[Interseccion]()
  
  //Se instancian las Intersecciones(se añadirán a intersecciones al crearse):
  val niquia = new Interseccion(300, 12000, "Niquia")
  val lauraAuto = new Interseccion(2400, 11400, "M. Laura Auto")
  val lauraReg = new Interseccion(2400, 12600, "M. Laura Reg")
  val ptoCero = new Interseccion(5400, 12000, "Pto 0")
  val mino = new Interseccion(6300, 15000, "Minorista")
  val villa = new Interseccion(6300, 19500, "Villanueva")
  val ig65 = new Interseccion(5400, 10500, "65 Igu")
  val robledo = new Interseccion(5400, 1500, "Exito Rob")
  val colReg = new Interseccion(8250, 12000, "Col Reg")
  val colFerr = new Interseccion(8250, 15000, "Col Ferr")
  val col65 = new Interseccion(8250, 10500, "Col 65")
  val col80 = new Interseccion(8250, 1500, "Col 80")
  val juanOr = new Interseccion(10500, 19500, "Sn Juan Ori")
  val maca = new Interseccion(10500, 12000, "Macarena")
  val expo = new Interseccion(12000, 13500, "Exposiciones")
  val reg30 = new Interseccion(13500, 15000, "Reg 30")
  val monte = new Interseccion(16500, 15000, "Monterrey")
  val agua = new Interseccion(19500, 15000, "Aguacatala")
  val viva = new Interseccion(21000, 15000, "Viva Env")
  val mayor = new Interseccion(23400, 15000, "Mayorca")
  val ferrCol = new Interseccion(8250, 15000, "Ferr Col")
  val ferrJuan = new Interseccion(10500, 15000, "Alpujarra")
  val sanDiego = new Interseccion(12000, 19500, "San Diego")
  val premium = new Interseccion(13500, 19500, "Premium")
  val pp = new Interseccion(16500, 19500, "Parque Pob")
  val santafe = new Interseccion(19500, 18750, "Santa Fe")
  val pqEnv = new Interseccion(21000, 18000, "Envigado")
  val juan65 = new Interseccion(10500, 10500, "Juan 65")
  val juan80 = new Interseccion(10500, 1500, "Juan 80")
  val _33_65 = new Interseccion(12000, 10500, "33 con 65")
  val bule = new Interseccion(12000, 7500, "Bulerias")
  val gema = new Interseccion(12000, 1500, "St Gema")
  val _30_65 = new Interseccion(13500, 10500, "30 con 65")
  val _30_70 = new Interseccion(13500, 4500, "30 con 70")
  val _30_80 = new Interseccion(13500, 1500, "30 con 80")
  val bol65 = new Interseccion(11100, 10500, "Boliv con 65")
  val gu10 = new Interseccion(16500, 12000, "Guay con 10")
  val terminal = new Interseccion(16500, 10500, "Term Sur")
  val gu30 = new Interseccion(13500, 12000, "Guay 30")
  val gu80 = new Interseccion(19500, 12000, "Guay 80")
  val _65_80 = new Interseccion(19500, 10500, "65 con 30")
  val gu_37S = new Interseccion(21000, 12000, "Guay con 37S")

  
  //Instanciamos la lista de vias:
  val vias = Array(
    new Via(niquia, lauraAuto, 80, TipoVia("Carrera"), Sentido.dobleVia, "64C", "Auto Norte"),
    new Via(niquia, lauraReg, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    new Via(lauraAuto, lauraReg, 60, TipoVia("Calle"), Sentido.dobleVia, "94", "Pte Madre Laura"),
    new Via(lauraAuto, ptoCero, 80, TipoVia("Carrera"), Sentido.dobleVia, "64C", "Auto Norte"),
    new Via(lauraReg, ptoCero, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    new Via(ptoCero, mino, 60, TipoVia("Calle"), Sentido.dobleVia, "58", "Oriental"),
    new Via(mino, villa, 60, TipoVia("Calle"), Sentido.dobleVia, "58", "Oriental"),
    new Via(ptoCero, ig65, 60, TipoVia("Calle"), Sentido.dobleVia, "55", "Iguaná"),
    new Via(ig65, robledo, 60, TipoVia("Calle"), Sentido.dobleVia, "55", "Iguaná"),
    new Via(ptoCero, colReg, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    new Via(colReg, maca, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    new Via(maca, expo, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    new Via(expo, reg30, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    new Via(reg30, monte, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    new Via(monte, agua, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    new Via(agua, viva, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    new Via(viva, mayor, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
    new Via(mino, ferrCol, 60, TipoVia("Carrera"), Sentido.dobleVia, "55", "Ferrocarril"),
    new Via(ferrCol, ferrJuan, 60, TipoVia("Carrera"), Sentido.dobleVia, "55", "Ferrocarril"),
    new Via(ferrJuan, expo, 60, TipoVia("Carrera"), Sentido.dobleVia, "55", "Ferrocarril"),
    new Via(villa, juanOr, 60, TipoVia("Carrera"), Sentido.dobleVia, "46", "Oriental"),
    new Via(juanOr, sanDiego, 60, TipoVia("Carrera"), Sentido.dobleVia, "46", "Oriental"),
    new Via(sanDiego, premium, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", "Av Pob"),
    new Via(premium, pp, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", "Av Pob"),
    new Via(pp, santafe, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", "Av Pob"),
    new Via(santafe, pqEnv, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", "Av Pob"),
    new Via(pqEnv, mayor, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", "Av Pob"),
    new Via(ferrCol, colReg, 60, TipoVia("Calle"), Sentido.dobleVia, "450", "Colombia"),
    new Via(colReg, col65, 60, TipoVia("Calle"), Sentido.dobleVia, "450", "Colombia"),
    new Via(col65, col80, 60, TipoVia("Calle"), Sentido.dobleVia, "450", "Colombia"),
    new Via(juanOr, ferrJuan, 60, TipoVia("Calle"), Sentido.dobleVia, "44", "Sn Juan"),
    new Via(ferrJuan, maca, 60, TipoVia("Calle"), Sentido.dobleVia, "44", "Sn Juan"),
    new Via(maca, juan65, 60, TipoVia("Calle"), Sentido.dobleVia, "44", "Sn Juan"),
    new Via(juan65, juan80, 60, TipoVia("Calle"), Sentido.dobleVia, "44", "Sn Juan"),
    new Via(sanDiego, expo, 60, TipoVia("Calle"), Sentido.dobleVia, "33", "33"),
    new Via(expo, _33_65, 60, TipoVia("Calle"), Sentido.dobleVia, "33", "33"),
    new Via(_33_65, bule, 60, TipoVia("Calle"), Sentido.dobleVia, "33", "33"),
    new Via(bule, gema, 60, TipoVia("Calle"), Sentido.dobleVia, "33", "33"),
    new Via(premium, reg30, 60, TipoVia("Calle"), Sentido.dobleVia, "30", "30"),
    new Via(reg30, _30_65, 60, TipoVia("Calle"), Sentido.dobleVia, "30", "30"),
    new Via(_30_65, _30_70, 60, TipoVia("Calle"), Sentido.dobleVia, "30", "30"),
    new Via(_30_70, _30_80, 60, TipoVia("Calle"), Sentido.dobleVia, "30", "30"),
    new Via(maca, bol65, 60, TipoVia("Diagonal"), Sentido.dobleVia, "74B", "Boliv"),
    new Via(bol65, bule, 60, TipoVia("Diagonal"), Sentido.dobleVia, "74B", "Boliv"),
    new Via(bule, _30_70, 60, TipoVia("Diagonal"), Sentido.dobleVia, "74B", "Boliv"),
    new Via(juan80, bule, 60, TipoVia("Transversal"), Sentido.dobleVia, "39B", "Nutibara"),
    new Via(pp, monte, 60, TipoVia("Calle"), Sentido.dobleVia, "10", "10"),
    new Via(monte, gu10, 60, TipoVia("Calle"), Sentido.dobleVia, "10", "10"),
    new Via(gu10, terminal, 60, TipoVia("Calle"), Sentido.dobleVia, "10", "10"),
    new Via(expo, gu30, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", "Av Guay"),
    new Via(gu30, gu10, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", "Av Guay"),
    new Via(gu10, gu80, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", "Av Guay"),
    new Via(gu80, gu_37S, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", "Av Guay"),
    new Via(lauraAuto, ig65, 60, TipoVia("Carrera"), Sentido.dobleVia, "65", "65"),
    new Via(ig65, col65, 60, TipoVia("Carrera"), Sentido.dobleVia, "65", "65"),
    new Via(juan65, col65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", "65"),
    new Via(bol65, juan65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", "65"),
    new Via(_33_65, bol65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", "65"),
    new Via(_30_65, _33_65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", "65"),
    new Via(_30_65, terminal, 60, TipoVia("Carrera"), Sentido.dobleVia, "65", "65"),
    new Via(terminal, _65_80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "65"),
    new Via(robledo, col80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
    new Via(col80, juan80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
    new Via(juan80, gema, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
    new Via(gema, _30_80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
    new Via(_30_80, _65_80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
    new Via(_65_80, gu80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
    new Via(gu80, agua, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
    new Via(agua, santafe, 60, TipoVia("Calle"), Sentido.dobleVia, "12S", "80"),
    new Via(viva, pqEnv, 60, TipoVia("Calle"), Sentido.dobleVia, "37S", "37S"),
    new Via(viva, gu_37S, 60, TipoVia("Calle"), Sentido.dobleVia, "63", "37S"))
    
  var vehiculos=Array[Vehiculo]()
  
  //Se crean arreglos de strings que indican el tipo de vehiculo, el tamaño depende de la
  //proporción de cada vehiculo
  val proporcionCarros= Array.fill((carros*1000).toInt)("carro")
  val proporcionMotos= Array.fill((motos*1000).toInt)("moto")
  val proporcionMotoTaxis= Array.fill((motoTaxis*1000).toInt)("mototaxi")
  val proporcionCamion= Array.fill((camiones*1000).toInt)("camion")
  val proporcionBus= Array.fill((buses*1000).toInt)("bus")
  
  //Se concatenan los arrays creando un array de
  //1000 string que indican el tipo de automovil de acuerdo a las 
  //proporciones enviadas y así elegir el tipo de la instancia a crear,
  //las proporciones deben sumar 1 y no deben de ser menores a 0.001
  val proporciones= proporcionCarros++proporcionMotos++proporcionMotoTaxis++proporcionCamion++proporcionBus  
      
  //Se instancian los vehículos, se realiza un número al azar de veces entre el mínimo y
  //el máximo número de vehículos
  for (i<- 1 to minimo+random.nextInt(maximo-minimo)) Vehiculo.crearVehiculo(velMin, velMax, proporciones, intersecciones)
//falta hacer que verifique que el origen no sea igual al destino
  
  override def run(): Unit = {
    /*
    /* testing class ResultadosSimulacion */
    val resultados = new ResultadosSimulacion

    resultados.buses_=(1)
    resultados.camiones_=(2)
    resultados.carros_=(3)
    resultados.distMaxima_=(4)
    resultados.distMinima_=(6)
    resultados.distPromedio_=(7)
    resultados.intersecciones_=(8)
    resultados.longitudPromedio_=(7)
    resultados.motos_=(4)
    resultados.motoTaxis_=(7)
    resultados.promedioDestino_=(10)
    resultados.promedioOrigen_=(2)
    resultados.realidad_=(6)
    resultados.simulacion_=(10)
    resultados.sinDestino_=(20)
    resultados.sinOrigen_=(20)
    resultados.total_=(20)
    resultados.viasUnSentido_=(10)
    resultados.viasDobleSentido_=(10)
    resultados.velPromedio_=(90)
    resultados.velMinima_=(90)
    resultados.velMaxima_=(20)
    resultados.vias_=(4)
    resultados.velocidadMaxima_=(50)
    resultados.velocidadMinima_=(20)

    resultados.guardar()
    //end of test
		*/
    
    /*test json loader*/
    var t = Json.tRefresh
    val dt = Json.dt
    println(s"camiones: ${Json.camiones}")
    println(s"carros: ${Json.carros}")
    println(s"t: $t")
    println(s"dt: $dt")
    //end of test


    while (true) {
      //t += td
      //Grafico.graficar
      println("thread is running")
      Thread.sleep(1000)
    }
    val resultados = new ResultadosSimulacion
//revisar unidades
    resultados.buses_=(vehiculos.filter(_.isInstanceOf[Bus]).length)
    resultados.camiones_=(vehiculos.filter(_.isInstanceOf[Camion]).length)
    resultados.carros_=(vehiculos.filter(_.isInstanceOf[Carro]).length)
    resultados.distMaxima_=(7)
    resultados.distMinima_=(6)
    resultados.distPromedio_=(7)
    resultados.intersecciones_=(intersecciones.length)
    resultados.longitudPromedio_=((vias.map(_.longitud.toInt).reduce(_+_))/vias.length)
    resultados.motos_=(vehiculos.filter(_.isInstanceOf[Moto]).length)
    resultados.motoTaxis_=(vehiculos.filter(_.isInstanceOf[MotoTaxi]).length)
    resultados.promedioDestino_=(10)
    resultados.promedioOrigen_=(2)
    resultados.realidad_=(6)
    resultados.simulacion_=(10)
    resultados.sinDestino_=(20)
    resultados.sinOrigen_=(20)
    resultados.total_=(vehiculos.length)
    resultados.viasUnSentido_=(vias.filter(_.sentido.tipo=="unaVia").length)
    resultados.viasDobleSentido_=(vias.filter(_.sentido.tipo=="dobleVia").length)
    resultados.velPromedio_=((vehiculos.map(_.velocidad.magnitud.toInt).reduce(_+_))/vehiculos.length)
    resultados.velMinima_=(vias.map(_.vMax).min)
    resultados.velMaxima_=(vias.map(_.vMax).max)
    resultados.vias_=(vias.length)
    resultados.velocidadMaxima_=(vehiculos.map(_.velocidad.magnitud.toInt).max)
    resultados.velocidadMinima_=(vehiculos.map(_.velocidad.magnitud.toInt).min)

    resultados.guardar()
  }
}
  