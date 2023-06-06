package fys.fysserver.api.security.services;

import fys.fysmodel.User;
import fys.fyspersistence.users.UsersRepository;
import fys.fysserver.api.security.WebSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private UsersRepository usersRepository;

    public void setUsersRepository(UsersRepository usersRepository) {
        logger.info("userDetailsServiceImpl usersRepository");

        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return UserDetailsImpl.build(user);
    }
}
