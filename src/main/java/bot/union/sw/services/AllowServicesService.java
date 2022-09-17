package bot.union.sw.services;

import bot.union.sw.entities.AllowService;
import bot.union.sw.exceptions.ResourceNotFound;
import bot.union.sw.repository.AllowServicesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AllowServicesService {

    private final AllowServicesRepository servicesRepository;

    public AllowService findByName(String service){
        return servicesRepository.findByName(service).orElseThrow(
                ()->new ResourceNotFound("Сервис '" + service+ "' не найден"));
    }

}
