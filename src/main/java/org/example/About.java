package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;

public class About extends ListenerAdapter{
    public EmbedBuilder aboutEmbed = new EmbedBuilder();
    public Color aboutEmbedColor = Color.RED;
    public SignUp signupObject = new SignUp();
    public DataBase dataBaseObject;
    public String coinEmote = "<a:SussyCoin:1004568859648466974>";
    public String botLogoThumbnail = "https://i.imgur.com/gX3XrZw.png";
    public String aboutEmbedImage = "https://media3.giphy.com/media/v1.Y2lkPTc5MGI3NjExY2ZxaWVxYjAydWdxaGlzMDNwdnkzdHhpdGxzb241MHZrMXJuaG1leCZlcD12MV9naWZzX3NlYXJjaCZjdD1n/ND6xkVPaj8tHO/giphy.gif";
    public String tradeMark = "© 2022 CSLotto Inc. All Rights Reserved.";
    public String conditionsOfUse = "Terms of Service\nAs a condition of use, you waiver all rights to sue CSLotto Inc for any and all of the following: addiction, liabilities, loss in sanity, financial ruin, missed ZyBooks assignments, lowered GPA, emotional damage, ruined relationships, and sleep deprivation.";
    public String description = "Brought to you by <@186547706636795904> & <@107022278838996992>";

    //overriden constructor for the DiscordBot.java file to use
    public About(DataBase db){
        dataBaseObject = db;
    }

    //button interaction click on button calls this function
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event){
        if(event.getComponentId().equals("exit")){
            event.getMessage().delete().queue();
            event.deferEdit().queue(); //gets rid of interaction failed waits for edit
        }
        else if(event.getComponentId().equals("signup")){
            long userID = event.getMember().getIdLong();
            signupObject.signupUser(event.getMessage().getChannel(), dataBaseObject.findUser(String.valueOf(userID)), dataBaseObject, userID, event);
            event.deferEdit().queue();
        }
    }
    public void createAboutEmbed(){
        aboutEmbed.setColor(aboutEmbedColor);
        aboutEmbed.setTitle(coinEmote + " All about CS Lotto! Discord Gamble Bot");
        aboutEmbed.setThumbnail(botLogoThumbnail);
        aboutEmbed.setImage(aboutEmbedImage);
        aboutEmbed.setDescription("*"+description+"*"); //uses markdown so this is why the asterisks aren't in the description msg itself
        aboutEmbed.addField("Welcome to CS Lotto...", "A JDA discord bot written in Java to bring you virtual gambling addictions straight to your discord server.", false);
        aboutEmbed.addField("Play with high stakes...", "\tTest your luck in a range of casino themed games, such as dice rolls, slots, wheel spins, and more! But be wary of the crippling debts you might encounter! " +
                "Those who are lucky will be rewarded fruitfully with piles of CsLotto while the unfortunate few will in be in big debt", false);
        aboutEmbed.addField("Reap the riches...", "\tEnjoy our rewards system offering custom commands, perks, and cosmetic items offered in our gift shops. " +
                "The life of Gamble Addiction comes with a personalized credit card customizable with animated banners and badges.", false);
        aboutEmbed.addField("A life of Gamble is waiting for you!", "Head down to your local CsLotto Casino to sign up today!\n", false);
        aboutEmbed.setTimestamp(Instant.now());
        aboutEmbed.setFooter(conditionsOfUse + "\n\n" + tradeMark);
    }

    //print embed with signup and exit interactable buttons
    public void printAboutEmbed(MessageReceivedEvent event){
        createAboutEmbed();
        ActionRow actionRow = ActionRow.of(Button.primary("signup", "Sign Up!"), Button.danger("exit", "Exit ✖"));
        event.getChannel().sendMessageEmbeds(aboutEmbed.build()).setActionRows(actionRow).queue();
        aboutEmbed.clear();
    }
}
