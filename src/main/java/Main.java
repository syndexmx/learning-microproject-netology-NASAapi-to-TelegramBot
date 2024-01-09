import nasaservice.NasaAPIAuth;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegrambotcontroller.TelegramBotController;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, TelegramApiException {
        String  telegramBotsToken;
        String telegramBotsName;
        String nasaToken;
        if (args.length==0){
            telegramBotsName = JOptionPane.showInputDialog("Enter bot's Name");
            telegramBotsToken = JOptionPane.showInputDialog("Enter bot's Token");
           nasaToken = JOptionPane.showInputDialog("Enter NASA Token");
        } else {
            telegramBotsToken = args[0];
            telegramBotsName = args[1];
            nasaToken = args[2];
        }
        NasaAPIAuth.NASA_API_TOKEN = nasaToken;
        new TelegramBotController(telegramBotsName,telegramBotsToken);


    }
}
