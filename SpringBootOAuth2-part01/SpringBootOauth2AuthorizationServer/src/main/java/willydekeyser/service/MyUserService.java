/*
package willydekeyser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import willydekeyser.entity.User;

@Service
public class MyUserService {

    @Autowired
    UserDetailsManager manager;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        manager.createUser(user);
    }
}
*/
