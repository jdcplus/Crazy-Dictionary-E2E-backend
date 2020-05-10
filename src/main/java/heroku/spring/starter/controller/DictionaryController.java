package heroku.spring.starter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import heroku.spring.starter.service.SearchService;

@RestController
public class DictionaryController {
	
	@Autowired
	private SearchService ss;
	
	@GetMapping("/definition/{word}")
	public ResponseEntity<WordFromDictionary> getWord(@PathVariable(name = "word") String wordToSearch) {
		return new ResponseEntity<WordFromDictionary>(ss.result(wordToSearch), HttpStatus.OK);
	}
	
}
