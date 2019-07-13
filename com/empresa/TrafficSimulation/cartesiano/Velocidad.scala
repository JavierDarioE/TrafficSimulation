package cartesiano

//están como variables por si luego ponen que no es constante la velocidad


class Velocidad (private var _magnitud:Double,private var _angulo:Angulo){
  
  def magnitud = _magnitud
  
  def magnitud_=(magnitud:Double):Unit = _magnitud = magnitud
  
  def angulo = _angulo
  
  def angulo_=(angulo:Angulo):Unit = _angulo = angulo
}