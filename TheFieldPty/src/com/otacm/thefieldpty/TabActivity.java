package com.otacm.thefieldpty;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.reflect.TypeToken;
import com.otacm.thefieldpty.adapters.ExpandableListAdapter;
import com.otacm.thefieldpty.adapters.FavoritosAdapter;
import com.otacm.thefieldpty.adapters.TodayScoresAdapter;
import com.otacm.thefieldpty.adapters.TwitterAdapter;
import com.otacm.thefieldpty.database.beans.Favoritos;
import com.otacm.thefieldpty.database.daos.FavoritosDAO;
import com.otacm.thefieldpty.groups.GroupLigas;
import com.otacm.thefieldpty.json.JSONUtils;
import com.otacm.thefieldpty.json.beans.Categoria;
import com.otacm.thefieldpty.json.beans.Liga;
import com.otacm.thefieldpty.json.beans.TodayScores;
import com.otacm.thefieldpty.remainders.RemainderBroadcastReceiver;
import com.otacm.thefieldpty.utils.AppUtils;
import com.otacm.thefieldpty.utils.Reporter;
import com.otacm.thefieldpty.utils.TwitterConfiguration;

public class TabActivity extends ActionBarActivity {
	private ExpandableListView expandable_list_ligas;
	private ListView listScores;
	private Context ctx = this;
	private SparseArray<GroupLigas> groups = new SparseArray<GroupLigas>();
	private List<String> strLigas = new ArrayList<String>();
	private static final int SELECT_TEAMS = 1;
	private ListView equipos_favoritos;
	private List<Favoritos> favoritos;
	private ArrayAdapter<Favoritos> arrayAdapter;
	private Reporter log = Reporter.getInstance();
	private FavoritosDAO dao;
	private TextView userName;
	private TwitterAdapter twitterAdapter;
	private ListView listTwitter;
	private RemainderBroadcastReceiver alarm;
	private String UPDATE_SERVER_TIME = "";
	private String PRE_ALARM_TIME = "";
	private boolean IS_NOTIFICATION_ACTIVATED = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		
		expandable_list_ligas = (ExpandableListView) findViewById(R.id.expandable_list_ligas);
		listScores  = (ListView) findViewById(R.id.listScores);
		equipos_favoritos     = (ListView) findViewById(R.id.equipos_favoritos);
		userName = (TextView) findViewById(R.id.userName);
		listTwitter = (ListView) findViewById(R.id.listTwitter);
		
		//Configuracion de Notificaciones
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		IS_NOTIFICATION_ACTIVATED = sharedPrefs.getBoolean("prefSendReport", false);
		
		UPDATE_SERVER_TIME = sharedPrefs.getString("prefSyncFrequency", "30");
		setUpServerRequests(Integer.valueOf(UPDATE_SERVER_TIME)); 
		
		if(IS_NOTIFICATION_ACTIVATED) {
			PRE_ALARM_TIME = sharedPrefs.getString("prefAlarmFrequency", "0");
			Toast.makeText(getApplicationContext(), getString(R.string.alarm_on), Toast.LENGTH_SHORT).show();
		}else
			Toast.makeText(getApplicationContext(), getString(R.string.alarm_off), Toast.LENGTH_SHORT).show();
		
		dao = new FavoritosDAO(getApplicationContext());
		
		equipos_favoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
            	Favoritos team = arrayAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putInt("team", team.getId());
            }
        });
		
		createTabHost();
		
		createExpandableData();
		final ExpandableListAdapter expListAdapter = new ExpandableListAdapter((Activity) ctx, groups);
        expandable_list_ligas.setAdapter(expListAdapter);
        
        cargarTabFavoritos();
		cargarTabNoticias();
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
			Intent i = new Intent(this, AppSettings.class);
