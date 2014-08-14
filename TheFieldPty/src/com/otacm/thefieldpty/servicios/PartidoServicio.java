package com.otacm.thefieldpty.servicios;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.otacm.thefieldpty.json.JSONUtils;
import com.otacm.thefieldpty.json.beans.Calendario;
import com.otacm.thefieldpty.utils.AppUtils;
import com.otacm.thefieldpty.utils.Reporter;

public class PartidoServicio {

	private static Reporter log = Reporter.getInstance();
	
	public static List<Integer> getIdPartidos(Context ctx) {
		StringBuilder jsonCalendario = AppUtils.getJsonFromDisk(ctx,
				"calendario");
		Type typeCalendario = new TypeToken<List<Calendario>>() {
		}.getType();
		List<Calendario> calendario = JSONUtils.factoryGson().fromJson(
				jsonCalendario.toString(), typeCalendario);
		List<Integer> ids = new ArrayList<Integer>();

		for (Calendario c : calendario) {
			ids.add(c.getIdPartido());
		}

		jsonCalendario = null;
		return ids;
	}

	public static String getNombreLigaByIdPartido(Context ctx, int idPartido) {
		StringBuilder jsonCalendario = AppUtils.getJsonFromDisk(ctx,
				"calendario");
		Type typeCalendario = new TypeToken<List<Calendario>>() {
		}.getType();
		List<Calendario> calendario = JSONUtils.factoryGson().fromJson(
				jsonCalendario.toString(), typeCalendario);
		String nombreLiga = "";

		for (Calendario c : calendario) {
			if (c.getIdPartido() == idPartido)
				nombreLiga = c.getLiga();
		}

		return nombreLiga;
	}
	
	public static Calendario getPartidoById(Context ctx, int id){
		try{
			StringBuilder jsonCalendario = AppUtils.getJsonFromDisk(ctx, "calendario");
			Type typeCalendario = new TypeToken<List<Calendario>>() {}.getType();
			List<Calendario> calendario = JSONUtils.factoryGson().fromJson(jsonCalendario.toString(), typeCalendario);
			Calendario retorno = new Calendario(); 
			for (Calendario c : calendario) {
				if (c.getIdPartido() == id){
					retorno = c;
					break;
				}
			}
			
			return retorno; 
		}catch(Exception e){
			log.error(Reporter.stringStackTrace(e));
			return null;
		}
	}
}