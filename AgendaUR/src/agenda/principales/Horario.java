package agenda.principales;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Horario extends ActionBarActivity {

	//Datos para Spinner1
	final String[] datos1 =
	        new String[]{"Primer Curso","Segundo Curso","Tercer Curso","Cuarto Curso"};
	private Spinner cmbOpciones1;
	
	//Datos para Spinner2
	final String[] datos2 =
	        new String[]{"Primer cuatrimestre","Segundo cuatrimestre"};
	private Spinner cmbOpciones2;
	
	private Button ah_button1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.horario);
        
		//Código para poner datos a spinners
		ArrayAdapter<String> adaptador1 =
		        new ArrayAdapter<String>(this,
		            android.R.layout.simple_spinner_item, datos1);
		cmbOpciones1 = (Spinner)findViewById(R.id.ah_spinner1);
		adaptador1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cmbOpciones1.setAdapter(adaptador1);
		
		ArrayAdapter<String> adaptador2 =
		        new ArrayAdapter<String>(this,
		            android.R.layout.simple_spinner_item, datos2);
		cmbOpciones2 = (Spinner)findViewById(R.id.ah_spinner2);
		adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cmbOpciones2.setAdapter(adaptador2);
		//Fin Código para poner datos a spinners
		
		//Botón Consultar
		ah_button1 = (Button)findViewById(R.id.ah_button1);
		ah_button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent intent =
                        new Intent(Horario.this, HorarioWeb.class);
            	//Pasamos los spinner seleccionados
            	int curso = cmbOpciones1.getSelectedItemPosition();
            	int cuatri = cmbOpciones2.getSelectedItemPosition();
            	Bundle b = new Bundle();
                b.putInt("curso", curso);
                b.putInt("cuatri", cuatri);
                intent.putExtras(b);
                //Fin pasamos los spinner seleccionados
                 startActivity(intent);
            }
       });
      //Fin Botón
        
	}
	
	
	//que simule que se ha presionado el botón back
		public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		        case android.R.id.home:
		            // app icon in action bar clicked; goto parent activity.
		            this.finish();
		            return true;
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}
   //fin boton back
		
}
