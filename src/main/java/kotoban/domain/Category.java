package kotoban.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;


//Category class OneToMany

@Entity 
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryId;

    @JsonIgnoreProperties("wordCategory")

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "wordCategory")
    private List<Word> words;

    private String wordCategory;


    public Category() {
    }

    

    public Category(List<Word> words, String wordCategory) {
        this.words = words;
        this.wordCategory = wordCategory;
    }

    public Long getId() {
        return categoryId;
    }

    public void setId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public String getWordCategory() {
        return wordCategory;
    }

    public void setWordCategory(String wordCategory) {
        this.wordCategory = wordCategory;
    }


}
