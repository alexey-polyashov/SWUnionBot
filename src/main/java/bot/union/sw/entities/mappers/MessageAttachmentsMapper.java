package bot.union.sw.entities.mappers;


import bot.union.sw.entities.MessageAttachment;
import bot.union.sw.entities.dto.MessageAttachmentsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageAttachmentsMapper {

    MessageAttachmentsDto toDto(MessageAttachment model);

}
