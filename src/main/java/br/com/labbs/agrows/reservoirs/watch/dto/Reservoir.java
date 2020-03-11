package br.com.labbs.agrows.reservoirs.watch.dto;

import javax.json.bind.JsonbBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Reservoir {

	private Integer codigo_reservatorio;
	private String nome_reservatorio;
	private String municipio;
	private String estado;

	public Integer getCodigo_reservatorio() {
		return codigo_reservatorio;
	}

	public void setCodigo_reservatorio(Integer codigo_reservatorio) {
		this.codigo_reservatorio = codigo_reservatorio;
	}

	public String getNome_reservatorio() {
		return nome_reservatorio;
	}

	public void setNome_reservatorio(String nome_reservatorio) {
		this.nome_reservatorio = nome_reservatorio.trim();
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio.trim();
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado.trim();
	}

	@Override
	public String toString() {
		return JsonbBuilder.create().toJson(this);
	}

}
