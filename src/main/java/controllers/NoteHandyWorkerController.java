
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

import security.LoginService;
import security.UserAccount;
import services.HandyWorkerService;
import services.NoteService;
import services.ReportService;
import domain.Note;
import domain.Report;

@Controller
@RequestMapping("/note/handyWorker")
public class NoteHandyWorkerController extends AbstractController {

	//Services
	@Autowired
	private ReportService		reportService;
	@Autowired
	private NoteService			noteService;
	@Autowired
	private HandyWorkerService	handyWorkerService;


	//Constructor
	public NoteHandyWorkerController() {
		super();
	}

	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int reportId) {

		ModelAndView result;

		Collection<Note> notes = new ArrayList<Note>();
		Report report = new Report();

		report = this.reportService.findOne(reportId);

		notes = report.getNotes();

		result = new ModelAndView("handy-worker/reportNotes");

		result.addObject("reportId", reportId);
		result.addObject("notes", notes);
		result.addObject("requestURI", "note/handyWorker/list.do");

		return result;
	}
	//Create
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int reportId) {
		ModelAndView result;
		Note note;

		note = this.noteService.create();
		result = this.createEditModelAndView(note);

		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Note note, BindingResult binding, @Valid int reportId) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(note);
		} else {
			try {
				Report report = this.reportService.findOne(reportId);
				List<Note> notes = report.getNotes();
				Note newNote = this.noteService.save(note);
				notes.add(newNote);
				report.setNotes(notes);
				this.reportService.save(report);

				result = new ModelAndView("redirect:list.do");
				result.addObject("reportId", reportId);
			} catch (Throwable oops) {
				result = this.createEditModelAndView(note, "note.commit.error");
			}
		}
		return result;
	}

	//Comments
	@RequestMapping(value = "/listComments", method = RequestMethod.GET)
	public ModelAndView commentList(@RequestParam int noteId) {
		ModelAndView result;

		Note note = this.noteService.findOne(noteId);

		Collection<String> comments = note.getOptionalComments();

		Collection<String> usernames = note.getUsernames();

		UserAccount userAccount = LoginService.getPrincipal();
		String username = userAccount.getUsername();

		result = new ModelAndView("handy-worker/noteComments");

		result.addObject("requestURI", "note/handyWorker/listComments.do");
		result.addObject("comments", comments);
		result.addObject("username", username);
		result.addObject("usernames", usernames);

		return result;
	}

	@RequestMapping(value = "/newComment", method = RequestMethod.GET)
	public ModelAndView newComment(@RequestParam int noteId, @RequestParam int reportId) {
		ModelAndView result;
		Note note = this.noteService.findOne(noteId);
		List<String> usernames = note.getUsernames();
		UserAccount userAccount = LoginService.getPrincipal();

		System.out.println("Prueba: " + usernames.contains(userAccount.getUsername()));
		Assert.isTrue(!(usernames.contains(userAccount.getUsername())));

		result = new ModelAndView("handy-worker/addNoteComment");
		result.addObject("noteId", noteId);
		result.addObject("reportId", reportId);
		return result;
	}

	@RequestMapping(value = "/saveComment", method = RequestMethod.POST, params = "create")
	public ModelAndView saveComment(@Valid int noteId, @Valid int reportId, @Valid int complaintId, @Valid int fixUpTaskId, @Valid String comment) {
		ModelAndView result;
		UserAccount userAccount = LoginService.getPrincipal();

		try {
			Note note = this.noteService.findOne(noteId);
			List<String> comments = note.getOptionalComments();
			List<String> usernames = note.getUsernames();
			usernames.add(userAccount.getUsername());
			comments.add(comment);
			note.setOptionalComments(comments);
			note.setUsernames(usernames);
			this.noteService.save(note);
			result = new ModelAndView("redirect:listComments.do");
			result.addObject("noteId", noteId);
			result.addObject("reportId", reportId);
			result.addObject("complaintId", complaintId);
			result.addObject("fixUpTaskId", fixUpTaskId);
		} catch (Throwable oops) {
			result = new ModelAndView("handy-worker/addNoteComment");
			result.addObject("comment", comment);
		}
		result.addObject("noteId", noteId);

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

		result = new ModelAndView("handy-worker/addNote");

		result.addObject("note", note);
		result.addObject("message", messageCode);

		return result;

	}

}
