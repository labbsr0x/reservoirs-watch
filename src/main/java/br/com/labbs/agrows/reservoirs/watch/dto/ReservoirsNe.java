package br.com.labbs.agrows.reservoirs.watch.dto;

import java.util.List;

import javax.json.bind.JsonbBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ArrayOfReservatorioNordeste")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReservoirsNe {
	
	@XmlElement(name = "ReservatorioNordeste")
	private List<Reservoir> reservatorios;

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
