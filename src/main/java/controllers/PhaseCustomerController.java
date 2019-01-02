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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FixUpTaskService;
import domain.FixUpTask;
import domain.Phase;

@Controller
@RequestMapping("/phase/customer")
public class PhaseCustomerController extends AbstractController {

	@Autowired
	private FixUpTaskService	FixUpTaskService;


	// Constructors -----------------------------------------------------------

	public PhaseCustomerController() {
		super();
	}

	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listPhases(@RequestParam int fixUpTaskId) {
		ModelAndView result;

		FixUpTask fixUpTask = this.FixUpTaskService.findOne(fixUpTaskId);
		List<Phase> phases = (List<Phase>) fixUpTask.getPhases();

		result = new ModelAndView("customer/phases");
		result.addObject("phases", phases);
		result.addObject("requestURI", "phase/customer/list.do");

		return result;
	}

}
