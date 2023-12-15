package me.nickf.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Object> getThoughtById(@PathVariable(value = "id") String id) {
		Optional<Thoughts> thought = this.thoughtsRepository.findById(id);
		
		if(thought.equals(Optional.empty())) {
			HashMap<String, String> m = new HashMap<>();
			m.put("code", "422");
			m.put("message", "The id variable is invalid, please provide a valid id.");
			return new ResponseEntity<>(m, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<>(thought, HttpStatus.OK);
	}

	@GetMapping("/thoughts/random")
	public Thoughts getThoughtByRandom() {
		Iterator<Thoughts> it = this.thoughtsRepository.findAll().iterator();
		long c = this.thoughtsRepository.count();
		long r = (long) (Math.random() * c);

		while(r > 0) {
			it.next();
			r--;
		}
		
		return it.next();
	}

	@GetMapping("/thoughts/count")
	public long getCount() {
		return this.thoughtsRepository.count();
	}

  @GetMapping("/thoughts")
  // public Iterable<Thoughts> findAllThoughts(
	public ResponseEntity<Object> findAllThoughts(
		@RequestParam(value = "author", defaultValue = "", required = true) String author,
		@RequestParam(value = "over_18", defaultValue = "false", required = true) String over_18,
		@RequestParam(value = "from", defaultValue = "", required = true) String from,
		@RequestParam(value = "to", defaultValue = "", required = true) String to,
		@RequestParam(value = "limit", defaultValue = "10", required = true) String limit
	) {
		over_18 = over_18.toLowerCase();
		if (!(over_18.equals("true") || over_18.equals("false"))) {
			HashMap<String, String> m = new HashMap<>();
			m.put("code", "422");
			m.put("message", "The over_18 parameter is invalid. Please either use 'true' or 'false'.");
			return new ResponseEntity<>(m, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		int limitInt = 10;

		try {
			limitInt = Integer.parseInt(limit);
		} catch (Exception e) {
			HashMap<String, String> m = new HashMap<>();
			m.put("code", "400");
			m.put("message", "The limit parameter is invalid. Please an integer ranging from 1 to 1000.");
			return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
		}
		if (limitInt < 1) limitInt = 1;
		if (limitInt > 1000) limitInt = 1000;

		Date fromDate = null;
		Date toDate = null;

		if (!from.equals("")) {
			try {
				fromDate = Date.valueOf(from);
			} catch (Exception e) {
				HashMap<String, String> m = new HashMap<>();
				m.put("code", "422");
				m.put("message", "The from date is in an invalid format. (YYYY-MM-DD)");
				return new ResponseEntity<>(m, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}

		if (!to.equals("")) {
			try {
				toDate = Date.valueOf(to);
			} catch (Exception e) {
				HashMap<String, String> m = new HashMap<>();
				m.put("code", "422");
				m.put("message", "The to date is in an invalid format. (YYYY-MM-DD)");
				return new ResponseEntity<>(m, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}

		if (fromDate != null && toDate != null && fromDate.compareTo(toDate) > 0) {
			HashMap<String, String> m = new HashMap<>();
			m.put("code", "422");
			m.put("message", "The to date must be later than or equal to the from date.");
			return new ResponseEntity<>(m, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		ArrayList<Thoughts> t = new ArrayList<>();
		Iterator<Thoughts> it = this.thoughtsRepository.findAllByOrderByCreatedDesc().iterator();


		while(it.hasNext() && t.size() < limitInt) {
			Thoughts thought = it.next();

			if (!author.equals("") && !author.equals(thought.getAuthor())) {
				continue;
			}

			if (!over_18.equals(thought.getOver_18() + "")) {
				continue;
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

		return new ResponseEntity<>(t, HttpStatus.OK);
  }
}