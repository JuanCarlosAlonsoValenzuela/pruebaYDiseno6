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

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.CustomerService;
import services.FixUpTaskService;
import services.WarrantyService;
import domain.Category;
import domain.FixUpTask;
import domain.Warranty;

@Controller
@RequestMapping("/fixUpTask/customer")
public class FixUpTaskCustomerController extends AbstractController {

	@Autowired
	private CustomerService		customerService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private CategoryService		categoryService;
	@Autowired
	private WarrantyService		warrantyService;


	// Constructors -----------------------------------------------------------

	public FixUpTaskCustomerController() {
		super();
	}

	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView fixUpTasksList() {
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;

		fixUpTasks = this.customerService.showFixUpTasks();

		result = new ModelAndView("customer/fixUpTask");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("requestURI", "fixUpTask/customer/list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView fixUpTaskShow(@RequestParam int fixUpTaskId) {
		ModelAndView result;

		FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);

		result = this.createEditModelAndView(fixUpTask);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView fixUpTaskCreate() {
		ModelAndView result;

		FixUpTask fixUpTask = this.fixUpTaskService.create();

		result = this.createEditModelAndView(fixUpTask);

		return result;
	}

	protected ModelAndView createEditModelAndView(FixUpTask fixUpTask) {
		ModelAndView result;
		Collection<Category> categories;
		Collection<Warranty> warranties;

		categories = this.categoryService.findAll();
		warranties = this.warrantyService.findAll();

		result = new ModelAndView("customer/createFixUpTask");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("categories", categories);
		result.addObject("warranties", warranties);

		return result;
	}

	protected ModelAndView createEditModelAndView(FixUpTask fixUpTask, String message) {
		ModelAndView result;
		Collection<Category> categories;
		Collection<Warranty> warranties;

		categories = this.categoryService.findAll();
		warranties = this.warrantyService.findAll();

		result = new ModelAndView("customer/createFixUpTask");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("categories", categories);
		result.addObject("warranties", warranties);

		result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/saveFixUpTask", method = RequestMethod.POST, params = "create")
	public ModelAndView saveComment(@Valid FixUpTask fixUpTask, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(fixUpTask);
		} else {
			try {
				this.customerService.saveFixUpTask(fixUpTask);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				System.out.println("CATCH");
				result = this.createEditModelAndView(fixUpTask, "operation.error");
			}
		}

		return result;
	}
}
