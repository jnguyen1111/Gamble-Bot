package org.example;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AddBadge {
    public String errorEmote = "<a:exclamationmark:1000459825722957905>";
    //adds and formats badge to be stored in the database returns true if sucessful
    public boolean addNewBadge(MessageReceivedEvent event, DataBase server, String badgeName, String badgeID, String badgeType, int badgeCost, String[] badgeTagArray){
        String user = "<@" + event.getMember().getId() + ">";
        if(server.isUserMod(String.valueOf(event.getMember().getIdLong()))){
            StringBuilder badgeTag = new StringBuilder();
            //iterate through multiple arguements given and concatenate the sentence into one string
            for (String s : badgeTagArray) badgeTag.append(" ").append(s);

            //with all info given for badge insert into the database
            server.insertNewBadge(badgeName,badgeID,badgeType,badgeCost,badgeTag.toString());
            event.getChannel().sendMessage("Added a badge! :partying_face: " + user).queue();
            return true;
        }
        else{
            event.getChannel().sendMessage(errorEmote +
                    "You are not a Moderator, you do not have access to this command! :angry: " + user).queue();
            return false;
        }
    }


}
