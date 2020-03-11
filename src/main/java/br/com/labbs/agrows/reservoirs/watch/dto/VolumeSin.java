package br.com.labbs.agrows.reservoirs.watch.dto;

import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.json.bind.JsonbBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import br.com.labbs.agrows.reservoirs.watch.dto.interfaces.IVolumeData;

@XmlAccessorType(XmlAccessType.FIELD)
public class VolumeSin implements IVolumeData {
	
	public Integer cod_reservatorio;
	public String nome_reservatorio;
	public Double volumeUtil;
	public Double cota;
	public Double afluencia;
	public Double defluencia;
	public Date data_medicao;

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

	public Double getVolumeUtil() {
		return volumeUtil;
	}

	public void setVolumeUtil(Double volumeUtil) {
		this.volumeUtil = volumeUtil;
	}

	public Double getCota() {
		return cota;
	}

	public void setCota(Double cota) {
		this.cota = cota;
	}

	public Double getAfluencia() {
		return afluencia;
	}

	public void setAfluencia(Double afluencia) {
		this.afluencia = afluencia;
	}

	public Double getDefluencia() {
		return defluencia;
	}

	public void setDefluencia(Double defluencia) {
		this.defluencia = defluencia;
	}

	public Date getData_medicao() {
		return data_medicao;
	}

	public void setData_medicao(Date data_medicao) {
		this.data_medicao = data_medicao;
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
		if (getCota() != null) {
			return getCota().toString().trim();
		}
		return "-1";
	}

	@Override
	public String getDateTime() {
		return DateTimeFormatter.ISO_INSTANT.format(getData_medicao().toInstant());
	}

}
