package com.otacm.thefieldpty;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.example.sample.R;
import com.google.gson.reflect.TypeToken;
import com.otacm.thefieldpty.adapters.ExpandableListAdapter;
import com.otacm.thefieldpty.adapters.ExpandableScores;
import com.otacm.thefieldpty.groups.GroupLigas;
import com.otacm.thefieldpty.groups.GroupScores;
import com.otacm.thefieldpty.http.HTTPTasks;
import com.otacm.thefieldpty.json.JSONUtils;
import com.otacm.thefieldpty.json.beans.Categoria;
import com.otacm.thefieldpty.json.beans.Liga;
import com.otacm.thefieldpty.json.beans.Scores;
import com.otacm.thefieldpty.utils.AppUtils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

public class TabActivity extends ActionBarActivity {
	private ExpandableListView expandable_list_ligas;
	private ExpandableListView expandableListScores;
	private StringBuilder jsonLigas;
	private StringBuilder jsonCategorias;
	private Context ctx = this;
	private SparseArray<GroupLigas> groups = new SparseArray<GroupLigas>();
	private SparseArray<GroupScores> groupScores = new SparseArray<GroupScores>();
	private List<String> strLigas = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		expandable_list_ligas = (ExpandableListView) findViewById(R.id.expandable_list_ligas);
		expandableListScores = (ExpandableListView) findViewById(R.id.expandableListScores);
		createTabHost();
		
		//Se llama al servicio web al iniciar la actividad
		new ConsumirServicio().execute();
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
	 * ASYNC TASK
	 */
	
	private class ConsumirServicio extends AsyncTask<Void, Void, String> {

		ProgressDialog progressDialog;
		long inicio;
		long termino;
		ArrayList<InputStream> streams = new ArrayList<InputStream>();

		@SuppressWarnings("resource")
		protected boolean obtenerDataDeServidor() {
			try{
				Resources res = getResources();
				String[] webservices = res.getStringArray(R.array.servicios_web);
				
				for(int i = 0; i < webservices.length; i++) {
					InputStream in =  HTTPTasks.getJsonFromServer(getString(R.string.host_webservices) + webservices[i]);
					if(in == null)
						return false;
					else
						streams.add(in);
				}
				
				String ligasData = new Scanner(streams.get(0)).useDelimiter("\\A").next(); 
				jsonLigas = new StringBuilder(ligasData);
				ligasData = null;
				
				String categoriasData = new Scanner(streams.get(1)).useDelimiter("\\A").next(); 
				jsonCategorias = new StringBuilder(categoriasData);
				categoriasData = null;
				
				//El tercer item de json lo escribo en disco para usarlo en otras actividades
				String calendarioData = new Scanner(streams.get(2)).useDelimiter("\\A").next(); 
				AppUtils.writeJsonOnDisk(getApplicationContext(), "calendario", new StringBuilder(calendarioData));
				calendarioData = null;
				
				//Escribo en disco tambien el json de los scores
				String scoresData = new Scanner(streams.get(3)).useDelimiter("\\A").next(); 
				AppUtils.writeJsonOnDisk(getApplicationContext(), "scores", new StringBuilder(scoresData));
				scoresData = null;
				
				return true;
			}catch(Exception e){
				progressDialog.dismiss();
				e.printStackTrace();
				return false;
			}
		}
		
		protected void onPreExecute() {
			super.onPreExecute();
			inicio = SystemClock.elapsedRealtime();
			progressDialog = new ProgressDialog(TabActivity.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setCancelable(false);
			progressDialog.setProgress(1);
			progressDialog.setMax(100);
			progressDialog.setMessage("Buscando en el servidor ...");
			progressDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			
			if(!obtenerDataDeServidor())
				return getString(R.string.error_http_request); 
			
			return "success";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			termino = SystemClock.elapsedRealtime();
			progressDialog.dismiss();
			
			if(result.equals("success")) {
				
				createExpandableData();
				final ExpandableListAdapter expListAdapter = new ExpandableListAdapter((Activity) ctx, groups);
				expandable_list_ligas.setAdapter(expListAdapter);
				
				//Se cargan los datos para el listView de los Scores
				cargarTabScores();
				final ExpandableScores expSc = new ExpandableScores((Activity) ctx, groupScores);
				expandableListScores.setAdapter(expSc); 
				
				Toast.makeText(getApplicationContext(), String.format(getString(R.string.data_actualizada), (termino - inicio)), Toast.LENGTH_LONG).show();
			}else {
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
			}
		}
	}
	
	/**
	 * PRIMER TAB (LIGAS)
	 * Carga los datos de la expandable list a partir de lo recibido por el servicio web
	 */
	public void createExpandableData() {
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
				
				GroupScores g = new GroupScores();
				String strTemp = "";
				
				g.setLabelNameLiga(sc.getLiga() + " : " + sc.getNomEquipo1() + " vs " + sc.getNomEquipo2());
				
				if(sc.getPeriodos().size() > 0) {
					
					for(String s : sc.getPeriodos())
						strTemp = strTemp + s + "\n";  
				}
				
				if(strTemp.length() > 0)
					g.setTeamsMatch(strTemp);
				else 
					g.setTeamsMatch("No hay puntajes para este partido");
				
				cont++;
				groupScores.append(cont, g); 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
