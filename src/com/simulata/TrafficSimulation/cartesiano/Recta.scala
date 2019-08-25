package com.simulata.TrafficSimulation.cartesiano

trait Recta {
  type T <: Punto
  
  var origen: T = _
  
  var fin: T = _
  
  def angulo():Angulo={
    //se haya el ángulo entre el vector (fin-origen) y el vector (1,0), se devuelve en grados
    val vector = new Punto(fin.x-origen.x,fin.y-origen.y)
    if(vector.y>0){ 
      return new Angulo((scala.math.acos(vector.x/Punto.distancia(vector,Punto(0,0))))*180/math.Pi)    
      }
   //se multiplica por -1 en caso de que el vector esté en los cuadrantes 3 o 4
    else{
      return new Angulo(-((scala.math.acos(vector.x/Punto.distancia(vector,Punto(0,0))))*180/math.Pi))
    }
  }
  def puntoMedio():Punto={
    val x=(origen.x+fin.x)/2
    val y=(origen.y+fin.y)/2
    Punto(x,y)
  }
}