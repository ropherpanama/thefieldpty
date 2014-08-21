package com.otacm.thefieldpty.database.daos;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.otacm.thefieldpty.database.DataBaseHelper;
import com.otacm.thefieldpty.database.DataSource;
import com.otacm.thefieldpty.database.beans.Favoritos;
import com.otacm.thefieldpty.utils.Reporter;

public class FavoritosDAO extends DataSource{
	private Reporter log = Reporter.getInstance();
	public String [] allColumns = {
			DataBaseHelper.COLUMN_TABLE_FAVORITOS_ID,
			DataBaseHelper.COLUMN_TABLE_FAVORITOS_NOMBRE
	};
	
	public FavoritosDAO(Context context) {
		super(context);
	}
	
	public int insertFavorito(Favoritos f) {
		try {
			open();
			ContentValues v = new ContentValues();
			v.put(DataBaseHelper.COLUMN_TABLE_FAVORITOS_NOMBRE, f.getNombre());
			getDatabase().insert(DataBaseHelper.TABLE_FAVORITOS_NAME, null, v);
			close();
			System.out.println("Favorito guardado");
			return 1;
		}catch (Exception e) {
			log.error(Reporter.stringStackTrace(e));
			close();
			return -1;
		}
	}
	
	public int deleteFavorito(int id) {
		try {
			open();
			getDatabase().delete(DataBaseHelper.TABLE_FAVORITOS_NAME, DataBaseHelper.COLUMN_TABLE_FAVORITOS_ID + " = " + id, null);
			close();
			return 1;
		}catch(Exception e) {
			log.error(Reporter.stringStackTrace(e));
			return -1;
		}
	}
	
	public List<Favoritos> getAll() {
		try {
			open();
			List<Favoritos> l = new ArrayList<Favoritos>();
			Cursor c = getDatabase().query(DataBaseHelper.TABLE_FAVORITOS_NAME, allColumns, null, null, null, null, DataBaseHelper.COLUMN_TABLE_FAVORITOS_ID + " desc");
			c.moveToFirst();
			
			while(!c.isAfterLast()) {
				Favoritos f = cursorToFavorito(c);
				System.out.println("Favorito : " + f.getNombre());
				l.add(f);
				c.moveToNext();
			}
			c.close();
			close();
			return l;
		}catch(Exception e) {
			log.error(Reporter.stringStackTrace(e));
			close();
			return null;
		}
	}
	
	private Favoritos cursorToFavorito(Cursor c) {
		Favoritos f = new Favoritos();
		f.setId(c.getInt(0));
		f.setNombre(c.getString(1));
		return f;
	}
	
	public boolean deleteByIds(String ids) {
		try {
			open();
			getDatabase().delete(DataBaseHelper.TABLE_FAVORITOS_NAME, DataBaseHelper.COLUMN_TABLE_FAVORITOS_ID + " in (" + ids + ")", null);
			close();
			return true;
		}catch(Exception e) {
			log.error(Reporter.stringStackTrace(e));
			return false;
		}
	}
}
