package telegrambotcontroller;

import nasaservice.NasaAnswerDTO;
import nasaservice.NasaService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

import static telegrambotcontroller.TelegramBotMenu.prepareKeyboard;

public class TelegramBotController extends TelegramLongPollingBot {
    final String BOT_NAME;
    final String BOT_TOKEN;

    final String INSTRUCTION_TEXT = """
            *NASA-to-telegram* бот.\s

            _Возможные команды:_\s
            * /about*  :о боте \s
            * /help*  :помощь = список команд \s
            * /today*  :картинка за сегодня\s
            * /random*  :случайный  день\s
            * /date YYYY-MM-DD *  :картинка за указанную дату\s
            """;

    final String PRESENTATION_TEXT = """
            Учебный *NASA-to-telegram* бот.\s
            \s
            _Чтобы узнать список команд введите:_\s
            * /help*  \s
            """;

    NasaService nasaService = new NasaService();

    public TelegramBotController(String botName, String botToken) throws TelegramApiException {
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            String userFullCommand = update.getMessage().getText().toLowerCase();
            long chatId = update.getMessage().getChatId();

            System.out.println(LocalDateTime.now().toString() + "Request from #"+chatId+": "+userFullCommand);

            String[] userCommandSplit = userFullCommand.split(" ");

            if (userCommandSplit.length==0){
                sendInstructionsMessage(PRESENTATION_TEXT, chatId);
                return;
            }

            try {
                switch (userCommandSplit[0]) {
                    case ("/today") -> {
                        NasaAnswerDTO nasaDTO = nasaService.getNasaObject("");
                        sendMessage(nasaDTO.getUrl(), chatId);
                        sendMessage(nasaDTO.getTitle(), chatId);
                    }
                    case ("/date") -> {
                        NasaAnswerDTO nasaDTO = nasaService.getNasaObject("&date=" + userCommandSplit[1]);
                        sendMessage(nasaDTO.getUrl(), chatId);
                        sendMessage(nasaDTO.getTitle(), chatId);

                    }
                    case ("/random") -> {
                        String rndDate = generateRandomDate();
                        sendMessage(
                                "Пусть будет " + rndDate + " ... сейчас ...",
                                chatId);
                        NasaAnswerDTO nasaDTO = nasaService.getNasaObject("&date=" + rndDate);
                        sendMessage(nasaDTO.getUrl(), chatId);
                        sendMessage(nasaDTO.getTitle(), chatId);
                        //sendMessage(nasaDTO.getExplanation(), chatId);
                    }
                    case ("/help") -> sendInstructionsMessage(INSTRUCTION_TEXT, chatId);
                    case ("/about") -> sendInstructionsMessage(PRESENTATION_TEXT, chatId);
                    case ("/start") -> sendInstructionsMessage(PRESENTATION_TEXT, chatId);
                    default -> sendInstructionsMessage(PRESENTATION_TEXT, chatId);
                }
            } catch (IOException e) {
                System.out.println("Error while acquiring a picture");
            }


        }
    }

    private String generateRandomDate() {
        Random random = new Random();
        return ""+(2000+random.nextInt(23))+"-"
                +(random.nextInt(12)+1)+"-"+
                (random.nextInt(30)+1);
    }

    void sendMessage (String m, long chatId){
        // How to from  https://github.com/rubenlagus/TelegramBots/wiki/Getting-Started
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        message.setChatId(chatId);
        message.setText( m );
        message.setReplyMarkup(prepareKeyboard());
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void sendInstructionsMessage (String m, long chatId){
        // How to from  https://github.com/rubenlagus/TelegramBots/wiki/Getting-Started
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        message.setChatId(chatId);
        message.setText( m );
        message.setParseMode("markdown");
        message.setReplyMarkup(prepareKeyboard());
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

}
