package br.com.hslife.encontreaquipecas.converter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="dateConverter")
public class DateConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) return null;
		String[] dataSeparada = value.split("/");
		Calendar temp = Calendar.getInstance();
		temp.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dataSeparada[0]));
		temp.set(Calendar.MONTH, Integer.parseInt(dataSeparada[1]) - 1);
		temp.set(Calendar.YEAR, Integer.parseInt(dataSeparada[2]));
		return temp.getTime();
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) return "";
		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
		return formataData.format((Date)value);
	}
}
