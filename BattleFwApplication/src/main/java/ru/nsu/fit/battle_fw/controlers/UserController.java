package ru.nsu.fit.battle_fw.controlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.battle_fw.configs.jwt.JwtUtils;
import ru.nsu.fit.battle_fw.database.model.User;
import ru.nsu.fit.battle_fw.database.repo.UserRepo;
import ru.nsu.fit.battle_fw.responses.AllUsersResponse;
import ru.nsu.fit.battle_fw.responses.info.UserInfo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.nsu.fit.battle_fw.helpers.GetFromHeaders.getUsernameFromJWT;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepo userRepo;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Вернёт всех, кроме самого пользователя
     * @param headers - получает header
     * @return - возвращает список users
     */
    @GetMapping("/get/all/users")
    public ResponseEntity<?>  getAllUsers(@RequestHeader Map<String, String> headers) {
        logger.info("GET /get/all/users");

        String nameOwner = getUsernameFromJWT(headers, jwtUtils);

        List<User> users = userRepo.getAllUsers();

        List<UserInfo> userInfoList = users.stream()
                .map(user -> new UserInfo(user.getId(), user.getUsername()))
                .collect(Collectors.toList());

        userInfoList.removeIf(obj -> obj.getUsername().equals(nameOwner));

        AllUsersResponse userResponse = new AllUsersResponse(userInfoList);
        return ResponseEntity.ok(userResponse);
    }
}
