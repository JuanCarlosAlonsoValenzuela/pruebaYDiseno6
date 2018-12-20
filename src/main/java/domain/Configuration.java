package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

    private List<String> spamWords;
    private Integer iva21;
    private List<String> cardType;
    private String spainTelephoneCode;
    private Integer minFinderResults;
    private Integer maxFinderResults;
    private Integer minTimeFinder;
    private Integer maxTimeFinder;

    private String goodWords;
    private String badWords;

    @NotNull
    public Integer getmaxFinderResults() {
	return this.maxFinderResults;
    }

    public void setmaxFinderResults(Integer maxFinderResults) {
	this.maxFinderResults = maxFinderResults;
    }

    @NotNull
    public Integer getMinTimeFinder() {
	return this.minTimeFinder;
    }

    public void setMinTimeFinder(Integer minTimeFinder) {
	this.minTimeFinder = minTimeFinder;
    }

    @NotNull
    public Integer getMaxTimeFinder() {
	return this.maxTimeFinder;
    }

    public void setMaxTimeFinder(Integer maxTimeFinder) {
	this.maxTimeFinder = maxTimeFinder;
    }

    @NotNull
    public Integer getMinFinderResults() {
	return this.minFinderResults;
    }

    public void setMinFinderResults(Integer minFinderResults) {
	this.minFinderResults = minFinderResults;
    }

    @ElementCollection(targetClass = String.class)
    public List<String> getSpamWords() {
	return this.spamWords;
    }

    public void setSpamWords(List<String> spamWords) {
	this.spamWords = spamWords;
    }

    @NotNull
    public Integer getIva21() {
	return this.iva21;
    }

    public void setIva21(Integer iva21) {
	this.iva21 = iva21;
    }

    @ElementCollection(targetClass = String.class)
    public List<String> getCardType() {
	return this.cardType;
    }

    public void setCardType(List<String> cardType) {
	this.cardType = cardType;
    }

    @NotBlank
    public String getSpainTelephoneCode() {
	return this.spainTelephoneCode;
    }

    public void setSpainTelephoneCode(String spainTelephoneCode) {
	this.spainTelephoneCode = spainTelephoneCode;
    }

    @NotBlank
    public String getGoodWords() {
	return this.goodWords;
    }

    public void setGoodWords(String goodWords) {
	this.goodWords = goodWords;
    }

    @NotBlank
    public String getBadWords() {
	return this.badWords;
    }

    public void setBadWords(String badWords) {
	this.badWords = badWords;
    }

}
