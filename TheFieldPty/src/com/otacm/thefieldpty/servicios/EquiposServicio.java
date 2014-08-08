package com.otacm.thefieldpty.servicios;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import com.google.gson.reflect.TypeToken;
import com.otacm.thefieldpty.json.JSONUtils;
import com.otacm.thefieldpty.json.beans.Equipos;
import com.otacm.thefieldpty.utils.AppUtils;

public class EquiposServicio {
	
	public static List<Equipos> getAll(Context ctx) {
		StringBuilder jsonCalendario = AppUtils.getJsonFromDisk(ctx, "equipos");
		Type typeEquipos = new TypeToken<List<Equipos>>() {}.getType();
		return JSONUtils.factoryGson().fromJson(jsonCalendario.toString(), typeEquipos);
	}
	
	public static List<Equipos> getEquiposByLiga(Context ctx, String liga) {
		StringBuilder jsonCalendario = AppUtils.getJsonFromDisk(ctx, "equipos");
		Type typeEquipos = new TypeToken<List<Equipos>>() {}.getType();
		List<Equipos> l = JSONUtils.factoryGson().fromJson(jsonCalendario.toString(), typeEquipos);
		List<Equipos> retorno = new ArrayList<Equipos>();
		
		for(Equipos e : l) {
			if(e.getLiga().equals(liga))
				retorno.add(e);
		}
		
		l = null;
		return retorno;
	}
	
	public static List<Equipos> getEquiposByCategoria(Context ctx, String liga, String cat) {
		StringBuilder jsonCalendario = AppUtils.getJsonFromDisk(ctx, "equipos");
		Type typeEquipos = new TypeToken<List<Equipos>>() {}.getType();
		List<Equipos> l = JSONUtils.factoryGson().fromJson(jsonCalendario.toString(), typeEquipos);
		List<Equipos> retorno = new ArrayList<Equipos>();
		
		for(Equipos e : l) {
			if(e.getLiga().equals(liga) && e.getCategoria().equals(cat)) 
				retorno.add(e);
		}
		
		l = null;
		return retorno;
	}
}
