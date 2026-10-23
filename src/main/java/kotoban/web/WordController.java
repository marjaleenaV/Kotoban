package kotoban.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kotoban.domain.Word;
import kotoban.domain.WordRepository;

@Controller
public class WordController {
    private WordRepository wordRepository;

    //Injection
    public WordController(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    //Spring REST
@RequestMapping (value = "/index", method = RequestMethod.GET) 
public String welcomePage() {
    return "index";
}

@RequestMapping(value = "/wordlist", method = RequestMethod.GET)
    public List<Word> showWordList() {
        List<Word> words = (List<Word>) wordRepository.findAll();
        return words;
    }
}
