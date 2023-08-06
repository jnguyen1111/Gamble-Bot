package org.example;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class BanUrl {
    public String errorEmote = "<a:exclamationmark:1000459825722957905>";
    //insert url to be banned
    public void banLink(String urlRequested, DataBase server, MessageReceivedEvent event){
        String user =  "<@" +event.getMember().getId() + ">";

        //checks if user is mod before using command
        if(server.isUserMod(String.valueOf(event.getMember().getIdLong()))){
            if(!urlRequested.contains("http")) {
                event.getChannel().sendMessage("Url requested is invalid please check again. Use &help for more details " + user).queue();
                return;
            }

            server.insertBanUrl(urlRequested);
            event.getChannel().sendMessage("Url requested has been banned!" + user ).queue();
        }
        else{
            event.getChannel().sendMessage(errorEmote +
                "You are not a Moderator, you do not have access to this command! :angry: " + user).queue();

        }
    }
}
