package com.otacm.thefieldpty.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Vibrator;

public class AppUtils {

	private static final Reporter log = Reporter.getInstance();
	
	public static void writeJsonOnDisk(Context context, String fileName,
			StringBuilder bigStr) {
		try {
			FileWriter Filewriter = new FileWriter(
					context.getApplicationInfo().dataDir + "/" + fileName
							+ ".json");
			Filewriter.write(bigStr.toString());
			Filewriter.close();
		} catch (Exception e) {
			log.error(Reporter.stringStackTrace(e));
			e.printStackTrace();
		}
	}

	public static StringBuilder getJsonFromDisk(Context context, String jsonFile) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					context.getApplicationInfo().dataDir + "/" + jsonFile
							+ ".json"));
			JsonElement json = new JsonParser().parse(br);
			StringBuilder data = new StringBuilder(json.getAsJsonArray()
					.toString());
			return data;
		} catch (Exception e) {
			log.error(Reporter.stringStackTrace(e));
			return null;
		}
	}
	
	/**
	 * Necesito encontrar las imagenes de los logos de los equipos
	 * por su nombre para colocarlos en la pantalla, para eso uso esta funcion
	 * @param ctx
	 * @param str
	 * @return id del resource, cero (0) si no existe
	 */
	public static int getDrawableByName(Context ctx, String str) {
		String name = "ic_" + str.replace(".", "_");
//		String name_aux = name.replace("-", "_");
//		System.out.println("Buscando drawable llamado : " + name_aux);
		return ctx.getResources().getIdentifier(name, "drawable", ctx.getPackageName());
	}
	
	public static Typeface normalFont(Context ctx) {
		return Typeface.createFromAsset(ctx.getAssets(), "ParmaPetit-Normal.ttf");
	}
	
	public static Typeface outlineFont(Context ctx) {
		return Typeface.createFromAsset(ctx.getAssets(), "ParmaPetitOutline.ttf");
	}
	
	public static void vibrar(Context context, int millisecs) {
		Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		 v.vibrate(millisecs);
	}
}