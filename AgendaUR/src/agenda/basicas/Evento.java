package basicas;

import java.util.Date;

public class Evento {
	private String id;
	private String titulo;
	private String fechaInicio;
	private String fechaFin;
	String descripcion;

	public Evento() {

	}

	public Evento(String id, String titulo, String fechaInicio, String fechaFin, String descripcion) {
		this.id = id;
		this.titulo = titulo;
		this.fechaInicio = fechaInicio;
		this.fechaFin=fechaFin;
		this.descripcion=descripcion;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setFechaInicio(String fecha) {
		this.fechaInicio = fecha;
	}
	
	public void setFechaFin(String fecha){
		this.fechaFin=fecha;
	}
	
	public void setDescripcion(String descripcion){
		this.descripcion=descripcion;
	}

	public String getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}
	
	public String getFechaFin(){
		return fechaFin;
	}
	
	public String getDescripcion(){
		return descripcion;
	}

	public String toString() {
		String cadena = "Evento[titulo:" + titulo + " , fecha:" + fechaInicio + " ]";
		return cadena;
	}
}
