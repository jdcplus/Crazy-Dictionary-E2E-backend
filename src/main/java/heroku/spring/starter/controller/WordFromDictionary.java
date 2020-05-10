package heroku.spring.starter.controller;

import java.util.ArrayList;

public class WordFromDictionary {
	
	private String headWord;
	private String pronunciations;
	private String functionalLabel;
	private ArrayList<String> shortDefinition;
	
	private Boolean validWord;
	private ArrayList<String> validSuggestion;
	
	public String getHeadWord() {
		return headWord;
	}
	public void setHeadWord(String headWord) {
		this.headWord = headWord;
	}
	public String getPronunciations() {
		return pronunciations;
	}
	public void setPronunciations(String pronunciations) {
		this.pronunciations = pronunciations;
	}
	public String getFunctionalLabel() {
		return functionalLabel;
	}
	public void setFunctionalLabel(String functionalLabel) {
		this.functionalLabel = functionalLabel;
	}
	public ArrayList<String> getShortDefinition() {
		return shortDefinition;
	}
	public void setShortDefinition(ArrayList<String> shortDefinition) {
		this.shortDefinition = shortDefinition;
	}
	public Boolean getValidWord() {
		return validWord;
	}
	public void setValidWord(Boolean validWord) {
		this.validWord = validWord;
	}
	public ArrayList<String> getValidSuggestion() {
		return validSuggestion;
	}
	public void setValidSuggestion(ArrayList<String> validSuggestion) {
		this.validSuggestion = validSuggestion;
	}
	@Override
	public String toString() {
		return "WordFromDictionary [headWord=" + headWord + ", pronunciations=" + pronunciations + ", functionalLabel="
				+ functionalLabel + ", shortDefinition=" + shortDefinition + ", validWord=" + validWord
				+ ", validSuggestion=" + validSuggestion + "]";
	}
	
	
	
}
