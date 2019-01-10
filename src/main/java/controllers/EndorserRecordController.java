
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
import services.CurriculumService;
import services.EndorserRecordService;
import services.HandyWorkerService;
import domain.Curriculum;
import domain.EndorserRecord;
import domain.HandyWorker;

@Controller
@RequestMapping("/curriculum")
public class EndorserRecordController extends AbstractController {

	@Autowired
	private EndorserRecordService	endorserRecordService;
	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private CurriculumService		curriculumService;


	public EndorserRecordController() {
		super();
	}

	@RequestMapping(value = "/handyWorker/addEndorserRecord", method = RequestMethod.GET)
	public ModelAndView addEndorserRecord() {

		ModelAndView result;
		EndorserRecord endorserRecord = this.endorserRecordService.create();

		result = this.createEditModelAndView(endorserRecord);
		return result;

	}

	@RequestMapping(value = "/handyWorker/editEndorserRecord", method = RequestMethod.GET)
	public ModelAndView editEndorserRecord(@RequestParam int endorserRecordId) {

		ModelAndView result;
		EndorserRecord endorserRecord = this.endorserRecordService.findOne(endorserRecordId);

		result = this.createEditModelAndView(endorserRecord);
		return result;

	}

	@RequestMapping(value = "/anonymous/showEndorserComments", method = RequestMethod.GET)
	public ModelAndView showEndorserComments(@RequestParam int endorserRecordId, @RequestParam boolean canEdit, @RequestParam int handyId) {

		ModelAndView result;
		EndorserRecord endorserRecord = this.endorserRecordService.findOne(endorserRecordId);

		result = new ModelAndView("curriculum/anonymous/showEndorserComments");
		result.addObject("comments", endorserRecord.getComments());
		result.addObject("requestURI", "curriculum/anonymous/showEndorserComments.do");
		result.addObject("canEdit", canEdit);
		result.addObject("handyId", handyId);
		return result;

	}

	@RequestMapping(value = "/handyWorker/editEndorserRecord", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid EndorserRecord endorserRecord, BindingResult binding, @RequestParam String newComment) {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum curriculum = handyWorker.getCurriculum();
		List<EndorserRecord> e = curriculum.getEndorserRecords();
		List<String> c = new ArrayList<String>();

		if (!newComment.trim().isEmpty() && newComment.trim().length() > 1) {
			c.add(newComment.substring(1));
		}
		endorserRecord.setComments(c);

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(endorserRecord);
		} else {
			try {
				e.add(endorserRecord);
				curriculum.setEndorserRecords(e);
				this.handyWorkerService.editCurriculum(curriculum);
				result = new ModelAndView("redirect:show.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(endorserRecord, "note.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/handyWorker/editEndorserRecord", method = RequestMethod.POST, params = "edit")
	public ModelAndView edit(@Valid EndorserRecord endorserRecord, BindingResult binding, @RequestParam String newComment) {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum curriculum = handyWorker.getCurriculum();
		List<EndorserRecord> e = curriculum.getEndorserRecords();
		List<String> comments = endorserRecord.getComments();
		List<String> c = new ArrayList<String>();

		for (String s : comments) {
			if (!s.trim().isEmpty()) {
				c.add(s);
			}
		}

		if (!newComment.trim().isEmpty() && newComment.trim().length() > 1) {
			c.add(newComment.substring(1));
			endorserRecord.setComments(c);
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(endorserRecord);
		} else {
			try {
				Assert.isTrue(e.contains(endorserRecord));
				this.endorserRecordService.save(endorserRecord);
				result = new ModelAndView("redirect:show.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(endorserRecord, "note.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/handyWorker/editEndorserRecord", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid EndorserRecord endorserRecord, BindingResult binding) {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum curriculum = handyWorker.getCurriculum();
		List<EndorserRecord> e = curriculum.getEndorserRecords();

		try {
			Assert.isTrue(e.contains(endorserRecord));
			e.remove(endorserRecord);
			curriculum.setEndorserRecords(e);
			this.curriculumService.save(curriculum);
			this.endorserRecordService.delete(endorserRecord);
			result = new ModelAndView("redirect:show.do");

		} catch (Throwable oops) {
			result = this.createEditModelAndView(endorserRecord, "note.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(EndorserRecord endorserRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(endorserRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(EndorserRecord endorserRecord, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curriculum/handyWorker/editEndorserRecord");
		result.addObject("endorserRecord", endorserRecord);
		result.addObject("message", messageCode);

		return result;
	}
}
