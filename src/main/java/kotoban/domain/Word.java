package kotoban.domain;

import org.springframework.stereotype.Indexed;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//Word Class, where the information of the word is determined.

@Entity
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String japanese;
    private String reading;
    private String meaning;

    public Word() {

    }

    //Constructor
    public Word(String japanese, String reading, String meaning) {
        super();
        this.japanese = japanese;
        this.reading = reading;
        this.meaning = meaning;
    }

    //Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getJapanese() {
        return japanese;
    }
    public void setJapanese(String japanese) {
        this.japanese = japanese;
    }
    public String getReading() {
        return reading;
    }
    public void setReading(String reading) {
        this.reading = reading;
    }
    public String getMeaning() {
        return meaning;
    }
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return "Word [japanese=" + japanese + ", reading=" + reading + ", meaning=" + meaning + "]";
    }

}
