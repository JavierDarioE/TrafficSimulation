package movimiento

import cartesiano._

class Vehiculo private (val placa:String, private var _p:Punto, private var _v:Velocidad) 
extends Movil(_p, _v) with MovimientoUniforme {
  
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