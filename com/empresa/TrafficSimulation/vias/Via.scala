package vias

import cartesiano.Recta

//no estoy seguro de si poner la velocidad de la via como tipo int o tipo velocidad, creo que
//es mas facil dejandola tipo Int por como se le mandan los parámetros
case class Via(origenn:Interseccion, finn:Interseccion, vmax:Int , tipo:TipoVia, sentido:Sentido, numero:String, nombre:String)
  extends Recta(){
  type T = Interseccion
  origen = origenn
  fin = finn
}