package br.com.labbs.agrows.reservoirs.watch.dto;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.json.bind.JsonbBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.labbs.agrows.reservoirs.watch.dto.interfaces.IVolumeData;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class VolumeNe implements IVolumeData {

	public Integer cod_reservatorio;
	public String nome_reservatorio;
	public Double cota;
	public Double volume;
	public Date data_medicao;
	public Long cod_hidro;

	public Integer getCod_reservatorio() {
		return cod_reservatorio;
	}

	public void setCod_reservatorio(Integer cod_reservatorio) {
		this.cod_reservatorio = cod_reservatorio;
	}

	public String getNome_reservatorio() {
		return nome_reservatorio;
	}

	public void setNome_reservatorio(String nome_reservatorio) {
		this.nome_reservatorio = nome_reservatorio;
	}

	public Double getCota() {
		return cota;
	}

	public void setCota(Double cota) {
		this.cota = cota;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Date getData_medicao() {
		return data_medicao;
	}

	public void setData_medicao(Timestamp data_medicao) {
		this.data_medicao = data_medicao;
	}

	public Long getCod_hidro() {
		return cod_hidro;
	}

	public void setCod_hidro(Long cod_hidro) {
		this.cod_hidro = cod_hidro;
	}

	@Override
	public String toString() {
		return JsonbBuilder.create().toJson(this);
	}

	@Override
	public String getCode() {
		return getCod_reservatorio().toString().trim();
	}

	@Override
	public String getName() {
		return getNome_reservatorio().trim();
	}

	@Override
	public String getVolum() {
		if (getVolume() != null) {
			return getVolume().toString().trim();
		}
		return "-1";
	}

	@Override
	public String getDateTime() {
		return DateTimeFormatter.ISO_INSTANT.format(getData_medicao().toInstant());
	}
	
}
