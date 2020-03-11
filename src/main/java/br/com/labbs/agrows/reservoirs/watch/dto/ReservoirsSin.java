package br.com.labbs.agrows.reservoirs.watch.dto;

import java.util.List;

import javax.json.bind.JsonbBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ArrayOfReservatorioSIN", namespace = "http://sarws.ana.gov.br")
public class ReservoirsSin {
	
	private List<Reservoir> reservatorios;

	@XmlElement(name = "ReservatorioSIN")
	public List<Reservoir> getReservatorios() {
		return reservatorios;
	}

	public void setReservatorios(List<Reservoir> reservatorios) {
		this.reservatorios = reservatorios;
	}
	
	@Override
	public String toString() {
		return JsonbBuilder.create().toJson(this);
	}

}
