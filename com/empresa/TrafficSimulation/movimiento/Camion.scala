package movimiento

import java.awt.Color

import cartesiano._
import vias._

//falta definir el modelo para representarlos*****/

//para poner los get y los set se debe de copiar todo el código, no vi como dejarlo en vehículo

class Camion (val pl:String, val o:Interseccion, val d:Interseccion, private var _v:Velocidad, val color:Color = Color.WHITE/*, val figura:??*/) extends Vehiculo(pl, o, d, _v){
  
  private var _p:Punto=o
  
  def p = _p
  
  def p_=(p:Punto): Unit = _p = p
  
  def v = _v
  
  def v_=(v:Velocidad):Unit = _v = v
  
  def mover(dt:Double):Unit={
    val dp = movimiento(dt, this.velocidad)
    val nuevox = dp.x+this.p.x
    val nuevoy = dp.y+this.p.y
    this.p_=(Punto(nuevox,nuevoy))
  }
}
object Camion{
val r = scala.util.Random

  def generarPlaca:String={
    var a:String="R"
    for(i<- 0 to 4) a=a+Vehiculo.digitos(r.nextInt(10))
    a
  }
}

