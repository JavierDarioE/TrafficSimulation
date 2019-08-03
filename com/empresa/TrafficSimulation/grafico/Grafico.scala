package grafico

import vias._
import java.awt.BasicStroke
import java.awt.Color

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


object Grafico {
  
  val dataset = new XYSeriesCollection
  val dataset1 = new XYSeriesCollection
  var frame: ChartFrame = _
  
  //chart con titulo, label para eje x, label para eje y, y dataset
  val chart = ChartFactory.createScatterPlot("titulo", "", "", dataset)
  var chart1 = ChartFactory.createScatterPlot("titulo", "", "", dataset1)
  
  def graficarVias(arrayVias: Array[Via]){
   
    var autoincremento = 0
    
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
    val plot = chart.getXYPlot
    plot.getDomainAxis.setVisible(false) //quitar indicadores del eje X
    plot.getRangeAxis.setVisible(false) //quitar indicadores del eje Y
    
    plot.setBackgroundPaint(Color.WHITE) //Cambiar color de fondo
    plot.setBackgroundAlpha(1) //Quitar grid
    plot.setRenderer(new XYLineAndShapeRenderer()) // coso para poder graficar las lineas
    
    // Esto se puede hacer sin el for con cosas chidas de Scala:
    for(i <- 0 to dataset.getSeriesCount){
    plot.getRenderer.setSeriesStroke(i, new BasicStroke(4.0f))
    plot.getRenderer.setSeriesPaint(i, Color.GRAY)
    }
    
    //nuevo frame (ventana) con titulo y con chart.
    frame = new ChartFrame("TrafficSimulation", chart)
    frame.pack()
    frame.setSize(1000, 500)
    RefineryUtilities.positionFrameRandomly(frame)
    frame.setVisible(true)
    frame.requestFocus()
  }

  def graficarMovil(movil: Array[Vehiculo]): Unit ={
    println("test Grafica vehiculos")
  }

}