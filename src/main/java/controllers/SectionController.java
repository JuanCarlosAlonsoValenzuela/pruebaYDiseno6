
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.SectionService;
import services.TutorialService;
import domain.HandyWorker;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping("/section")
public class SectionController extends AbstractController {

	@Autowired
	SectionService	sectionService;
	@Autowired
	TutorialService	tutorialService;


	public SectionController() {
		super();
	}

	@RequestMapping(value = "/anonymous/list", method = RequestMethod.GET)
	public ModelAndView listAnonymous(@RequestParam int tutorialId) {

		ModelAndView result;
		Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		List<Section> sections = tutorial.getSections();
		HandyWorker author = this.tutorialService.getAuthor(tutorial);
		Sponsorship sponsorship = this.tutorialService.getRandomSponsorShip(tutorial);
		Boolean canEdit = false;

		result = new ModelAndView("section/anonymous/list");

		result.addObject("canEdit", canEdit);
		result.addObject("author", author);
		result.addObject("sponsorship", sponsorship);
		result.addObject("tutorial", tutorial);
		result.addObject("sections", sections);
		result.addObject("requestURI", "section/anonymous/list.do");

		return result;

	}

	@RequestMapping(value = "/actor/list", method = RequestMethod.GET)
	public ModelAndView listActor(@RequestParam int tutorialId) {

		ModelAndView result;
		Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		List<Section> sections = tutorial.getSections();
		HandyWorker author = this.tutorialService.getAuthor(tutorial);
		Sponsorship sponsorship = this.tutorialService.getRandomSponsorShip(tutorial);
		Boolean canEdit = false;

		UserAccount userAccount = LoginService.getPrincipal();
		String username = userAccount.getUsername();
		canEdit = username.equals(author.getUserAccount().getUsername());

		result = new ModelAndView("section/actor/list");

		result.addObject("canEdit", canEdit);
		result.addObject("author", author);
		result.addObject("sponsorship", sponsorship);
		result.addObject("tutorial", tutorial);
		result.addObject("sections", sections);
		result.addObject("requestURI", "section/actor/list.do");

		return result;

	}
}
