package agenda.principales;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class HorarioWeb extends Activity {

	private String GOOGLE_DOC = "http://docs.google.com/gview?embedded=true&url=";
	private String URL_00 = "http://www.unirioja.es/facultades_escuelas/fceai/horarios/horarios_13_14/801_primero_s1.pdf";
	private String URL_01 = "http://www.unirioja.es/facultades_escuelas/fceai/horarios/horarios_13_14/801_primero_s2.pdf";
	private String URL_10 = "http://www.unirioja.es/facultades_escuelas/fceai/horarios/horarios_13_14/801_segundo_s1.pdf";
	private String URL_11 = "http://www.unirioja.es/facultades_escuelas/fceai/horarios/horarios_13_14/801_segundo_s2.pdf";
	private String URL_20 = "http://www.unirioja.es/facultades_escuelas/fceai/horarios/horarios_13_14/801_tercero_s1.pdf";
	private String URL_21 = "http://www.unirioja.es/facultades_escuelas/fceai/horarios/horarios_13_14/801_tercero_s2.pdf";
	private String URL_30 = "http://www.unirioja.es/facultades_escuelas/fceai/horarios/horarios_13_14/801_cuarto_s1.pdf";
	private String URL_31 = "http://www.unirioja.es/facultades_escuelas/fceai/horarios/horarios_13_14/801_cuarto_s2.pdf";
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_horario_web);
		
		//Recojo spinner seleccionados
		Bundle bundle = this.getIntent().getExtras();
        int curso = bundle.getInt("curso");
        int cuatri = bundle.getInt("cuatri");
        //Fin recojo spinner
        
      //Abro diálogo de carga
      		pDialog = new ProgressDialog(this);
      		pDialog.setMessage("Cargando...");
      		pDialog.setIndeterminate(false);
      		pDialog.show();
      		pDialog.setCancelable(true);
      		//Fin abro diálogo de carga
      		
      		// Para WebView
      		WebView webView = (WebView) findViewById(R.id.ahw_webView1);
      		webView.setWebViewClient(new Callback());				
      		WebSettings settings = webView.getSettings();
      		settings.setJavaScriptEnabled(true);
      		//Fin Para WebView	
        
        //Abro url en función de la opción seleccionada
        if(curso == 0 && cuatri == 0){
        	webView.loadUrl(GOOGLE_DOC + URL_00);
        }
        else if(curso == 0 && cuatri == 1){
        	webView.loadUrl(GOOGLE_DOC + URL_01);
        }
        else if(curso == 1 && cuatri == 0){
        	webView.loadUrl(GOOGLE_DOC + URL_10);
        }
        else if(curso == 1 && cuatri == 1){
        	webView.loadUrl(GOOGLE_DOC + URL_11);
        }
        else if(curso == 2 && cuatri == 0){
        	webView.loadUrl(GOOGLE_DOC + URL_20);
        }
        else if(curso == 2 && cuatri == 1){
        	webView.loadUrl(GOOGLE_DOC + URL_21);
        }
        else if(curso == 3 && cuatri == 0){
        	webView.loadUrl(GOOGLE_DOC + URL_30);
        }
        else if(curso == 3 && cuatri == 1){
        	webView.loadUrl(GOOGLE_DOC + URL_31);
        }
        //Fin abro url en función de la opción seleccionada
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.horario_web, menu);
		return true;
	}
	
	//Mi propio WebView Client
		private class Callback extends WebViewClient {
			//Para que no redirija 
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}
			//Fin para que no redirija	
			
			//Para que el diálogo se cierre cuando cargue el webView
			@Override
	        public void onPageFinished(WebView view, String url) {                  
	            if (pDialog.isShowing()) {
	                pDialog.dismiss();
	            }
	        }
			//Fin para que el diálogo se cierre cuando cargue el webView
		}
		//Fin mi propio Web View Client

}
