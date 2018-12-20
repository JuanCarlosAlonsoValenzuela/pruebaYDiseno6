/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/services")
public class CustomerController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/general-services")
	public ModelAndView action1() {
		ModelAndView result;

		result = new ModelAndView("services/general-services");

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/repairs")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("services/repairs");

		return result;
	}

	// Action-3 ---------------------------------------------------------------		

	@RequestMapping("/installations")
	public ModelAndView action3() {
		ModelAndView result;

		result = new ModelAndView("services/installations");

		return result;
	}

	// Action-4 ---------------------------------------------------------------		

	@RequestMapping("/outdoor-services")
	public ModelAndView action4() {
		ModelAndView result;

		result = new ModelAndView("services/outdoor-services");

		return result;
	}

	// Action-5 ---------------------------------------------------------------		

	@RequestMapping("/indoor-services")
	public ModelAndView action5() {
		ModelAndView result;

		result = new ModelAndView("services/indoor-services");

		return result;
	}

	@RequestMapping("/carpentry")
	public ModelAndView action6() {
		ModelAndView result;

		result = new ModelAndView("services/carpentry");

		return result;
	}

	@RequestMapping("/cleaning")
	public ModelAndView action7() {
		ModelAndView result;

		result = new ModelAndView("services/cleaning");

		return result;
	}

	@RequestMapping("/painting")
	public ModelAndView action8() {
		ModelAndView result;

		result = new ModelAndView("services/painting");

		return result;
	}

	@RequestMapping("/moving")
	public ModelAndView action9() {
		ModelAndView result;

		result = new ModelAndView("services/moving");

		return result;
	}

	@RequestMapping("/ceiling-repair")
	public ModelAndView action10() {
		ModelAndView result;

		result = new ModelAndView("services/ceiling-repairs");

		return result;
	}

	@RequestMapping("/fence-repair")
	public ModelAndView action11() {
		ModelAndView result;

		result = new ModelAndView("services/fence-repair");

		return result;
	}

	@RequestMapping("/plumbing-repairs")
	public ModelAndView action12() {
		ModelAndView result;

		result = new ModelAndView("services/plumbing-repairs");

		return result;
	}

	@RequestMapping("/sprinkler-repair")
	public ModelAndView action13() {
		ModelAndView result;

		result = new ModelAndView("services/sprinkler-repair");

		return result;
	}

	@RequestMapping("/window-repair")
	public ModelAndView action14() {
		ModelAndView result;

		result = new ModelAndView("services/window-repair");

		return result;
	}

	@RequestMapping("/lamp-repair")
	public ModelAndView action15() {
		ModelAndView result;

		result = new ModelAndView("services/lamp-repair");

		return result;
	}

}
