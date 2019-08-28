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
    borrarDatosGuardados()
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
           |(via)-[:VIA_DE]->(viaje$id)""".stripMargin
      session.run(script)
    })
    session.close()
    driver.close()
  }

  def borrarDatosGuardados(): Unit = {
    val (driver, session) = getSession
    val scriptRelaciones =
      s"""MATCH (:Interseccion)-[i1:INTERSECCION_DESTINO]->(:Viaje),
         |(:Interseccion)-[i2:INTERSECCION_ORIGEN]->(:Viaje),
         |(:Posicion)-[p:POSICION_DE]->(:Vehiculo),
         |(:Via)-[v:VIA_DE]->(:Viaje)
         |DELETE i1, i2, p, v""".stripMargin
    val scriptNodos =
      s"""MATCH (v1:Viaje),
         |(p:Posicion),
         |(v2: Velocidad),
         |(v3: Vehiculo)
         |DELETE  v1, p, v2, v3""".stripMargin
    session.run(scriptRelaciones)
    session.run(scriptNodos)
    session.close()
    driver.close()
  }

  def cargarEstado(interseccion : Interseccion): String = {

    s"""MATCH ()"""
  }

}
