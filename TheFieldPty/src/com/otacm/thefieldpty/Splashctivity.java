package com.otacm.thefieldpty;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.otacm.thefieldpty.http.HTTPTasks;
import com.otacm.thefieldpty.utils.AppUtils;
import com.otacm.thefieldpty.utils.Reporter;

public class Splashctivity extends Activity {
	private static final int SEGUNDOS = 1000;
	private ProgressBar progressBar;
	private Reporter log = Reporter.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splashctivity);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		
		Handler handler = new Handler();
		handler.postDelayed(initApp(), SEGUNDOS);

	}

	private Runnable initApp() {
		Runnable res = new Runnable() {
			public void run() {
				//Se llama al servicio web al iniciar la actividad
				new ConsumirServicio().execute();
			}
		};
		
		return res;
	}
	
	public class ConsumirServicio extends AsyncTask<Void, Void, String> {

		ProgressDialog progressDialog;
		long inicio;
		long termino;
		ArrayList<InputStream> streams = new ArrayList<InputStream>();

		@SuppressWarnings("resource")
		protected boolean obtenerDataDeServidor() {
			try{
				Resources res = getResources();
				String[] webservices = res.getStringArray(R.array.servicios_web);
				
//				log.write("Inciando descarga de datos");
				
				for(int i = 0; i < webservices.length; i++) {
					InputStream in =  HTTPTasks.getJsonFromServer(getString(R.string.host_webservices) + webservices[i]);
					if(in == null)
						return false;
					else
						streams.add(in);
				}
				
//				log.write("Termina descarga de datos");
				
				String ligasData = new Scanner(streams.get(0)).useDelimiter("\\A").next(); 
				AppUtils.writeJsonOnDisk(getApplicationContext(), "ligas", new StringBuilder(ligasData));
				ligasData = null;
				
				String categoriasData = new Scanner(streams.get(1)).useDelimiter("\\A").next(); 
				AppUtils.writeJsonOnDisk(getApplicationContext(), "cats", new StringBuilder(categoriasData));
				categoriasData = null;
				
				//El tercer item de json lo escribo en disco para usarlo en otras actividades
				String calendarioData = new Scanner(streams.get(2)).useDelimiter("\\A").next(); 
				AppUtils.writeJsonOnDisk(getApplicationContext(), "calendario", new StringBuilder(calendarioData));
				calendarioData = null;
				
				//Escribo en disco tambien el json de los scores
				String scoresData = new Scanner(streams.get(3)).useDelimiter("\\A").next(); 
				AppUtils.writeJsonOnDisk(getApplicationContext(), "scores", new StringBuilder(scoresData));
				scoresData = null;
				
				//Escribo en disco tambien el json de los equipos
				String equiposData = new Scanner(streams.get(4)).useDelimiter("\\A").next(); 
				AppUtils.writeJsonOnDisk(getApplicationContext(), "equipos", new StringBuilder(equiposData));
				equiposData = null;
				
				//Escribo en disco tambien el json de los scores
				String todayScores = new Scanner(streams.get(5)).useDelimiter("\\A").next(); 
				AppUtils.writeJsonOnDisk(getApplicationContext(), "today_scores", new StringBuilder(todayScores));
				todayScores = null;
				
				String tablaPosiciones = new Scanner(streams.get(6)).useDelimiter("\\A").next(); 
				AppUtils.writeJsonOnDisk(getApplicationContext(), "posiciones", new StringBuilder(tablaPosiciones));
				tablaPosiciones = null;
				
				return true;
			}catch(Exception e){
				if(e instanceof java.util.NoSuchElementException) {
					return true;
				}
				e.printStackTrace();
//				log.error(Reporter.stringStackTrace(e));
				return false;
			}
		}
		
		protected void onPreExecute() {
			super.onPreExecute();
			inicio = SystemClock.elapsedRealtime();
			progressBar.setVisibility(View.VISIBLE);
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
			progressBar.setVisibility(View.GONE);
			if(result.equals("success")) {//Llamo a mi pantalla inicial al terminar
				Intent intent = new Intent(Splashctivity.this, TabActivity.class);
				startActivity(intent);
				finish();
				Toast.makeText(getApplicationContext(), String.format(getString(R.string.data_actualizada), (termino - inicio)), Toast.LENGTH_LONG).show();
			}else {
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
				finish();
			}
		}
	}
}