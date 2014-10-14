package com.otacm.thefieldpty;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.otacm.thefieldpty.adapters.CalendarioArrayAdapter;
import com.otacm.thefieldpty.groups.GroupCalendario;
import com.otacm.thefieldpty.json.JSONUtils;
import com.otacm.thefieldpty.json.beans.Calendario;
import com.otacm.thefieldpty.json.beans.Grupo;
import com.otacm.thefieldpty.json.beans.Posiciones;
import com.otacm.thefieldpty.servicios.TablaPosicioneServicio;
import com.otacm.thefieldpty.utils.AppUtils;
import com.otacm.thefieldpty.utils.Reporter;
public class FirstTabChildActivity extends ActionBarActivity {

	private String parent;
	private String children;
	private List<GroupCalendario> groups = new ArrayList<GroupCalendario>();
	private ListView list_partidos;
	private Reporter log = Reporter.getInstance();
	private CalendarioArrayAdapter adapter;
	private List<Grupo> grupos = new ArrayList<Grupo>();
	private TableLayout tablaPosiciones;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_tab_child);
		parent                   = getIntent().getExtras().getString("parent");
		children                 = getIntent().getExtras().getString("child");
		list_partidos = (ListView) findViewById(R.id.list_partidos);
//		listTablaPosiciones = (ListView) findViewById(R.id.listTablaPosiciones);
		tablaPosiciones = (TableLayout) findViewById(R.id.tablaPosiciones);
		
		createTabHost();//Se levantan los tabs
		cargarDatosDeCalendario(); //Se cargan los datos del calendario para el tab
//		ExpandableCalendario expListAdapter = new ExpandableCalendario(this, groups);
		adapter = new CalendarioArrayAdapter(this, groups);
		list_partidos.setAdapter(adapter);
		
		//Cargar tabla de posiciones
		crearTablaPosiciones();
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
//					group.setMarcadorFinal(c.getMarcadorFinal());
					group.setPts1(c.getPts1().trim());
					group.setPts2(c.getPts2().trim());
					groups.add(group);
//					System.out.println(group.getDetallePartido());
				}else {
					//Toast.makeText(getApplicationContext(), getString(R.string.no_hay_calendario), Toast.LENGTH_LONG).show();
				}
//				cont++;
			}
		}catch(Exception e) {
//			log.error(Reporter.stringStackTrace(e));
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
//			log.error(Reporter.stringStackTrace(e));
		}
	}
	
	/**
	 * Este metodo debe crear una tabla de posiciones dinamica, dependiendo del contenido de los grupos
	 * extraidos 
	 */
	public void crearTablaPosiciones() {
		try {
			grupos = TablaPosicioneServicio.getGruposByCategoria(getApplicationContext(), parent, children);
			
			if(grupos == null) {
				Toast.makeText(getApplicationContext(), getString(R.string.tabla_no_dispon), Toast.LENGTH_SHORT).show();
			}else {
				for(Grupo g : grupos) {
					//Fila de titulo del grupo
					TableRow tr_grupo = new TableRow(this);
					tr_grupo.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
					TextView t1 = new TextView(this);
					t1.setPadding(3, 3, 0, 3); 
					t1.setTypeface(null, Typeface.BOLD);
					t1.setBackgroundResource(R.drawable.gray_gradient_yellow_stroke); 
					t1.setText(getString(R.string.title_grupo) + g.getNombreGrupo()); 
					t1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
					tr_grupo.addView(t1);
					tablaPosiciones.addView(tr_grupo);
					//Fila de titulo del grupo
					
					//Fila de headers del grupo
					TableRow tr_detalle_header = new TableRow(this);
					tr_detalle_header.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
					TextView h1 = new TextView(this);
					h1.setPadding(3, 2, 0, 2); 
					h1.setBackgroundResource(R.drawable.gray_gradient_yellow_stroke); 
					h1.setText(getString(R.string.title_equipo)); 
					h1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
					tr_detalle_header.addView(h1);
					TextView h2 = new TextView(this);
					h2.setPadding(3, 2, 0, 2); 
					h2.setBackgroundResource(R.drawable.gray_gradient_yellow_stroke);
					h2.setText(getString(R.string.pj)); 
					h2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
					tr_detalle_header.addView(h2);
					TextView h3 = new TextView(this);
					h3.setPadding(3, 2, 0, 2);
					h3.setBackgroundResource(R.drawable.gray_gradient_yellow_stroke);
					h3.setText(getString(R.string.pg)); 
					h3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
					tr_detalle_header.addView(h3);
					TextView h4 = new TextView(this);
					h4.setPadding(3, 2, 0, 2);
					h4.setBackgroundResource(R.drawable.gray_gradient_yellow_stroke);
					h4.setText(getString(R.string.pe)); 
					h4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
					tr_detalle_header.addView(h4);
					TextView h5 = new TextView(this);
					h5.setPadding(3, 2, 0, 2);
					h5.setBackgroundResource(R.drawable.gray_gradient_yellow_stroke);
					h5.setText(getString(R.string.pp)); 
					h5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
					tr_detalle_header.addView(h5);
					TextView h6 = new TextView(this);
					h6.setPadding(3, 2, 0, 2);
					h6.setBackgroundResource(R.drawable.gray_gradient_yellow_stroke);
					h6.setText(getString(R.string.pts)); 
					h6.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
					tr_detalle_header.addView(h6);
					tablaPosiciones.addView(tr_detalle_header, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
					//Fila de headers del grupo
					
					//Row para info de cada equipo
					for(Posiciones p : g.getPosiciones()) {
						TableRow tr_team = new TableRow(this);
						tr_team.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
						TextView teamName = new TextView(this);
						teamName.setPadding(3, 0, 0, 0);
						teamName.setText(p.getNombreEquipo()); 
						teamName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
						tr_team.addView(teamName);
						
						TextView pj = new TextView(this);
						pj.setPadding(3, 0, 0, 0);
						pj.setText(String.valueOf(p.getCantJuegos()));  
						pj.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
						tr_team.addView(pj);
						
						TextView pg = new TextView(this);
						pg.setPadding(3, 0, 0, 0);
						pg.setText(String.valueOf(p.getCantJG()));  
						pg.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
						tr_team.addView(pg);
						
						TextView pe = new TextView(this);
						pe.setPadding(3, 0, 0, 0);
						pe.setText(String.valueOf(p.getCantJE()));  
						pe.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
						tr_team.addView(pe);
						
						TextView pp = new TextView(this);
						pp.setPadding(3, 0, 0, 0);
						pp.setText(String.valueOf(p.getCantJP()));  
						pp.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
						tr_team.addView(pp);
						
						TextView pts = new TextView(this);
						pts.setPadding(3, 0, 0, 0);
						pts.setText(String.valueOf(p.getCantPts()));  
						pts.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
						tr_team.addView(pts);
						
						tablaPosiciones.addView(tr_team, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
					}
				}
			}
			
		}catch(Exception e) {
//			log.error(Reporter.stringStackTrace(e));
		}
	}
}
