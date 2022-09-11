package bot.union.sw.entities.mappers;

import bot.union.sw.entities.ExtMessage;
import bot.union.sw.entities.dto.*;
import bot.union.sw.services.AllowServicesService;
import bot.union.sw.services.ExtMessageService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses={ExtMessageService.class, AllowServicesService.class})
public interface ExtMessageMapper {

    ExtMessage toModel(ExtMessageDto dto);
    ExtMessage toModel(NewExtMessageDto dto);
    ExtMessageDto toDto(ExtMessage botUser);

}
