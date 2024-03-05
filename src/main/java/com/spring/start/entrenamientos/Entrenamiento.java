package com.spring.start.entrenamientos;

import java.util.ArrayList;
import java.util.List;

import com.spring.start.plan.Plan;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Entrenamiento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String nombre_entrenamiento;
	private String descripcion_entrenamiento;

	@OneToMany(mappedBy = "entrenamiento")
	private List<Plan> planes = new ArrayList<Plan>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre_entrenamiento() {
		return nombre_entrenamiento;
	}

	public void setNombre_entrenamiento(String nombre_entrenamiento) {
		this.nombre_entrenamiento = nombre_entrenamiento;
	}

	public String getDescripcion_entrenamiento() {
		return descripcion_entrenamiento;
	}

	public void setDescripcion_entrenamiento(String descripcion_entrenamiento) {
		this.descripcion_entrenamiento = descripcion_entrenamiento;
	}

	public List<Plan> getPlanes() {
		return planes;
	}

	public void setPlanes(List<Plan> planes) {
		this.planes = planes;
	}
	
	
	
}
