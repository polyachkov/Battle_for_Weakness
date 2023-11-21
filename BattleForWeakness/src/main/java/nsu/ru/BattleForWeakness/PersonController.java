package nsu.ru.BattleForWeakness;

import nsu.ru.BattleForWeakness.model.Person;
import nsu.ru.BattleForWeakness.repo.PersonRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    final
    PersonRepo repo;

    public PersonController(PersonRepo repo) {
        this.repo = repo;
    }

    @PostMapping("/persons/add")
    public void addPerson(@RequestBody Person[] persons) {
        for (int i = 0; i < persons.length; i++) {
            repo.save(persons[i]);
        }
    }
}
