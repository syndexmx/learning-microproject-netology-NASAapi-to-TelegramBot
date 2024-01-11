import nasaservice.NasaAPIAuth;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegrambotcontroller.TelegramBotController;

import java.io.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, TelegramApiException {
        String telegramBotsToken;
        String telegramBotsName;
        String nasaToken;

        BufferedReader bReader = new BufferedReader(new FileReader ("telegramandnasa.ini"));
        try {
            telegramBotsName = bReader.readLine();
            telegramBotsToken = bReader.readLine();
            nasaToken = bReader.readLine();
        } finally {
            bReader.close();
        }

        NasaAPIAuth.NASA_API_TOKEN = nasaToken;
        new TelegramBotController(telegramBotsName,telegramBotsToken);

    }
}