//	        startActivityForResult(i, RESULT_SETTINGS);
			startActivity(i); 
			return true;
		}
		
		switch (item.getItemId()) {
		case android.R.id.home:
			exitApp();
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
					System.out.println("TAB ID : " + tabId);
					
					if (tabId.equals("Scores"))
						cargarTabScores();
					else if (tabId.equals("Noticias")) 
						cargarTabNoticias();
					else if(tabId.equals("My teams"))
						cargarTabFavoritos();
				}
			});

			tabHost.setCurrentTab(0);
		} catch (Exception e) {
			log.error(Reporter.stringStackTrace(e));
		}
	}
	
	/**
	 * Evento para desplegar la pantalla de seleccion de favoritos
	 * @param v
	 */
	public void mostrarEquiposDisponibles(View v){
		Intent i = new Intent(TabActivity.this, SelectFavsActivity.class);
		startActivityForResult(i, SELECT_TEAMS);
	}
	
	public void borrarFavoritos(View v) {
		try {
			boolean somethingToDelete = false;
			String selectedIds = "";
			
			if(arrayAdapter == null || arrayAdapter.getCount() < 0){
				Toast.makeText(getApplicationContext(), getString(R.string.no_favs_del), Toast.LENGTH_SHORT).show();
			}else {
				for (int i = 0; i < arrayAdapter.getCount(); i++) {
		            Favoritos f = arrayAdapter.getItem(i);
			            
		            if (f.isSelected()) {
		            	somethingToDelete = true;
		            	selectedIds += f.getId() + ",";
		            }
		        }
					
				if(somethingToDelete) {
					selectedIds = selectedIds.substring(0, selectedIds.length() - 1);	
					dao.deleteByIds(selectedIds);
					Toast.makeText(getApplicationContext(), getString(R.string.favs_deleted), Toast.LENGTH_SHORT).show();
					cargarTabFavoritos();
				}else
					Toast.makeText(getApplicationContext(), getString(R.string.no_favs_del), Toast.LENGTH_SHORT).show();
			
			}
		}catch(Exception e) {
			log.write(Reporter.stringStackTrace(e));
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
	 * CARGA EL SEGUNDO TAB (MY TEAMS)
	 */
	public void cargarTabFavoritos() {
		FavoritosDAO dao = new FavoritosDAO(getApplicationContext());
		List<Favoritos> l = dao.getAll();
		
		favoritos = l; 
		
		if(l.size() == 0) {
			equipos_favoritos.setAdapter(null); 
		}else {
			arrayAdapter = new FavoritosAdapter(this, favoritos);
			equipos_favoritos.setAdapter(arrayAdapter);
		}
	}
	
	/**
	 * TERCER TAB (SCORES)
	 * Carga la informacion del tercer tab
	 */
	public void cargarTabScores() {
		try {
			StringBuilder jsonScores = AppUtils.getJsonFromDisk(getApplicationContext(), "today_scores");
			Type typeScores = new TypeToken<List<TodayScores>>() {}.getType();
			List<TodayScores> scores = JSONUtils.factoryGson().fromJson(jsonScores.toString(), typeScores);
			final TodayScoresAdapter expSc = new TodayScoresAdapter(getApplicationContext(), scores);
			
			if(scores.size() == 0) {
				listScores.setAdapter(null);
				Toast.makeText(getApplicationContext(), "No hay partidos el dia de hoy", Toast.LENGTH_SHORT).show();
			} else{
				listScores.setAdapter(expSc);
			}
			
		} catch (Exception e) {
			log.error(Reporter.stringStackTrace(e));
		}
	}
	
	/**
	 * CARGA EL CUARTO TAB (NOTICIAS)
	 */
	@SuppressLint("SetJavaScriptEnabled")
	public void cargarTabNoticias() {
		try {			
			new GetTwitterStatus().execute();
		} catch (Exception e) {
			log.write(Reporter.stringStackTrace(e));
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("requestCode " + requestCode);
		System.out.println("resultCode  "  + resultCode);
		if(requestCode == SELECT_TEAMS) {
			cargarTabFavoritos();
		}
	}
	
	private class GetTwitterStatus extends AsyncTask<Void, Void, Boolean> {

		List<twitter4j.Status> twitts = new ArrayList<twitter4j.Status>();
		String username = "";
		String imageProfileUrl = "";
		Bitmap bmp = null;
		int followers = 0;
		
		public Boolean hacerTrabajoSucio() {
			try {
				TwitterConfiguration tc = new TwitterConfiguration();
				twitts = tc.getInterface().getUserTimeline();
				
				if(twitts.size() > 0) {
					imageProfileUrl = twitts.get(0).getUser().getOriginalProfileImageURL();
					followers = twitts.get(0).getUser().getFollowersCount();
				}
				
				URL url = new URL(imageProfileUrl);
				bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
				username = tc.getUserName();
				return true;
			} catch (Exception e) {
				log.write(Reporter.stringStackTrace(e));
				return false;
			}
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			return hacerTrabajoSucio();
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			
			if(result) {
				userName.setText("@" + username + " " + followers + " Followers"); 
				twitterAdapter = new TwitterAdapter(getApplicationContext(), twitts, bmp);
				listTwitter.setAdapter(twitterAdapter); 
			}else
				Toast.makeText(getApplicationContext(), "No puedo acceder a Twitter", Toast.LENGTH_LONG).show();
		}
	}
	
	private void setUpServerRequests(int refreshTime) {
		try {
			System.out.println("ACTUALIZAR DATOS CADA " + refreshTime + " MINUTOS");
			alarm = new RemainderBroadcastReceiver();
			
			if (alarm != null)
				alarm.setAlarm(ctx, refreshTime);
			else
				Toast.makeText(ctx, "No pudo iniciarse servicio de actualizacion", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			log.write(Reporter.stringStackTrace(e));
		}
	}
	
	public void exitApp() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					finish();
					System.exit(0);
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle("Salir");
		builder.setMessage("¿Realmente quiere salir?")
				.setPositiveButton("Sí", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();
	}
	
	@Override
	public void onBackPressed() {
		exitApp();
	}
}

