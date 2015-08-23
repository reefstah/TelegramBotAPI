# TelegramBotAPI    [![Build Status](https://travis-ci.org/Reefstah/TelegramBotAPI.svg?branch=master)](https://travis-ci.org/Reefstah/TelegramBotAPI)
The Java 8 implementation of Telegram's bot API.

## Example bot
```java
public class AwesomoBot {

    private static final String TOKEN = "YourTokenHere";
    private static final TelegramBot bot = TelegramBot.getInstance(TOKEN);

    public static void main(String[] args) throws IOException {
        new AwesomoBot().replyWithAwesomeness();
    }

    public AwesomoBot replyWithAwesomeness() throws IOException {
        List<Update> updates = bot.getUpdates();
        updates.forEach(update -> {
            try {
                bot.sendMessage(update.getMessage().getChat().getId(), "You are awesum.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return this;
    }
}
```
## Or try the fluent API
```java
SendMessage
        .to(YourChatIdHere)
        .withText("Sexy fluent TelegramBotAPI.")
        .fromBot(YourBotTokenHere);
```