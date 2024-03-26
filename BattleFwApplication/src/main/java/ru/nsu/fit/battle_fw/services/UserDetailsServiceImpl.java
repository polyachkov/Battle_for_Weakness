package ru.nsu.fit.battle_fw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.nsu.fit.battle_fw.database.model.User;
import ru.nsu.fit.battle_fw.database.repo.UserRepo;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username" + username));


        return UserDetailsImpl.build(user);
    }
}
