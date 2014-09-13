package com.otacm.thefieldpty;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.google.gson.reflect.TypeToken;
import com.otacm.thefieldpty.adapters.CalendarioArrayAdapter;
import com.otacm.thefieldpty.groups.GroupCalendario;
import com.otacm.thefieldpty.json.JSONUtils;
import com.otacm.thefieldpty.json.beans.Calendario;
import com.otacm.thefieldpty.utils.AppUtils;
import com.otacm.thefieldpty.utils.Reporter;
public class FirstTabChildActivity extends ActionBarActivity {

	private String parent;
	private String children;
	private List<GroupCalendario> groups = new ArrayList<GroupCalendario>();
	private ListView list_partidos;
	private Reporter log = Reporter.getInstance();
	private CalendarioArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_tab_child);
		parent                   = getIntent().getExtras().getString("parent");
		children                 = getIntent().getExtras().getString("child");
		list_partidos = (ListView) findViewById(R.id.list_partidos);
		
		createTabHost();//Se levantan los tabs
		cargarDatosDeCalendario(); //Se cargan los datos del calendario para el tab
//		ExpandableCalendario expListAdapter = new ExpandableCalendario(this, groups);
		adapter = new CalendarioArrayAdapter(getApplicationContext(), groups);
		list_partidos.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.first_tab_child, menu);
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
	
	private void cargarDatosDeCalendario() {
		try{
			StringBuilder jsonCalendario = AppUtils.getJsonFromDisk(getApplicationContext(), "calendario");
			Type typeCalendario = new TypeToken<List<Calendario>>() {}.getType();
			List<Calendario> calendario = JSONUtils.factoryGson().fromJson(jsonCalendario.toString(), typeCalendario);
			groups.clear();
			
//			int cont = 0;
			for(Calendario c : calendario) {
				if(c.getLiga().equals(parent) && c.getCategoria().equals(children)){
					GroupCalendario group = new GroupCalendario();
					group.setEquipo1(c.getEquipo1());
					group.setEquipo2(c.getEquipo2()); 
					group.setCategoria(c.getCategoria()); 
					group.setDetallePartido(String.format(getString(R.string.detalle_partido), c.getFecha(), c.getHora(), c.getLugar()));
					group.setMarcadorFinal(c.getMarcadorFinal());
					groups.add(group);
					System.out.println(group.getDetallePartido());
				}else {
					//Toast.makeText(getApplicationContext(), getString(R.string.no_hay_calendario), Toast.LENGTH_LONG).show();
				}
//				cont++;
			}
		}catch(Exception e) {
			log.error(Reporter.stringStackTrace(e));
		}
	}
	
	/**
	 * Crea los tabs en la pantalla
	 */
	private void createTabHost() {
		try {
			final TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
			tabHost.setup();

			TabHost.TabSpec spec = tabHost.newTabSpec("Calendario");
			spec.setContent(R.id.tabCalendario);
			spec.setIndicator("Calendario");
			tabHost.addTab(spec);
			spec = tabHost.newTabSpec("Posiciones");
			spec.setContent(R.id.tabPosiciones);
			spec.setIndicator("Tabla de posiciones");
			tabHost.addTab(spec);

			tabHost.setOnTabChangedListener(new OnTabChangeListener() {
				@Override
				public void onTabChanged(String tabId) {
					InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(tabHost.getWindowToken(), 0);
					if (tabId.equals("some text in the tab header")) {
					} else if (tabId.equals("some text in the tab header")) {
					}
				}
			});

			tabHost.setCurrentTab(0);
		} catch (Exception e) {
			log.error(Reporter.stringStackTrace(e));
		}
	}
}
