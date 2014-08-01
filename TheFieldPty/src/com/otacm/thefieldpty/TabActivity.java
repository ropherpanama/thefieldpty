package com.otacm.thefieldpty;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
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
import com.otacm.thefieldpty.adapters.ExpandableListAdapter;
import com.otacm.thefieldpty.adapters.ExpandableScores;
import com.otacm.thefieldpty.groups.GroupLigas;
import com.otacm.thefieldpty.groups.GroupScores;
import com.otacm.thefieldpty.json.JSONUtils;
import com.otacm.thefieldpty.json.beans.Calendario;
import com.otacm.thefieldpty.json.beans.Categoria;
import com.otacm.thefieldpty.json.beans.Liga;
import com.otacm.thefieldpty.json.beans.Scores;
import com.otacm.thefieldpty.servicios.PartidoServicio;
import com.otacm.thefieldpty.utils.AppUtils;
import com.otacm.thefieldpty.utils.Fechas;

public class TabActivity extends ActionBarActivity {
	private ExpandableListView expandable_list_ligas;
	private ExpandableListView expandableListScores;
//	private StringBuilder jsonLigas;
//	private StringBuilder jsonCategorias;
	
	private Context ctx = this;
	private SparseArray<GroupLigas> groups = new SparseArray<GroupLigas>();
	private SparseArray<GroupScores> groupScores = new SparseArray<GroupScores>();
	private List<String> strLigas = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		expandable_list_ligas = (ExpandableListView) findViewById(R.id.expandable_list_ligas);
		expandableListScores  = (ExpandableListView) findViewById(R.id.expandableListScores);
		
		createTabHost();
		
		createExpandableData();
		final ExpandableListAdapter expListAdapter = new ExpandableListAdapter((Activity) ctx, groups);
        expandable_list_ligas.setAdapter(expListAdapter);
        
        cargarTabScores();
		final ExpandableScores expSc = new ExpandableScores((Activity) ctx, groupScores);
		expandableListScores.setAdapter(expSc); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.tab, menu);
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

	/**
	 * Crea los tabs en la pantalla
	 */
	private void createTabHost() {
		try {
			Resources resources = getResources();
			final TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
			tabHost.setup();

			TabHost.TabSpec spec = tabHost.newTabSpec("Ligas");
			spec.setContent(R.id.tabLigas);
			spec.setIndicator("", resources.getDrawable(R.drawable.ic_ligas));
			tabHost.addTab(spec);
			spec = tabHost.newTabSpec("My teams");
			spec.setContent(R.id.tabMyTeams);
			spec.setIndicator("", resources.getDrawable(R.drawable.ic_tab_myteam));
			tabHost.addTab(spec);

			spec = tabHost.newTabSpec("Scores");
			spec.setContent(R.id.tabScores);
			spec.setIndicator("", resources.getDrawable(R.drawable.ic_tab_scores));
			tabHost.addTab(spec);

			spec = tabHost.newTabSpec("Noticias");
			spec.setContent(R.id.tabNoticias);
			spec.setIndicator("", resources.getDrawable(R.drawable.ic_tab_twitter));
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
			e.printStackTrace();
		}
	}
	
	/**
	 * PRIMER TAB (LIGAS)
	 * Carga los datos de la expandable list a partir de lo recibido por el servicio web
	 */
	public void createExpandableData() {
		//Cargo json del SD Card
		StringBuilder jsonLigas = AppUtils.getJsonFromDisk(getApplicationContext(), "ligas");
		StringBuilder jsonCategorias = AppUtils.getJsonFromDisk(getApplicationContext(), "cats");
		
		Type typeLiga = new TypeToken<List<Liga>>() {}.getType();
		List<Liga> ligas = JSONUtils.factoryGson().fromJson(jsonLigas.toString(), typeLiga);

		Type typeCategoria = new TypeToken<List<Categoria>>() {}.getType();
		List<Categoria> categorias = JSONUtils.factoryGson().fromJson(jsonCategorias.toString(), typeCategoria);
		int cont = 0;
		strLigas.clear();
		
		for (Liga l : ligas) {
			
			strLigas.add(l.getNombre());//Inicializar lista para usar luego en el tab Scores

			GroupLigas group = new GroupLigas(l.getNombre());
			for (Categoria c : categorias) {
				if (l.getNombre().equals(c.getLiga()))
					group.children.add(c.getNombre());
			}
			groups.append(cont, group);
			cont++;
		}
	}
	
	/**
	 * TERCER TAB (SCORES)
	 * Carga la informacion del tercer tab
	 */
	public void cargarTabScores() {
		try {
			StringBuilder jsonScores = AppUtils.getJsonFromDisk(getApplicationContext(), "scores");
			Type typeScores = new TypeToken<List<Scores>>() {}.getType();
			List<Scores> scores = JSONUtils.factoryGson().fromJson(jsonScores.toString(), typeScores);
			groupScores.clear();
			int cont = 0; 
			
			for(Scores sc : scores) {
				Calendario c = new Calendario();
				c = PartidoServicio.getPartidoById(getApplicationContext(), sc.getIdPartido());
				GroupScores g = new GroupScores();
				String strTemp = "";
				
				g.setNombreEquipo1(sc.getNomEquipo1());
				g.setNombreEquipo2(sc.getNomEquipo2());
				
				if(sc.getPeriodos().size() > 0) {
					
					for(String s : sc.getPeriodos())
						strTemp = strTemp + s + "\n";  
				}
				
				if(strTemp.length() > 0)
					g.setTeamsMatch(strTemp);
				else 
					g.setTeamsMatch("No hay puntajes para este partido");
				
				long dias = Fechas.diasEntreDosFecha(Fechas.fechahoy(), Fechas.string2Date(c.getFecha(), Fechas.DDMMYYYYGUION));
				//Solo muestro los partidos para hoy 
				if(dias >= -3 && dias <= 4){
					if(dias == 0 && Fechas.compareHours(c.getHora()) == 1) //PARTIDO DE HOY,
						g.setStatus("Hoy");
					else if(dias == 0 && Fechas.compareHours(c.getHora()) > 3)//PARTIDO DE HOY, Despues de 3 horas se da por finalizado el partido
						g.setStatus("Finalizado");
					else if(dias == 0 && Fechas.compareHours(c.getHora()) == 2)//PARTIDO DE HOY,
						g.setStatus("No ha iniciado");
					else if (dias < 0)//PARTIDO DE ANTES,
						g.setStatus("Finalizado");
					else
						g.setStatus("Sin informacion");
					
					cont++;
					groupScores.append(cont, g);
				} 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
