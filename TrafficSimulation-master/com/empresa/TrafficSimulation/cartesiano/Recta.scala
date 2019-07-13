package cartesiano

trait Recta {
  type T <: Punto
  
  var origen: T = _
  
  var fin: T = _
}