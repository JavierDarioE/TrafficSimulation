
    resultados.promedioDestino_=(10)  //valores temporales
    resultados.promedioOrigen_=(2)  //valores temporales
    resultados.realidad_=(6)  //valores temporales
    resultados.simulacion_=(10) //valores temporales
    resultados.sinDestino_=(20) //valores temporales
    resultados.sinOrigen_=(20)  //valores temporales
    resultados.total_=(vehiculos.length)
    resultados.viasUnSentido_=(vias.count(_.sentido.tipo == "unaVia"))
    resultados.viasDobleSentido_=(vias.count(_.sentido.tipo == "dobleVia"))
    resultados.velPromedio_=(vehiculos.map(_.velocidad.magnitud.toInt).sum/vehiculos.length)
    resultados.velMinima_=(vias.map(_.vMax).min)
    resultados.velMaxima_=(vias.map(_.vMax).max)
    resultados.vias_=(vias.length)
    resultados.velocidadMaxima_=(vehiculos.map(_.velocidad.magnitud.toInt).max)
    resultados.velocidadMinima_=(vehiculos.map(_.velocidad.magnitud.toInt).min)

    resultados.guardar()
  }
}

    while (true) {
      Running match {
        case 1 =>
          mover(vehiculos)
          Grafico.graficarMovil(vehiculos)
          t+=dt
          println("thread is running")
          Thread.sleep(tRefresh)


        case 0 =>
          println("paused")


        case _ =>
          println("restarted")
          /* poner codigo para reiniciar posición de vehiculos*/


      }
      /* Añadir un evento que retorne false cuando se cierre la ventana
      o alguna joda así.
       */
    resultados.promedioDestino_=(10)  //valores temporales
    resultados.promedioOrigen_=(2)  //valores temporales
    resultados.realidad_=(6)  //valores temporales
    resultados.simulacion_=(10) //valores temporales
    resultados.sinDestino_=(20) //valores temporales
    resultados.sinOrigen_=(20)  //valores temporales
    resultados.total_=(vehiculos.length)
    resultados.viasUnSentido_=(vias.count(_.sentido.tipo == "unaVia"))
    resultados.viasDobleSentido_=(vias.count(_.sentido.tipo == "dobleVia"))
    resultados.velPromedio_=(vehiculos.map(_.velocidad.magnitud.toInt).sum/vehiculos.length)
    resultados.velMinima_=(vias.map(_.vMax).min)
    resultados.velMaxima_=(vias.map(_.vMax).max)
    resultados.vias_=(vias.length)
    resultados.velocidadMaxima_=(vehiculos.map(_.velocidad.magnitud.toInt).max)
    resultados.velocidadMinima_=(vehiculos.map(_.velocidad.magnitud.toInt).min)

    resultados.guardar()
  }
}
