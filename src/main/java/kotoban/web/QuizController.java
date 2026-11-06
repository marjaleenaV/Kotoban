package kotoban.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import kotoban.domain.CategoryRepository;
import kotoban.domain.UserRepository;
import kotoban.domain.Word;
import kotoban.domain.WordList;
import kotoban.domain.WordListRepository;
import kotoban.domain.WordRepository;
import kotoban.domain.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuizController {

    private WordListRepository wordListRepository;
    private CategoryRepository categoryRepository;

    public QuizController(WordRepository wordRepository, WordListRepository wordListRepository,
            UserRepository userRepository, CategoryRepository categoryRepository) {
        this.wordListRepository = wordListRepository;
        this.categoryRepository = categoryRepository;

    }

    // Shuffle wordlist
    @RequestMapping(value = "/wordlists/quiz/{listid}", method = RequestMethod.GET)
    public String StartQuiz(@PathVariable("listid") Long listId, HttpSession session) {
        WordList words = wordListRepository.findById(listId).orElseThrow();
        List<Word> list = new ArrayList<>(words.getWordlist());
        Collections.shuffle(list);
        if (list.isEmpty()) {
            return "redirect:/wordlists";
        }
        // keeps the same suffled list the same whole session
        session.setAttribute("quizWords", list); // list
        session.setAttribute("quizIndex", 0); // starting index
        return "redirect:/quiz";
    }

    // Shuffle Category list
    @RequestMapping(value = "/categories/quiz/{categoryId}", method = RequestMethod.GET)
    public String StartCategoryQuiz(@PathVariable("categoryId") Long categoryId, HttpSession session) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        List<Word> list = new ArrayList<>(category.getWords());
        Collections.shuffle(list);
        if (list.isEmpty()) {
            return "redirect:/categories";
        }

        // keeps the same suffled categorylist through the list in session
        session.setAttribute("quizWords", list); // list
        session.setAttribute("quizIndex", 0); // starting index
        return "redirect:/quiz";
    }

    // Shows a question in a page
    @GetMapping("/quiz")
    public String showQuestion(HttpSession session, Model model) {
        List<Word> words = (List<Word>) session.getAttribute("quizWords");
        if (words.isEmpty()) {
            return "/wordlists"; 
        }
        int index = (int) session.getAttribute("quizIndex");

        model.addAttribute("last", index == words.size() - 1);
        model.addAttribute("quizWord", words.get(index));
        if (index >= words.size()) {
            return "quizresult";
        }
        return "quiz";
    }

    // Check if the answer was correct and sends the result to the page
    @PostMapping("/quiz/answer")
    public String checkAnswer(@RequestParam(name = "answer", defaultValue = "") String answer, HttpSession session,
            Model model) {
        List<Word> words = (List<Word>) session.getAttribute("quizWords");
        int index = (Integer) session.getAttribute("quizIndex");

        // check that list and index are valid
        if (words == null || index < 0 || index >= words.size()) {
            return "redirect:/quiz";
        }
        // Check if users answer equals to the answer on the list
        Word word = words.get(index);
        boolean correct = word.getMeaning() != null && word.getMeaning().trim().equalsIgnoreCase(answer.trim());

        // Sends information to the quiz page
        model.addAttribute("last", index == words.size() - 1);
        model.addAttribute("quizWord", word);
        model.addAttribute("answer", correct);
        model.addAttribute("correctAnswer", word.getMeaning());

        return "quiz";
    }

    // Move forward to the next question
    @GetMapping("/quiz/next")
    public String getMethodName(HttpSession session) {
        int index = (Integer) session.getAttribute("quizIndex");
        session.setAttribute("quizIndex", index + 1);
        return "redirect:/quiz";
    }

    // Finish game
    @GetMapping("/quiz/finished")
    public String quizFinished() {
        return "quizresult";
    }
}
