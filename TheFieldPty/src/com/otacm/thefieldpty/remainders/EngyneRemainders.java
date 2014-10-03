package com.otacm.thefieldpty.remainders;

import java.util.List;

import android.content.Context;

import com.otacm.thefieldpty.database.beans.Remainder;
import com.otacm.thefieldpty.servicios.PartidoServicio;
import com.otacm.thefieldpty.utils.Reporter;

public class EngyneRemainders {
	private static Reporter log = Reporter.getInstance();
	  
	/**
	 * Este metodo se apoya en el servicio de los partidos
	 * para encontrar los partidos en los que el equipo seleccionado
	 * tiene participacion, de esta forma se programaran las 
	 * alarmas para dichos partidos
	 * @param ctx
	 * @param teamName nombre del team
	 * @return lista de los partidos para activar alarmas
	 */
	public static List<Remainder> getRemaindersByTeam(Context ctx, String teamName, int favid) {
		try {
			return PartidoServicio.createRemainders(ctx, teamName, favid);
		}catch(Exception e) {
//			log.write(Reporter.stringStackTrace(e));
			return null;
		}
	}
}
