package bot.union.sw.controllers;

import bot.union.sw.services.BotUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/message")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final BotUserService botUserService;

    @PostMapping
    public void getMessage(@RequestParam String textMessage,
                           @RequestParam String serviceName,
                           @RequestParam String userName,
                            @RequestParam("attachments") List<MultipartFile> files
                           ){

    }
    
    @GetMapping("/queue-size")
    public Integer getMessage(){
        return 0;
    }

}
