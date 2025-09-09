package ro.siit.HRS.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.siit.HRS.exceptions.UserNotFoundException;
import ro.siit.HRS.model.User;
import ro.siit.HRS.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class HrsUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(()
                -> new UserNotFoundException("This username " + username + " was not found!"));

        return new HrsUserDetails(user);
    }
}
