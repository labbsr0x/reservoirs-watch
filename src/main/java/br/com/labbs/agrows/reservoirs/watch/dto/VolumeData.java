package br.com.labbs.agrows.reservoirs.watch.dto;

import javax.json.bind.JsonbBuilder;

import br.com.labbs.agrows.reservoirs.watch.dto.interfaces.IVolumeData;

public class VolumeData implements IVolumeData {

	public String code;
	public String name;
	public String volum;
	public String dateTime;

	public VolumeData(IVolumeData values) {
		this.code = values.getCode();
		this.name = values.getName();
		this.volum = values.getVolum();
		this.dateTime = values.getDateTime();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVolum() {
		return volum;
	}

	public void setVolum(String volum) {
		this.volum = volum;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String date) {
		this.dateTime = date;
	}

	@Override
	public String toString() {
		return JsonbBuilder.create().toJson(this);
	}
	
	

}
