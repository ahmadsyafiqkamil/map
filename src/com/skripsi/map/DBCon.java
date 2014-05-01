package com.skripsi.map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBCon extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "map";

	public DBCon(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if (db == null || !db.isOpen()) {
			db = getWritableDatabase();
		}
		String tabel = "";
		tabel = "CREATE TABLE JADWAL(Hari varchar(10),JamPerkuliahan varchar(25),KdMataKuliah varchar(20),NamaMatakuliah varchar(50),NamaKelas varchar(10),NamaRuang varchar(20),Gedung varchar(50));\n";
		db.execSQL(tabel);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (db == null || !db.isOpen()) {
			db = getWritableDatabase();
		}
		String tabel = "";
		tabel = "DROP TABLE IF EXISTS JADWAL;";
		db.execSQL(tabel);

		onCreate(db);
	}
	
	public void dropTabel(String tabel) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + tabel);
		onCreate(db);
	}

	public void insert(String col[], String nilai[], String tabel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		for (int i = 0; i < col.length; i++) {
			values.put(col[i], nilai[i]);
		}
		db.insert(tabel, null, values);
		db.close();
	}

	public void insert(String col[], String nilai[][], String tabel) {
		SQLiteDatabase db = this.getWritableDatabase();
		for (int i = 0; i < nilai.length; i++) {
			ContentValues values = new ContentValues();
			for (int x = 0; x < col.length; x++) {
				values.put(col[x], nilai[i][x]);
			}
			db.insert(tabel, null, values);
		}
		db.close();
	}

	public void delete(String col, String nilai, String tabel) {
		tabel += " where " + col + "='" + nilai + "'";
		truncate(tabel);
	}

	public void truncate(String tabel) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("Delete from " + tabel);
		db.close();
	}

	public void logout() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("Delete from JADWAL;");

		db.close();
	}

	public List<String[]> getResult(String selectQuery) {
		List<String[]> labels = new ArrayList<String[]>();
		Log.i("Test", selectQuery);
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				do {
					String data[] = new String[cursor.getColumnCount()];
					for (int i = 0; i < data.length; i++) {
						data[i] = cursor.getString(i);
						Log.i("Test", "Kol " + i + " : " + data[i]);
					}
					labels.add(data);
				} while (cursor.moveToNext());
			}
		} else {
			labels = null;
		}
		cursor.close();
		db.close();
		return labels;
	}

	public List<String[]> getTabel(String tabel) {
		List<String[]> labels = new ArrayList<String[]>();
		String selectQuery = "SELECT * FROM " + tabel;
		Log.i("Test", selectQuery);
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				do {
					String data[] = new String[cursor.getColumnCount()];
					for (int i = 0; i < data.length; i++) {
						data[i] = cursor.getString(i);
					}
					labels.add(data);
				} while (cursor.moveToNext());
			}
		} else {
			labels = null;
		}
		cursor.close();
		db.close();
		return labels;
	}

	public List<String[]> getTabel(String tabel, String col[], String nilai[]) {
		String where = " WHERE ";
		for (int i = 0; i < col.length; i++) {
			if (i > 0) {
				where += " AND ";
			}
			where += col[i] + " = " + nilai[i];
		}
		return getTabel(tabel + where);
	}

	public void update(String nilai[][][]) {
		// logout();
		String tabel[] = { "JADWAL", "NILAI", "STATISTIK", "ABSEN", "TAHUN",
				"KRS" };
		SQLiteDatabase db = this.getWritableDatabase();
		for (int i = 0; i < nilai.length; i++) {
			for (int y = 0; y < nilai[i].length; y++) {
				boolean first = true;
				String val = "";
				for (int x = 0; x < nilai[i][y].length; x++) {
					if (first) {
						first = false;
					} else {
						val += ",";
					}
					val += "\"" + nilai[i][y][x] + "\"";
				}
				Log.i("Test", "INSERT INTO " + tabel[i] + " values (" + val
						+ ");");
				db.execSQL("INSERT INTO " + tabel[i] + " values (" + val + ");");
			}
		}
		db.close();
	}

	public void update(String nilai[][], String tabel) {
		SQLiteDatabase db = this.getWritableDatabase();
		for (int y = 0; y < nilai.length; y++) {
			boolean first = true;
			String val = "";
			for (int x = 0; x < nilai[y].length; x++) {
				if (first) {
					first = false;
				} else {
					val += ",";
				}
				val += "\"" + nilai[y][x] + "\"";
			}
			Log.i("Test", "INSERT INTO " + tabel + " values (" + val + ");");
			db.execSQL("INSERT INTO " + tabel + " values (" + val + ");");
		}
		db.close();
	}

	
}
