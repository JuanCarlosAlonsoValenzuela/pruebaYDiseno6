
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
import services.HandyWorkerService;
import services.MiscellaneousRecordService;
import domain.Curriculum;
import domain.HandyWorker;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/curriculum")
public class MiscellaneousRecordController extends AbstractController {

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;
	@Autowired
	private HandyWorkerService			handyWorkerService;
	@Autowired
	private CurriculumService			curriculumService;


	public MiscellaneousRecordController() {
		super();
	}

	@RequestMapping(value = "/handyWorker/addMiscellaneousRecord", method = RequestMethod.GET)
	public ModelAndView addMiscellaneousRecord() {

		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();

		result = this.createEditModelAndView(miscellaneousRecord);
		return result;

	}

	@RequestMapping(value = "/handyWorker/editMiscellaneousRecord", method = RequestMethod.GET)
	public ModelAndView editMiscellaneousRecord(@RequestParam int miscellaneousRecordId) {

		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);

		result = this.createEditModelAndView(miscellaneousRecord);
		return result;

	}

	@RequestMapping(value = "/anonymous/showMiscellaneousComments", method = RequestMethod.GET)
	public ModelAndView showMiscellaneousComments(@RequestParam int miscellaneousRecordId, @RequestParam boolean canEdit, @RequestParam int handyId) {

		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);

		result = new ModelAndView("curriculum/anonymous/showMiscellaneousComments");
		result.addObject("comments", miscellaneousRecord.getComments());
		result.addObject("requestURI", "curriculum/anonymous/showMiscellaneousComments.do");
		result.addObject("canEdit", canEdit);
		result.addObject("handyId", handyId);
		return result;

	}

	@RequestMapping(value = "/handyWorker/editMiscellaneousRecord", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid MiscellaneousRecord miscellaneousRecord, BindingResult binding, @RequestParam String newComment) {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum curriculum = handyWorker.getCurriculum();
		List<MiscellaneousRecord> m = curriculum.getMiscellaneousRecords();
		List<String> c = new ArrayList<String>();

		if (!newComment.trim().isEmpty() && newComment.trim().length() > 1) {
			c.add(newComment.substring(1));
		}
		miscellaneousRecord.setComments(c);

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(miscellaneousRecord);
		} else {
			try {
				m.add(miscellaneousRecord);
				curriculum.setMiscellaneousRecords(m);
				this.handyWorkerService.editCurriculum(curriculum);
				result = new ModelAndView("redirect:show.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(miscellaneousRecord, "note.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/handyWorker/editMiscellaneousRecord", method = RequestMethod.POST, params = "edit")
	public ModelAndView edit(@Valid MiscellaneousRecord miscellaneousRecord, BindingResult binding, @RequestParam String newComment) {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum curriculum = handyWorker.getCurriculum();
		List<MiscellaneousRecord> m = curriculum.getMiscellaneousRecords();
		List<String> comments = miscellaneousRecord.getComments();
		List<String> c = new ArrayList<String>();

		for (String s : comments) {
			if (!s.trim().isEmpty()) {
				c.add(s);
			}
		}

		if (!newComment.trim().isEmpty() && newComment.trim().length() > 1) {
			c.add(newComment.substring(1));
			miscellaneousRecord.setComments(c);
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(miscellaneousRecord);
		} else {
			try {
				Assert.isTrue(m.contains(miscellaneousRecord));
				this.miscellaneousRecordService.save(miscellaneousRecord);
				result = new ModelAndView("redirect:show.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(miscellaneousRecord, "note.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/handyWorker/editMiscellaneousRecord", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid MiscellaneousRecord miscellaneousRecord, BindingResult binding) {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum curriculum = handyWorker.getCurriculum();
		List<MiscellaneousRecord> m = curriculum.getMiscellaneousRecords();

		try {
			Assert.isTrue(m.contains(miscellaneousRecord));
			m.remove(miscellaneousRecord);
			curriculum.setMiscellaneousRecords(m);
			this.curriculumService.save(curriculum);
			this.miscellaneousRecordService.delete(miscellaneousRecord);
			result = new ModelAndView("redirect:show.do");

		} catch (Throwable oops) {
			result = this.createEditModelAndView(miscellaneousRecord, "note.commit.error");
		}

		return result;
	}
	protected ModelAndView createEditModelAndView(MiscellaneousRecord miscellaneousRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(MiscellaneousRecord miscellaneousRecord, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curriculum/handyWorker/editMiscellaneousRecord");
		result.addObject("miscellaneousRecord", miscellaneousRecord);
		result.addObject("message", messageCode);

		return result;
	}

}
