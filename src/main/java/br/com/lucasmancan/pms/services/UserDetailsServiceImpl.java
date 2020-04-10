package br.com.lucasmancan.pms.services;

import br.com.lucasmancan.pms.configurations.AuthenticationUser;
import br.com.lucasmancan.pms.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public UserDetailsServiceImpl(UserRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    private UserRepository repo;

    private ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = repo.findByEmail(email);

        if (user == null)
            throw new UsernameNotFoundException("Credentials not found!");

        return mapper.map(user, AuthenticationUser.class);
    }

}
