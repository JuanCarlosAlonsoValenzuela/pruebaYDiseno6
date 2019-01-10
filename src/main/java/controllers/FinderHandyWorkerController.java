
package controllers;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ConfigurationService;
import services.FinderService;
import services.HandyWorkerService;
import domain.Customer;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;

@Controller
@RequestMapping("/finder/handyWorker/")
public class FinderHandyWorkerController extends AbstractController {

	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private FinderService			finderService;
	@Autowired
	private ConfigurationService	configuarionService;


	// Constructors -----------------------------------------------------------

	public FinderHandyWorkerController() {
		super();
	}

	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView fixUpTasksList() {
		ModelAndView result;

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerService.getHandyWorkerByUsername(userAccount.getUsername());

		//Finder
		Finder finder = logguedHandyWorker.getFinder();

		//Current Date
		Date currentDate = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		Integer currentDay = calendar.get(Calendar.DATE);
		Integer currentMonth = calendar.get(Calendar.MONTH);
		Integer currentYear = calendar.get(Calendar.YEAR);
		Integer currentHour = calendar.get(Calendar.HOUR);

		//LastEdit Finder
		Date lasEdit = logguedHandyWorker.getFinder().getLastEdit();
		calendar.setTime(lasEdit);
		Integer lastEditDay = calendar.get(Calendar.DATE);
		Integer lastEditMonth = calendar.get(Calendar.MONTH);
		Integer lastEditYear = calendar.get(Calendar.YEAR);
		Integer lastEditHour = calendar.get(Calendar.HOUR);

		Integer time = this.configuarionService.getConfiguration().getTimeFinder();

		Map<FixUpTask, Customer> map;
		Collection<FixUpTask> fixUpTasks;

		String username = userAccount.getUsername();

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		if (currentDay.equals(lastEditDay) && currentMonth.equals(lastEditMonth) && currentYear.equals(lastEditYear) && lastEditHour < (currentHour + time)) {
			Integer numFinderResult = this.configuarionService.getConfiguration().getFinderResult();
			List<FixUpTask> fixUpTasksResult = finder.getFixUpTasks();
			fixUpTasks = new HashSet<FixUpTask>();

			if (fixUpTasksResult.size() > numFinderResult) {
				for (int i = 0; i < numFinderResult; i++) {
					fixUpTasks.add(fixUpTasksResult.get(i));
				}
				map = this.handyWorkerService.getFixUpTaksAndCustomer(fixUpTasks);
			} else {
				map = this.handyWorkerService.getFixUpTaksAndCustomer(fixUpTasksResult);

			}
			fixUpTasks = map.keySet();
		} else {

			fixUpTasks = new HashSet<FixUpTask>();
			map = this.handyWorkerService.getFixUpTaksAndCustomer(fixUpTasks);
			fixUpTasks = map.keySet();
		}

		result = new ModelAndView("handy-worker/finderResult");

		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("map", map);
		result.addObject("currentUsername", username);
		result.addObject("locale", locale);
		result.addObject("requestURI", "finder/handyWorker/list.do");

		return result;
	}
	//Create
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		UserAccount userAccount = LoginService.getPrincipal();
		HandyWorker logguedHandyWorker = this.handyWorkerService.getHandyWorkerByUsername(userAccount.getUsername());

		Finder finder = logguedHandyWorker.getFinder();

		Assert.notNull(finder);

		result = this.createEditModelAndView(finder);

		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Finder finder, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(finder);
		} else {
			try {
				Date date = new Date();
				finder.setLastEdit(date);
				this.finderService.save(finder);
				this.handyWorkerService.filterFixUpTasksByFinder();

				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		}
		return result;
	}

	//CreateEditModelAndView
	protected ModelAndView createEditModelAndView(Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Finder finder, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("handy-worker/finder");

		result.addObject("finder", finder);
		result.addObject("message", messageCode);

		return result;

	}

	//Clean Filter
	@RequestMapping(value = "/clean", method = RequestMethod.POST, params = "save")
	public ModelAndView save() {
		ModelAndView result;

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerService.getHandyWorkerByUsername(userAccount.getUsername());

		Finder finder = logguedHandyWorker.getFinder();

		finder.setCategory("");
		finder.setEndDate(null);
		finder.setKeyWord("");
		finder.setMaxPrice(0.0);
		finder.setMinPrice(0.0);
		finder.setStartDate(null);
		finder.setWarranty("");
		this.finderService.save(finder);
		this.handyWorkerService.filterFixUpTasksByFinder();

		result = new ModelAndView("redirect:list.do");

		return result;

	}
}
