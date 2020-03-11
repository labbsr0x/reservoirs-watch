package br.com.labbs.agrows.reservoirs.watch.dto;

import java.util.List;

import javax.json.bind.JsonbBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ArrayOfDadoHistoricoSIN", namespace = "http://sarws.ana.gov.br")
public class VolumesSin {
	private List<VolumeSin> reservatorios;

	@XmlElement(name = "DadoHistoricoSIN")
	public List<VolumeSin> getReservatorios() {
		return reservatorios;
	}

	public void setReservatorios(List<VolumeSin> reservatorios) {
		this.reservatorios = reservatorios;
	}

	@Override
	public String toString() {
		return JsonbBuilder.create().toJson(this);
	}
	
}
