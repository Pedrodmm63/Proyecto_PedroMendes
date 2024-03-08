package com.spring.start.dietas;

import java.util.ArrayList;
import java.util.List;

import com.spring.start.plan.Plan;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;



@Entity
public class Dieta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Size(max = 100)
	private String nombre_dieta;
	@Size(max = 255)
	private String descripcion_dieta;

	@OneToMany(mappedBy = "dieta")
	private List<Plan> planes = new ArrayList<Plan>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre_dieta() {
		return nombre_dieta;
	}

	public void setNombre_dieta(String nombre_dieta) {
		this.nombre_dieta = nombre_dieta;
	}

	public String getDescripcion_dieta() {
		return descripcion_dieta;
	}

	public void setDescripcion_dieta(String descripcion_dieta) {
		this.descripcion_dieta = descripcion_dieta;
	}

	public List<Plan> getPlanes() {
		return planes;
	}

	public void setPlanes(List<Plan> planes) {
		this.planes = planes;
	}
	
	

	
}
