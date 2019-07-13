package movimiento

import cartesiano._

abstract class Movil (private var _posicion: Punto, private var _velocidad: Velocidad) {
  
  def posicion = _posicion
  
  def posicion_=(posicion:Punto): Unit = _posicion = posicion
  
  def velocidad = _velocidad
  
  def velocidad_=(velocidad:Velocidad):Unit = _velocidad = velocidad
  
  //Funcion que aumenta la posicion en un dt, está definida en el trait MovimientoUniforme
  def mover(dt:Double):Unit
  
  def angulo = this.velocidad.angulo.angulo
}