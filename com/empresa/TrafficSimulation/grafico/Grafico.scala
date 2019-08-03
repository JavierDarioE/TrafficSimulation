package grafico

import vias._
import movimiento._
import simulacion.Simulacion
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Shape
import java.awt.geom.Rectangle2D.Double

import movimiento.Vehiculo
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartFrame
import org.jfree.chart.JFreeChart
import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.plot.XYPlot
import org.jfree.data.xy.XYDataset
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.chart.annotations.XYTextAnnotation
import org.jfree.ui.RefineryUtilities
import java.awt.event.{KeyEvent, KeyListener, WindowEvent, WindowListener, WindowStateListener}

object Grafico {
  
  val dataset = new XYSeriesCollection
  val dataset1 = new XYSeriesCollection
  var frame: ChartFrame = _
  
  //chart con titulo, label para eje x, label para eje y, y dataset
  val chart = ChartFactory.createScatterPlot("titulo", "", "", dataset)
  
  val plot = chart.getXYPlot
  
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
      var etiqueta1 = new XYTextAnnotation(
          via.origenn.nombre, via.origenn.xx, via.origenn.yy)
      
      etiqueta1.setPaint(via.origenn.color)
      
      var etiqueta2 = new XYTextAnnotation(
          via.finn.nombre, via.finn.xx, via.finn.yy)
      
      etiqueta2.setPaint(via.finn.color)
      
      plot.addAnnotation(etiqueta1); plot.addAnnotation(etiqueta2) 
      })
    
    // Esto se puede hacer sin el for con cosas chidas de Scala pero meh:
    // Personalizacion del grafico
    for(i <- 0 to dataset.getSeriesCount - 1){
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
  }
  
  def graficarVehiculos(arrayVehiculos: Array[Vehiculo]) {
    
    // Igual al numero de series que ya hay
    var autoincremento = dataset.getSeriesCount
    
    val numeroDeVias = dataset.getSeriesCount
    
    // Crear un XYSeries por cada vehiculo y agregar al dataset
    arrayVehiculos.foreach(vehiculo => {
      val serie = new XYSeries(autoincremento)
      serie.add(
            vehiculo.asInstanceOf[Movil].posicion.x, 
            vehiculo.asInstanceOf[Movil].posicion.y)
      dataset.addSeries(serie)
      autoincremento += 1
    })
    
    var indice = 0
    
    // Esto se puede hacer sin el for con cosas chidas de Scala pero meh:
    // Personalizacion del grafico  
    for(i <- numeroDeVias to dataset.getSeriesCount-1){
      
      // TODO En donde va el color se llamaria el atributo color de Vehiculo
      plot.getRenderer.setSeriesPaint(i, arrayVehiculos(indice).destino.color)
      
      // TODO En donde esta el new Double se llamaria al atributo figura de Vehiculo
      plot.getRenderer.setSeriesShape(i, arrayVehiculos(indice).forma)
      indice += 1
    }
  }
}