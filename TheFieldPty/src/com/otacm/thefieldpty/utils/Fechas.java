package com.otacm.thefieldpty.utils;

import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SuppressLint("SimpleDateFormat")
public class Fechas {
	public static final String DDMMYYYYSLASH = "dd/MM/yyyy";
	public static final String DDMMYYYY = "ddMMyyyy";
	public static final String DDMMYYYYGUION = "dd-MM-yyyy";
	public static final String YYYYMMDDGUION = "yyyy-MM-dd";
	public static final String MMDDYYYYSLASH = "MM/dd/yyyy";
	public static final String YYYYMMDDHORAGUION = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYYMMDDHORASLASH = "yyyy/MM/dd HH:mm:ss";
	public static final String YYYYMMDDHORA = "yyyyMMdd HH:mm:ss";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static SimpleDateFormat formatter = new SimpleDateFormat();
	/**
	 * Convierte un java.util.Date a un java.sql.Date
	 * 
	 * @return java.sql.Date
	 */
	public static java.sql.Date utilDateToSQLDate(java.util.Date d) {
		java.sql.Date sqlDate = new java.sql.Date(d.getTime());
		return sqlDate;
	}

	/**
	 * Retorna la fecha de hoy en java.sql.Date
	 * 
	 * @return java.sql.Date now
	 */
	public static java.sql.Date fechahoy() {
		java.util.Date now = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(now.getTime());
		return sqlDate;
	}

	/**
	 * Retorna la cantidad de dias que hay entre 2 fechas
	 * 
	 * @param f1
	 * @param f2
	 * @return Dias entre la fechas
	 */
	public static long diasEntreDosFecha(Date f1, Date f2) {
		long fechaInicialMs = f1.getTime();
		long fechaFinalMs = f2.getTime();
		long diferencia = fechaFinalMs - fechaInicialMs;
		double dias = Math.floor(diferencia / 86400000);
		System.out.println("Dias entre las fechas : " + dias);
		return (long) dias;
	}

	/**
	 * Retorna el resultado de sumar la cantidad de dias enviadas a la fecha
	 * 
	 * @param fecha
	 *            Fecha a la que se le quiere sumar los dias
	 * @param dias
	 *            cantidad de dias a sumar a la fecha
	 * @return nueva fecha con dias sumados
	 */
	public static Date sumarDiasFecha(Date fecha, int dias) {
		Date nuevaFecha;
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(fecha.getTime());
		cal.add(Calendar.DATE, dias);
		nuevaFecha = new Date(cal.getTimeInMillis());
		return nuevaFecha;
	}

	/**
	 * Retorna la fecha con las catidad de dias restados
	 * 
	 * @param fecha
	 *            fecha a restar dias
	 * @param dias
	 *            cantidad de dias a restar
	 * @return fecha con los dias restados
	 */
	public static Date restarDiasFecha(Date fecha, int dias) {
		Date nuevaFecha;
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(fecha.getTime());
		cal.add(Calendar.DATE, -dias);
		nuevaFecha = new Date(cal.getTimeInMillis());
		return nuevaFecha;
	}
	
	public static Date string2Date(String str, String format){
		try {
			formatter.applyPattern(format);
			Date date = formatter.parse(str);
			System.out.println("Converted Date: " + date);
			System.out.println(formatter.format(date));
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	/**
	 * Compara las horas dada como argumento contra la del sistema(del mismo dia)
	 * @param str hora para comparar contra la del sistema
	 * @return 1 cuando la hora dada como argumento ha pasado o esta en transcurso
	 *         2 cuando la hora dada como argumento aun no ha llegado
	 */
	public static int compareHours(String str) {
		try {
			int answer = 0;
			SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
			Date systemTime = parser.parse(nowTime());
			Date inputTime = parser.parse(str);

			if (systemTime.after(inputTime))//Esta pasando o ya paso
				answer = 1;
			else if(systemTime.before(inputTime))//No ha iniciado aun
				answer =  2;
			
			return answer;
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * Retorna solo la hora y minutos de la fecha actual
	 * @return horas/minutos
	 */
	@SuppressLint("SimpleDateFormat")
	public static String nowTime() {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date today = Calendar.getInstance().getTime(); 
		return df.format(today);
	}
	
	/**
	 * Retorna la diferencia de horas entre 2 fechas
	 * @param date1 fecha
	 * @param date2
	 * @return
	 * @throws ParseException 
	 */
	public static int hoursDifference(String str) throws ParseException { 
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
		Date systemTime = parser.parse(nowTime());
		Date inputTime = parser.parse(str);
	    final int MILLI_TO_HOUR = 1000 * 60 * 60;
	    return (int) (systemTime.getTime() - inputTime.getTime()) / MILLI_TO_HOUR;
	}
}