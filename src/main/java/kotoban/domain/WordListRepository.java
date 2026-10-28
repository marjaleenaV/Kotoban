package kotoban.domain;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;


public interface WordListRepository extends CrudRepository<WordList, Long> {
List<WordList> findByOwner(User owner);
List<WordList> findByWordlist(Set<Word> wordlist);
List<WordList> findByListName(String listName);
}
