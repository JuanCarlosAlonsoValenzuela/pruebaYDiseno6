
package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.HandyWorkerService;
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
	private SectionService		sectionService;
	@Autowired
	private TutorialService		tutorialService;
	@Autowired
	private HandyWorkerService	handyWorkerService;


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

	@RequestMapping(value = "/anonymous/showPictures", method = RequestMethod.GET)
	public ModelAndView showPictures(@RequestParam int sectionId) {

		ModelAndView result;
		Section section = this.sectionService.findOne(sectionId);

		result = new ModelAndView("section/anonymous/showPictures");
		result.addObject("pictures", section.getSectionPictures());
		result.addObject("requestURI", "section/anonymous/showPictures.do");

		return result;

	}
	@RequestMapping(value = "/handyWorker/list", method = RequestMethod.GET)
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

		result = new ModelAndView("section/handyWorker/list");

		result.addObject("canEdit", canEdit);
		result.addObject("author", author);
		result.addObject("sponsorship", sponsorship);
		result.addObject("tutorial", tutorial);
		result.addObject("sections", sections);
		result.addObject("requestURI", "section/handyWorker/list.do");

		return result;

	}

	@RequestMapping(value = "/handyWorker/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int tutorialId) {
		ModelAndView result;
		Section section = this.sectionService.create();

		result = this.createEditModelAndView(section);
		result.addObject("tutorialId", tutorialId);
		return result;
	}

	@RequestMapping(value = "/handyWorker/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int sectionId, @RequestParam int tutorialId) {
		ModelAndView result;
		Section section = this.sectionService.findOne(sectionId);

		result = this.createEditModelAndView(section);
		result.addObject("pictures", section.getSectionPictures());
		result.addObject("tutorialId", tutorialId);
		return result;
	}

	@RequestMapping(value = "/handyWorker/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Section section, BindingResult binding, @RequestParam String newPictures, @RequestParam int tutorialId) {
		ModelAndView result;
		List<String> pic = new ArrayList<String>();
		Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		if (!newPictures.trim().equals("")) {
			pic.add(newPictures);
		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(section);
			result.addObject("tutorialId", tutorialId);
		} else {
			try {
				section.setSectionPictures(pic);
				this.handyWorkerService.createSection(section, tutorial);
				result = new ModelAndView("redirect:list.do?tutorialId=" + tutorialId);

			} catch (Throwable oops) {
				result = this.createEditModelAndView(section, "note.commit.error");
				result.addObject("tutorialId", tutorialId);
			}
		}
		return result;
	}

	@RequestMapping(value = "/handyWorker/edit", method = RequestMethod.POST, params = "edit")
	public ModelAndView edit(@Valid Section section, BindingResult binding, @RequestParam String newPictures, @RequestParam int tutorialId) {
		ModelAndView result;
		List<String> pic = new ArrayList<String>();
		Section sec = this.sectionService.findOne(section.getId());
		Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		if (!newPictures.trim().equals("")) {
			pic.add(newPictures);
		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(section);
			result.addObject("tutorialId", tutorialId);
			result.addObject("pictures", sec.getSectionPictures());
		} else {
			try {
				section.setSectionPictures(pic);
				this.handyWorkerService.updateSection(section, tutorial);
				result = new ModelAndView("redirect:list.do?tutorialId=" + tutorialId);

			} catch (Throwable oops) {
				result = this.createEditModelAndView(section, "note.commit.error");
				result.addObject("tutorialId", tutorialId);
				result.addObject("pictures", sec.getSectionPictures());
			}
		}
		return result;
	}

	@RequestMapping(value = "/handyWorker/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid Section section, BindingResult binding, @RequestParam int tutorialId) {
		ModelAndView result;
		Section sec = this.sectionService.findOne(section.getId());
		Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		try {
			this.handyWorkerService.deleteSection(section, tutorial);
			result = new ModelAndView("redirect:list.do?tutorialId=" + tutorialId);

		} catch (Throwable oops) {
			result = this.createEditModelAndView(section, "note.commit.error");
			result.addObject("tutorialId", tutorialId);
			result.addObject("pictures", sec.getSectionPictures());
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(Section section) {
		ModelAndView result;

		result = this.createEditModelAndView(section, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Section section, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("section/handyWorker/edit");
		result.addObject("section", section);

		result.addObject("message", messageCode);

		return result;
	}
}
