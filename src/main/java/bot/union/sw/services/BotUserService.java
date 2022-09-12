package bot.union.sw.services;

import bot.union.sw.entities.AllowService;
import bot.union.sw.entities.BotUser;
import bot.union.sw.entities.Roles;
import bot.union.sw.entities.dto.BotUserDto;
import bot.union.sw.entities.dto.BotUserUpdateDto;
import bot.union.sw.entities.dto.NewBotUserDto;
import bot.union.sw.entities.dto.RoleDto;
import bot.union.sw.entities.mappers.BotUserMapper;
import bot.union.sw.exceptions.InternalError;
import bot.union.sw.exceptions.ResourceNotFound;
import bot.union.sw.repository.BotUserRepository;
import bot.union.sw.repository.RoleRepository;
import com.pengrad.telegrambot.model.Chat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BotUserService {

    private final BotUserMapper botUserMapper;
    private final RoleRepository roleRepository;
    private final BotUserRepository botUserRepository;


    public BotUser getUserByString(String identifier){
        log.info("getUser: user id - {}", identifier);
        return getUser(identifier, new Chat())
                .orElseThrow(()->new ResourceNotFound("Пользователь с логином или email '" + identifier + "' не найден в базе"));
    }

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

    public Long saveBotUser(BotUser botUser) {
        botUserRepository.save(botUser);
        return botUser.getId();
    }

    public Long saveBotUser(BotUserUpdateDto botUserDto) {
        Long id = botUserDto.getId();
        if(id==null || id==0){
            throw new InternalError("Должен быть указан id изменяемого объекта");
        }
        BotUser oldBotUser = botUserRepository.findById(id).orElseThrow(
                ()->new ResourceNotFound("Пользователь с id '" + id + "' не найден"));
        BotUser newBotUser = botUserMapper.copy(oldBotUser, botUserDto);
        botUserRepository.save(newBotUser);
        return newBotUser.getId();
    }

    public Long addBotUser(NewBotUserDto botUserDto) {
        BotUser botUser = botUserMapper.toModel(botUserDto);
        botUserRepository.save(botUser);
        return botUser.getId();
    }

    public void saveRoles(Long userId, List<RoleDto> rolesDto) {
        BotUser oldBotUser = botUserRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFound("Пользователь с id '" + userId + "' не найден"));
        List<Roles> roles = rolesDto.stream()
                        .map((v)->roleRepository.findByName(v.getName()).orElseThrow(
                                ()->new ResourceNotFound("Роль с наименованием '" + v.getName() + "' не найденa"))
                        )
                                .collect(Collectors.toList());
        oldBotUser.setRoles(roles);
        botUserRepository.save(oldBotUser);
    }

    public void setMarkUser(Long userId, Boolean mark){
        BotUser oldBotUser = botUserRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFound("Пользователь с id '" + userId + "' не найден"));
        oldBotUser.setMarked(mark);
        botUserRepository.save(oldBotUser);
    }

    public void setBlockUser(Long userId, Boolean block){
        BotUser oldBotUser = botUserRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFound("Пользователь с id '" + userId + "' не найден"));
        oldBotUser.setBlocked(block);
        botUserRepository.save(oldBotUser);
    }

    public void savePassword(Long userId, String password){
        BotUser oldBotUser = botUserRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFound("Пользователь с id '" + userId + "' не найден"));
        oldBotUser.setPassword(password);
        botUserRepository.save(oldBotUser);
    }

    public BotUserDto findUserById(Long userId){
        BotUser oldBotUser = botUserRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFound("Пользователь с id '" + userId + "' не найден"));
        return botUserMapper.toDto(oldBotUser);
    }

    public void deleteBotUser(Long userId){
        botUserRepository.deleteById(userId);
    }

    public List<BotUser> findByService(AllowService service){
        return botUserRepository.findByUserServices(service);
    }

}
