/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;

@Controller
@RequestMapping("/statistics/administrator")
public class AdministratorStatisticsController extends AbstractController {

	@Autowired
	private AdminService	adminService;


	// Constructors -----------------------------------------------------------

	public AdministratorStatisticsController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView statistics() {
		ModelAndView result;

		Map<String, Double[]> statistics = this.adminService.computeStatistics();
		Map<String, Double> statisticsRatios = this.adminService.computeStatisticsRatios();

		result = new ModelAndView("administrator/statistics");
		result.addObject("statistics", statistics);
		result.addObject("statisticsRatios", statisticsRatios);

		return result;
	}

}
