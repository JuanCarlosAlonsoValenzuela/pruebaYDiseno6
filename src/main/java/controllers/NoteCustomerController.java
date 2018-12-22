
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import repositories.CustomerRepository;
import services.CustomerService;
import services.NoteService;
import services.ReportService;
import domain.Customer;
import domain.Note;
import domain.Report;

@Controller
@RequestMapping("/note/customer")
public class NoteCustomerController extends AbstractController {

	@Autowired
	ReportService		reportService;

	@Autowired
	NoteService			noteService;

	@Autowired
	CustomerService		customerService;

	@Autowired
	CustomerRepository	customerRepository;


	public NoteCustomerController() {
		super();
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int reportId) {

		ModelAndView result;

		Collection<Note> notes = new ArrayList<Note>();
		Report report = new Report();

		report = this.reportService.findOne(reportId);

		notes = this.customerService.showNotes(report);

		result = new ModelAndView("note/customer/show");

		result.addObject("reportId", reportId);
		result.addObject("notes", notes);
		result.addObject("requestURI", "note/customer/show.do");

		return result;

	}
	@RequestMapping(value = "/showComments", method = RequestMethod.GET)
	public ModelAndView listComments(@RequestParam int noteId) {

		ModelAndView result;

		Customer loggedCustomer = this.customerService.securityAndCustomer();

		Note note = this.noteService.findOne(noteId);
		List<String> usernames = note.getUsernames();

		Boolean canComment = !usernames.contains(loggedCustomer.getUserAccount().getUsername());

		List<String> optionalComments = new ArrayList<String>();
		optionalComments = this.customerService.showNotesComments(note, loggedCustomer);

		result = new ModelAndView("note/customer/showComments");

		result.addObject("optionalComments", optionalComments);
		result.addObject("requestURI", "note/customer/showComments.do");
		result.addObject("canComment", canComment);

		return result;

	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int reportId) {
		ModelAndView result;
		Note note;

		note = this.noteService.create();
		result = this.createEditModelAndView(note);
		result.addObject("reportId", reportId);

		return result;
	}

	//Save
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Note note, BindingResult binding, @RequestParam int reportId) {
		ModelAndView result;
		Report report = this.reportService.findOne(reportId);

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(note);
			result.addObject("reportId", reportId);
		} else {
			try {
				this.customerService.createNote(report, note);
				result = new ModelAndView("redirect:show.do?reportId=" + reportId);

			} catch (Throwable oops) {
				result = this.createEditModelAndView(note, "note.commit.error");

			}
		}
		return result;
	}

	//CreateEditModelAndView
	protected ModelAndView createEditModelAndView(Note note) {
		ModelAndView result;

		result = this.createEditModelAndView(note, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Note note, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("note/customer/create");
		result.addObject("noteTest", note);

		result.addObject("message", messageCode);

		return result;
	}
}
