package me.nickf.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThoughtsController {
	private final ThoughtsRepository thoughtsRepository;

	public ThoughtsController(ThoughtsRepository thoughtsRepository) {
		this.thoughtsRepository = thoughtsRepository;
	}

	@GetMapping("/thoughts/{id}")
	public Optional<Thoughts> getMethodName(@PathVariable(value = "id") String id) {
		return this.thoughtsRepository.findById(id);
	}

  @GetMapping("/thoughts")
  public Iterable<Thoughts> findAllThoughts(
		@RequestParam(value = "author", defaultValue = "", required = true) String author,
		@RequestParam(value = "over_18", defaultValue = "false", required = true) String over_18,
		@RequestParam(value = "from", defaultValue = "", required = true) String from,
		@RequestParam(value = "to", defaultValue = "", required = true) String to,
		@RequestParam(value = "limit", defaultValue = "10", required = true) String limit
	) {
		ArrayList<Thoughts> t = new ArrayList<>();
		Iterator<Thoughts> it = this.thoughtsRepository.findAllByOrderByCreatedDesc().iterator();

		int limitInt = Integer.parseInt(limit);
		if (limitInt < 1) limitInt = 1;
		if (limitInt > 1000) limitInt = 1000;

		while(it.hasNext() && t.size() < limitInt) {
			Thoughts thought = it.next();

			if (!author.equals("") && !author.equals(thought.getAuthor())) {
				continue;
			}

			if (!over_18.equals(thought.getOver_18() + "")) {
				continue;
			}

			Date fromDate = null;
			Date toDate = null;

			if (!from.equals("")) {
				fromDate = Date.valueOf(from);
			}

			if (!to.equals("")) {
				toDate = Date.valueOf(to);
			}

			Date td = thought.getCreated();

			if (fromDate != null && toDate != null) {
				if (!(fromDate.compareTo(td) <= 0 && toDate.compareTo(td) >= 0)) {
					continue;
				}
			}

			if (fromDate != null && toDate == null) {
				if (!(fromDate.compareTo(td) <= 0)) {
					continue;
				}
			}

			if (fromDate == null && toDate != null) {
				if (!(toDate.compareTo(td) >= 0)) {
					continue;
				}
			}

			t.add(thought);
		}

		return t;
  }
}
