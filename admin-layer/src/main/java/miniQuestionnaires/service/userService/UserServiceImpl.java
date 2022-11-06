package miniQuestionnaires.service.userService;

import miniQuestionnaires.api.converter.UserApiConverter;
import miniQuestionnaires.api.data.UserApiData;
import miniQuestionnaires.dto.RegisterUserDto;
import miniQuestionnaires.persistence.layer.entity.Question;
import miniQuestionnaires.persistence.layer.entity.Role;
import miniQuestionnaires.persistence.layer.entity.User;
import miniQuestionnaires.persistence.layer.repository.RoleRepo;
import miniQuestionnaires.persistence.layer.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserApiConverter userApiConverter;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo,
                           BCryptPasswordEncoder passwordEncoder,
                           UserApiConverter userApiConverter) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.userApiConverter = userApiConverter;
    }

    @Override
    public List<UserApiData> getUsersCompletedQuestionnaire(Long questionnaireId) {
        return userRepo.findUsersByQuestionAndQuestionnaire(questionnaireId).stream()
                .map(userApiConverter::convertToShowApi)
                .collect(Collectors.toList());
    }

    @Override
    public UserApiData getUserById(Long userId) {
        Optional<User> result = userRepo.findById(userId);

        return result.isPresent() ?
            userApiConverter.convertToShowApi(result.get()) : new UserApiData();
    }

    @Override
    public void saveRegisterUser(RegisterUserDto userDto) {
        User user = new User();

        user.setRoles(Collections.singletonList(roleRepo.getByTitle("ROLE_USER")));
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setPatronymic(userDto.getPatronymic());

        userRepo.save(user);
    }

    @Override
    public String validateNewUser(RegisterUserDto registerUser) {
        String message = "";
        if (registerUser.getEmail() != null) {
            if (userRepo.findUserByEmail(registerUser.getEmail()).isPresent()) {
                message = "Пользователь с почтой \"" + registerUser.getEmail() + "\" уже существует";
            }
        }
        return message;
    }

    @Override
    public String getUserRole(User user) {
        return user.getRoles().contains(roleRepo.getByTitle("ROLE_ADMIN")) ? "ROLE_ADMIN" : "ROLE_USER";
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> result = userRepo.findUserByEmail(email);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw new UsernameNotFoundException("Пользователь с почтой \"" + email + "\" не найден");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> result = userRepo.findUserByEmail(email);

        User user;

        if (result.isPresent()) {
            user = result.get();
        } else {
            throw new UsernameNotFoundException("Пользователь с почтой \"" + email + "\" не найден");
        }

        return new org.springframework.security.core.userdetails.User
                (user.getEmail(), user.getPassword(),
                getRoles(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getRoles(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getTitle()))
                .collect(Collectors.toList());
    }
}
