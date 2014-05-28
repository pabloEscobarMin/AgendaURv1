package agenda.principales;

import java.util.List;

import persistencia.EventosSQLiteHelper;
import basicas.Evento;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Eventos extends ActionBarActivity {
	private List<Evento> datos2;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eventos);

		Bundle b = this.getIntent().getExtras();
		if (b != null) {

			if (b.containsKey("fecha")) {
				final EventosSQLiteHelper sqlite = new EventosSQLiteHelper(this);
				String fecha = b.getString("fecha");
				System.out.println(fecha);
				datos2 = sqlite.getEventos(fecha);
				
			}
		}

		// Codigo para lista
		AdaptadorTitulares adaptador = new AdaptadorTitulares(this);
		ListView ev_listView1 = (ListView) findViewById(R.id.ev_listView1);

		ev_listView1.setAdapter(adaptador);
		// Fin codigo para lista

		// Botón Tabs
		Button ev_button1 = (Button) findViewById(R.id.ev_button1);
		ev_button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(Eventos.this, CrearEvento.class);
				
				Bundle b = Eventos.this.getIntent().getExtras();
				if (b != null) {
				if (b.containsKey("fecha")) {
					intent.putExtras(b);
				}
				}
				
				// Iniciamos la nueva actividad
				startActivity(intent);
			}
		});
		// Fin Botón

	}

	// que simule que se ha presionado el botón back
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; goto parent activity.
			this.finish();
			Intent intent = new Intent(Eventos.this, MainActivity.class);
			// Iniciamos la nueva actividad
			startActivity(intent);
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void clickBoton(View v) {
		ListView lv = (ListView) findViewById(R.id.ev_listView1);
		View parent = (View) v.getParent();

		if (parent != null) {
			TextView lblTitulo = (TextView) parent.findViewById(R.id.LblTitulo);
			System.out.println(lblTitulo.getText());
			Evento e = new Evento("1", lblTitulo.getText().toString(), "", "",
					"");
			EventosSQLiteHelper sqlite = new EventosSQLiteHelper(this);
			sqlite.borraEvento(e);
			Intent intent = new Intent(Eventos.this, Eventos.class);

			Bundle b = this.getIntent().getExtras();
			if (b != null) {
				if (b.containsKey("fecha")) {
					String fecha = b.getString("fecha");
					Bundle b2 = new Bundle();
					b2.putString("fecha", fecha);
					intent.putExtras(b);
				}
			}
			startActivity(intent);
		}
	}

	// Adaptador para la lista
	class AdaptadorTitulares extends ArrayAdapter<Evento> {

		Activity context;

		AdaptadorTitulares(Activity context) {
			super(context, R.layout.eltos_listview1, datos2);
			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View item = inflater.inflate(R.layout.eltos_listview1, null);

			TextView lblTitulo = (TextView) item.findViewById(R.id.LblTitulo);
			lblTitulo.setText(datos2.get(position).getTitulo());

			TextView lblSubtitulo = (TextView) item
					.findViewById(R.id.LblSubTitulo);
			lblSubtitulo.setText(datos2.get(position).getFechaInicio());

			return (item);
		}
	}
}