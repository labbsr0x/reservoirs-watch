package br.com.labbs.agrows.reservoirs.watch.dto;

import java.util.List;

import javax.json.bind.JsonbBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ArrayOfDadoHistoricoNordeste", namespace = "http://sarws.ana.gov.br")
public class VolumesNe {
	private List<VolumeNe> reservatorios;

	@XmlElement(name = "DadoHistoricoNordeste")
	public List<VolumeNe> getReservatorios() {
		return reservatorios;
	}

	public void setReservatorios(List<VolumeNe> reservatorios) {
		this.reservatorios = reservatorios;
	}

	@Override
	public String toString() {
		return JsonbBuilder.create().toJson(this);
	}
	
}
