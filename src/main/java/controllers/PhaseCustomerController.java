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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PhaseService;

@Controller
@RequestMapping("/phase/customer")
public class PhaseCustomerController extends AbstractController {

	@Autowired
	private PhaseService	phaseService;


	// Constructors -----------------------------------------------------------

	public PhaseCustomerController() {
		super();
	}

	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listPhases(@RequestParam int fixUpTaskId) {
		ModelAndView result = null;

		return result;
	}

}
