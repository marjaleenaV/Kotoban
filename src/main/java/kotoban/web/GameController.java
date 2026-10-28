package kotoban.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kotoban.domain.Word;
import kotoban.domain.WordRepository;
import kotoban.domain.WordList;
import kotoban.domain.WordListRepository;
import kotoban.domain.User;
import kotoban.domain.UserRepository;


@Controller
public class GameController {
    private WordListRepository wordListRepository;
    private WordRepository wordRepository;
    private UserRepository userRepository;
    

    // Injection
    public GameController(WordRepository wordRepository, WordListRepository wordListRepository, UserRepository userRepository) {
        this.wordRepository = wordRepository;
        this.wordListRepository = wordListRepository;
        this.userRepository = userRepository;
    }

    // Game start 
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String welcomePage() {
        return "index";
    }

    //Review
   // @RequestMapping(value= "/review", method=RequestMethod.GET)
    //public String reviewWords(@RequestParam String param) {
      //  List<Word> words = (List<Word>)
        //return "review";
    //}

    //Search users wordlists
    @RequestMapping(value="/wordlists", method=RequestMethod.GET)
    public String showWordLists(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("lists", wordListRepository.findByOwner(user));
        return "wordlists";
    }
    

    //Wordlist view
    @RequestMapping(value = "/wordlist", method = RequestMethod.GET)
    public List<Word> showWordList() {
        List<Word> words = (List<Word>) wordRepository.findAll();
        return words;
    }
}
