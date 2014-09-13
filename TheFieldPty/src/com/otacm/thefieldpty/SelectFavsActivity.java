package com.otacm.thefieldpty;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.otacm.thefieldpty.adapters.EquiposArrayAdapter;
import com.otacm.thefieldpty.database.beans.Favoritos;
import com.otacm.thefieldpty.database.daos.FavoritosDAO;
import com.otacm.thefieldpty.json.beans.Equipos;
import com.otacm.thefieldpty.servicios.EquiposServicio;
import com.otacm.thefieldpty.utils.AppUtils;

public class SelectFavsActivity extends ActionBarActivity {

	private ListView listEquipos;
	private List<Equipos> equipos = new ArrayList<Equipos>();
	private List<Equipos> filterEquipos = new ArrayList<Equipos>();
	private EquiposArrayAdapter adapter;
	private TextView testTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_favs);
		listEquipos = (ListView) findViewById(R.id.listEquipos);
		testTitle = (TextView) findViewById(R.id.testTitle);
		testTitle.setTypeface(AppUtils.normalFont(getApplicationContext()));;
		equipos = EquiposServicio.getAll(getApplicationContext());
		
		filtrarFavoritos();//Los favoritos no deben aparecer en la lista de equipos a elegir
		
		filterEquipos = equipos;
		
		adapter = new EquiposArrayAdapter(getApplicationContext(), filterEquipos);
		listEquipos.setAdapter(adapter);
		
		equipos = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.select_favs, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.action_settings) {
			return true;
		}
		
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {//Se envia el resultCode para actualizar la lista
		super.onBackPressed();
		Intent in = new Intent();
		setResult(1, in);
	}
	
	/**
	 * Se crea este metodo para filtrar los equipos que son favoritos
	 * para que no aparezcan en la lista para seleccion de los mismos
	 * esto evita confusiones
	 */
	public void filtrarFavoritos() {
		FavoritosDAO fdao = new FavoritosDAO(getApplicationContext());
		List<Favoritos> favs = new ArrayList<Favoritos>();
		favs = fdao.getAll();
		
		for(int i = 0; i < equipos.size(); i++) {
			for(Favoritos f : favs) {
				if(equipos.get(i).getNombre().equals(f.getNombre())) 
					equipos.remove(i);
			}
		}
		
		favs = null;
	}
}
