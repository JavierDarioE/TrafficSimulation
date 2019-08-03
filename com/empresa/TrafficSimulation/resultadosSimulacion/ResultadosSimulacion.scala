package resultadosSimulacion

import movimiento._

import json.Json

class ResultadosSimulacion {

  private var _total: Int = _
  def total_= (total: Int){_total = total}

  private var _carros: Int = _
  def carros_=(carros: Int){_carros = carros}

  private var _motos: Int = _
  def motos_=(motos: Int){_motos = motos}

  private var _buses: Int = _
  def buses_=(buses: Int){_buses = buses}

  private var _camiones: Int = _
  def camiones_=(camiones: Int){_camiones = camiones}

  private var _motoTaxis: Int = _
  def motoTaxis_=(motoTaxis: Int){_motoTaxis = motoTaxis}

  private var _vias: Int = _
  def vias_=(vias: Int){_vias = vias}

  private var _intersecciones: Int = _
  def intersecciones_=(intersecciones: Int){_intersecciones = intersecciones}

  private var _viasUnSentido: Int = _
  def viasUnSentido_=(viasUnSentido: Int){_viasUnSentido = viasUnSentido}

  private var _viasDobleSentido: Int = _
  def viasDobleSentido_=(viasDobleSentido: Int){_viasDobleSentido = viasDobleSentido}

  private var _velocidadMinima: Int = _
  def velocidadMinima_=(velocidadMinima: Int){_velocidadMinima = velocidadMinima}

  private var _velocidadMaxima: Int = _
  def velocidadMaxima_=(velocidadMaxima: Int){_velocidadMaxima = velocidadMaxima}

  private var _longitudPromedio: Int = _
  def longitudPromedio_=(longitudPromedio: Int){_longitudPromedio = longitudPromedio}

  private var _promedioOrigen: Int = _
  def promedioOrigen_=(promedioOrigen: Int){_promedioOrigen = promedioOrigen}

  private var _promedioDestino: Int = _
  def promedioDestino_=(promedioDestino: Int){_promedioDestino = promedioDestino}

  private var _sinOrigen: Int = _
  def sinOrigen_=(sinOrigen: Int){_sinOrigen = sinOrigen}

  private var _sinDestino: Int = _
  def sinDestino_=(sinDestino: Int){_sinDestino = sinDestino}

  private var _simulacion: Int = _
  def simulacion_=(simulacion: Int){_simulacion = simulacion}

  private var _realidad: Int = _
  def realidad_=(realidad: Int){_realidad = realidad}

  private var _velMinima: Int = _
  def velMinima_=(velMinima: Int){_velMinima = velMinima}

  private var _velMaxima: Int = _
  def velMaxima_=(velMaxima: Int){_velMaxima = velMaxima}

  private var _velPromedio: Int = _
  def velPromedio_=(velPromedio: Int){_velPromedio = velPromedio}

  private var _distMinima: Int = _
  def distMinima_=(distMinima: Int){_distMinima  = distMinima}

  private var _distMaxima: Int = _
  def distMaxima_=(distMaxima: Int){_distMaxima = distMaxima}

  private var _distPromedio: Int = _
  def distPromedio_=(distPromedio: Int){_distPromedio = distPromedio}

  def guardar(): Unit = {
    Json.saveResults(_total,
      _carros,
      _motos,
      _buses,
      _camiones,
      _motoTaxis,
      _vias,
      _intersecciones,
      _viasUnSentido,
      _viasDobleSentido,
      _velocidadMinima,
      _velocidadMaxima,
      _longitudPromedio,
      _promedioOrigen,
      _promedioDestino,
      _sinOrigen,
      _sinDestino,
      _simulacion,
      _realidad,
      _velMinima,
      _velMaxima,
      _velPromedio,
      _distMinima,
      _distMaxima,
      _distPromedio)
  }

}