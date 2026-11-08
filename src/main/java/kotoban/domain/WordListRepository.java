package kotoban.domain;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;


public interface WordListRepository extends CrudRepository<WordList, Long> {
List<WordList> findByOwner(Principal principal);
List<WordList> findByWordlist(Set<Word> wordlist);
List<WordList> findByListName(String listName);
List<WordList> findByWordListId(Long wordListId);
Set<WordList> findByOwner(User user);

//Check if word exist in any wordlist
boolean existsByWordlistContaining(Word word); 
}
