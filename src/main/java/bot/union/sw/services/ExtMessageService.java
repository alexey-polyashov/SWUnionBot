package bot.union.sw.services;

import bot.union.sw.entities.ExtMessage;
import bot.union.sw.entities.MessageAttachment;
import bot.union.sw.entities.dto.ExtMessageDto;
import bot.union.sw.entities.dto.NewExtMessageDto;
import bot.union.sw.entities.mappers.ExtMessageMapper;
import bot.union.sw.exceptions.ResourceNotFound;
import bot.union.sw.repository.ExtMessageRepository;
import bot.union.sw.repository.MessageAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExtMessageService {

    private final ExtMessageRepository messageRepository;
    private final MessageAttachmentRepository attachmentRepository;
    private final ExtMessageMapper messageMapper;

    @Transactional
    public ExtMessageDto addMessage(NewExtMessageDto newMessage){
        ExtMessage message = messageMapper.toModel(newMessage);
        messageRepository.save(message);
        return messageMapper.toDto(message);
    }

    public String addAttachment(UUID messageId, MultipartFile mpf) throws IOException {
        ExtMessage extMessage = messageRepository.findById(messageId)
                .orElseThrow(()->new ResourceNotFound("Сообщение с идентификатором '" + messageId + "' не найдено"));
        MessageAttachment attachment = new MessageAttachment();
        attachment.setMessage(extMessage);
        attachment.setIdentifier(String.valueOf(extMessage.getAttachments().size()));
        attachment.setFileSize(mpf.getSize());
        attachment.setContentType(mpf.getContentType());
        attachment.setData(mpf.getBytes());
        attachmentRepository.save(attachment);
        return attachment.getIdentifier();
    }

    public ExtMessageDto findMessage(UUID messageId){
        ExtMessage message = messageRepository.findById(messageId).orElseThrow(
                ()->new ResourceNotFound("Сообщение с идентификатором '" + messageId + "' не найдено")
        );
        return messageMapper.toDto(message);
    }

    public MessageAttachment getAttachmentData(UUID attachmentId){

        return null;
    }

    public List<ExtMessageDto> getMessagesQueue(int limit){
        return null;
    }

    public int truncateMassageQueue(){
        return 0;
    }

}
