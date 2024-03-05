package com.spring.start.plan;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.start.dietas.Dieta;
import com.spring.start.dietas.DietaDAO;
import com.spring.start.entrenamientos.Entrenamiento;
import com.spring.start.entrenamientos.EntrenamientoDAO;


@Controller
public class PlanController {

	@Autowired
	DietaDAO dietaDAO;
	@Autowired
	PlanDAO planDAO;
	@Autowired
	EntrenamientoDAO entrenamientoDAO;
	
	@GetMapping("/plan")
	public ModelAndView planes() {
		ModelAndView model = new ModelAndView();
		model.setViewName("planes");
		
		Plan plan = new Plan();
		model.addObject("plan", plan);
		
		List<Plan> planes = (List<Plan>) planDAO.findAll();
		model.addObject("planes", planes);
		return model;
	}
	@GetMapping("/plan/{id}")
	public ModelAndView plan(@PathVariable long id){
		
		Plan plan = planDAO.findById(id).get();
		
		ModelAndView model = new ModelAndView();
		model.setViewName("plan");
		model.addObject("plan", plan);
		
		return model;
	}
	
	@GetMapping("/plan/add")
	public ModelAndView addPlan() {

		ModelAndView model = new ModelAndView();
		Plan plan = new Plan();
		model.setViewName("formPlan");
		model.addObject("plan", plan);
		model.addObject("dietas", dietaDAO.findAll());
		model.addObject("entrenamientos", entrenamientoDAO.findAll());


		return model;
	}
	@PostMapping("/plan/save")
	public ModelAndView formTutoria(@ModelAttribute Plan plan) {

	    if (plan.getDieta() != null) {
	        Optional<Dieta> optionalDieta = dietaDAO.findById(plan.getDieta().getId());
	        if (optionalDieta.isPresent()) {
	            Dieta dieta = optionalDieta.get();
	            // Aquí se asume que ya existe una relación entre plan y dieta y que solo se necesita actualizar la referencia.
	            plan.setDieta(dieta);
	            // Añadir el plan a la lista de planes de la dieta si no está presente
	            if (!dieta.getPlanes().contains(plan)) {
	                dieta.getPlanes().add(plan);
	            }
	            // No es necesario guardar explícitamente la dieta aquí si la relación entre Plan y Dieta está configurada con cascade en el mapeo JPA
	        }
	    }

	    if (plan.getEntrenamiento() != null) {
	        Optional<Entrenamiento> optionalEntrenamiento = entrenamientoDAO.findById(plan.getEntrenamiento().getId());
	        if (optionalEntrenamiento.isPresent()) {
	            Entrenamiento entrenamiento = optionalEntrenamiento.get();
	            // Si Entrenamiento maneja una lista de Planes similar a Dieta, realizar la lógica adecuada aquí
	            plan.setEntrenamiento(entrenamiento);
	            
	            if (!entrenamiento.getPlanes().contains(plan)) {
	                entrenamiento.getPlanes().add(plan);
	            }
	        }
	    }

	    planDAO.save(plan);

	    ModelAndView model = new ModelAndView();
	    model.addObject("planNuevo", plan);
	    model.setViewName("redirect:/plan/nuevo/" + plan.getId());

	    return model;
	}

	@GetMapping("plan/nuevo/{id}")
	public ModelAndView popupNuevoPlan(@PathVariable long id) {

		Plan plan = new Plan();
		ModelAndView model = new ModelAndView();

		List<Plan> planes = (List<Plan>) planDAO.findAll();

		Plan planNuevo = planDAO.findById(id).get();

		model.addObject("plan", plan);
		model.addObject("dietas", dietaDAO.findAll());
		model.addObject("planes", planes);
		model.addObject("planNuevo", planNuevo);
		model.addObject("entrenamiento", entrenamientoDAO.findAll());
		model.setViewName("planes");

		return model;
	}
}
