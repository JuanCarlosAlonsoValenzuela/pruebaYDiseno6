
package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TutorialService;
import domain.HandyWorker;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController {

	@Autowired
	TutorialService	tutorialService;


	public TutorialController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		List<Sponsorship> sponsorships = new ArrayList<Sponsorship>();
		List<Tutorial> tutorials = new ArrayList<Tutorial>();
		List<HandyWorker> authors = new ArrayList<HandyWorker>();

		tutorials = this.tutorialService.findAll();
		authors = this.tutorialService.getAuthors(tutorials);
		sponsorships = this.tutorialService.getRandomSponsorShip(tutorials);

		result = new ModelAndView("tutorial/list");

		result.addObject("sponsorships", sponsorships);
		result.addObject("authors", authors);
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "tutorial/list.do");

		return result;

	}
}
