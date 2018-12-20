
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.NoteRepository;
import domain.Note;

@Service
@Transactional
public class NoteService {

	@Autowired
	private NoteRepository	noteRepository;


	public Note create() {

		List<String> optionalComments = new ArrayList<String>();
		Note note = new Note();
		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);
		note.setMoment(thisMoment);
		note.setMandatoryComment("");
		note.setOptionalComments(optionalComments);

		return note;
	}

	public Note create(String mandatoryComment, List<String> optionalComments) {

		Note note = new Note();
		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);
		note.setMoment(thisMoment);
		note.setMandatoryComment(mandatoryComment);
		note.setOptionalComments(optionalComments);

		return note;
	}

	public Note save(Note note) {
		return this.noteRepository.save(note);
	}

	public Note findOne(int noteId) {
		return this.noteRepository.findOne(noteId);
	}

	public void delete(Note note) {
		this.noteRepository.delete(note);
	}

	public List<Note> findAll() {
		return this.noteRepository.findAll();
	}
}
