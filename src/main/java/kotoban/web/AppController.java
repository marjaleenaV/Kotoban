package kotoban.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kotoban.domain.Category;
import kotoban.domain.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kotoban.domain.Word;
import kotoban.domain.WordRepository;
import kotoban.domain.WordList;
import kotoban.domain.WordListRepository;
import kotoban.domain.User;
import kotoban.domain.UserRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AppController {
    private CategoryRepository categoryRepository;
    private WordListRepository wordListRepository;
    private WordRepository wordRepository;
    private UserRepository userRepository;

    // Injection
    public AppController(WordRepository wordRepository, WordListRepository wordListRepository,
            UserRepository userRepository, CategoryRepository categoryRepository) {
        this.wordRepository = wordRepository;
        this.wordListRepository = wordListRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    // Welcome page
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String welcomePage() {
        return "index";
    }

    // Shows users wordlists
    @RequestMapping(value = "/wordlists", method = RequestMethod.GET)
    public String showWordLists(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("lists", wordListRepository.findByOwner(user));
        return "wordlists";
    }

    // Show Category lists
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public String showCategorylists(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories";
    }

    // Create a new Worlist and open a list creation form
    @RequestMapping(value = "/listform", method = RequestMethod.GET)
    public String showNewListForm(Model model) {
        model.addAttribute("wordlist", new WordList());
        return "listform";
    }

    // Create a new category and open a category creation form
    @RequestMapping(value = "/admin/categoryform", method = RequestMethod.GET)
    public String showNewCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categoryform";
    }

    // Save new users private wordlist
    @RequestMapping(value = "savelist", method = RequestMethod.POST)
    public String saveList(WordList wordList, Principal principal) {
        wordList.setOwner(userRepository.findByUsername(principal.getName()));
        WordList newList = wordListRepository.save(wordList);
        return "redirect:wordlists/" + newList.getWordListId();
    }

    // Save Category
    @RequestMapping(value = "/admin/savecategory", method = RequestMethod.POST)
    public String saveCategoryList(Category category) {
        Category newCategory = categoryRepository.save(category);
        return "redirect:/admin/categories/" + newCategory.getCategoryId();
    }

    // Open a addwords form
    @RequestMapping(value = "/wordlists/{id}", method = RequestMethod.GET)
    public String openAddWordsForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("list", wordListRepository.findById(id).get());
        model.addAttribute("word", new Word());
        return "addwords";
    }

    // Open a addCategoryWords form
    @RequestMapping(value = "/admin/categories/{listId}", method = RequestMethod.GET)
    public String openAddCategoryWordsForm(@PathVariable("listId") Long listId, Model model) {
        model.addAttribute("list", categoryRepository.findById(listId).get());
        model.addAttribute("word", new Word());
        return "addCategoryWords";
    }

    // Save words to the list
    @RequestMapping(value = "/wordlists/{listid}/addwords", method = RequestMethod.POST)
    public String saveWords(@PathVariable("listid") Long listid, Word word) {
        WordList wordList = wordListRepository.findById(listid).orElseThrow();
        wordList.getWordlist().add(word);
        wordListRepository.save(wordList);
        return "redirect:/wordlists/" + listid;
    }

    // Save words under the category
    @RequestMapping(value = "/admin/categories/{listId}/addCategoryWords", method = RequestMethod.POST)
    public String saveWordsToCategory(@PathVariable("listId") Long listId, Word word) {
        Category category = categoryRepository.findById(listId).orElseThrow();
        word.setWordCategory(category);
        wordRepository.save(word);
        return "redirect:/admin/categories/" + listId;
    }

    // Delete wordlist
    @GetMapping("/wordlists/{listid}/deletelist")
    public String deleteList(@PathVariable("listid") Long listid, WordList wordlist) {
        wordListRepository.deleteById(listid);
        return "redirect:/wordlists";
    }

    // deletes a category and all words in it, then goes back to the category list
    @GetMapping("/admin/categories/{categoryId}/delete")
    public String deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return "redirect:/categories";
    }

    // Remove words from wordlist (while creating it)
    @RequestMapping(value = "/wordlists/delete/{listId}/{wordId}", method = RequestMethod.GET)
    public String deleteWord(@PathVariable("listId") Long listId,
            @PathVariable("wordId") Long wordId) {

        WordList list = wordListRepository.findById(listId).get();
        Word word = wordRepository.findById(wordId).get();

        list.getWordlist().remove(word);
        wordListRepository.save(list);
        wordRepository.delete(word);
        return "redirect:/wordlists/" + listId;
    }

    // Remove words from category (while creating a category)
    @GetMapping("/admin/categories/{categoryId}/removeword/{wordId}")
    public String removeWordFromCategory(@PathVariable("categoryId") Long categoryId,
            @PathVariable("wordId") Long wordId) {
        wordRepository.deleteById(wordId);
        return "redirect:/admin/categories/" + categoryId;
    }

    // Show all words
    @RequestMapping(value = "/words", method = RequestMethod.GET)
    public List<Word> showWordList(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());

        Set<Word> words = new HashSet<>();

        // Admins list of words
        if ("ADMIN".equals(user.getRole())) {

            List<WordList> allLists = (List<WordList>) wordListRepository.findAll();

            for (WordList wordlist : allLists) {
                words.addAll(wordlist.getWordlist());
            }

            // Users list of words
        } else {
            Set<WordList> userlists = wordListRepository.findByOwner(user);
            for (WordList wordlist : userlists) {
                words.addAll(wordlist.getWordlist());
            }
        }
        words.addAll(wordRepository.findByWordCategoryIsNotNull());

        List<Word> wordlist = new ArrayList<>();
        wordlist.addAll(words);

        model.addAttribute("words", wordlist);
        return wordlist;
    }

    // Delete words in word view, only for ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    // Security
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteWord(@PathVariable("id") Long wordId, RedirectAttributes redirectAttributes) {
        Word word = wordRepository.findById(wordId).orElse(null);

        // Check that word exist
        if (word == null) {
            return "redirect:/words";
        }

        // Check if word belongs to users wordlist
        if (wordListRepository.existsByWordlistContaining(word)) {
            redirectAttributes.addFlashAttribute("error",
                    "This word belongs to a user's word list and cannot be deleted.");
            return "redirect:/words";
        }

        wordRepository.deleteById(wordId);
        return "redirect:../words";
    }

}
