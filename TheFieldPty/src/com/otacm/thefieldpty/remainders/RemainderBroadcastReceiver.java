package com.otacm.thefieldpty.remainders;

import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import com.example.sample.R;
import com.otacm.thefieldpty.TabActivity;
import com.otacm.thefieldpty.database.beans.Remainder;
import com.otacm.thefieldpty.database.daos.RemaindersDAO;
import com.otacm.thefieldpty.utils.AppUtils;
import com.otacm.thefieldpty.utils.Fechas;

public class RemainderBroadcastReceiver extends BroadcastReceiver {

	private RemaindersDAO rdao;
	private List<Remainder> actives;
	private NotificationManager myNotificationManager;
	private Context context;
	private int notificationId = 111;
	private int numMessages = 0;
	
	@SuppressLint("SimpleDateFormat")
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		StringBuilder notificatioMsg = new StringBuilder();
		rdao = new RemaindersDAO(context);
		actives = rdao.getActiveRemainders();
		
		for(Remainder r : actives) {
			Date remainderDate = Fechas.string2Date(r.getDate(), Fechas.DDMMYYYYHORAGUION);
			Date nowDate = new Date();
			
			if(Fechas.diasEntreDosFecha(nowDate, remainderDate) == 0) {
				notificatioMsg.append(r.getTitle()).append("\n");
				String remainderTime = r.getDate().split(" ")[1];
				
				if(Fechas.compareHours(remainderTime) == 2) {
					AppUtils.vibrar(context, 500); 
					displayNotifications(r.getTitle() + " " + remainderTime, r.getTitle() + "\n" + r.getDescription() + " " + remainderTime);
				}
			}
		}
	}

	public void setAlarm(Context context) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, RemainderBroadcastReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 30, pi);
	}
	
	public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, RemainderBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
	
	protected void displayNotifications(String sticker, String contextText) {
	      NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(context); 
	  
	      mBuilder.setContentTitle("Juegos para hoy");
	      mBuilder.setContentText(contextText);
	      mBuilder.setTicker(sticker);
	      mBuilder.setSmallIcon(R.drawable.ic_launcher);
	      mBuilder.setNumber(++numMessages);
	      mBuilder.setAutoCancel(true);
	      
	      Intent resultIntent = new Intent(context, TabActivity.class);
	      resultIntent.putExtra("notificationId", notificationId);
	 
	      TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
	 
	      stackBuilder.addParentStack(TabActivity.class);
	      stackBuilder.addNextIntent(resultIntent);
	      PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
	      mBuilder.setContentIntent(resultPendingIntent);
	 
	      myNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	      myNotificationManager.notify(notificationId, mBuilder.build());     
	   }
}
