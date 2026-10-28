package kotoban.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


//WordList Entity
@Entity 
public class WordList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wordListId;

    @JsonIgnoreProperties("wordList")

    //Connection between user and lists -> 
    //One user can have many lists, but lists are private so every list has only one user
    @ManyToOne
    @JoinColumn(name = "userId") 
    private User owner;

    //Connection between words and lists -> Wordlist can have many words and word can belong in many lists
    @ManyToMany 
    @JoinTable(name="wordList",
        joinColumns = @JoinColumn(name="wordListId"),
            inverseJoinColumns = @JoinColumn(name="id")
        )

    //Set makes sure that list doesn't have same word twice
    private Set<Word> wordlist = new HashSet<>();

    private String listName;


    public WordList() {
    }


    public WordList(User owner, Set<Word> wordlist, String listName) {
        this.owner = owner;
        this.wordlist = wordlist;
        this.listName = listName;
    }


    public Long getWordListId() {
        return wordListId;
    }


    public void setWordListId(Long wordListId) {
        this.wordListId = wordListId;
    }


    public User getOwner() {
        return owner;
    }


    public void setOwner(User owner) {
        this.owner = owner;
    }


    public Set<Word> getWordlist() {
        return wordlist;
    }


    public void setWordlist(Set<Word> wordlist) {
        this.wordlist = wordlist;
    }


    public String getListName() {
        return listName;
    }


    public void setListName(String listName) {
        this.listName = listName;
    }

    
}
