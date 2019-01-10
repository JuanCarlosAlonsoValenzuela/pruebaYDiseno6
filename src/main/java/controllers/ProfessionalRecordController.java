
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
import services.ProfessionalRecordService;
import domain.Curriculum;
import domain.HandyWorker;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/curriculum")
public class ProfessionalRecordController extends AbstractController {

	@Autowired
	private ProfessionalRecordService	professionalRecordService;
	@Autowired
	private HandyWorkerService			handyWorkerService;
	@Autowired
	private CurriculumService			curriculumService;


	public ProfessionalRecordController() {
		super();
	}

	@RequestMapping(value = "/handyWorker/addProfessionalRecord", method = RequestMethod.GET)
	public ModelAndView addProfessionalRecord() {

		ModelAndView result;
		ProfessionalRecord professionalRecord = this.professionalRecordService.create();

		result = this.createEditModelAndView(professionalRecord);
		return result;

	}

	@RequestMapping(value = "/handyWorker/editProfessionalRecord", method = RequestMethod.GET)
	public ModelAndView editProfessionalRecord(@RequestParam int professionalRecordId) {

		ModelAndView result;
		ProfessionalRecord professionalRecord = this.professionalRecordService.findOne(professionalRecordId);

		result = this.createEditModelAndView(professionalRecord);
		return result;

	}

	@RequestMapping(value = "/anonymous/showProfessionalComments", method = RequestMethod.GET)
	public ModelAndView showProfessionalComments(@RequestParam int professionalRecordId, @RequestParam boolean canEdit, @RequestParam int handyId) {

		ModelAndView result;
		ProfessionalRecord professionalRecord = this.professionalRecordService.findOne(professionalRecordId);

		result = new ModelAndView("curriculum/anonymous/showProfessionalComments");
		result.addObject("comments", professionalRecord.getComments());
		result.addObject("requestURI", "curriculum/anonymous/showProfessionalComments.do");
		result.addObject("canEdit", canEdit);
		result.addObject("handyId", handyId);
		return result;

	}
	@RequestMapping(value = "/handyWorker/editProfessionalRecord", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ProfessionalRecord professionalRecord, BindingResult binding, @RequestParam String newComment) {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum curriculum = handyWorker.getCurriculum();
		List<ProfessionalRecord> p = curriculum.getProfessionalRecords();
		List<String> c = new ArrayList<String>();

		if (!newComment.trim().isEmpty() && newComment.trim().length() > 1) {
			c.add(newComment.substring(1));
		}
		professionalRecord.setComments(c);

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(professionalRecord);
		} else {
			try {
				if (professionalRecord.getEndDate() != null) {
					Assert.isTrue(professionalRecord.getStartDate().before(professionalRecord.getEndDate()));
				}
				p.add(professionalRecord);
				curriculum.setProfessionalRecords(p);
				this.handyWorkerService.editCurriculum(curriculum);
				result = new ModelAndView("redirect:show.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(professionalRecord, "note.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/handyWorker/editProfessionalRecord", method = RequestMethod.POST, params = "edit")
	public ModelAndView edit(@Valid ProfessionalRecord professionalRecord, BindingResult binding, @RequestParam String newComment) {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum curriculum = handyWorker.getCurriculum();
		List<ProfessionalRecord> p = curriculum.getProfessionalRecords();
		List<String> comments = professionalRecord.getComments();
		List<String> c = new ArrayList<String>();

		for (String s : comments) {
			if (!s.trim().isEmpty()) {
				c.add(s);
			}
		}

		if (!newComment.trim().isEmpty() && newComment.trim().length() > 1) {
			c.add(newComment.substring(1));
			professionalRecord.setComments(c);
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(professionalRecord);
		} else {
			try {
				if (professionalRecord.getEndDate() != null) {
					Assert.isTrue(professionalRecord.getStartDate().before(professionalRecord.getEndDate()));
				}
				Assert.isTrue(p.contains(professionalRecord));
				this.professionalRecordService.save(professionalRecord);
				result = new ModelAndView("redirect:show.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(professionalRecord, "note.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/handyWorker/editProfessionalRecord", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid ProfessionalRecord professionalRecord, BindingResult binding) {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum curriculum = handyWorker.getCurriculum();
		List<ProfessionalRecord> p = curriculum.getProfessionalRecords();

		try {
			Assert.isTrue(p.contains(professionalRecord));
			p.remove(professionalRecord);
			curriculum.setProfessionalRecords(p);
			this.curriculumService.save(curriculum);
			this.professionalRecordService.delete(professionalRecord);
			result = new ModelAndView("redirect:show.do");

		} catch (Throwable oops) {
			result = this.createEditModelAndView(professionalRecord, "note.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(ProfessionalRecord professionalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(professionalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(ProfessionalRecord professionalRecord, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curriculum/handyWorker/editProfessionalRecord");
		result.addObject("professionalRecord", professionalRecord);
		result.addObject("message", messageCode);

		return result;
	}
}
