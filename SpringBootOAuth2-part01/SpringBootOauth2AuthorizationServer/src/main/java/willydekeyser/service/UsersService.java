package willydekeyser.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import willydekeyser.entity.SecurityUser;
import willydekeyser.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UserRepository usersRepository;

    public List<SecurityUser> getUsers() {
        return usersRepository.findAll();
    }

}



    /*
        SecurityUser createUser(SecurityUser securityUser){
            securityUser.setPassword(passwordEncoder.encode(securityUser.getPassword()));
            return usersRepository.save(securityUser);
    }
    */


    /*
        public String addUser(UserInfo userInfo) {
            userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
            repository.save(userInfo);
        return "user added to system ";
    }*/