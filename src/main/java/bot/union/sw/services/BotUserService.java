package bot.union.sw.services;

import bot.union.sw.entities.BotUser;
import bot.union.sw.repository.BotUserRepository;
import com.pengrad.telegrambot.model.Chat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BotUserService {

    private final BotUserRepository botUserRepository;

    public Optional<BotUser> getUser(String identifier, Chat chat){
        log.info("chat:{}, getUser: user id - {}", chat.id(), identifier);
        Optional<BotUser> botUser = botUserRepository.findByEmail(identifier);
        if(botUser.isEmpty()){
            log.info("chat:{}, getUser: user id - {}, don't find by email", chat.id(), identifier);
            botUser = botUserRepository.findByDomainName(identifier);
        }
        return botUser;
    }

    public Optional<BotUser> getUserByChat(Chat chat){
        log.info("chat:{}, getUserByChat", chat.id());
        Optional<BotUser> botUser = botUserRepository.findByChatId(chat.id());
        if(botUser.isEmpty()){
            log.info("chat:{}, getUserByChat, don't find", chat.id());
        }
        return botUser;
    }

}
