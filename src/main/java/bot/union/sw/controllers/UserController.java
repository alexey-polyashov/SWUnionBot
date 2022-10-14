package bot.union.sw.controllers;

import bot.union.sw.entities.BotUser;
import bot.union.sw.entities.dto.BotUserDto;
import bot.union.sw.entities.dto.BotUserUpdateDto;
import bot.union.sw.entities.dto.NewBotUserDto;
import bot.union.sw.entities.dto.RoleDto;
import bot.union.sw.services.BotUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final BotUserService botUserService;

    @PostMapping()
    public Long newUser(@RequestBody NewBotUserDto newBotUserDto){
        return botUserService.addBotUser(newBotUserDto);
    }

    @PutMapping()
    public Long newUser(@RequestBody BotUserUpdateDto botUserDto){
        return botUserService.saveBotUser(botUserDto);
    }

    @PutMapping("/{userId}/password")
    public void setPassword(@PathVariable(name="userId") Long userId, @RequestBody String newPassword){
        botUserService.setPassword(userId, newPassword);
    }

    @GetMapping("/{userId}")
    public BotUserDto findUser(@PathVariable(name="userId") Long userId){
        return botUserService.findUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public BotUserDto deleteUser(@PathVariable(name="userId") Long userId){
        return botUserService.findUserById(userId);
    }

    @PutMapping("/{userId}/roles")
    public void newUser(@PathVariable(name="userId") Long userId, @RequestBody List<RoleDto> roleList){
        botUserService.saveRoles(userId, roleList);
    }

    @PutMapping("/{userId}/block")
    public void blockUser(@PathVariable(name="userId") Long userId){
        botUserService.setBlockUser(userId, true);
    }

    @PutMapping("/{userId}/unblock")
    public void unblockUser(@PathVariable(name="userId") Long userId){
        botUserService.setBlockUser(userId, false);
    }

    @PutMapping("/{userId}/mark")
    public void markUser(@PathVariable(name="userId") Long userId){
        botUserService.setMarkUser(userId, true);
    }

    @PutMapping("/{userId}/unmark")
    public void unmarkUser(@PathVariable(name="userId") Long userId){
        botUserService.setMarkUser(userId, false);
    }

}
