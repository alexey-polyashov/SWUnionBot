package bot.union.sw.services;

import bot.union.sw.entities.AllowService;
import bot.union.sw.exceptions.ResourceNotFound;
import bot.union.sw.repository.AllowServicesRepository;
import com.pengrad.telegrambot.model.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllowServicesService {

    private final AllowServicesRepository servicesRepository;

    public AllowService findByName(String service){
        return servicesRepository.findByName(service).orElseThrow(
                ()->new ResourceNotFound("Сервис '" + service+ "' не найден"));
    }

    public List<AllowService> findAll() {
        return servicesRepository.findByMarked(false);
    }

    public List<AllowService> findAlowServicesForUser(Chat chat) {
        return servicesRepository.findAllowServicesForUser(chat.id());
    }
}
