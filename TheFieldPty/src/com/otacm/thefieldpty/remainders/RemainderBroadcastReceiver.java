package com.otacm.thefieldpty.remainders;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.otacm.thefieldpty.R;
import com.otacm.thefieldpty.http.HTTPTasks;
import com.otacm.thefieldpty.utils.AppUtils;
import com.otacm.thefieldpty.utils.Fechas;
import com.otacm.thefieldpty.utils.Reporter;

public class RemainderBroadcastReceiver extends BroadcastReceiver {
	
	private Reporter log = Reporter.getInstance();
	@SuppressLint("SimpleDateFormat")
	@Override
	public void onReceive(final Context context, Intent intent) {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		if(sharedPrefs.getBoolean("prefVibrate", false))
			AppUtils.vibrar(context, 500);
		
		final Runnable request = new Runnable() {
			
			@Override
			public void run() {
				if(consultingData(context)) {
					System.out.println("Research OK :" + Fechas.fechahoy(Fechas.DDMMYYYYHORAGUION));
//					log.write("Research OK :" + Fechas.fechahoy(Fechas.DDMMYYYYHORAGUION));
				}
				else{
					System.out.println("Research ERROR : " + Fechas.fechahoy(Fechas.DDMMYYYYHORAGUION));
//					log.write("Research ERROR : " + Fechas.fechahoy(Fechas.DDMMYYYYHORAGUION));
				}	
			}
		};
		
		Thread t = new Thread(request);
		t.start();
	}

	public void setAlarm(Context context, long timeRepeating) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, RemainderBroadcastReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * timeRepeating * 60, pi);
//		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 20, pi);
	}
	
	public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, RemainderBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
	
//	protected void displayNotifications(String sticker, String contextText) {
//	      NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(context); 
//	  
//	      mBuilder.setContentTitle("Juegos para hoy");
//	      mBuilder.setContentText(contextText);
//	      mBuilder.setTicker(sticker);
//	      mBuilder.setSmallIcon(R.drawable.ic_launcher);
//	      mBuilder.setNumber(++numMessages);
//	      mBuilder.setAutoCancel(true);
//	      
//	      Intent resultIntent = new Intent(context, TabActivity.class);
//	      resultIntent.putExtra("notificationId", notificationId);
//	 
//	      TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//	 
//	      stackBuilder.addParentStack(TabActivity.class);
//	      stackBuilder.addNextIntent(resultIntent);
//	      PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
//	      mBuilder.setContentIntent(resultPendingIntent);
//	 
//	      myNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//	      myNotificationManager.notify(notificationId, mBuilder.build());     
//	   }
	
	@SuppressWarnings("resource")
	private boolean consultingData(Context context) {
		try {
			ArrayList<InputStream> streams = new ArrayList<InputStream>();
			Resources res = context.getResources();
			String[] webservices = res.getStringArray(R.array.servicios_web);
			for(int i = 0; i < webservices.length; i++) {
				InputStream in =  HTTPTasks.getJsonFromServer(context.getString(R.string.host_webservices) + webservices[i]);
				if(in == null)
					return false;
				else
					streams.add(in);
			}
			
			String ligasData = new Scanner(streams.get(0)).useDelimiter("\\A").next(); 
			AppUtils.writeJsonOnDisk(context, "ligas", new StringBuilder(ligasData));
			ligasData = null;
			
			String categoriasData = new Scanner(streams.get(1)).useDelimiter("\\A").next(); 
			AppUtils.writeJsonOnDisk(context, "cats", new StringBuilder(categoriasData));
			categoriasData = null;
			
			//El tercer item de json lo escribo en disco para usarlo en otras actividades
			String calendarioData = new Scanner(streams.get(2)).useDelimiter("\\A").next(); 
			AppUtils.writeJsonOnDisk(context, "calendario", new StringBuilder(calendarioData));
			calendarioData = null;
			
			//Escribo en disco tambien el json de los scores
			String scoresData = new Scanner(streams.get(3)).useDelimiter("\\A").next(); 
			AppUtils.writeJsonOnDisk(context, "scores", new StringBuilder(scoresData));
			scoresData = null;
			
			//Escribo en disco tambien el json de los equipos
			String equiposData = new Scanner(streams.get(4)).useDelimiter("\\A").next(); 
			AppUtils.writeJsonOnDisk(context, "equipos", new StringBuilder(equiposData));
			equiposData = null;
			
			//Escribo en disco tambien el json de los scores
			String todayScores = new Scanner(streams.get(5)).useDelimiter("\\A").next(); 
			AppUtils.writeJsonOnDisk(context, "today_scores", new StringBuilder(todayScores));
			todayScores = null;
			
			String tablaPosiciones = new Scanner(streams.get(6)).useDelimiter("\\A").next(); 
			AppUtils.writeJsonOnDisk(context, "posiciones", new StringBuilder(tablaPosiciones));
			tablaPosiciones = null;
			
			return true;
		}catch(Exception e) {
			
			if(e instanceof java.util.NoSuchElementException) {
//				log.write(Reporter.stringStackTrace(e));
				return true;
			}
			
//			log.write(Reporter.stringStackTrace(e));
			return false;
		}
	}
}
