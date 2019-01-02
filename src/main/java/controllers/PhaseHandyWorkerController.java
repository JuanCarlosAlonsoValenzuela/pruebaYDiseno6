
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.FixUpTaskService;
import services.PhaseService;
import domain.Application;
import domain.FixUpTask;
import domain.Phase;

@Controller
@RequestMapping("/phase/handyWorker/")
public class PhaseHandyWorkerController extends AbstractController {

	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private PhaseService		phaseService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Constructors -----------------------------------------------------------

	public PhaseHandyWorkerController() {
		super();
	}

	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView phasesList(@RequestParam int applicationId) {
		ModelAndView result;

		Application application = this.applicationService.findOne(applicationId);
		FixUpTask fixUpTask = application.getFixUpTask();
		Collection<Phase> phases = fixUpTask.getPhases();

		result = new ModelAndView("handy-worker/workPlan");

		result.addObject("phases", phases);
		result.addObject("applicationId", applicationId);
		result.addObject("requestURI", "phase/handyWorker/list.do");

		return result;

	}

	//Create
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView createAndEdit(@RequestParam int phaseId, @RequestParam int applicationId) {
		ModelAndView result;
		Phase phase;

		if (phaseId == 0) {
			phase = this.phaseService.create();
		} else {
			phase = this.phaseService.findOne(phaseId);
			Assert.notNull(phase);
		}
		result = this.createEditModelAndView(phase);

		return result;
	}
	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Phase phase, BindingResult binding, @Valid int applicationId) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(phase);
		} else {
			try {
				if (phase.getId() == 0) {
					this.phaseService.saveAndUpdateFixUpTask(phase, applicationId);
				} else {
					this.phaseService.saveWithPreviousCheck(phase, applicationId);
				}
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(phase, "phase.commit.error");
			}
		}
		result.addObject("applicationId", applicationId);
		return result;
	}
	//Delete
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Phase phase, BindingResult binding, @Valid int applicationId) {
		ModelAndView result;

		try {

			Application application = this.applicationService.findOne(applicationId);
			FixUpTask fixUpTask = application.getFixUpTask();
			Collection<Phase> phases = fixUpTask.getPhases();
			phases.remove(phase);
			fixUpTask.setPhases(phases);
			this.fixUpTaskService.save(fixUpTask);
			this.phaseService.delete(phase);
			result = new ModelAndView("redirect:list.do");
			result.addObject("applicationId", applicationId);

		} catch (Throwable oops) {
			result = this.createEditModelAndView(phase, "phase.commit.error");
			result.addObject("applicationId", applicationId);
		}
		return result;
	}
	//CreateEditModelAndView
	protected ModelAndView createEditModelAndView(Phase phase) {
		ModelAndView result;

		result = this.createEditModelAndView(phase, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(Phase phase, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("handy-worker/workPlanEdit");

		result.addObject("phase", phase);
		result.addObject("message", messageCode);

		return result;

	}
}
