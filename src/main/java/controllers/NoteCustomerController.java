
package controllers;

import java.util.ArrayList;
import java.util.Collection;
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
	private ReportService		reportService;

	@Autowired
	private NoteService			noteService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private CustomerRepository	customerRepository;


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
		Assert.notNull(note);
		List<String> usernames = note.getUsernames();

		Boolean canComment = !usernames.contains(loggedCustomer.getUserAccount().getUsername());

		List<String> optionalComments = new ArrayList<String>();
		optionalComments = this.customerService.showNotesComments(note, loggedCustomer);

		result = new ModelAndView("note/customer/showComments");

		result.addObject("optionalComments", optionalComments);
		result.addObject("canComment", canComment);
		result.addObject("noteId", noteId);

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

	@RequestMapping(value = "/addComment", method = RequestMethod.GET)
	public ModelAndView addComment(@RequestParam int noteId) {
		ModelAndView result;
		Note note;

		note = this.noteService.findOne(noteId);
		Assert.notNull(note);
		result = this.createEditModelAndView(note);

		result.addObject("noteId", noteId);

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
				result.addObject("reportId", reportId);
			}
		}
		return result;
	}

	@RequestMapping(value = "/addComment", method = RequestMethod.POST, params = "Comment")
	public ModelAndView saveComment(@RequestParam int noteId, @RequestParam String comment) {
		ModelAndView result;
		Note note = this.noteService.findOne(noteId);

		try {
			this.customerService.addComent(note, comment);
			result = new ModelAndView("redirect:showComments.do?noteId=" + noteId);

		} catch (Throwable oops) {
			result = this.createEditModelAndView(note, "note.commit.error");
			result.addObject("noteId", noteId);
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
