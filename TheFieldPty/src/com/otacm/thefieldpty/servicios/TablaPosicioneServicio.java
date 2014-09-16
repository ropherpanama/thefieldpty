package com.otacm.thefieldpty.servicios;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.otacm.thefieldpty.json.JSONUtils;
import com.otacm.thefieldpty.json.beans.Categoria;
import com.otacm.thefieldpty.json.beans.Grupo;
import com.otacm.thefieldpty.utils.AppUtils;

public class TablaPosicioneServicio {
	
	public static List<Grupo> getGruposByCategoria(Context ctx, String nomLiga, String nomCategoria) {
		System.out.println("Buscando categoria para: " + nomLiga + " > " + nomCategoria);
		StringBuilder jsonCalendario = AppUtils.getJsonFromDisk(ctx, "posiciones");
		
		if(jsonCalendario == null)
			return null;
		
		Type typeGrupo = new TypeToken<List<Grupo>>() {}.getType();
		List<Grupo> data = JSONUtils.factoryGson().fromJson(jsonCalendario.toString(), typeGrupo);
		List<Grupo> retorno = new ArrayList<Grupo>();
		Categoria c = CategoriaServicio.getCategoriaByName(ctx, nomLiga, nomCategoria);
			
		if(c != null) {
			System.out.println("Categoria para tabla de posiciones " + c.getId());
			
			if(data != null) {
				for(Grupo g : data) {
					if(g.getIdCategoria() == c.getId()) {
						retorno.add(g);
					}
				}
			}else 
				return null;
		}
		
		return retorno;
	}
}
