package com.simulata.TrafficSimulation.neo4J

import com.simulata.TrafficSimulation.movimiento.Vehiculo
import com.simulata.TrafficSimulation.cartesiano.{Velocidad, Viaje}
import com.simulata.TrafficSimulation.vias._
import org.neo4j.driver.v1._
import scala.collection.mutable.ArrayBuffer
import java.awt.Color

object Neo4J {
  val url = "bolt://localhost/7687"
  val user = "neo4j" //Usuario por defecto
  val pass = "123" //contraseña de la base de datos

  def getSession: (Driver, Session) = {
    val driver = GraphDatabase.driver(url, AuthTokens.basic(user, pass))
    val session = driver.session
    (driver, session)
  }
 //las intersecciones se agregan automáticamente al arreglo de intersecciones
  def leerMallaVial: ArrayBuffer[Via] = {
    val (driver, session) = getSession
    val script = s"MATCH (origen:Interseccion)-[:ORIGEN_DE]->(v:Via)<-[:FIN_DE]-(fin:Interseccion) RETURN origen, v ,fin "
    val result = session.run(script)
    val vias = ArrayBuffer.empty[Via]
    while (result.hasNext()) {
      val values = result.next().values()
      val o = values.get(0)
      val v = values.get(1)
      val f = values.get(2)
      vias += Via(new Interseccion(o.get("xx").asInt(),o.get("yy").asInt(), Some(o.get("nombre").asString()),getColor(o.get("color").asString())), 
                  new Interseccion(f.get("xx").asInt,f.get("yy").asInt(), Some(f.get("nombre").asString()),getColor(f.get("color").asString())), 
                  v.get("v").asInt(), TipoVia(v.get("tipoVia").asString()), getSentidoVia(v.get("sentido").asString()), v.get("numero").asString(), Some(v.get("nombre").asString()), None)
    }
    session.close()
    driver.close()
    vias
   } 
  
  def leerCamaras: ArrayBuffer[CamaraFotoDeteccion] = {
    val (driver, session) = getSession
    val script = s"MATCH (v:Via)<-[:ESTA_EN]-(c:Camara) MATCH (origen:Interseccion)-[:ORIGEN_DE]->(v)<-[:FIN_DE]-(fin:Interseccion) RETURN origen, v, fin, c "
    val result = session.run(script)
    val camaras = ArrayBuffer.empty[CamaraFotoDeteccion]
    while (result.hasNext()) {
      val values = result.next().values()
      val o = values.get(0)
      val v = values.get(1)
      val f = values.get(2)
      val c= values.get(4)
      var q=Via(new Interseccion(o.get("xx").asInt(),o.get("yy").asInt(), Some(o.get("nombre").asString()),getColor(o.get("color").asString())), 
                  new Interseccion(f.get("xx").asInt,f.get("yy").asInt(), Some(f.get("nombre").asString()),getColor(f.get("color").asString())), 
                  v.get("v").asInt(), TipoVia(v.get("tipoVia").asString()), getSentidoVia(v.get("sentido").asString()), v.get("numero").asString(), Some(v.get("nombre").asString()), None)
      val camara= new CamaraFotoDeteccion(q)
      q.camara_=(camara)
      camaras+=camara
    }
    session.close()
    driver.close()
    camaras
   } 
  def borrarEstado():Unit = {
    val (driver, session) = getSession
    val script = s"CREATE (:Categoria {nombre: 'test'})"
    session.run(script)
    session.close()
    driver.close()
  }

  def guardarEstado(viajes: Array[Viaje]): Unit = {
    //se debe borrar primero lo que hay guardado
    val (driver, session) = getSession
    var id:Int = 0
    viajes.foreach(v => {
      id += 1
      val veh: Vehiculo = v.vehiculo
      val posicionx = veh.posicion.x
      val posiciony = veh.posicion.y
      val placa:String = veh.placa
      val tipo:String = veh.tipo
      val tasaAceleracion = veh.aceleracion
      val velocidad:Velocidad = veh.velocidadActual
      val origen = v.via.origenn.nombre.getOrElse("Nothing")
      val fin = v.via.finn.nombre.getOrElse("Nothing")
      val script: String =
        s"""MATCH (intOr:Interseccion {nombre:'${v.interseccionOrigen.nombre.getOrElse("Nothing")}'}),
           |(intDest:Interseccion {nombre:'${v.interseccionDestino.nombre.getOrElse("Nothing")}'}),
           |(:Interseccion {nombre:'$origen'})-[:ORIGEN_DE]->(via:Via)<-[:FIN_DE]-(:Interseccion {nombre:'$fin'})
           |CREATE (viaje$id:Viaje {direccion:'${v.direccion.angulo}', distancia:'${v.distanciaParaRecorrer}'}),
           |(p$id:Posicion {x:'$posicionx', y:'$posiciony'}),
           |(vel$id:Velocidad {magnitud:'${velocidad.magnitud}', angulo:'${velocidad.angulo.angulo}'}),
           |(vehiculo$id:Vehiculo{placa:'$placa', tasaAceleracion:'$tasaAceleracion', tipo:'$tipo'}),
           |(intDest)-[:INTERSECCION_DESTINO]->(viaje$id),
           |(intOr)-[:INTERSECCION_ORIGEN]->(viaje$id),
           |(viaje$id)-[:VIAJE_DE]->(vehiculo$id),
           |(p$id)-[:POSICION_DE]->(vehiculo$id),
           |(via)-[:VIA_DE]->(viaje$id)
           |""".stripMargin
      session.run(script)
    })
    session.close()
    driver.close()
  }

  def cargarEstado(): Unit = {
    val (driver, session) = getSession
    val script =s"CREATE (: Categoria {nombre: 'test'})"
    session.run(script)
    session.close()
    driver.close()
  }

  def borrarDatosGuardados(interseccion : Interseccion): String = {

    s"""MATCH ()"""
  }
 //Usados para desempaquetar los atributos color y sentidoVia de neo4j
   def getColor(s:String):Color = s match{
      case "Color.BLUE"=>Color.BLUE
      case "Color.RED"=>Color.RED
      case "Color.MAGENTA"=>Color.MAGENTA
      case "Color.CYAN"=>Color.CYAN
      case "Color.DARK_GRAY"=>Color.DARK_GRAY
      case "Color.GRAY"=>Color.GRAY
      case "Color.GREEN"=>Color.GREEN
      case "Color.ORANGE"=>Color.ORANGE
      case "Color.YELLOW"=>Color.YELLOW
      case "Color.BLACK"=>Color.BLACK
      case "Color.PINK"=>Color.PINK
      case _=>Color.BLACK
    }
    def getSentidoVia(s:String):Sentido = s match{
      case "dobleVia" => Sentido.dobleVia
      case "unaVia" => Sentido.unaVia
    }
}
