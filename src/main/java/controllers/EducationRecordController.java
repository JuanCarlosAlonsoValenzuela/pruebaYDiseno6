
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
import services.EducationRecordService;
import services.HandyWorkerService;
import domain.Curriculum;
import domain.EducationRecord;
import domain.HandyWorker;

@Controller
@RequestMapping("/curriculum")
public class EducationRecordController extends AbstractController {

	@Autowired
	private EducationRecordService	educationRecordService;
	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private CurriculumService		curriculumService;


	//Constructor
	public EducationRecordController() {
		super();
	}

	@RequestMapping(value = "/handyWorker/addEducationRecord", method = RequestMethod.GET)
	public ModelAndView addEducationRecord() {

		ModelAndView result;
		EducationRecord educationRecord = this.educationRecordService.create();

		result = this.createEditModelAndView(educationRecord);
		return result;

	}

	@RequestMapping(value = "/handyWorker/editEducationRecord", method = RequestMethod.GET)
	public ModelAndView editEducationRecord(@RequestParam int educationRecordId) {

		ModelAndView result;
		EducationRecord educationRecord = this.educationRecordService.findOne(educationRecordId);

		result = this.createEditModelAndView(educationRecord);
		return result;

	}

	@RequestMapping(value = "/anonymous/showEducationComments", method = RequestMethod.GET)
	public ModelAndView showEducationComments(@RequestParam int educationRecordId, @RequestParam boolean canEdit, @RequestParam int handyId) {

		ModelAndView result;
		EducationRecord educationRecord = this.educationRecordService.findOne(educationRecordId);

		result = new ModelAndView("curriculum/anonymous/showEducationComments");
		result.addObject("comments", educationRecord.getComments());
		result.addObject("requestURI", "curriculum/anonymous/showEducationComments.do");
		result.addObject("canEdit", canEdit);
		result.addObject("handyId", handyId);

		return result;

	}

	@RequestMapping(value = "/handyWorker/editEducationRecord", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid EducationRecord educationRecord, BindingResult binding, @RequestParam String newComment) {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum curriculum = handyWorker.getCurriculum();
		List<EducationRecord> e = curriculum.getEducationRecords();
		List<String> c = new ArrayList<String>();

		if (!newComment.trim().isEmpty() && newComment.trim().length() > 1) {
			c.add(newComment.substring(1));
		}
		educationRecord.setComments(c);

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(educationRecord);
		} else {
			try {
				if (educationRecord.getEndDateStudy() != null) {
					Assert.isTrue(educationRecord.getStartDateStudy().before(educationRecord.getEndDateStudy()));
				}
				e.add(educationRecord);
				curriculum.setEducationRecords(e);
				this.handyWorkerService.editCurriculum(curriculum);
				result = new ModelAndView("redirect:show.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(educationRecord, "note.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/handyWorker/editEducationRecord", method = RequestMethod.POST, params = "edit")
	public ModelAndView edit(@Valid EducationRecord educationRecord, BindingResult binding, @RequestParam String newComment) {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum curriculum = handyWorker.getCurriculum();
		List<EducationRecord> e = curriculum.getEducationRecords();
		List<String> comments = educationRecord.getComments();
		List<String> c = new ArrayList<String>();

		for (String s : comments) {
			if (!s.trim().isEmpty()) {
				c.add(s);
			}
		}

		if (!newComment.trim().isEmpty() && newComment.trim().length() > 1) {
			c.add(newComment.substring(1));
			educationRecord.setComments(c);
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(educationRecord);
		} else {
			try {
				if (educationRecord.getEndDateStudy() != null) {
					Assert.isTrue(educationRecord.getStartDateStudy().before(educationRecord.getEndDateStudy()));
				}
				Assert.isTrue(e.contains(educationRecord));
				this.educationRecordService.save(educationRecord);
				result = new ModelAndView("redirect:show.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(educationRecord, "note.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/handyWorker/editEducationRecord", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid EducationRecord educationRecord, BindingResult binding) {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum curriculum = handyWorker.getCurriculum();
		List<EducationRecord> e = curriculum.getEducationRecords();

		try {
			Assert.isTrue(e.contains(educationRecord));
			e.remove(educationRecord);
			curriculum.setEducationRecords(e);
			this.curriculumService.save(curriculum);
			this.educationRecordService.delete(educationRecord);
			result = new ModelAndView("redirect:show.do");

		} catch (Throwable oops) {
			result = this.createEditModelAndView(educationRecord, "note.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(EducationRecord educationRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(educationRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(EducationRecord educationRecord, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curriculum/handyWorker/editEducationRecord");
		result.addObject("educationRecord", educationRecord);
		result.addObject("message", messageCode);

		return result;
	}
}
