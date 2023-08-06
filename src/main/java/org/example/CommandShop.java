package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CommandShop extends ListenerAdapter {
    public EmbedBuilder shopEmbed = new EmbedBuilder();
    public Color shopEmbedColor = Color.MAGENTA;
    public String stackCashEmote = "<:cash:1000666403675840572>";
    public String moneyCashEmote = "<a:moneycash:1000225442260861018>";
    public String tradeMark = "© 2022 CsLotto Inc. All Rights Reserved.";
    public String shopEmbedImage = "https://arc-anglerfish-arc2-prod-tronc.s3.amazonaws.com/public/YPZFICVQMRGXPMWDR2HVEEMTNA.jpg";
    public String shopEmbedThumbnail = "https://c.tenor.com/rgNhzkA41qIAAAAM/catjam-cat-jamming.gif";
    public String shopEmbedDescription = "Shop at CsLotto's Megacenter today for Every Day Low Prices! <a:coinbag:1000231940793843822>\n";

    public LinkedList<MessageEmbed> commandShopEmbedPages = new LinkedList<>();
    public static HashMap<String, List<String>> commandList;
    ActionRow defActionRow = ActionRow.of(
            Button.primary("commandPage1", "1"),
            Button.secondary("commandPage2", "2"),
            Button.secondary("commandPage3", "3"),
            Button.danger("exitCommandShop", "Exit ✖")
    );
    ActionRow secondActionRow = ActionRow.of(
            Button.secondary("commandPage1", "1"),
            Button.primary("commandPage2", "2"),
            Button.secondary("commandPage3", "3"),
            Button.danger("exitCommandShop", "Exit ✖")
    );
    ActionRow thirdActionRow = ActionRow.of(
            Button.secondary("commandPage1", "1"),
            Button.secondary("commandPage2", "2"),
            Button.primary("commandPage3", "3"),
            Button.danger("exitCommandShop", "Exit ✖")
    );


    //create shop embed and return it
    public void createShopEmbed(HashMap<String, List<String>> commandList) {
        shopEmbed.setTitle(moneyCashEmote + "CsLotto'S ONE STOP COMMAND SHOP™" + moneyCashEmote);
        shopEmbed.setDescription(shopEmbedDescription);
        shopEmbed.setThumbnail(shopEmbedThumbnail);
        shopEmbed.setImage(shopEmbedImage);
        shopEmbed.setTimestamp(Instant.now());
        shopEmbed.setFooter(tradeMark);
        shopEmbed.setColor(shopEmbedColor);

        int i = 0;
        for (Map.Entry<String, List<String>> stringListEntry : commandList.entrySet()) {
            List<String> elementVal = (List<String>) ((Map.Entry) stringListEntry).getValue();
            shopEmbed.addField((String) ((Map.Entry) stringListEntry).getKey(), stackCashEmote + " Price: $" + elementVal.get(1), true);
            if(i == 24)
                commandShopEmbedPages.add(shopEmbed.build());
            i++;
        }

        //add in the empty pages
        while(commandShopEmbedPages.size() < 3) commandShopEmbedPages.add(shopEmbed.build());
    }

    //display the shop embed to user
    public void printShopEmbed(MessageReceivedEvent event, HashMap<String, List<String>> commandList) {
        createShopEmbed(commandList);
        event.getChannel().sendMessageEmbeds(commandShopEmbedPages.getFirst()).setActionRows(defActionRow).queue();
        shopEmbed.clear();
    }
    public void printCommandShopEmbedPage(ButtonInteractionEvent event, int index) {
        //if empty do not print
        if(commandShopEmbedPages.isEmpty()) return;
        //defines how the button looks at the bottom what buttons are highlighted and what are grayed out
        ActionRow actionRow = defActionRow;
        switch(index){
            case 1:
                actionRow = secondActionRow;
                break;
            case 2:
                actionRow = thirdActionRow;
                break;
        }
        event.getMessage().editMessageEmbeds(commandShopEmbedPages.get(index)).setActionRows(actionRow).queue();
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event){
        //if iterator is null set iterator to the first page of the embed

        //check which button id value
        switch(event.getComponentId()){
            case "commandPage1":
                if (commandShopEmbedPages.size() >= 1) {
                    printCommandShopEmbedPage(event, 0);
                    event.deferEdit().queue();
                }
                break;
            case "commandPage2":
                if (commandShopEmbedPages.size() >= 2) {
                    printCommandShopEmbedPage(event, 1);
                    event.deferEdit().queue();
                }
                break;
            case "commandPage3":
                if (commandShopEmbedPages.size() >= 3) {
                    printCommandShopEmbedPage(event, 2);
                    event.deferEdit().queue();
                }
                break;
            case "exitCommandShop":
                event.getMessage().delete().queue();
                event.deferEdit().queue();
                break;
            default:
                break;
        }
    }



}
