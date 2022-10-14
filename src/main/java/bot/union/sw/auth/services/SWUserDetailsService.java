package bot.union.sw.auth.services;

import bot.union.sw.entities.BotUser;
import bot.union.sw.exceptions.ResourceNotFound;
import bot.union.sw.repository.BotUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SWUserDetailsService implements UserDetailsService {

    private BotUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        BotUser botUser = userRepository.findByLogin(s)
                .orElseThrow(()->new UsernameNotFoundException("Пользователь с логином '" + s + "' не найден"));
        return new SWUserDetails(botUser);
    }
}
