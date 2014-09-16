package com.otacm.thefieldpty.servicios;

import java.lang.reflect.Type;
import java.util.List;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.otacm.thefieldpty.json.JSONUtils;
import com.otacm.thefieldpty.json.beans.Categoria;
import com.otacm.thefieldpty.utils.AppUtils;

public class CategoriaServicio {

	public static Categoria getCategoriaByName(Context ctx, String nomLiga, String nomCategoria) {
		StringBuilder jsonCategorias = AppUtils.getJsonFromDisk(ctx, "cats");
		Type typeCategoria = new TypeToken<List<Categoria>>() {}.getType();
		List<Categoria> categorias = JSONUtils.factoryGson().fromJson(jsonCategorias.toString(), typeCategoria);
		
		for(Categoria c : categorias) {
			if(c.getLiga().equals(nomLiga) && c.getNombre().equals(nomCategoria))
				return c;
		}
		return null;
	}
}
