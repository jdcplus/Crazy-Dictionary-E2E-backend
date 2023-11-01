package heroku.spring.starter.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import heroku.spring.starter.controller.WordFromDictionary;

@Service
public class SearchService {

	private final String URL = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/"; // Put your api URL here
	private final String API_KEY = "abcde1234-abcd-abcd-abcd-abcde1234"; // Put your api key here

	private RestTemplate template;
	private WordFromDictionary processedWord;
	private boolean validWord;

	public WordFromDictionary result(String word) {

		template = new RestTemplate();
		String fullURLPath = URL + word + "?key=" + API_KEY;
		ResponseEntity<Object[]> response = template.getForEntity(fullURLPath, Object[].class);

		//System.out.println("Total words :" + response.getBody().length);
		if(response.getBody() == null) {
			return new WordFromDictionary();
		}
		Object fullWordTree = response.getBody()[0];
		if (fullWordTree.getClass().equals(String.class)) {
			validWord = false;
		} else {
			validWord = true;
		}
		//System.out.println("valid word:" + validWord);

		ObjectMapper mapper = new ObjectMapper();
		if (validWord) {
			processedWord = processWordTree(word, fullWordTree, mapper);
		} else {
			processedWord = new WordFromDictionary();
			processedWord.setValidWord(false);
			processedWord.setHeadWord(word);
			ArrayList<Object> suggestedWordList = pojoToList(mapper, response.getBody());
			ArrayList<String> outputWordList = new ArrayList<>();
			for (Object obj : suggestedWordList) {
				outputWordList.add(obj.toString());
			}
			processedWord.setValidSuggestion(outputWordList);
		}
		//System.out.println(processedWord.toString());
		return processedWord;
	}

	public WordFromDictionary processWordTree(String word, Object wordTree, ObjectMapper mapper) {

		Object headWordInfo = null, functionalLabel = null, shortdef = null;
		try {
			LinkedHashMap<String, Object> oneFullWordMap = pojoToMap(mapper, wordTree);

			// Getting individual objects from map
			headWordInfo = oneFullWordMap.get("hwi");
			functionalLabel = oneFullWordMap.get("fl");
			shortdef = oneFullWordMap.get("shortdef");
		} catch (Exception e) {
			System.err.println("Got exception while processing word tree " + e.getMessage());
		} finally {
			processedWord = new WordFromDictionary();
			processedWord.setValidWord(true);
			processedWord.setValidSuggestion(new ArrayList<>());
			processedWord.setHeadWord(word);
			processedWord.setFunctionalLabel(functionalLabel.toString());
			processedWord.setPronunciations(getPrs(headWordInfo, mapper));
			processedWord.setShortDefinition(getShortDef(shortdef, mapper));
		}
		//System.out.println(processedWord.toString());
		return processedWord;

	}

	public String getPrs(Object headWordInfo, ObjectMapper mapper) {

		String prsStr;
		try {
			LinkedHashMap<String, Object> prsFullWordMap = pojoToMap(mapper, headWordInfo);
			Object prs = prsFullWordMap.get("prs");
			ArrayList<Object> prsNested = pojoToList(mapper, prs);
			LinkedHashMap<String, Object> mwFullWordMap = pojoToMap(mapper, prsNested.get(0));
			prsStr = mwFullWordMap.get("mw").toString();
		} catch (Exception e) {
			System.err.println("Got exception while getting Pronunciation " + e.getMessage());
			prsStr = "Pronunciation is not available for given word";
		}

		return prsStr;
	}

	public ArrayList<String> getShortDef(Object shortdef, ObjectMapper mapper) {
		ArrayList<String> sdResult = new ArrayList<>();
		try {
			ArrayList<Object> defArr = pojoToList(mapper, shortdef);
			for (Object def : defArr) {
				sdResult.add(def.toString());
			}
		} catch (Exception e) {
			System.err.println("Got exception while getting Short Definition" + e.getMessage());
			sdResult.add("Short Definition is not available for this word");
		}

		return sdResult;
	}

	@SuppressWarnings("unchecked")
	public LinkedHashMap<String, Object> pojoToMap(ObjectMapper mapper, Object genericPojo) {
		return mapper.convertValue(genericPojo, LinkedHashMap.class);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Object> pojoToList(ObjectMapper mapper, Object genericPojo) {
		return mapper.convertValue(genericPojo, ArrayList.class);
	}

}
