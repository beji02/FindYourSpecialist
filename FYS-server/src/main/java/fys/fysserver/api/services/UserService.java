package fys.fysserver.api.services;

import fys.fysmodel.Announcement;
import fys.fysmodel.Specialist;
import fys.fysmodel.User;
import fys.fyspersistence.users.UsersRepository;
import fys.fysserver.api.dtos.*;
import fys.fysserver.api.dtos.announcements.AnnouncementDto;
import fys.fysserver.api.dtos.users.*;
import fys.fysserver.api.exceptions.ValidationException;
import fys.fysserver.api.security.jwt.JwtUtils;
import fys.fysserver.api.security.services.UserDetailsImpl;
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
    private UsersRepository usersRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    private JwtUtils jwtUtils;

    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }


    public UserService() {
    }


    /**
     * Get user by username
     * @param username username of the user
     * @return user in dto format
     * @throws ValidationException if username is invalid
     */
    public UserDto findUserByUsername(String username) throws ValidationException {
        System.out.println("findUserByUsername: " + username);

        // get user
        User user = usersRepository.findByUsername(username);
        if(user == null) throw new ValidationException("Username is invalid");

        // build user DTO
        UserDto userDto = DtoBuilder.buildUserDTO(user);

        return userDto;
    }

    /**
     * update user details
     * @param username username of the user
     * @param updatedUserDto new details in DTO format
     * @return updated user in DTO format
     * @throws ValidationException if username is invalid
     */
    public UserDto updateUser(String username, UserDto updatedUserDto) throws ValidationException {

        // get user
        User user = usersRepository.findByUsername(username);
        if(user == null) throw new ValidationException("Username is invalid");

        // update user
        user.setFirstName(updatedUserDto.getFirstName());
        user.setLastName(updatedUserDto.getLastName());
        user.setPhoneNumber(updatedUserDto.getPhoneNumber());
        user.setEmail(updatedUserDto.getEmail());
        user.setBirthDate(updatedUserDto.getBirthDate());

        // save user
        usersRepository.modify(user);
        System.out.println("updateUser: " + user.getUsername() + " " + user.getPassword() + " " + user.getEmail());

        // build user DTO
        UserDto userDto = DtoBuilder.buildUserDTO(user);

        return userDto;
    }

    /**
     * update specialist details
     * @param username username of the specialist
     * @param updatedSpecialistDto new details in DTO format
     * @return updated specialist in DTO format
     * @throws ValidationException if username is not a specialist's username
     */
    public SpecialistDto updateSpecialist(String username, SpecialistDto updatedSpecialistDto) throws ValidationException {

        // get specialist
        Specialist specialist = usersRepository.findSpecialistByUsername(username);
        if(specialist == null) throw new ValidationException("Username is invalid");

        // update specialist
        specialist.setLocation(updatedSpecialistDto.getLocation());
        specialist.setDescription(updatedSpecialistDto.getDescription());

        // save specialist
        System.out.println("updateUser: " + specialist.getUsername() + " " + specialist.getEmail());
        usersRepository.modify(specialist);

        // build specialist DTO
        SpecialistDto specialistDto = DtoBuilder.buildSpecialistDTO(specialist);

        return specialistDto;
    }

    /**
     * get the specialist by username
     * @param username username of the specialist
     * @return specialist in DTO format
     * @throws ValidationException if username is not a specialist's username
     */
    public SpecialistDto findSpecialistByUsername(String username) throws ValidationException {
        System.out.println("findSpecialistByUsername: " + username);

        // get specialist
        Specialist specialist = usersRepository.findSpecialistByUsername(username);
        if(specialist == null) throw new ValidationException("Username is invalid");

        SpecialistDto specialistDto = DtoBuilder.buildSpecialistDTO(specialist);

        return specialistDto;
    }

    /**
     * upgrade status of user to specialist
     * @param username username of the user
     * @throws ValidationException if username is invalid or user is already a specialist
     */
    public void upgradeUserToSpecialist(String username) throws ValidationException {

        // get user
        User user = usersRepository.findByUsername(username);
        if(user == null) throw new ValidationException("Username is invalid");
        if(user instanceof Specialist) throw new ValidationException("Already specialist");

        // upgrade user to specialist
        usersRepository.upgradeToSpecialist(user);
    }

    /**
     * get a list of recently visited announcements of a user
     * @param username username of user
     * @return list of recently visited announcements in DTO format
     */
    public List<AnnouncementDto> getRecentlyVisitedAnnouncements(String username) {
        // ger user
        User user = usersRepository.findByUsername(username);
        if(user == null) return new ArrayList<>();

        // get recently visited announcements
        List<Announcement> announcements = (List<Announcement>) user.getRecentlyVisitedAnnouncements();

        // build announcement DTOs
        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        announcements.forEach(announcement -> {
                    Boolean isFavourite = user.getFavoriteAnnouncements().contains(announcement);
                    announcementDtos.add(DtoBuilder.buildAnnouncementDTO(announcement, isFavourite));
                }
        );

        return announcementDtos;
    }

    /**
     * login a user providing a JWT token and roles
     * @param newLoginDto username and password in DTO format
     * @return JWT token and roles in DTO format
     * @throws ValidationException if username or password are invalid
     */
    public LoginDto login(NewLoginDto newLoginDto) throws ValidationException {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    newLoginDto.getUsername(), newLoginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            //build login DTO
            LoginDto loginDto = DtoBuilder.buildLoginDTO(userDetails, jwt, roles);
            return loginDto;
        }
        catch (AuthenticationException e){
            throw new ValidationException("Invalid username or password");
        }
    }

    /**
     * register a new user using username, password and mail
     * @param newRegistrationDto username, password and mail in DTO format
     * @return new user in DTO format
     * @throws ValidationException if username is already taken
     */
    public UserDto register(NewRegistrationDto newRegistrationDto) throws ValidationException {
        System.out.println("register: " + newRegistrationDto.getUsername() + " " + newRegistrationDto.getPassword() + " " + newRegistrationDto.getEmail());

        // check if username is already taken
        User user = usersRepository.findByUsername(newRegistrationDto.getUsername());
        if(user != null) throw new ValidationException("Username is already taken");

        // build a new user
        user = new User();
        user.setUsername(newRegistrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(newRegistrationDto.getPassword()));
        user.setEmail(newRegistrationDto.getEmail());

        usersRepository.add(user);

        // build user DTO
        UserDto userDto = DtoBuilder.buildUserDTO(user);
        return userDto;
    }
}
