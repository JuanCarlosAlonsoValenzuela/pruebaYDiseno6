
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ReportService;
import domain.Note;

@Controller
@RequestMapping("/note/customer")
public class NoteCustomerController extends AbstractController {

	@Autowired
	ReportService	noteService;


	public NoteCustomerController() {
		super();
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView list(int reportId) {

		ModelAndView result;

		Collection<Note> notes;

		notes = this.noteService.findOne(reportId).getNotes();

		result = new ModelAndView("note/customer/show");

		result.addObject("notes", notes);
		result.addObject("requestURI", "note/customer/show.do");

		return result;

	}
}
