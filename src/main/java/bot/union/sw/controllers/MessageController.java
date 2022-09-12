package bot.union.sw.controllers;

import bot.union.sw.BotConfig;
import bot.union.sw.entities.dto.ExtMessageDto;
import bot.union.sw.entities.dto.NewExtMessageDto;
import bot.union.sw.services.BotService;
import bot.union.sw.services.BotUserService;
import bot.union.sw.services.ExtMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/message")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final BotUserService botUserService;
    private final ExtMessageService messageService;

    private final BotConfig botConfig;
    private final BotService botService;

    @PostMapping
    public ExtMessageDto sendMessage(@RequestParam String textMessage,
                           @RequestParam String serviceName,
                           @RequestParam String userName, //логин или email
                           @RequestParam("attachments") List<MultipartFile> files
                           ){

        ExtMessageDto messageDto = messageService.addMessage(
                NewExtMessageDto.builder()
                        .textMessage(textMessage)
                        .serviceName(serviceName)
                        .userIdentifier(userName)
                        .build());
        UUID uuid = messageDto.getId();
        files.stream().forEach(
                (f) -> {
                    try {
                        messageService.addAttachment(uuid, f);
                    } catch (IOException e) {
                        log.error("sendMessage: add attachment, message id - {}, error - {}", uuid, e.getMessage());
                    }
                }
        );
        messageService.setReady(uuid);
        messageDto = messageService.findMessage(messageDto.getId());
        return messageDto;
    }
    
    @GetMapping("/queue-size")
    public int getMessageQueue(){
        return messageService.getMessagesQueueSze();
    }

}
