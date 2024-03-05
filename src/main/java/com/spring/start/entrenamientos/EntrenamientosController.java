package com.spring.start.entrenamientos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.start.plan.Plan;
import com.spring.start.plan.PlanDAO;

@Controller
public class EntrenamientosController {

	@Autowired
	EntrenamientoDAO entrenamientoDAO;
	@Autowired
	PlanDAO planDAO;
	
	@GetMapping("/entrenamiento")
	public ModelAndView entrenamientos() {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("entrenamientos");
		
		Entrenamiento entrenamiento = new Entrenamiento();
		model.addObject("entrenamiento", entrenamiento);
		
		List<Entrenamiento> entrenamientos = (List<Entrenamiento>) entrenamientoDAO.findAll();
		model.addObject("entrenamientos", entrenamientos);
		
		return model;
	}

	@GetMapping("/entrenamiento/{id}")
	public ModelAndView tutoria(@PathVariable long id) {

		Entrenamiento entrenamiento = entrenamientoDAO.findById(id).get();
		
		ModelAndView model = new ModelAndView();
		model.setViewName("entrenamiento");
		model.addObject("entenamiento", entrenamiento);

		return model;
	}
	
	@GetMapping("/entrenamiento/add")
	public ModelAndView addPlan() {

		ModelAndView model = new ModelAndView();
		Entrenamiento entrenamiento = new Entrenamiento();
		model.setViewName("formEntrenamiento");
		model.addObject("entrenamiento", entrenamiento);
		model.addObject("planes", planDAO.findAll());

		return model;
	}
	
	@GetMapping("/entrenamiento/edit/{id}")
	public ModelAndView editPlan(@PathVariable long id) {
	    ModelAndView model = new ModelAndView();
	    Optional<Entrenamiento> entrenamiento = entrenamientoDAO.findById(id);
	    if (entrenamiento.isPresent()) {
	        model.addObject("entrenamiento", entrenamiento.get());
	        model.addObject("planes", planDAO.findAll());
	        model.setViewName("formEntrenamiento");
	    } else {
	        model.setViewName("redirect:/entrenamiento");
	    }
	    return model;
	}

	
	@GetMapping("/entrenamiento/del/{id}")
	public ModelAndView delDieta(@PathVariable long id) {
	    Optional<Entrenamiento> entrenamientoOpt = entrenamientoDAO.findById(id);
	    if (entrenamientoOpt.isPresent()) {
	        Entrenamiento entrenamiento = entrenamientoOpt.get();
	        
	        List<Plan> planes = entrenamiento.getPlanes();
	        for (Plan plan : planes) {
	            plan.setDieta(null); 
	            planDAO.save(plan);
	        }
	        
	        entrenamientoDAO.deleteById(id);
	    }

	    ModelAndView model = new ModelAndView();
	    model.setViewName("redirect:/entrenamiento");
	    return model;
	}

	
	@GetMapping("/entrenamiento/plan/remove/{idPlan}")
	public ModelAndView eliminarDietaDePlan(@PathVariable long idPlan) {
	    Optional<Plan> planOptional = planDAO.findById(idPlan);
	    if (planOptional.isPresent()) {
	        Plan plan = planOptional.get();
	        plan.setDieta(null);
	        planDAO.save(plan); 
	    }

	    ModelAndView model = new ModelAndView();
	    model.setViewName("redirect:/entrenamiento");

	    return model;
	}

	@PostMapping("/entrenamiento/save")
	public ModelAndView guardarDietaConPlan(@ModelAttribute Entrenamiento entrenamiento) {
	    if (entrenamiento.getPlanes() != null) {
	    	for (Plan plan : entrenamiento.getPlanes()) {
	            plan.setEntrenamiento(entrenamiento);
	        }
	    }

	    entrenamientoDAO.save(entrenamiento);
	    
	    ModelAndView model = new ModelAndView();
	    model.setViewName("redirect:/entrenamiento");
	    return model;
	}




	@GetMapping("entrenamiento/nuevo/{id}")
	public ModelAndView popupNuevoPlan(@PathVariable long id) {

		Entrenamiento entrenamiento = new Entrenamiento();
		ModelAndView model = new ModelAndView();

		List<Entrenamiento> entrenamientos = (List<Entrenamiento>) entrenamientoDAO.findAll();

		Entrenamiento entrenamientoNuevo = entrenamientoDAO.findById(id).get();

		model.addObject("entrenamiento", entrenamiento);
		model.addObject("planes", planDAO.findAll());;
		model.addObject("entrenamientoNuevo", entrenamientoNuevo);
		model.addObject("entrenamientos", entrenamientos);
		model.setViewName("entrenamientos");

		return model;
	}
}
