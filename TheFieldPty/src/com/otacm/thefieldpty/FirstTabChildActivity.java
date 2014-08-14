package com.otacm.thefieldpty;

import java.lang.reflect.Type;
import java.util.List;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.example.sample.R;
import com.google.gson.reflect.TypeToken;
import com.otacm.thefieldpty.adapters.ExpandableCalendario;
import com.otacm.thefieldpty.groups.GroupCalendario;
import com.otacm.thefieldpty.json.JSONUtils;
import com.otacm.thefieldpty.json.beans.Calendario;
import com.otacm.thefieldpty.utils.AppUtils;
import com.otacm.thefieldpty.utils.Reporter;
public class FirstTabChildActivity extends ActionBarActivity {

	private String parent;
	private String children;
	private SparseArray<GroupCalendario> groups = new SparseArray<GroupCalendario>();
	private ExpandableListView expandable_list_partidos;
	private Reporter log = Reporter.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_tab_child);
		parent                   = getIntent().getExtras().getString("parent");
		children                 = getIntent().getExtras().getString("child");
		expandable_list_partidos = (ExpandableListView) findViewById(R.id.expandable_list_partidos);
		
		createTabHost();//Se levantan los tabs
		cargarDatosDeCalendario(); //Se cargan los datos del calendario para el tab
		ExpandableCalendario expListAdapter = new ExpandableCalendario(this, groups);
		expandable_list_partidos.setAdapter(expListAdapter);
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
		return super.onOptionsItemSelected(item);
	}
	
	private void cargarDatosDeCalendario() {
		try{
			StringBuilder jsonCalendario = AppUtils.getJsonFromDisk(getApplicationContext(), "calendario");
			Type typeCalendario = new TypeToken<List<Calendario>>() {}.getType();
			List<Calendario> calendario = JSONUtils.factoryGson().fromJson(jsonCalendario.toString(), typeCalendario);
			groups.clear();
			
			int cont = 0;
			for(Calendario c : calendario) {
				if(c.getLiga().equals(parent) && c.getCategoria().equals(children)){
					GroupCalendario group = new GroupCalendario();
					group.setEquipo1(c.getEquipo1());
					group.setEquipo2(c.getEquipo2()); 
					group.setDetallePartido(String.format(getString(R.string.detalle_partido), c.getFecha(), c.getHora(), c.getLugar()));
					groups.append(cont, group);
					System.out.println(group.getDetallePartido());
				}else {
					//Toast.makeText(getApplicationContext(), getString(R.string.no_hay_calendario), Toast.LENGTH_LONG).show();
				}
				cont++;
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
