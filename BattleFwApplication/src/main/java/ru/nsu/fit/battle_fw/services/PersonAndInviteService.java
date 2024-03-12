package ru.nsu.fit.battle_fw.services;

import org.springframework.stereotype.Component;
import ru.nsu.fit.battle_fw.database.model.Invites;
import ru.nsu.fit.battle_fw.database.model.Person;
import ru.nsu.fit.battle_fw.database.repo.*;
import ru.nsu.fit.battle_fw.exceptions.PersonAlreadyExistsException;

import java.util.List;
import java.util.Optional;

/**
 * Класс для управления пользователями и приглашениями в игру
 */
@Component
public class PersonAndInviteService {
    private final PersonRepo personR;
    private final CardRepo cardR;
    private final GameRepo gameR;
    private final LibraryRepo libR;
    private final LibraryCompRepo libCompR;
    private final HandRepo handR;
    private final HandCompRepo handCompR;
    private final CellRepo cellR;
    private final StatusRepo statusR;
    private final InvitesRepo invitesR;

    /**
     * Конструктор. Принимает объекты, позволяющие влиять на базу данных.
     * @param personR - Таблица с персонами
     * @param cardR - Таблица карт (описание карт и их id)
     * @param gameR - Таблица игр (id игр и их состояние)
     * @param libR - Таблица библиотеки (редкость)
     * @param libCompR - Таблица состава библиотек
     * @param handR - Таблица рук
     * @param handCompR - Таблица состава рук
     * @param cellR - Таблица клеток игр (полей)
     * @param statusR - Статус каждого игрока в контексте каждой из его игр
     */
    public PersonAndInviteService(PersonRepo personR, CardRepo cardR, GameRepo gameR, LibraryRepo libR, LibraryCompRepo libCompR,
                       HandRepo handR, HandCompRepo handCompR, CellRepo cellR, StatusRepo statusR, InvitesRepo invitesR) {
        this.personR = personR;
        this.cardR = cardR;
        this.gameR = gameR;
        this.libR = libR;
        this.libCompR = libCompR;
        this.handR = handR;
        this.handCompR = handCompR;
        this.cellR = cellR;
        this.statusR = statusR;
        this.invitesR = invitesR;
    }

    /**
     * Сохранение нового пользователя в базе данных
     * @param person - Объект персоны
     * @throws PersonAlreadyExistsException - Персона уже существует, поэтому нельзя добавить
     * Ничего не возвращает
     */
    public void addPerson(Person person) throws PersonAlreadyExistsException {
        Person reference = personR.findByName(person.getName()); // Проверка на наличие в базе
        if (reference == null) {
            personR.save(person);
        } else {
            throw new PersonAlreadyExistsException("p");
        }
    }

    /**
     * Получить все игры, куда был приглашён пользователь
     * @param playerId - ID целевого пользователя
     * @return - возвращает список объектов типа Invites
     */
    public Optional<List<Invites>> getInvitesByPlayerId(Integer playerId) {
        return Optional.of(invitesR.getInvites(playerId));
    }

}
