package agenda.principales;

import java.util.Calendar;

import basicas.Evento;
import persistencia.EventosSQLiteHelper;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class CrearEvento extends Activity {
	private TextView tvDisplayTime;
	private Button btnChangeTime;

	private int hour;
	private int minute;

	static final int TIME_DIALOG_ID = 999;

	// Para el DatePicker
	private TextView tvDisplayDate;
	private Button btnChangeDate;

	private int year;
	private int month;
	private int day;

	static final int DATE_DIALOG_ID = 666;

	// Hasta aquí

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crear_evento);

		setCurrentTimeOnView();
		addListenerOnButton();

		setCurrentDateOnView();
		addListenerOnDateButton();

		final EditText etTitulo = (EditText) findViewById(R.id.editTextTitulo);
		final TextView tvFecha = (TextView) findViewById(R.id.textViewFecha);
		final TextView tvHora = (TextView) findViewById(R.id.textViewHora);
		final EditText etDescripcion = (EditText) findViewById(R.id.editTextDescripcion);

		//Pongo la fecha en la que está
		Bundle bundle = this.getIntent().getExtras();
		String fecha = bundle.getString("fecha");
		tvFecha.setText(fecha);
		
		// Botón Tabs
		Button buttonGuardar = (Button) findViewById(R.id.buttonGuardarEvento);
		final EventosSQLiteHelper sqlite = new EventosSQLiteHelper(this);
		buttonGuardar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Evento ev = new Evento("1", etTitulo.getText()
						.toString(), tvFecha.getText().toString(), tvHora
						.getText().toString(), etDescripcion.getText()
						.toString());
				System.out.println(ev);
				
				sqlite.añadeEvento(ev);
				

				// Creamos el Intent
				Intent intent = new Intent(CrearEvento.this, Eventos.class);
				// Iniciamos la nueva actividad
				Bundle b = new Bundle();
				b.putString("fecha", tvFecha.getText().toString());
				intent.putExtras(b);
				startActivity(intent);
			}
		});
		// Fin Botón
	}

	public void setCurrentTimeOnView() {

		tvDisplayTime = (TextView) findViewById(R.id.textViewHora);

		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);

		tvDisplayTime.setText(new StringBuilder().append(pad(hour)).append(":")
				.append(pad(minute)));
	}

	public void setCurrentDateOnView() {

		tvDisplayDate = (TextView) findViewById(R.id.textViewFecha);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		tvDisplayDate.setText(new StringBuilder().append(day).append("-")
				.append(month + 1).append("-").append(year));
	}

	public void addListenerOnButton() {
		btnChangeTime = (Button) findViewById(R.id.buttonCambiar);
		btnChangeTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});
	}

	public void addListenerOnDateButton() {
		btnChangeDate = (Button) findViewById(R.id.buttonCambiarFecha);
		btnChangeDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timePickerListener, hour, minute,
					false);
		}
		return null;
	}

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;

			tvDisplayTime.setText(new StringBuilder().append(pad(hour))
					.append(":").append(pad(minute)));
		}
	};

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			tvDisplayDate.setText(new StringBuilder().append(day).append("-")
					.append(month + 1).append("-").append(year));
		}
	};

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.crear_evento, menu);
		return true;
	}
}
