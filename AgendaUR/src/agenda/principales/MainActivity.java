
package agenda.principales;

import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

	private String[] opcionesMenu;
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;

	private CharSequence tituloSeccion;
	private CharSequence tituloApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// voy a cmbia algo
		opcionesMenu = new String[] { "Calendario", "Eventos", "Horario" };
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);

		drawerList
				.setAdapter(new ArrayAdapter<String>(
						getSupportActionBar().getThemedContext(),
						(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) ? android.R.layout.simple_list_item_activated_1
								: android.R.layout.simple_list_item_checked,
						opcionesMenu));

		drawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parent, View view,
					int position, long id) {

				Intent i;

				switch (position) {
				case 0:
					i = new Intent(MainActivity.this, Calendario.class);
					startActivity(i);
					break;
				case 1:
					i = new Intent(MainActivity.this, Eventos.class);

					Bundle b = new Bundle();

					Date date = new Date();
					Calendar c = Calendar.getInstance();
					c.setTime(date);
					int dia = c.get(Calendar.DAY_OF_MONTH);
					int mes = c.get(Calendar.MONTH);
					mes++;
					int año = c.get(Calendar.YEAR);

					String fecha = new String(dia + "-" + mes + "-" + año);

					b.putString("fecha", fecha);
					i.putExtras(b);
					startActivity(i);
					break;
				case 2:
					i = new Intent(MainActivity.this, Horario.class);
					startActivity(i);
					break;
				}

				drawerList.setItemChecked(position, true);

				tituloSeccion = opcionesMenu[position];
				getSupportActionBar().setTitle(tituloSeccion);

				drawerLayout.closeDrawer(drawerList);
			}
		});

		// Comienza aqui los cambios
		tituloSeccion = getTitle();
		tituloApp = getTitle();

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(tituloSeccion);
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(tituloApp);
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);
			}
		};

		drawerLayout.setDrawerListener(drawerToggle);
		// Acaba aqui los cambios

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		// Botón Tabs
		Button am_bt1 = (Button) findViewById(R.id.am_button1);
		am_bt1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Creamos el Intent
				Intent intent = new Intent(MainActivity.this, Calendario.class);
				// Iniciamos la nueva actividad
				startActivity(intent);
			}
		});
		// Fin Botón

		// Botón Tabs
		Button am_bt2 = (Button) findViewById(R.id.am_button2);
		am_bt2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Creamos el Intent
				Intent i = new Intent(MainActivity.this, Eventos.class);
				// Iniciamos la nueva actividad
				Bundle b = new Bundle();

				Date date = new Date();
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				int dia = c.get(Calendar.DAY_OF_MONTH);
				int mes = c.get(Calendar.MONTH);
				mes++;
				int año = c.get(Calendar.YEAR);

				String fecha = new String(dia + "-" + mes + "-" + año);

				b.putString("fecha", fecha);
				i.putExtras(b);
				startActivity(i);
			}
		});
		// Fin Botón

		// Botón Tabs
		Button am_bt3 = (Button) findViewById(R.id.am_button3);
		am_bt3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Creamos el Intent
				Intent intent = new Intent(MainActivity.this, Horario.class);
				/*
				 * Bundle b = new Bundle(); b.putString("fecha", );
				 * i.putExtras(b);
				 */
				// Iniciamos la nueva actividad
				startActivity(intent);
			}
		});
		// Fin Botón
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		case R.id.action_settings:
			Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
			;
			break;
		case R.id.action_search:
			Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		boolean menuAbierto = drawerLayout.isDrawerVisible(drawerList);

		if (menuAbierto)
			menu.findItem(R.id.action_search).setVisible(false);
		else
			menu.findItem(R.id.action_search).setVisible(true);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}
}

/*package agenda.principales;

import persistencia.EventosSQLiteHelper;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

	private String[] opcionesMenu;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    
    private CharSequence tituloSeccion;
    private CharSequence tituloApp; 
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //voy a cmbia algo
        opcionesMenu = new String[] {"Calendario", "Eventos", "Horario"};
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
 
        drawerList.setAdapter(new ArrayAdapter<String>(getSupportActionBar().getThemedContext(),
        	    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) ?
        	    android.R.layout.simple_list_item_activated_1 :
        	    android.R.layout.simple_list_item_checked, opcionesMenu));
        
        
        drawerList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view,
                    int position, long id) {
     
                Intent i;
     
                switch (position) {
                    case 0:
                    	i = new Intent(MainActivity.this, Calendario.class);
            	        startActivity(i);                  	
                        break;
                    case 1:
                    	i=new Intent(MainActivity.this, Eventos.class);
                    	startActivity(i);
                        break;
                    case 2:
                    	i=new Intent(MainActivity.this, Horario.class);
                    	startActivity(i);
                        break;
                }
        
                drawerList.setItemChecked(position, true);
     
                tituloSeccion = opcionesMenu[position];
                getSupportActionBar().setTitle(tituloSeccion);
     
                drawerLayout.closeDrawer(drawerList);
            }
        }); 
        
        
        //Comienza aqui los cambios
        tituloSeccion = getTitle();
		tituloApp = getTitle();
		
		drawerToggle = new ActionBarDrawerToggle(this, 
				drawerLayout,
				R.drawable.ic_drawer, 
				R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(tituloSeccion);
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(tituloApp);
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);
			}
		};

		drawerLayout.setDrawerListener(drawerToggle);
		//Acaba aqui los cambios
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    getSupportActionBar().setHomeButtonEnabled(true);
	    
	  //Botón Tabs
        Button am_bt1 = (Button)findViewById(R.id.am_button1);
        am_bt1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                 //Creamos el Intent
                 Intent intent =
                         new Intent(MainActivity.this, Calendario.class);
                 //Iniciamos la nueva actividad
                 startActivity(intent);
            }
       });
      //Fin Botón
        
	  //Botón Tabs
        Button am_bt2 = (Button)findViewById(R.id.am_button2);
        am_bt2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                 //Creamos el Intent
                 Intent intent =
                         new Intent(MainActivity.this, Eventos.class);
                 //Iniciamos la nueva actividad
                 startActivity(intent);
            }
       });
      //Fin Botón
        
	    //Botón Tabs
        Button am_bt3 = (Button)findViewById(R.id.am_button3);
        am_bt3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                 //Creamos el Intent
                 Intent intent =
                         new Intent(MainActivity.this, Horario.class);
                 //Iniciamos la nueva actividad
                 startActivity(intent);
            }
       });
      //Fin Botón
        
      
        
    }
        
        

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (drawerToggle.onOptionsItemSelected(item)) {
	        return true;
	    }
		
	    switch(item.getItemId())
	    {
	        case R.id.action_settings:
	            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();;
	            break;
	        case R.id.action_search:
	            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
	            break;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	 
	    return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		boolean menuAbierto = drawerLayout.isDrawerVisible(drawerList);
		
		if(menuAbierto)
			menu.findItem(R.id.action_search).setVisible(false);
		else
			menu.findItem(R.id.action_search).setVisible(true);
		
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}
}*/
