package bot.union.sw;

import com.github.kshashov.telegram.api.MessageType;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.TelegramRequest;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.BotRequest;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import weatherbot.geocode.GeoService;
import weatherbot.geocode.dto.GeoResponseDto;
import weatherbot.weather.WeatherService;
import weatherbot.weather.dto.WeatherDto;

@BotController
public class BotMvcController  implements TelegramMvcController {

    private final String ON_LOCATION = "По моему месту";
    private final String GET_START = "/start";

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private GeoService geoService;

    @Autowired
    private weatherbot.BotConfig botConfig;

    @Override
    public String getToken() {
        return botConfig.getToken();
    }

    @BotRequest(type = {MessageType.ANY})
    public void optionMethod(String mes, TelegramBot bot, Chat chat, Message fullMes, TelegramRequest request){
        GeoResponseDto geoResponseDto = new GeoResponseDto();
        geoResponseDto.setQc_geo("-1");
        if(mes==null){
            //try to get location
            if(fullMes.location()!=null){
                bot.execute(new SendMessage(chat.id(), "Широта: " + fullMes.location().latitude()));
                bot.execute(new SendMessage(chat.id(), "Долгота: " + fullMes.location().longitude()));
                geoResponseDto.setQc_geo("0");
                geoResponseDto.setGeo_lat(fullMes.location().latitude().toString());
                geoResponseDto.setGeo_lon(fullMes.location().longitude().toString());
            }
        }else {
            switch (mes) {
                case GET_START:
                    bot.execute(new SendMessage(chat.id(), "Добро пожаловать!"));
                    bot.execute(new SendMessage(chat.id(), "Я помогу вам узнать погоду в любом месте планеты"));
                    break;
                default:
                    //try to define location
                    ResponseEntity<GeoResponseDto> coord = geoService.getCoordinates(mes);
                    if(coord.getStatusCode() == HttpStatus.OK){
                        geoResponseDto = coord.getBody();
                    }else{
                        bot.execute(new SendMessage(chat.id(),"Не удалось определить указанный адрес, код ответа сервиса dadata " + coord.getStatusCode().toString()));
                    }
            }
        }
        if(geoResponseDto.getQc_geo().equals("5")){
            bot.execute(new SendMessage(chat.id(),"Указанный адрес не найден"));
            if(!geoResponseDto.getErrorMessage().isEmpty()) {
                bot.execute(new SendMessage(chat.id(), "Возможная причина:"
                        +"\n" + geoResponseDto.getErrorMessage()));
            }
            bot.execute(new SendMessage(chat.id(), "Укажите адрес точнее."));
        }else if(!geoResponseDto.getQc_geo().equals("-1")){

            String respString = "<b>Прогноз погоды в местоположении:</b>\n";
            ResponseEntity<WeatherDto> response = weatherService.getWeather(geoResponseDto.getGeo_lat(), geoResponseDto.getGeo_lon());
            respString += "\n" + response.getBody().toString();
            bot.execute(new SendMessage(chat.id(), respString).parseMode(ParseMode.HTML));
            bot.execute(new SendMessage(chat.id(), ""));

        }

        KeyboardButton kbLocation = new KeyboardButton(ON_LOCATION);
        kbLocation.requestLocation(true);
        bot.execute(new SendMessage(chat.id(), "Введите адрес, или выберите вариант 'По моему месту'")
                .replyMarkup(new ReplyKeyboardMarkup(kbLocation).resizeKeyboard(true))
        );
    }

}
