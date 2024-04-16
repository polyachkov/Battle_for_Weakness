package ru.nsu.fit.battle_fw.controlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.battle_fw.database.model.User;
import ru.nsu.fit.battle_fw.database.repo.UserRepo;
import ru.nsu.fit.battle_fw.responses.AllUsersResponse;
import ru.nsu.fit.battle_fw.responses.info.UserInfo;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepo userRepo;

    @GetMapping("/get/all/users")
    public ResponseEntity<?>  getAllUsers() {
        logger.info("GET /get/all/users");

        List<User> users = userRepo.getAllUsers();

        List<UserInfo> userInfoList = users.stream()
                .map(user -> new UserInfo(user.getId(), user.getUsername()))
                .collect(Collectors.toList());

        AllUsersResponse userResponse = new AllUsersResponse(userInfoList);
        return ResponseEntity.ok(userResponse);
    }
}
