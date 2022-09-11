package bot.union.sw.entities.mappers;

import bot.union.sw.entities.BotUser;
import bot.union.sw.entities.dto.BotUserDto;
import bot.union.sw.entities.dto.BotUserUpdateDto;
import bot.union.sw.entities.dto.NewBotUserDto;
import bot.union.sw.services.BotUserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses={BotUserService.class})
public interface BotUserMapper {

    BotUser toModel(NewBotUserDto dto);

    @Mapping(target = "id", source = "botUserSource.id")
    @Mapping(target = "email", source = "botUserUpdate.email")
    @Mapping(target = "login", source = "botUserUpdate.login")
    @Mapping(target = "domainName", source = "botUserUpdate.domainName")
    BotUser copy(BotUser botUserSource, BotUserUpdateDto botUserUpdate);
    BotUser toModel(BotUserUpdateDto dto);
    BotUserDto toDto(BotUser botUser);

}
