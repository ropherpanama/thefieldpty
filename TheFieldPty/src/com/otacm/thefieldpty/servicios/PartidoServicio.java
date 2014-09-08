package com.otacm.thefieldpty.servicios;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.otacm.thefieldpty.database.beans.Remainder;
import com.otacm.thefieldpty.json.JSONUtils;
import com.otacm.thefieldpty.json.beans.Calendario;
import com.otacm.thefieldpty.utils.AppUtils;
import com.otacm.thefieldpty.utils.Fechas;
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
	
	public static List<Remainder> createRemainders(Context ctx, String teamName, int favid){
		try{
			StringBuilder jsonCalendario = AppUtils.getJsonFromDisk(ctx, "calendario");
			Type typeCalendario = new TypeToken<List<Calendario>>() {}.getType();
			List<Calendario> calendario = JSONUtils.factoryGson().fromJson(jsonCalendario.toString(), typeCalendario);
			List<Remainder> remainders = new ArrayList<Remainder>();
 
			for (Calendario c : calendario) {
				if (c.getEquipo1().equals(teamName) || c.getEquipo2().equals(teamName)) {
					Date thisDate = new Date();
					Date thatDate = Fechas.string2Date(c.getFecha(), Fechas.DDMMYYYYGUION); 
					String str1 = c.getFecha();
					String str2 = Fechas.fechahoy(Fechas.DDMMYYYYGUION);
					
					if(thisDate.before(thatDate) || str1.equals(str2)) {
						Remainder r = new Remainder();
						r.setTitle(c.getEquipo1() + " vs " + c.getEquipo2());
						r.setDescription(c.getLugar());
						r.setDate(c.getFecha() + " " + c.getHora()); 
						r.setStatus(0);
						r.setFavid(favid); 
						remainders.add(r);
					}
				}
			}
			calendario = null;
			return remainders; 
		}catch(Exception e){
			log.error(Reporter.stringStackTrace(e));
			return null;
		}
	}
}