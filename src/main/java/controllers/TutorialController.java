
package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.HandyWorkerService;
import services.TutorialService;
import domain.HandyWorker;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController {

	@Autowired
	private TutorialService		tutorialService;
	@Autowired
	private HandyWorkerService	handyWorkerService;


	public TutorialController() {
		super();
	}

	@RequestMapping(value = "/anonymous/list", method = RequestMethod.GET)
	public ModelAndView listAnonymous() {

		ModelAndView result;
		List<Sponsorship> sponsorships = new ArrayList<Sponsorship>();
		List<Tutorial> tutorials = new ArrayList<Tutorial>();
		List<HandyWorker> authors = new ArrayList<HandyWorker>();
		Boolean noAuthor = true;
		Boolean canEdit = false;

		tutorials = this.tutorialService.findAll();
		authors = this.tutorialService.getAuthors(tutorials);
		sponsorships = this.tutorialService.getRandomSponsorShips(tutorials);

		result = new ModelAndView("tutorial/anonymous/list");

		result.addObject("canEdit", canEdit);
		result.addObject("noAuthor", noAuthor);
		result.addObject("sponsorships", sponsorships);
		result.addObject("authors", authors);
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "tutorial/anonymous/list.do");

		return result;

	}

	@RequestMapping(value = "/anonymous/showPictures", method = RequestMethod.GET)
	public ModelAndView showPictures(@RequestParam int tutorialId) {

		ModelAndView result;
		Tutorial tutorial = this.tutorialService.findOne(tutorialId);

		result = new ModelAndView("tutorial/anonymous/showPictures");
		result.addObject("pictures", tutorial.getPictures());
		result.addObject("requestURI", "tutorial/anonymous/showPictures.do");

		return result;

	}

	@RequestMapping(value = "/actor/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		List<Sponsorship> sponsorships = new ArrayList<Sponsorship>();
		List<Tutorial> tutorials = new ArrayList<Tutorial>();
		List<HandyWorker> authors = new ArrayList<HandyWorker>();
		Boolean noAuthor = true;
		Boolean canEdit = false;
		String username = LoginService.getPrincipal().getUsername();

		tutorials = this.tutorialService.findAll();
		authors = this.tutorialService.getAuthors(tutorials);
		sponsorships = this.tutorialService.getRandomSponsorShips(tutorials);

		result = new ModelAndView("tutorial/actor/list");

		result.addObject("username", username);
		result.addObject("canEdit", canEdit);
		result.addObject("noAuthor", noAuthor);
		result.addObject("sponsorships", sponsorships);
		result.addObject("authors", authors);
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "tutorial/actor/list.do");

		return result;

	}

	@RequestMapping(value = "/anonymous/listHandyTutorials", method = RequestMethod.GET)
	public ModelAndView listHandyTutorialsAnonymous(@RequestParam int handyId) {

		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.findOne(handyId);
		Assert.notNull(handyWorker);
		List<Sponsorship> sponsorships = new ArrayList<Sponsorship>();
		List<Tutorial> tutorials = new ArrayList<Tutorial>();
		List<HandyWorker> authors = new ArrayList<HandyWorker>();
		Boolean noAuthor = false;
		Boolean canEdit = false;

		tutorials = handyWorker.getTutorials();
		authors = this.tutorialService.getAuthors(tutorials);
		sponsorships = this.tutorialService.getRandomSponsorShips(tutorials);

		result = new ModelAndView("tutorial/anonymous/listHandyTutorials");

		result.addObject("handyId", handyId);
		result.addObject("canEdit", canEdit);
		result.addObject("noAuthor", noAuthor);
		result.addObject("sponsorships", sponsorships);
		result.addObject("authors", authors);
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "tutorial/anonymous/listHandyTutorials.do");

		return result;

	}

	@RequestMapping(value = "/handyWorker/listHandyTutorials", method = RequestMethod.GET)
	public ModelAndView listHandyTutorialsActor() {

		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Assert.notNull(handyWorker);
		List<Sponsorship> sponsorships = new ArrayList<Sponsorship>();
		List<Tutorial> tutorials = new ArrayList<Tutorial>();
		List<HandyWorker> authors = new ArrayList<HandyWorker>();
		Boolean noAuthor = false;
		Boolean canEdit = false;
		String username = LoginService.getPrincipal().getUsername();

		canEdit = LoginService.getPrincipal().getUsername().equals(handyWorker.getUserAccount().getUsername());

		tutorials = handyWorker.getTutorials();
		authors = this.tutorialService.getAuthors(tutorials);
		sponsorships = this.tutorialService.getRandomSponsorShips(tutorials);

		result = new ModelAndView("tutorial/handyWorker/listHandyTutorials");

		result.addObject("username", username);
		result.addObject("canEdit", canEdit);
		result.addObject("noAuthor", noAuthor);
		result.addObject("sponsorships", sponsorships);
		result.addObject("authors", authors);
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "tutorial/handyWorker/listHandyTutorials.do");

		return result;

	}

	@RequestMapping(value = "/handyWorker/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Tutorial tutorial = this.tutorialService.create();

		result = this.createEditModelAndView(tutorial);

		return result;
	}

	@RequestMapping(value = "/handyWorker/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int tutorialId) {
		ModelAndView result;
		Tutorial tutorial = this.tutorialService.findOne(tutorialId);

		result = this.createEditModelAndView(tutorial);
		result.addObject("tutorialId", tutorialId);

		return result;
	}

	@RequestMapping(value = "/handyWorker/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Tutorial tutorial, BindingResult binding, @RequestParam String newPictures) {
		ModelAndView result;
		List<String> pic = new ArrayList<String>();
		if (!newPictures.trim().equals("")) {
			pic.add(newPictures);
		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(tutorial);
		} else {
			try {
				tutorial.setPictures(pic);
				this.handyWorkerService.createTutorial(tutorial);
				result = new ModelAndView("redirect:listHandyTutorials.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(tutorial, "note.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/handyWorker/edit", method = RequestMethod.POST, params = "edit")
	public ModelAndView edit(@Valid Tutorial tutorial, BindingResult binding, @RequestParam String newPictures) {
		ModelAndView result;
		List<String> pic = new ArrayList<String>();
		if (!newPictures.trim().equals("")) {
			pic.add(newPictures);
		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(tutorial);
		} else {
			try {
				tutorial.setPictures(pic);
				this.handyWorkerService.updateTutorial(tutorial);
				result = new ModelAndView("redirect:listHandyTutorials.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(tutorial, "note.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/handyWorker/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid Tutorial tutorial, BindingResult binding) {
		ModelAndView result;

		try {
			this.handyWorkerService.deleteTutorial(tutorial);
			result = new ModelAndView("redirect:listHandyTutorials.do");

		} catch (Throwable oops) {
			result = this.createEditModelAndView(tutorial, "note.commit.error");
		}

		return result;
	}
	protected ModelAndView createEditModelAndView(Tutorial tutorial) {
		ModelAndView result;

		result = this.createEditModelAndView(tutorial, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Tutorial tutorial, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("tutorial/handyWorker/edit");
		result.addObject("tutorial", tutorial);

		result.addObject("message", messageCode);

		return result;
	}
}
