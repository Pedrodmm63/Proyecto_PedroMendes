package com.spring.start.usuarios;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.start.dietas.Dieta;
import com.spring.start.plan.Plan;
import com.spring.start.plan.PlanDAO;
import com.spring.start.planUsuario.PlanUsuarioDAO;

@Controller
public class UsuarioController {

	@Autowired
	UsuarioDAO usuarioDAO;
	@Autowired
	PlanDAO planDAO;
	@Autowired
	PlanUsuarioDAO planUsuarioDAO;
	
	@GetMapping("/usuarios")
	public ModelAndView planes() {
		ModelAndView model = new ModelAndView();
		model.setViewName("usuarios");

		Usuario usuario = new Usuario();
		model.addObject("usuario", usuario);

		List<Usuario> usuarios = (List<Usuario>) usuarioDAO.findAll();
		model.addObject("usuarios", usuarios);
		return model;
	}
	@GetMapping("/usuario/{id}")
	public ModelAndView plan(@PathVariable long id) {

		Usuario usuario = usuarioDAO.findById(id).get();

		ModelAndView model = new ModelAndView();
		model.setViewName("usuario");
		model.addObject("usuario", usuario);

		return model;
	}
	@GetMapping("/usuario/add")
	public ModelAndView addPlan() {

		ModelAndView model = new ModelAndView();
		Usuario usuario = new Usuario();
		model.setViewName("formUsuario");
		model.addObject("usuario", usuario);
		model.addObject("planes", planDAO.findAll());

		return model;
	}
	
	//MODIFICAR PARA AÑADIR /USUARIO/NUEVO/{ID}
	@PostMapping("/usuario/save")
	public ModelAndView formTutoria(@ModelAttribute Usuario usuario) {
	
		
		ModelAndView model = new ModelAndView();
		model.addObject("usuarioNuevo", usuario);
		model.setViewName("redirect:/usuarios");
		usuarioDAO.save(usuario);

		return model;
	}
	
	
	@GetMapping("/usuario/edit/{id}")
	public ModelAndView editPlan(@PathVariable long id) {

		ModelAndView model = new ModelAndView();

		Optional<Usuario> usuarios = usuarioDAO.findById(id);
		if (usuarios.isPresent()) {

			model.addObject("usuario", usuarios.get());
			model.addObject("planes", planDAO.findAll());

			model.setViewName("formUsuario");
		} else
			model.setViewName("redirect:/usuario");
		return model;
	}
	@GetMapping("/usuario/del/{id}")
	public ModelAndView delPlan(@PathVariable long id) {

		usuarioDAO.deleteById(id);

		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/usuarios");

		return model;
	}
	

}
