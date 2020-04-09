package br.com.lucasmancan.gap.services;

import br.com.lucasmancan.gap.exceptions.AppException;
import br.com.lucasmancan.gap.exceptions.AppNotFoundException;
import br.com.lucasmancan.gap.models.*;
import br.com.lucasmancan.gap.models.dto.SignUpInfo;
import br.com.lucasmancan.gap.models.dto.UserDTO;
import br.com.lucasmancan.gap.repositories.UserRepository;
import br.com.lucasmancan.gap.services.interfaces.EmailService;
import br.com.lucasmancan.gap.services.interfaces.TokenService;
import br.com.lucasmancan.gap.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private EmailService emailService;

    private ModelMapper mapper;

    private UserRepository repository;

    private TokenService tokenService;

    @Autowired
    public UserServiceImpl(EmailService emailService, ModelMapper mapper, UserRepository repository, TokenService tokenService) {
        this.emailService = emailService;
        this.mapper = mapper;
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @Override
    public UserDTO signUp(SignUpInfo info) throws AppException {

        checkEmail(info.getEmail());

        var user = new AppUser();

        user.setPassword(info.getPassword());
        user.setName(info.getName());
        user.setEmail(info.getEmail());

        user = repository.save(user);
        return convert(user);
    }

    @Override
    public void verifyEmail(String email) {

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

    @Override
    public void activate(String token) {
        // Ativar acesso do usuário após registro
    }

    @Override
    public void changePassword(PasswordConfirmation passwordConfirmation) throws AppException {

        if (!passwordConfirmation.matchPassword())
            throw new AppException("Password and confirmation doesn't match.");


        Token token = tokenService.findByToken(passwordConfirmation.getToken());

        var user = token.getUser();

        user.setPassword(passwordConfirmation.getPassword());


        repository.save(user);
    }

    @Override
    public UserDTO save(UserDTO dto) throws AppException {

        checkEmail(dto.getEmail());

        var user = mapper.map(dto, AppUser.class);

        //TODO verificar e-mail para ativar acesso

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

        var user = this.find(id);

        user.setStatus(Status.inactive);

        repository.save(user);
    }

    @Override
    public List<UserDTO> findAll() {

        // TODO implementar a busca de usuários ativos para administração
        return null;
    }
}
