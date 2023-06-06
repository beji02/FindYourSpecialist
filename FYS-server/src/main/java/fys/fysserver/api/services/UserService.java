package fys.fysserver.api.services;

import fys.fysmodel.Announcement;
import fys.fysmodel.Specialist;
import fys.fysmodel.User;
import fys.fyspersistence.users.UsersRepository;
import fys.fysserver.api.dtos.*;
import fys.fysserver.api.dtos.announcements.AnnouncementDto;
import fys.fysserver.api.dtos.users.*;
import fys.fysserver.api.exceptions.ValidationException;
import fys.fysserver.api.security.jwt.AuthTokenFilter;
import fys.fysserver.api.security.jwt.JwtUtils;
import fys.fysserver.api.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private UsersRepository usersRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    private JwtUtils jwtUtils;

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        logger.info("UsersRepository set");
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        logger.info("AuthenticationManager set");
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        logger.info("PasswordEncoder set");
    }

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
        logger.info("JwtUtils set");
    }

    public UserService() {
        logger.info("UserService created");
    }

    public UserDto findUserByUsername(String username) throws ValidationException {
        logger.info("findUserByUsername: " + username);

        User user = usersRepository.findByUsername(username);
        if (user == null) throw new ValidationException("Username is invalid");

        UserDto userDto = DtoBuilder.buildUserDTO(user);

        return userDto;
    }

    public UserDto updateUser(String username, UserDto updatedUserDto) throws ValidationException {
        logger.info("updateUser: " + username);

        User user = usersRepository.findByUsername(username);
        if (user == null) throw new ValidationException("Username is invalid");

        user.setFirstName(updatedUserDto.getFirstName());
        user.setLastName(updatedUserDto.getLastName());
        user.setPhoneNumber(updatedUserDto.getPhoneNumber());
        user.setEmail(updatedUserDto.getEmail());
        user.setBirthDate(updatedUserDto.getBirthDate());

        usersRepository.modify(user);
        logger.info("User updated: " + user.getUsername() + " " + user.getPassword() + " " + user.getEmail());

        UserDto userDto = DtoBuilder.buildUserDTO(user);

        return userDto;
    }

    public SpecialistDto updateSpecialist(String username, SpecialistDto updatedSpecialistDto) throws ValidationException {
        logger.info("updateSpecialist: " + username);

        Specialist specialist = usersRepository.findSpecialistByUsername(username);
        if (specialist == null) throw new ValidationException("Username is invalid");

        specialist.setLocation(updatedSpecialistDto.getLocation());
        specialist.setDescription(updatedSpecialistDto.getDescription());

        usersRepository.modify(specialist);

        SpecialistDto specialistDto = DtoBuilder.buildSpecialistDTO(specialist);

        return specialistDto;
    }

    public SpecialistDto findSpecialistByUsername(String username) throws ValidationException {
        logger.info("findSpecialistByUsername: " + username);

        Specialist specialist = usersRepository.findSpecialistByUsername(username);
        if (specialist == null) throw new ValidationException("Username is invalid");

        SpecialistDto specialistDto = DtoBuilder.buildSpecialistDTO(specialist);

        return specialistDto;
    }

    public void upgradeUserToSpecialist(String username) throws ValidationException {
        logger.info("upgradeUserToSpecialist: " + username);

        User user = usersRepository.findByUsername(username);
        if (user == null) throw new ValidationException("Username is invalid");
        if (user instanceof Specialist) throw new ValidationException("Already a specialist");

        usersRepository.upgradeToSpecialist(user);
    }

    public List<AnnouncementDto> getRecentlyVisitedAnnouncements(String username) {
        logger.info("getRecentlyVisitedAnnouncements: " + username);

        User user = usersRepository.findByUsername(username);
        if (user == null) return new ArrayList<>();

        List<Announcement> announcements = (List<Announcement>) user.getRecentlyVisitedAnnouncements();

        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        announcements.forEach(announcement -> {
            Boolean isFavourite = user.getFavoriteAnnouncements().contains(announcement);
            announcementDtos.add(DtoBuilder.buildAnnouncementDTO(announcement, isFavourite));
        });

        return announcementDtos;
    }

    /**
     * login a user providing a JWT token and roles
     * @param newLoginDto username and password in DTO format
     * @return JWT token and roles in DTO format
     * @throws ValidationException if username or password are invalid
     */
    public LoginDto login(NewLoginDto newLoginDto) throws ValidationException {
        logger.info("login: " + newLoginDto.getUsername());

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    newLoginDto.getUsername(), newLoginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            LoginDto loginDto = DtoBuilder.buildLoginDTO(userDetails, jwt, roles);
            return loginDto;
        } catch (AuthenticationException e) {
            throw new ValidationException("Invalid username or password");
        }
    }

    public UserDto register(NewRegistrationDto newRegistrationDto) throws ValidationException {
        logger.info("register: " + newRegistrationDto.getUsername() + " " + newRegistrationDto.getEmail());

        User user = usersRepository.findByUsername(newRegistrationDto.getUsername());
        if (user != null) throw new ValidationException("Username is already taken");

        user = new User();
        user.setUsername(newRegistrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(newRegistrationDto.getPassword()));
        user.setEmail(newRegistrationDto.getEmail());

        usersRepository.add(user);

        UserDto userDto = DtoBuilder.buildUserDTO(user);
        return userDto;
    }
}
