package br.com.lucasmancan.pms.services;

import br.com.lucasmancan.pms.configurations.AuthenticationUser;
import br.com.lucasmancan.pms.exceptions.AppException;
import br.com.lucasmancan.pms.exceptions.AppNotFoundException;
import br.com.lucasmancan.pms.models.*;
import br.com.lucasmancan.pms.models.dto.AccountInfo;
import br.com.lucasmancan.pms.models.dto.UserDTO;
import br.com.lucasmancan.pms.repositories.UserRepository;
import br.com.lucasmancan.pms.services.interfaces.EmailService;
import br.com.lucasmancan.pms.services.interfaces.TokenService;
import br.com.lucasmancan.pms.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private EmailService emailService;

    private ModelMapper mapper;

    private UserRepository repository;

    private PasswordEncoder passwordEncoder;

    private TokenService tokenService;

    @Autowired
    public UserServiceImpl(EmailService emailService, ModelMapper mapper, UserRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.emailService = emailService;
        this.mapper = mapper;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    public UserDTO signUp(AccountInfo info) throws AppException {

        checkEmail(info.getEmail());

        var user = new AppUser();

        user.setPassword(passwordEncoder.encode(info.getPassword()));
        user.setName(info.getName());
        user.setEmail(info.getEmail());

        user = repository.save(user);
        return convert(user);
    }

    @Override
    public AppUser getCurrentUser(){
        var authUser = (AuthenticationUser) SecurityContextHolder.getContext().getAuthentication();
        return repository.findById(authUser.getId()).orElseThrow(() -> new RuntimeException("User information is not in the context."));
    }

    @Override
    public void verifyEmail(String email) throws AppException {

        var user = repository.findByEmail(email);

        UUID uuid = UUID.randomUUID();

        tokenService.save(user, uuid.toString());

        MailMessage mailMessage = new MailMessage();
        mailMessage.setBody("Seu código de recuperação de senha é: " + uuid.toString());
        mailMessage.setEmail(user.getEmail());
        mailMessage.setSubject("Recuperação de senha");

        emailService.send(mailMessage);

    }

    @Override
    public void verifyToken(String tokenString) throws AppException {

        var token = tokenService.findByToken(tokenString);


        if (token.getExpiresAt() != null
                && token.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new AppException("Invalid token.");

    }


    // Ativar acesso do usuário após registro
    @Override
    public void activate(String tokenString ) {

        var token = tokenService.findByToken(tokenString);

        var user = token.getUser();

        user.setStatus(Status.active);

        repository.save(user);

    }

    @Override
    public void changePassword(PasswordConfirmation passwordConfirmation) throws AppException {

        if (!passwordConfirmation.matchPassword())
            throw new AppException("Password and confirmation doesn't match.");


        Token token = tokenService.findByToken(passwordConfirmation.getToken());

        if(token == null)
            throw new AppException("Token is not valid.");


        var user = token.getUser();

        user.setPassword(passwordEncoder.encode(passwordConfirmation.getPassword()));


        repository.save(user);
    }

    @Override
    public UserDTO save(UserDTO dto) throws AppException {


        var user = getCurrentUser();

        if(!user.getEmail().equals(dto.getEmail()))
            checkEmail(dto.getEmail());

        user.setEmail(dto.getEmail());
        user.setName(dto.getName());

        user = repository.save(user);

        return convert(user);
    }

    private void checkEmail(String email) throws AppException {

        var user = repository.findByEmail(email);

        if (user != null)
            throw new AppException("User already exists, try to change your password.");
    }

    @Override
    public UserDTO update(Long id, UserDTO dto) throws AppNotFoundException {

        var user = find(id);

        user.setEmail(dto.getEmail());
        user.setName(dto.getName());

        user = repository.save(user);
        return convert(user);
    }

    @Override
    public UserDTO findById(Long id) throws AppNotFoundException {
        return this.convert(find(id));
    }

    private UserDTO convert(AppUser appUser) {
        return this.mapper.map(appUser, UserDTO.class);
    }

    private AppUser find(Long id) throws AppNotFoundException {
        return repository.findById(id).orElseThrow(AppNotFoundException::new);
    }

    @Override
    public void inactivate(Long id) throws AppNotFoundException {

        var user = getCurrentUser();

        user.setStatus(Status.inactive);

        repository.save(user);
    }

    @Override
    public List<UserDTO> findAll() {

        // TODO implementar a busca de usuários ativos para administração
        return null;
    }
}
