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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;
import domain.Customer;
import domain.HandyWorker;

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
		List<Customer> tenPercentCustomers = this.adminService.tenPercentMoreApplicationsCustomers().get("customers10PercentMoreApplications");
		List<HandyWorker> tenPercentHandyWorkers = this.adminService.tenPercentMoreApplicationsHandyWorker().get("handyWorkers10PercentMoreApplications");
		List<Customer> topThreeCustomers = this.adminService.top3Customers().get("topThreeCustomers");
		List<HandyWorker> topThreeHandyWorkers = this.adminService.top3HandyWorker().get("topThreeHandyWorkers");

		List<String> customersUsernames = new ArrayList<>();
		for (Customer c : tenPercentCustomers) {
			customersUsernames.add(c.getUserAccount().getUsername());
		}

		List<String> handyWorkersUsernames = new ArrayList<>();
		for (HandyWorker h : tenPercentHandyWorkers) {
			handyWorkersUsernames.add(h.getUserAccount().getUsername());
		}

		List<String> customers3Usernames = new ArrayList<>();
		for (Customer c : topThreeCustomers) {
			customers3Usernames.add(c.getUserAccount().getUsername());
		}

		List<String> handyWorkers3Usernames = new ArrayList<>();
		for (HandyWorker h : topThreeHandyWorkers) {
			handyWorkers3Usernames.add(h.getUserAccount().getUsername());
		}

		result = new ModelAndView("administrator/statistics");
		result.addObject("statistics", statistics);
		result.addObject("statisticsRatios", statisticsRatios);
		result.addObject("tenPercentCustomers", customersUsernames);
		result.addObject("tenPercentHandyWorkers", handyWorkersUsernames);
		result.addObject("topThreeCustomers", customers3Usernames);
		result.addObject("topThreeHandyWorkers", handyWorkers3Usernames);

		return result;
	}

}
