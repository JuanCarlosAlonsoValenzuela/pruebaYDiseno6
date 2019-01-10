
package controllers;

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
import services.CurriculumService;
import services.HandyWorkerService;
import services.PersonalRecordService;
import domain.Curriculum;
import domain.HandyWorker;
import domain.PersonalRecord;

@Controller
@RequestMapping("/curriculum")
public class CurriculumController extends AbstractController {

	@Autowired
	private CurriculumService		curriculumService;
	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private PersonalRecordService	personalRecordService;


	public CurriculumController() {
		super();
	}

	@RequestMapping(value = "/anonymous/show", method = RequestMethod.GET)
	public ModelAndView showCurriculumAnonymous(@RequestParam int handyId) {

		ModelAndView result;
		HandyWorker h = this.handyWorkerService.findOne(handyId);
		Curriculum curriculum = h.getCurriculum();
		Boolean canEdit = false;

		result = new ModelAndView("curriculum/anonymous/show");

		result.addObject("handyId", handyId);
		result.addObject("canEdit", canEdit);
		result.addObject("curriculum", curriculum);
		result.addObject("requestURI", "curriculum/anonymous/show.do?handyId=" + handyId);

		return result;

	}

	@RequestMapping(value = "/handyWorker/show", method = RequestMethod.GET)
	public ModelAndView showCurriculumHandyWorker() {

		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum curriculum = handyWorker.getCurriculum();
		Boolean canEdit;

		try {
			Assert.isTrue(handyWorker.getCurriculum().equals(curriculum));
			canEdit = true;
		} catch (Exception e) {
			canEdit = false;
		}

		result = new ModelAndView("curriculum/handyWorker/show");

		result.addObject("handyId", handyWorker.getId());
		result.addObject("canEdit", canEdit);
		result.addObject("curriculum", curriculum);
		result.addObject("requestURI", "curriculum/handyWorker/show.do");

		return result;

	}

	@RequestMapping(value = "/handyWorker/add", method = RequestMethod.GET)
	public ModelAndView addCurriculum() {

		ModelAndView result;
		PersonalRecord personalRecord = this.personalRecordService.create();

		result = this.createEditModelAndView(personalRecord);
		return result;

	}
	@RequestMapping(value = "/handyWorker/editPersonalRecord", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int personalRecordId) {

		ModelAndView result;
		PersonalRecord personalRecord = this.personalRecordService.findOne(personalRecordId);

		result = this.createEditModelAndView(personalRecord);
		return result;

	}

	@RequestMapping(value = "/handyWorker/editPersonalRecord", method = RequestMethod.POST, params = "save")
	public ModelAndView addCurriculum(@Valid PersonalRecord personalRecord, BindingResult binding) {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Boolean hasNoCurriculum;

		try {
			Assert.notNull(handyWorker.getCurriculum());
			hasNoCurriculum = false;
		} catch (Exception e) {
			hasNoCurriculum = true;
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(personalRecord);
		} else {
			try {
				Assert.isTrue(personalRecord.getId() == 0 && hasNoCurriculum);
				Curriculum c = this.curriculumService.create();
				c.setPersonalRecord(personalRecord);
				this.handyWorkerService.addCurriculum(c);
				result = new ModelAndView("redirect:show.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(personalRecord, "note.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/handyWorker/editPersonalRecord", method = RequestMethod.POST, params = "edit")
	public ModelAndView editPersonalRecord(@Valid PersonalRecord personalRecord, BindingResult binding) {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum c = handyWorker.getCurriculum();

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(personalRecord);
		} else {
			try {
				Assert.notNull(c);
				Assert.isTrue(c.getPersonalRecord().getId() == personalRecord.getId());
				c.setPersonalRecord(personalRecord);
				this.curriculumService.save(c);
				result = new ModelAndView("redirect:show.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(personalRecord, "note.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(PersonalRecord personalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(personalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(PersonalRecord personalRecord, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curriculum/handyWorker/editPersonalRecord");
		result.addObject("personalRecord", personalRecord);
		result.addObject("message", messageCode);

		return result;
	}
}
