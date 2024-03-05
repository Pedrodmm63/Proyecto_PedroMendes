package com.spring.start.plan;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.spring.start.dietas.Dieta;
import com.spring.start.entrenamientos.Entrenamiento;
import com.spring.start.planUsuario.Plan_usuario;
import com.spring.start.usuarios.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Plan {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	
	private String descripcion;
	
	@ManyToOne
    @JoinColumn(name = "FK_DIETA") 
    private Dieta dieta;
	
	@ManyToOne
    @JoinColumn(name = "FK_ENTRENAMIENTO") 
    private Entrenamiento entrenamiento;

	@OneToMany(
			targetEntity = Plan_usuario.class,
			mappedBy="plan")
	@Cascade(CascadeType.ALL)
	private Set<Usuario> usuarios = new HashSet<Usuario>();
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Dieta getDieta() {
		return dieta;
	}

	public void setDieta(Dieta dieta) {
		this.dieta = dieta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Entrenamiento getEntrenamiento() {
		return entrenamiento;
	}

	public void setEntrenamiento(Entrenamiento entrenamiento) {
		this.entrenamiento = entrenamiento;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	
	
	
}
