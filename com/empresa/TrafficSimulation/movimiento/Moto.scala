package movimiento

import cartesiano._

import vias._

//falta definir el modelo para representarlos*****/

//para poner los get y los set se debe de copiar todo el código, no vi como dejarlo en vehículo

class Moto (val pl:String, val o:Interseccion, val d:Interseccion, private var _v:Velocidad/*,val color:??, val figura:??*/) extends Vehiculo(pl, o, d, _v){
  
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
object Moto{
  val r = scala.util.Random

  def generarPlaca:String={
    var a:String=""
    for(i<- 0 to 2) a=a+Vehiculo.digitos(r.nextInt(10))
    for(i<- 0 to 1) a=a+Vehiculo.letras(r.nextInt(26))
    a=a+Vehiculo.digitos(r.nextInt(10))
    a
  }
}
