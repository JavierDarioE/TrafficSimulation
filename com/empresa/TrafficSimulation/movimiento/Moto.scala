package movimiento

import java.awt.Color

import cartesiano._
import vias._

//falta definir el modelo para representarlos*****/

//para poner los get y los set se debe de copiar todo el código, no vi como dejarlo en vehículo

class Moto (val pl:String, val o:Interseccion, val d:Interseccion, private var _v:Velocidad, val color:Color = Color.WHITE/*, val figura:??*/) extends Vehiculo(pl, o, d, _v){
  
  private var _p:Punto=o  
  
  def p = _p
  
  def p_=(p:Punto): Unit = _p = p
  
  def v = _v
  
  def v_=(v:Velocidad):Unit = _v = v
    
//Indica el ángulo que deberá seguir entre el origen y la primer intersección de la ruta
  if (ruta.length>1) actualizarAngulo(o,ruta(1),v)  
  
  def mover(dt:Double):Unit={
    //se verifica que aún no se haya llegado a la via final
    if (ruta.length>1){
      //se verifica que la distancia entre el vehículo y la intersección objetivo actual sea mayor que la distancia que se
      //mueve el vehículo en dt, si es menor se corrige poniendo el vehículo en la posición de la intersección objetivo actual
      //y como se alcanzó se actualiza el ángulo y se remueve esta intersección de la ruta
      if(Punto.distancia(p, ruta(1))<=Punto.distancia(p,movimiento(dt,v))){
        actualizarAngulo(ruta(0),ruta(1),v)
        p=Punto(ruta(0).x,ruta(0).y)
        ruta=ruta.drop(1)
      }
    }
    //Verifica que aún no haya llegado a la intersección final
    if(ruta.length>0){
      val dp = movimiento(dt,v)
      val nuevox = dp.x+this.p.x
      val nuevoy = dp.y+this.p.y
      this.p_=(Punto(nuevox,nuevoy))
    }
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
