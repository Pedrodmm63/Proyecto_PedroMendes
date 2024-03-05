package com.spring.start.dietas;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.start.plan.PlanDAO;
import com.spring.start.plan.Plan;


@Controller
public class DietaController {
	
	@Autowired
	DietaDAO dietaDAO;
	@Autowired
	PlanDAO planDAO;
	
	@GetMapping("/dieta")
	public ModelAndView dietas() {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("dietas");
		
		Dieta dieta = new Dieta();
		model.addObject("dieta", dieta);
		
		List<Dieta> dietas = (List<Dieta>) dietaDAO.findAll();
		model.addObject("dietas", dietas);
		
		return model;
	}

	@GetMapping("/dieta/{id}")
	public ModelAndView tutoria(@PathVariable long id) {

		Dieta dieta = dietaDAO.findById(id).get();
		
		ModelAndView model = new ModelAndView();
		model.setViewName("dieta");
		model.addObject("dieta", dieta);

		return model;
	}
	
	@GetMapping("/dieta/add")
	public ModelAndView addPlan() {

		ModelAndView model = new ModelAndView();
		Dieta dieta = new Dieta();
		model.setViewName("formDieta");
		model.addObject("dieta", dieta);
		model.addObject("planes", planDAO.findAll());

		return model;
	}
	
	@GetMapping("/dieta/edit/{id}")
	public ModelAndView editPlan(@PathVariable long id) {
	    ModelAndView model = new ModelAndView();
	    Optional<Dieta> dieta = dietaDAO.findById(id);
	    if (dieta.isPresent()) {
	        model.addObject("dieta", dieta.get());
	        model.addObject("planes", planDAO.findAll());
	        model.setViewName("formDieta");
	    } else {
	        model.setViewName("redirect:/dieta");
	    }
	    return model;
	}

	
	@GetMapping("/dieta/del/{id}")
	public ModelAndView delDieta(@PathVariable long id) {
	    Optional<Dieta> dietaOpt = dietaDAO.findById(id);
	    if (dietaOpt.isPresent()) {
	        Dieta dieta = dietaOpt.get();
	        
	        List<Plan> planes = dieta.getPlanes();
	        for (Plan plan : planes) {
	            plan.setDieta(null); 
	            planDAO.save(plan);
	        }
	        
	        dietaDAO.deleteById(id);
	    }

	    ModelAndView model = new ModelAndView();
	    model.setViewName("redirect:/dieta");
	    return model;
	}

	
	@GetMapping("/dieta/plan/remove/{idPlan}")
	public ModelAndView eliminarDietaDePlan(@PathVariable long idPlan) {
	    Optional<Plan> planOptional = planDAO.findById(idPlan);
	    if (planOptional.isPresent()) {
	        Plan plan = planOptional.get();
	        plan.setDieta(null);
	        planDAO.save(plan); 
	    }

	    ModelAndView model = new ModelAndView();
	    model.setViewName("redirect:/dieta");

	    return model;
	}

	@PostMapping("/dieta/save")
	public ModelAndView guardarDietaConPlan(@ModelAttribute Dieta dieta) {
	    if (dieta.getPlanes() != null) {
	    	for (Plan plan : dieta.getPlanes()) {
	            plan.setDieta(dieta);
	        }
	    }

	    dietaDAO.save(dieta);
	    
	    ModelAndView model = new ModelAndView();
	    model.setViewName("redirect:/dieta");
	    return model;
	}




	@GetMapping("dieta/nuevo/{id}")
	public ModelAndView popupNuevoPlan(@PathVariable long id) {

		Dieta dieta = new Dieta();
		ModelAndView model = new ModelAndView();

		List<Dieta> dietas = (List<Dieta>) dietaDAO.findAll();

		Dieta dietaNueva = dietaDAO.findById(id).get();

		model.addObject("dieta", dieta);
		model.addObject("planes", planDAO.findAll());;
		model.addObject("dietaNueva", dietaNueva);
		model.addObject("dietas", dietas);
		model.setViewName("dietas");

		return model;
	}

}
