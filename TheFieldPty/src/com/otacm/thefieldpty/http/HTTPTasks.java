package com.otacm.thefieldpty.http;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class HTTPTasks {
	
	/**
	 * Este metodo busca en el contenido json en el servidor (ejecuta un http request)
	 * @param _url direccion web del servicio a consumir
	 * @return json con la trama devuelta por el servidor
	 */
	public static InputStream getJsonFromServer(String _url) {
		try {
			URL url = new URL(_url);
			URLConnection urlConnection = url.openConnection();
			return urlConnection.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
