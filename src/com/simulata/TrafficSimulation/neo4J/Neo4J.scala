package com.simulata.TrafficSimulation.neo4J

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

  def guardarEstado(): Unit = {
    //se debe borrar primero lo que hay guardado
    val (driver, session) = getSession
    val script = "CREATE (:Categoria {nombre:'test'})"
    session.run(script)
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

}
