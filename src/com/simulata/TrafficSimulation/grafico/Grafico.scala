package com.simulata.TrafficSimulation.grafico

import com.simulata.TrafficSimulation.movimiento.{Bus, Camion, Carro, Moto, MotoTaxi}
import com.simulata.TrafficSimulation.vias._
import com.simulata.TrafficSimulation.simulacion.Simulacion
import com.simulata.TrafficSimulation.movimiento.Vehiculo
import java.awt.BasicStroke
import java.awt.Color
import java.awt.geom.Rectangle2D.Double
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartFrame
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.XYPlot
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.chart.annotations.XYTextAnnotation
import org.jfree.ui.RefineryUtilities
import java.awt.event.{KeyEvent, KeyListener, WindowEvent, WindowListener/*, WindowStateListener*/}
import java.awt.geom.Ellipse2D
import java.awt.Rectangle
import java.awt.Polygon

object Grafico {
  var cantVias: Int = _
  val dataset = new XYSeriesCollection
  val dataset1 = new XYSeriesCollection
  var frame: ChartFrame = _
  
  //chart con titulo, label para eje x, label para eje y, y dataset
  val chart: JFreeChart = ChartFactory.createScatterPlot("titulo", "", "", dataset)
  
  val plot: XYPlot = chart.getXYPlot
  
  def graficarVias(arrayVias: Array[Via]){
   
    var autoincremento = 0
    
    // Crear un XYSeries por cada via y agregar al dataset
    arrayVias.foreach(via => {
      val serie = new XYSeries(autoincremento)
      serie.add(via.origenn.xx, via.origenn.yy)
      serie.add(via.finn.xx, via.finn.yy)
      dataset.addSeries(serie)
      autoincremento += 1
      })
    
    chart.setBackgroundPaint(Color.WHITE)
    
    chart.removeLegend() //quitar las leyendas
    chart.clearSubtitles()
    chart.getTitle.setVisible(false) //quitar el titulo del chart
    
    plot.getDomainAxis.setVisible(false) //quitar indicadores del eje X
    plot.getRangeAxis.setVisible(false) //quitar indicadores del eje Y
    
    plot.setBackgroundPaint(Color.WHITE) //Cambiar color de fondo
    plot.setBackgroundAlpha(1) //Quitar grid
    plot.setRenderer(new XYLineAndShapeRenderer()) // coso para poder graficar las lineas
    
    // Crear la etiqueta de cada interseccion
    arrayVias.foreach(via => {
      val etiqueta1 = new XYTextAnnotation(
          via.origenn.nombre, via.origenn.xx, via.origenn.yy)
      
      etiqueta1.setPaint(via.origenn.color)
      
      val etiqueta2 = new XYTextAnnotation(
          via.finn.nombre, via.finn.xx, via.finn.yy)
      
      etiqueta2.setPaint(via.finn.color)
      
      plot.addAnnotation(etiqueta1); plot.addAnnotation(etiqueta2) 
      })
    
    // Esto se puede hacer sin el for con cosas chidas de Scala pero meh:
    // Personalizacion del grafico
    for(i <- 0 until dataset.getSeriesCount){
    plot.getRenderer.setSeriesStroke(i, new BasicStroke(4.0f))
    plot.getRenderer.setSeriesPaint(i, Color.LIGHT_GRAY)
    plot.getRenderer.setSeriesShape(i, new Double)
    
    }
    
    //nuevo frame (ventana) con titulo y con chart.
    frame = new ChartFrame("TrafficSimulation", chart)
    frame.pack()
    frame.setSize(1000, 500)
    RefineryUtilities.positionFrameRandomly(frame)
    frame.setVisible(true)
    frame.requestFocus()
    frame.addWindowListener(new WindowListener {
      override def windowOpened(e: WindowEvent){}

      override def windowClosing(e: WindowEvent){}

      override def windowClosed(e: WindowEvent): Unit = {
        Simulacion.active = false
      }

      override def windowIconified(e: WindowEvent){}

      override def windowDeiconified(e: WindowEvent){}

      override def windowActivated(e: WindowEvent){}

      override def windowDeactivated(e: WindowEvent){}
    })
    frame.addKeyListener(new KeyListener{
      
      override def keyPressed(evento: KeyEvent): Unit = {
        if(evento.getKeyCode == KeyEvent.VK_F5){
          
          if(Simulacion.Running == 0) Simulacion.Running = 1
          else if(Simulacion.Running ==1) Simulacion.Running = 2
          
        }
        else if(evento.getKeyCode == KeyEvent.VK_F6){
          Simulacion.Running=0
        }
      }
      
      override def keyReleased(evento: KeyEvent){}
      
      override def keyTyped(evento: KeyEvent){}
    })
    cantVias = dataset.getSeriesCount
  }

  def limpiarVehiculos(arrayVehiculos: Array[Vehiculo]): Unit ={
    arrayVehiculos.foreach(vehiculo => {
      while (dataset.getSeriesCount > cantVias){
        dataset.removeSeries(dataset.getSeriesIndex(vehiculo.placa))
      }
    })
  }

  def moverVehiculos(arrayVehiculos: Array[Vehiculo]): Unit = {
    arrayVehiculos.foreach(vehiculo => {
      val puntos: XYSeries = dataset.getSeries(vehiculo.placa)
      puntos.clear()
      puntos.add(vehiculo.posicion.x, vehiculo.posicion.y)
    })
  }

  def graficarVehiculos(arrayVehiculos: Array[Vehiculo]): Unit = {
    
    // Igual al numero de series que ya hay
    var autoincremento = dataset.getSeriesCount
    
    val numeroDeVias = dataset.getSeriesCount
    
    // Crear un XYSeries por cada vehiculo y agregar al dataset
    arrayVehiculos.foreach(vehiculo => {
      val serie = new XYSeries(vehiculo.placa)
      serie.add(
          vehiculo.posicion.x,
          vehiculo.posicion.y)
      dataset.addSeries(serie)
      autoincremento += 1
      plot.getRenderer.setSeriesPaint(this.dataset.getSeriesCount-1, vehiculo.color)

      vehiculo match {
        case _:Carro =>
          plot.getRenderer.setSeriesShape(this.dataset.getSeriesCount-1, new Rectangle(-4,-4,8,8))

        case _:Moto =>
          plot.getRenderer.setSeriesShape(this.dataset.getSeriesCount-1, new Polygon(Array(-4,4,6,0,-6),Array(-6,-6,0,6,0),5))

        case _:MotoTaxi =>
          plot.getRenderer.setSeriesShape(this.dataset.getSeriesCount-1, new Ellipse2D.Double(-4,-4,8,8))

        case _:Bus =>
          val pointsx = Array(-6, -2, -2, 2, 2, 6, 6, 2, 2, -2, -2, -6)
          val pointsy = Array(-2, -2, -6, -6,-2, -2, 2, 2, 6, 6, 2, 2)
          plot.getRenderer.setSeriesShape(this.dataset.getSeriesCount-1, new Polygon(pointsx,pointsy,12))

        case _:Camion =>
          plot.getRenderer.setSeriesShape(this.dataset.getSeriesCount-1, new Polygon(Array(-5,0,5),Array(-5,5,-5),3))

        case _ =>
          plot.getRenderer.setSeriesShape(this.dataset.getSeriesCount-1, new Rectangle(0,0,2,8))

      }
    })
  }
}
