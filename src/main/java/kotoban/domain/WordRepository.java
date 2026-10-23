package kotoban.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface WordRepository extends CrudRepository<Word, Long> {
    List<Word> findById(String Id);

}
