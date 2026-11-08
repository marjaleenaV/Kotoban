package kotoban.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kotoban.domain.Category;
import kotoban.domain.CategoryRepository;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;



@RestController 
@RequestMapping("/api")
public class ApiController {

    private final CategoryRepository categoryRepository;

    public ApiController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return (List<Category>) categoryRepository.findAll();
    }
    

}
