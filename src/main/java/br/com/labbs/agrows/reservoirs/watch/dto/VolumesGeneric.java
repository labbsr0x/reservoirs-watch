package br.com.labbs.agrows.reservoirs.watch.dto;

import java.util.List;

import javax.json.bind.JsonbBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ArrayOfDadosHistoricosReservatorios", namespace = "http://sarws.ana.gov.br")
public class VolumesGeneric {
	private List<VolumeGeneric> reservatorios;

	@XmlElement(name = "DadosHistoricosReservatorios")
	public List<VolumeGeneric> getReservatorios() {
		return reservatorios;
	}

	public void setReservatorios(List<VolumeGeneric> reservatorios) {
		this.reservatorios = reservatorios;
	}
	
	@Override
	public String toString() {
		return JsonbBuilder.create().toJson(this);
	}
}
