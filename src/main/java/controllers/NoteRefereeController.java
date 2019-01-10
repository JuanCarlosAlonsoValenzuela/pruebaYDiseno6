
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

import services.NoteService;
import services.RefereeService;
import services.ReportService;
import domain.Note;
import domain.Referee;
import domain.Report;

@Controller
@RequestMapping("/note/referee")
public class NoteRefereeController extends AbstractController {

	@Autowired
	private ReportService	reportService;

	@Autowired
	private NoteService		noteService;

	@Autowired
	private RefereeService	refereeService;


	public NoteRefereeController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int reportId) {

		ModelAndView result;

		Collection<Note> notes = new ArrayList<Note>();
		Report report = new Report();

		report = this.reportService.findOne(reportId);

		notes = this.refereeService.showNotes(report);

		result = new ModelAndView("note/referee/list");

		result.addObject("reportId", reportId);
		result.addObject("notes", notes);
		result.addObject("requestURI", "note/referee/list.do");

		return result;

	}

	@RequestMapping(value = "/listComments", method = RequestMethod.GET)
	public ModelAndView listComments(@RequestParam int noteId) {

		ModelAndView result;

		Referee loggedReferee = this.refereeService.securityAndReferee();

		Note note = this.noteService.findOne(noteId);
		Assert.notNull(note);
		List<String> usernames = note.getUsernames();

		Boolean canComment = !usernames.contains(loggedReferee.getUserAccount().getUsername());

		List<String> optionalComments = new ArrayList<String>();
		optionalComments = note.getOptionalComments();

		result = new ModelAndView("note/referee/listComments");

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

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Note note, BindingResult binding, @RequestParam int reportId) {
		ModelAndView result;
		Report report = this.reportService.findOne(reportId);

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(note);
			result.addObject("reportId", reportId);
		} else {
			try {
				this.refereeService.createNote(report, note);
				result = new ModelAndView("redirect:list.do?reportId=" + reportId);

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
			this.refereeService.addComent(note, comment);
			result = new ModelAndView("redirect:listComments.do?noteId=" + noteId);

		} catch (Throwable oops) {
			result = this.createEditModelAndView(note, "note.commit.error");
			result.addObject("noteId", noteId);
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Note note) {
		ModelAndView result;

		result = this.createEditModelAndView(note, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Note note, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("note/referee/create");
		result.addObject("note", note);

		result.addObject("message", messageCode);

		return result;
	}

}
