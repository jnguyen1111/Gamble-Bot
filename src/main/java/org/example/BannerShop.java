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

public class BannerShop extends ListenerAdapter {
    public EmbedBuilder shopEmbed = new EmbedBuilder();
    public Color shopEmbedColor = Color.MAGENTA;
    public String stackCashEmote = "<:cash:1000666403675840572>";
    public String pepeEmote = "<a:pepeMEX:1000825833335828620>";
    public String tradeMark = "© 2022 Sussy Inc. All Rights Reserved.";
    public String shopEmbedImage = "https://cdn.discordapp.com/attachments/954548409396785162/1009383688603177071/unknown.png";
    public String shopEmbedThumbnail = "https://c.tenor.com/YlBfgZ3_INcAAAAM/cat-kitty.gif";

    public LinkedList<MessageEmbed> bannerShopEmbedPages = new LinkedList<>();
    public static HashMap<String, java.util.List<String>> bannerList;

    ActionRow defActionRow = ActionRow.of(
            net.dv8tion.jda.api.interactions.components.buttons.Button.primary("page1", "1"),
            net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("page2", "2"),
            net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("page3", "3"),
            net.dv8tion.jda.api.interactions.components.buttons.Button.danger("exit", "Exit ✖")
    );
    ActionRow secondActionRow = ActionRow.of(
            net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("page1", "1"),
            net.dv8tion.jda.api.interactions.components.buttons.Button.primary("page2", "2"),
            net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("page3", "3"),
            net.dv8tion.jda.api.interactions.components.buttons.Button.danger("exit", "Exit ✖")
    );
    ActionRow thirdActionRow = ActionRow.of(
            net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("page1", "1"),
            net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("page2", "2"),
            net.dv8tion.jda.api.interactions.components.buttons.Button.primary("page3", "3"),
            Button.danger("exit", "Exit ✖")
    );

    //create shop embed and return it
    public void createShopEmbed(HashMap<String, java.util.List<String>> bannerList) {
        shopEmbed.setTitle(pepeEmote + "SUSSY'S BANNERSHOP™" + pepeEmote);
        shopEmbed.setThumbnail(shopEmbedThumbnail);
        shopEmbed.setImage(shopEmbedImage);
        shopEmbed.setTimestamp(Instant.now());
        shopEmbed.setFooter(tradeMark);
        shopEmbed.setColor(shopEmbedColor);

        if (bannerList == null){return;}
        int i = 0; //index variable
        //iterate through map and get price of each banner
        for (Map.Entry<String, java.util.List<String>> stringListEntry : bannerList.entrySet()) {
            java.util.List<String> elementVal = (java.util.List<String>) ((Map.Entry) stringListEntry).getValue();
            shopEmbed.addField((String) ((Map.Entry) stringListEntry).getKey(), stackCashEmote + " Price: $" + elementVal.get(1), true);
            if(i == 24) //each page gets 24 banners
                bannerShopEmbedPages.add(shopEmbed.build());
            i++;
        }

        //add in the empty pages
        while(bannerShopEmbedPages.size() < 3)
            bannerShopEmbedPages.add(shopEmbed.build());
        //refactor by finding a way to fix 'x' amount of items into each MessageEmbed and shove it into this linkedlist, in an efficient manner.
    }

    //display the shop embed to user
    public void printShopEmbed(MessageReceivedEvent event, HashMap<String, List<String>> bannerList) {
        createShopEmbed(bannerList);
        event.getChannel().sendMessageEmbeds(bannerShopEmbedPages.getFirst()).setActionRows(defActionRow).queue();
        shopEmbed.clear();
    }


    //Whenever a click occurs we need to print a specific page after the pageIndex (local version of pageNumber) has been incremented/decremented
    public void printBadgeShopEmbedPage(ButtonInteractionEvent event, int index){
        //if empty do not print
        if(bannerShopEmbedPages.isEmpty()) return;
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
        event.getMessage().editMessageEmbeds(bannerShopEmbedPages.get(index)).setActionRows(actionRow).queue();
    }

    //handles button interaction and increments/decrements the pageNumber accordingly
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event){
        //if iterator is null set iterator to the first page of the embed
        //if(iter == null){iter = badgeShopEmbedPages.listIterator(0); }

        //check which button id value
        switch(event.getComponentId()){
            case "page1":
                printBadgeShopEmbedPage(event, 0);
                event.deferEdit().queue();
                break;
            case "page2":
                printBadgeShopEmbedPage(event, 1);
                event.deferEdit().queue();
                break;
            case "page3":
                printBadgeShopEmbedPage(event, 2);
                event.deferEdit().queue();
                break;
            case "exit":
                event.getMessage().delete().queue();
                event.deferEdit().queue();
                break;
            default:
                break;
        }
    }


}
