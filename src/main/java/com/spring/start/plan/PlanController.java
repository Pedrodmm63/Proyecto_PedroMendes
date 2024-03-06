package com.spring.start.plan;

import java.util.HashSet;
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
import com.spring.start.dietas.DietaDAO;
import com.spring.start.entrenamientos.Entrenamiento;
import com.spring.start.entrenamientos.EntrenamientoDAO;
import com.spring.start.usuarios.Usuario;
import com.spring.start.usuarios.UsuarioDAO;

@Controller
public class PlanController {

	@Autowired
	DietaDAO dietaDAO;
	@Autowired
	PlanDAO planDAO;
	@Autowired
	EntrenamientoDAO entrenamientoDAO;
	@Autowired
	UsuarioDAO usuarioDAO;

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
	public ModelAndView plan(@PathVariable long id) {

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
		model.addObject("usuarios", usuarioDAO.findAll());
		model.addObject("entrenamientos", entrenamientoDAO.findAll());

		return model;
	}

	@PostMapping("/plan/save")
	public ModelAndView formTutoria(@ModelAttribute Plan plan) {

		if (plan.getDieta() != null) {
			Optional<Dieta> optionalDieta = dietaDAO.findById(plan.getDieta().getId());
			if (optionalDieta.isPresent()) {
				Dieta dieta = optionalDieta.get();
				plan.setDieta(dieta);
				if (!dieta.getPlanes().contains(plan)) {
					dieta.getPlanes().add(plan);
				}
			}
		}

		if (plan.getEntrenamiento() != null) {
			Optional<Entrenamiento> optionalEntrenamiento = entrenamientoDAO.findById(plan.getEntrenamiento().getId());
			if (optionalEntrenamiento.isPresent()) {
				Entrenamiento entrenamiento = optionalEntrenamiento.get();
				plan.setEntrenamiento(entrenamiento);

				if (!entrenamiento.getPlanes().contains(plan)) {
					entrenamiento.getPlanes().add(plan);
				}
			}
		}
		if (!plan.getUsuarios().isEmpty()) {
		    Set<Usuario> usuariosActualizados = new HashSet<Usuario>();
		    for (Usuario usuario : plan.getUsuarios()) {
		        Optional<Usuario> optionalUsuario = usuarioDAO.findById(usuario.getId());
		        if (optionalUsuario.isPresent()) {
		            Usuario usuarioExistente = optionalUsuario.get();
		            usuariosActualizados.add(usuarioExistente);
		            
		            if (!usuarioExistente.getPlanes().contains(plan)) {
		                usuarioExistente.getPlanes().add(plan);
		            }
		        }
		    }
		    plan.setUsuarios(usuariosActualizados);
		}

		planDAO.save(plan);

		ModelAndView model = new ModelAndView();
		model.addObject("planNuevo", plan);
		model.setViewName("redirect:/plan/nuevo/" + plan.getId());

		return model;
	}

	@GetMapping("plan/nuevo/{id}")
	public ModelAndView NuevoPlan(@PathVariable long id) {

		Plan plan = new Plan();
		ModelAndView model = new ModelAndView();

		List<Plan> planes = (List<Plan>) planDAO.findAll();

		Plan planNuevo = planDAO.findById(id).get();

		model.addObject("plan", plan);
		model.addObject("dietas", dietaDAO.findAll());
		model.addObject("planes", planes);
		model.addObject("planNuevo", planNuevo);
		model.addObject("entrenamiento", entrenamientoDAO.findAll());
		model.addObject("usuarios", usuarioDAO.findAll());
		model.setViewName("planes");

		return model;
	}

	@GetMapping("/plan/edit/{id}")
	public ModelAndView editPlan(@PathVariable long id) {

		ModelAndView model = new ModelAndView();

		Optional<Plan> planazo = planDAO.findById(id);
		if (planazo.isPresent()) {

			model.addObject("plan", planazo.get());
			model.addObject("dietas", dietaDAO.findAll());

			model.addObject("entrenamientos", entrenamientoDAO.findAll());
			model.addObject("usuarios", usuarioDAO.findAll());

			model.setViewName("formPlan");
		} else
			model.setViewName("redirect:/plan");

		return model;
	}

	@GetMapping("/plan/del/{id}")
	public ModelAndView delPlan(@PathVariable long id) {

		planDAO.deleteById(id);

		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/plan");

		return model;
	}

	@GetMapping("/plan/dieta/del/{idPlan}")
	public ModelAndView eliminarPlanDieta(@PathVariable long idPlan) {

		Optional<Plan> plan = planDAO.findById(idPlan);
		if (plan.isPresent()) {

			Plan planazo = plan.get();
			Dieta dieta = planazo.getDieta();
			planazo.setDieta(null);
			dieta.setPlanes(null);
			planDAO.save(planazo);
		}

		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/plan");

		return model;
	}

	@GetMapping("/plan/entrenamiento/del/{idPlan}")
	public ModelAndView eliminarPlanEntrenamiento(@PathVariable long idPlan) {

		Optional<Plan> plan = planDAO.findById(idPlan);
		if (plan.isPresent()) {

			Plan planazo = plan.get();
			Entrenamiento entrenamiento = planazo.getEntrenamiento();
			planazo.setEntrenamiento(null);
			entrenamiento.setPlanes(null);
			planDAO.save(planazo);
		}

		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/plan");

		return model;
	}
	/*@GetMapping("/plan/entrenamiento/del/{idPlan}")
	public ModelAndView eliminarPlanUsuario(@PathVariable long idPlan) {

		Optional<Plan> plan = planDAO.findById(idPlan);
		if (plan.isPresent()) {

			Plan planazo = plan.get();
			Usuario usuario = (Usuario) planazo.getUsuarios();
			planazo.setEntrenamiento(null);
			usuario.setPlanes(null);
			planDAO.save(planazo);
		}

		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/plan");

		return model;
	}*/
}
