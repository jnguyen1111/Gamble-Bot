package org.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AddBanner {
    public String errorEmote = "<a:exclamationmark:1000459825722957905>";

    //add new banner to database return true if successfully added into the database
    public boolean addNewBanner(DataBase server, MessageReceivedEvent event, String commandName, String commandUrl, String commandType, int commandCost){
        String user =  "<@" +event.getMember().getId() + ">";

        //checks if user is mod before using command
        if(server.isUserMod(String.valueOf(event.getMember().getIdLong()))){

            //formatting in order as shown to right -> banner name , url , type , cost
            server.insertBanner(commandName,commandUrl,commandType,commandCost);
            event.getChannel().sendMessage("Added a banner! :partying_face: " + user).queue();
            return  true;
        }
        else{
            event.getChannel().sendMessage(errorEmote +
                    "You are not a Moderator, you do not have access to this command! :angry: " + user).queue();
            return false;
        }
    }
}
