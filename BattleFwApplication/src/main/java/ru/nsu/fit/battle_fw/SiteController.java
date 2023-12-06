package ru.nsu.fit.battle_fw;

import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.battle_fw.database.model.*;
import ru.nsu.fit.battle_fw.database.repo.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class SiteController {

    final
    PersonRepo personR;
    final
    CardRepo cardR;
    final
    GameRepo gameR;
    final
    LibraryRepo libR;
    final
    LibraryCompRepo libCompR;
    final
    HandRepo handR;
    final
    HandCompRepo handCompR;
    final
    FieldRepo fieldR;

    public SiteController(PersonRepo personR,
                          CardRepo cardR,
                          GameRepo gameR,
                          LibraryRepo libR,
                          LibraryCompRepo libCompR,
                          HandRepo handR,
                          HandCompRepo handCompR,
                          FieldRepo fieldR) {
        this.personR = personR;
        this.cardR = cardR;
        this.gameR = gameR;
        this.libR = libR;
        this.libCompR = libCompR;
        this.handR = handR;
        this.handCompR = handCompR;
        this.fieldR = fieldR;
    }

    @PostMapping("/persons/add")
    public void addPerson(@RequestBody Person[] persons) {
        personR.saveAll(Arrays.asList(persons));
    }

    @PostMapping("/init")
    public void init(@RequestBody InitGameRequest req) {
        String fraction1 = req.getFraction1();
        String fraction2 = req.getFraction2();
        Integer player1 = req.getPlayer1();
        Integer player2 = req.getPlayer2();

        //Game init (create game id and get it)
        Game game = new Game();
        game.setId_player1(player1);
        game.setId_player2(player2);
        game.setIs_ended(false);
        gameR.save(game);

        String[] rarityS = new String[] {"common", "uncommon", "rare", "epic", "legendary"};
        for (String s : rarityS) {
            createLib(s, fraction1, game, player1);
            createLib(s, fraction2, game, player2);
        }

        createHand(libR.getLibId(player1, game.getId_game(), "common"), game.getId_game(), player1);
        createHand(libR.getLibId(player2, game.getId_game(), "common"), game.getId_game(), player2);

        for (int i = 1; i <= 40; i++) {
            Field field = new Field();
            field.setField_num(i);
            field.setId_card(null);
            field.setId_game(game.getId_game());
            fieldR.save(field);
        }
    }

    //Create libraries and shuffle it + Add libraries in Database
    private void createLib(String rarity, String fraction, Game game, Integer playerId) {
        List<Card> listC =  cardR.getListOfCard(rarity, fraction);
        List<Integer> listId =  new ArrayList<Integer>();
        for (Card c : listC) {
            for (int i = c.getNumber_of_cards(); i > 0; i--) {
                listId.add(c.getId_card());
            }
        }
        Collections.shuffle(listId);

        Library lib = new Library();

        lib.setCards_cnt(listId.size());
        lib.setGame_id(game.getId_game());
        lib.setPlayer_id(playerId);
        if (rarity == "common") {
            lib.setLocked(false);
        }
        else {
            lib.setLocked(true);
        }
        lib.setRarity(rarity);

        libR.save(lib);

        for (Integer c : listId) {
            LibraryComp libComp = new LibraryComp();
            libComp.setId_card(c);
            libComp.setId_library(lib.getId_library());
            libCompR.save(libComp);
        }
    }

    private void createHand(Integer lib_id, Integer id_game, Integer id_player) {
        Hand hand = new Hand();
        hand.setId_game(id_game);
        hand.setCards_cnt(0);
        hand.setId_player(id_player);
        handR.save(hand);
        for(int i = 0; i < 7; i++) {
            getCardToHand(lib_id, hand.getId_hand());
            hand.setCards_cnt(hand.getCards_cnt() + 1);
        }
    }

    private void getCardToHand(Integer lib_id, Integer id_hand) {
        LibraryComp libComp = libCompR.getMinCard(lib_id);
        HandComp handComp = new HandComp();
        handComp.setId_hand(id_hand);
        handComp.setId_card(libComp.getId_card());
        libCompR.deleteById(libComp.getId_card_lib());
        Library lib = libR.getReferenceById(lib_id);
        lib.setCards_cnt(lib.getCards_cnt() - 1);
        handCompR.save(handComp);
    }
}
