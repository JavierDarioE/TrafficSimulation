package com.simulata.TrafficSimulation.neo4J

import com.simulata.TrafficSimulation.movimiento.Vehiculo
import com.simulata.TrafficSimulation.cartesiano.{Velocidad, Viaje}
import com.simulata.TrafficSimulation.vias.Interseccion
import org.neo4j.driver.v1._

object Neo4J {
  val url = "bolt://localhost/7687"
  val user = "neo4j" //Usuario por defecto
  val pass = "123" //contraseña de la base de datos

  def getSession: (Driver, Session) = {
    val driver = GraphDatabase.driver(url, AuthTokens.basic(user, pass))
    val session = driver.session
    (driver, session)
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
    println("pasó por acá")
    viajes.foreach(v => {
      id += 1
      val veh: Vehiculo = v.vehiculo
      val posicionx = veh.posicion.x
      val posiciony = veh.posicion.y
      val placa:String = veh.placa
      val tipo:String = veh.tipo
      val tasaAceleracion = veh.tasaAceleracion
      val velocidad:Velocidad = veh.velocidad
      val origen = v.via.origenn
      val fin = v.via.finn
      println("entró")
      val script: String =
        s"""MATCH (intOr:Interseccion {nombre:'${v.interseccionOrigen.nombre.getOrElse("Nothing")}'}),
           |(intDest:Interseccion {nombre:'${v.interseccionDestino.nombre.getOrElse("Nothing")}'}),
           |(:Interseccion {nombre:'${origen}'})-[:ORIGEN_DE]->(via:Via)<-[:FIN_DE]-(:Interseccion {nombre:'${fin}'})
           |CREATE (viaje$id:Viaje {direccion:'${v.direccion}', distancia:'${v.distanciaParaRecorrer}'}),
           |(p$id:Posicion {x:'$posicionx', y:'$posiciony'}),
           |(vel$id:Velocidad {magnitud:'${velocidad.magnitud}', angulo:'${velocidad.angulo}'})
           |(vehiculo$id:Vehiculo{placa:'$placa', tasaAceleracion:'$tasaAceleracion', tipo:'$tipo'}),
           |(intOr)-[:INTERSECCION_ORIGEN]->(viaje$id),
           |(vaje$id)-[VIAJE_DE]->(vehiculo$id),
           |(p$id)-[POSICION_DE]->(vehiculo$id),
           |(via)-[VIA_DE]->(viaje$id)
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

  def scriptInterseccion(interseccion : Interseccion): String = {
    s"""MATCH (n:Interseccion{nombre:'${interseccion.nombre.getOrElse("Nothing")}'})
       |CREATE (n)<-[:INTERSECCION_ORIGEN]-()
       |""".stripMargin
  }

}
