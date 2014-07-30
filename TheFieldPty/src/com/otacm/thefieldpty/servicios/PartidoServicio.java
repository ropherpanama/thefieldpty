package com.otacm.thefieldpty.servicios;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.otacm.thefieldpty.json.JSONUtils;
import com.otacm.thefieldpty.json.beans.Calendario;
import com.otacm.thefieldpty.utils.AppUtils;

public class PartidoServicio {

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
}