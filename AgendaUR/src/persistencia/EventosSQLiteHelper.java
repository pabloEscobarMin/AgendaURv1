package persistencia;

import java.util.List;
import java.util.Vector;

import basicas.Evento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EventosSQLiteHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "DBEventos";

	public EventosSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS Eventos");
		Log.d("Base de datos borrada","BD borrada");

		String sqlCreate = "CREATE TABLE Eventos (id TEXT, titulo TEXT, fechaInicio TEXT, fechaFin TEXT, descripcion TEXT)";
		
		db.execSQL(sqlCreate);
		Log.d("Base de datos creada","La base de datos se ha creado correctamente");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS Eventos");

		this.onCreate(db);
	}

	/*
	 * Métodos para añadir, visualizar, borrar y actualizar un Evento
	 */

	// Nombre de la tabla
	private static final String TABLA_EVENTOS = "Eventos";

	// Columnas de la tabla Eventos
	private static final String KEY_ID= "id";
	private static final String KEY_NOMBRE = "titulo";
	private static final String KEY_FECHAINICIO = "fechaInicio";
	private static final String KEY_FECHAFIN ="fechaFin";
	private static final String KEY_DESCRIPCION="descripcion";

	private static final String[] COLUMNS = {KEY_ID, KEY_NOMBRE, KEY_FECHAINICIO, KEY_FECHAFIN, KEY_DESCRIPCION };

	public void añadeEvento(Evento evento) {		
		// 1. Obtenemos la referencia a la BD en modo ESCRITURA
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. Creamos un ContentValues para añadir pares clave-valor
		ContentValues values = new ContentValues();
		values.put(KEY_ID, evento.getId());
		values.put(KEY_NOMBRE, evento.getTitulo());
		values.put(KEY_FECHAINICIO, evento.getFechaInicio().toString());
		values.put(KEY_FECHAFIN, evento.getFechaFin().toString());
		values.put(KEY_DESCRIPCION, evento.getDescripcion());

		// 3. Insertamos en la BD
		db.insert(TABLA_EVENTOS, null, values);

		// 4. Cerramos la BD
		db.close();
		Log.d("añadeEvento", evento.toString());
	}

	public Evento getEvento(String fecha) {
		Evento evento = null;
		Cursor cursor = null;

		SQLiteDatabase db = this.getReadableDatabase();

		cursor = db.query(TABLA_EVENTOS, // a. Tabla
				COLUMNS, // b. Nombres de las columnas
				" fechaInicio = ?", // c. Selecciones
				new String[] { fecha }, // d. Argumentos de
										// selección
				null, // e. group by
				null, // f. having
				null, // g. order by
				null); // h. limit

		if (cursor.moveToFirst()) {

			evento = new Evento();
			evento.setId(cursor.getString(0));
			evento.setTitulo(cursor.getString(1));
			evento.setFechaInicio(cursor.getString(2));
			evento.setFechaFin(cursor.getString(3));
			evento.setDescripcion(cursor.getString(4));

			Log.d("getEvento(" + fecha + ")", evento.toString());

			return evento;
		} else {
			return null;
		}
	}
	
	public List<Evento> getEventos(String fecha){
	/*Dada una fecha, devuelve todos los eventos asociados a esa fecha.*/
		List<Evento> eventos=new Vector<Evento>();
		
		String query="SELECT * FROM " + TABLA_EVENTOS + " WHERE fechaInicio = " + "'" + fecha + "'";
		
		System.out.println(query);
		
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cursor=db.rawQuery(query, null);
		Evento evento=null;
		
		if(cursor.moveToFirst()){
			do{
				evento = new Evento();
				evento.setId(cursor.getString(0));
				evento.setTitulo(cursor.getString(1));
				evento.setFechaInicio(cursor.getString(2));
				evento.setFechaFin(cursor.getString(3));
				evento.setDescripcion(cursor.getString(4));
				eventos.add(evento);
			}while(cursor.moveToNext());
		}
		
		Log.d("getEventos(fecha)", eventos.toString());
		return eventos;
	}

	public List<Evento> getEventos() {
	/*Devuelve todos los eventos guardados.*/
		List<Evento> eventos = new Vector<Evento>();

		String query = "SELECT  * FROM " + TABLA_EVENTOS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		Evento evento = null;
		if (cursor.moveToFirst()) {
			do {
				evento = new Evento();
				evento.setId(cursor.getString(0));
				evento.setTitulo(cursor.getString(1));
				evento.setFechaInicio(cursor.getString(2));
				evento.setFechaFin(cursor.getString(3));
				evento.setDescripcion(cursor.getString(4));

				eventos.add(evento);
			} while (cursor.moveToNext());
		}

		Log.d("getEventos()", eventos.toString());

		return eventos;
	}

	public int actualizaEvento(Evento evento) {
	/*Dado un Evento, actualiza el evento guardado con los datos dados.*/

		SQLiteDatabase db = this.getWritableDatabase();

		// 2. Creamos un ContentValues para añadir pares clave-valor
		ContentValues values = new ContentValues();
		values.put(KEY_ID, evento.getId());
		values.put(KEY_NOMBRE, evento.getTitulo());
		values.put(KEY_FECHAINICIO, evento.getFechaInicio().toString());
		values.put(KEY_FECHAFIN, evento.getFechaFin().toString());
		values.put(KEY_DESCRIPCION, evento.getDescripcion());

		// 3. Actualizamos la fila
		int i = db.update(TABLA_EVENTOS, values, KEY_NOMBRE + " = ?",
				new String[] { String.valueOf(evento.getTitulo()) });

		// 4. Cerramos la BD
		db.close();

		return i;
	}

	public void borraEvento(Evento evento) {

		// 1. Obtenemos la referencia a la BD en modo ESCRITURA
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. Borramos el Evento
		db.delete(TABLA_EVENTOS, KEY_NOMBRE + " = ?",
				new String[] { String.valueOf(evento.getTitulo()) });

		// 3. Cerramos la BD
		db.close();

		Log.d("borraEvento", evento.toString());
	}
}