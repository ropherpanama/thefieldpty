package com.otacm.thefieldpty;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.sample.R;
import com.otacm.thefieldpty.adapters.EquiposArrayAdapter;
import com.otacm.thefieldpty.json.beans.Equipos;
import com.otacm.thefieldpty.servicios.EquiposServicio;

public class SelectFavsActivity extends ActionBarActivity {

	private ListView listEquipos;
	private List<Equipos> equipos = new ArrayList<Equipos>();
	private EquiposArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_favs);
		listEquipos = (ListView) findViewById(R.id.listEquipos);
		equipos = EquiposServicio.getAll(getApplicationContext());
		adapter = new EquiposArrayAdapter(getApplicationContext(), equipos);
		listEquipos.setAdapter(adapter);
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
		return super.onOptionsItemSelected(item);
	}
}
