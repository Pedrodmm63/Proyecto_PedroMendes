package com.spring.start.planUsuario;

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
import com.spring.start.usuarios.UsuarioDAO;


@Controller
public class Plan_UsuarioController {

	@Autowired
	PlanUsuarioDAO planUsuarioDAO;
	
	@Autowired
	PlanDAO planDAO;
	
	@Autowired
	UsuarioDAO usuarioDAO;
	
	@GetMapping("/planUsuario")
	public ModelAndView planUsuario() {
		ModelAndView model = new ModelAndView();
		
		model.addObject("planUsuarios", planUsuarioDAO.findAll());
		model.setViewName("planUsuario");
		
		
		return model;
	}
	@GetMapping("/planUsuario/add")
	public ModelAndView enmarcaAdd() {
			
		ModelAndView model = new ModelAndView();
		model.addObject("planUsuario", new PlanUsuario());
		model.addObject("planes", planDAO.findAll());
		model.addObject("usuarios", usuarioDAO.findAll());
		
		model.setViewName("formPlanUsuario");
		
		return model;
	}
	
	@PostMapping("/planUsuario/save")
	public ModelAndView formTutoria(@ModelAttribute PlanUsuario planUsuario) {
	
		 planUsuarioDAO.save(planUsuario);
		
		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/planUsuario");	
		
		return model;
	}	
	
	@GetMapping("/planUsuario/del/{id}")
	public ModelAndView delPlanUsuario(@PathVariable long id) {

		planUsuarioDAO.deleteById(id);

		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/planUsuario");

		return model;
	}
	
}
