package com.otacm.thefieldpty.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.content.Context;

public class AppUtils {

	public static void writeJsonOnDisk(Context context, String fileName,
			StringBuilder bigStr) {
		try {
			FileWriter Filewriter = new FileWriter(
					context.getApplicationInfo().dataDir + "/" + fileName
							+ ".json");
			Filewriter.write(bigStr.toString());
			Filewriter.close();
		} catch (Exception e) {
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
			e.printStackTrace();
			return null;
		}
	}
}