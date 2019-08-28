package com.simulata.TrafficSimulation.neo4J

import com.simulata.TrafficSimulation.movimiento.Vehiculo
import com.simulata.TrafficSimulation.cartesiano.{Angulo, Punto, Velocidad, Viaje}
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
      val aceleracion = veh.aceleracion
      val velocidadActual:Velocidad = veh.velocidadActual
      val velocidadCrucero:Velocidad = veh.velocidadCrucero
      val origen = v.via.origenn.nombre.getOrElse("Nothing")
      val fin = v.via.finn.nombre.getOrElse("Nothing")
      val script: String =
        s"""MATCH (intOr:Interseccion {nombre:'${v.interseccionOrigen.nombre.getOrElse("Nothing")}'}),
           |(intDest:Interseccion {nombre:'${v.interseccionDestino.nombre.getOrElse("Nothing")}'}),
           |(:Interseccion {nombre:'$origen'})-[:ORIGEN_DE]->(via:Via)<-[:FIN_DE]-(:Interseccion {nombre:'$fin'})
           |CREATE (viaje$id:Viaje {direccion:${v.direccion.angulo}, distancia:${v.distanciaParaRecorrer}, llego:'${v.yaLlego}'}),
           |(p$id:Posicion {x:$posicionx, y:$posiciony}),
           |(velA$id:Velocidad {magnitud:${velocidadActual.magnitud}, angulo:${velocidadActual.angulo.angulo}}),
           |(velC$id:Velocidad {magnitud: ${velocidadCrucero.magnitud}, angulo:${velocidadCrucero.angulo.angulo}}),
           |(vehiculo$id:Vehiculo{placa:'$placa', aceleracion:$aceleracion, tipo:'$tipo'}),
           |(intDest)-[:INTERSECCION_DESTINO]->(viaje$id),
           |(intOr)-[:INTERSECCION_ORIGEN]->(viaje$id),
           |(viaje$id)-[:VIAJE_DE]->(vehiculo$id),
           |(p$id)-[:POSICION_DE]->(vehiculo$id),
           |(velA$id)-[:VELOCIDAD_A_DE]->(vehiculo$id),
           |(velC$id)-[:VELOCIDAD_C_DE]->(vehiculo$id),
           |(via)-[:VIA_DE]->(viaje$id)""".stripMargin
      session.run(script)
    })
    session.close()
    driver.close()
  }

  def borrarDatosGuardados(): Unit = {
    val (driver, session) = getSession
    //uno por uno, para que la ausencia de uno de ellos no evite que los demas sean borrados
    session.run("MATCH (:Interseccion)-[i1:INTERSECCION_DESTINO]->(:Viaje) DELETE i1")
    session.run("MATCH (:Interseccion)-[i2:INTERSECCION_ORIGEN]->(:Viaje) DELETE i2")
    session.run("MATCH (:Posicion)-[p:POSICION_DE]->(:Vehiculo) DELETE p")
    session.run("MATCH (:Via)-[v:VIA_DE]->(:Viaje) DELETE v")
    session.run("MATCH (:Viaje)-[viaje:VIAJE_DE]->(:Vehiculo) DELETE viaje")
    session.run("MATCH (:Velocidad)-[vela:VELOCIDAD_A_DE]->(:Vehiculo) DELETE vela")
    session.run("MATCH (:Velocidad)-[velc:VELOCIDAD_C_DE]->(:Vehiculo) DELETE velc")
    session.run("MATCH (v1:Viaje) DELETE v1")
    session.run("MATCH (p:Posicion) DELETE p")
    session.run("MATCH (v2:Velocidad) DELETE v2")
    session.run("MATCH (v3:Vehiculo) DELETE v3")
    session.close()
    driver.close()
  }

  def cargarEstado(): Unit = {
    val (driver, session) = getSession
    val script : String =
      """MATCH (viaje:Viaje)-[:VIAJE_DE]->(vehiculo:Vehiculo),
        |(via:Via)-[:VIA_DE]->(viaje),
        |(i1:Interseccion)-[:ORIGEN_DE]->(via)<-[:FIN_DE]-(i2:Interseccion),
        |(intDest)-[:INTERSECCION_DESTINO]->(viaje),
        |(intOr)-[:INTERSECCION_ORIGEN]->(viaje),
        |(velA)-[:VELOCIDAD_A_DE]->(vehiculo),
        |(velC)-[:VELOCIDAD_C_DE]->(vehiculo),
        |(posicion)-[:POSICION_DE]->(vehiculo)
        |RETURN viaje, vehiculo, posicion, via, intDest, intOr, i1, i2, velA, velC""".stripMargin
    val resultado = session.run(script)
    if (resultado.hasNext) {
      while (resultado.hasNext) {
        val values = resultado.next().values()
        val viaje = values.get(0)
        val vehiculo = values.get(1)
        val posicion = values.get(2)
        val via = values.get(3)
        val interDest = values.get(4)
        val interOr = values.get(5)
        val viaInicio = values.get(6)
        val viaFin = values.get(7)
        val velA = values.get(8)
        val velC = values.get(9)
        instanciarVehiculos(viaje, vehiculo, posicion, via, interDest, interOr, viaInicio, viaFin, velA, velC)
      }
    }
    session.close()
    driver.close()
  }

  def instanciarVehiculos(viaje: Value,
                          vehiculo: Value,
                          posicion: Value,
                          via:Value,
                          interDest:Value,
                          interOr:Value,
                          viaInicio:Value,
                          viaFin:Value,
                          velA:Value,
                          velC:Value): Unit ={
    val velCrucero = new Velocidad(velC.get("magnitud").asDouble(), Angulo(velC.get("angulo").asDouble()))
    //val velActual = new Velocidad(velA.get("magnitud").asDouble(), Angulo(velA.get("angulo").asDouble()))
    val pos = new Punto(posicion.get("x").asDouble(), posicion.get("y").asDouble())
    val intOr = interOr.get("nombre").asString()
    val intDes = interDest.get("nombre").asString()
    val inicioViaActual  = viaInicio.get("nombre").asString()
    val finViaActual  = viaFin.get("nombre").asString()
    val direccion = viaje.get("direccion").asDouble()
    val distancia = viaje.get("distancia").asDouble()
    val llegoBool = viaje.get("llego").asString().toBoolean

    val veh: Vehiculo = Vehiculo.crearVehiculo(velCrucero,
      vehiculo.get("aceleracion").asDouble(),
      vehiculo.get("placa").asString(),
      vehiculo.get("tipo").asString())

    veh.velocidadActual.angulo_=(new Angulo(velA.get("angulo").asDouble()))
    veh.velocidadActual.magnitud_=(velA.get("magnitud").asDouble())

    new Viaje(veh, pos, intOr, intDes, inicioViaActual, finViaActual, direccion, distancia, llegoBool)
  }

}
