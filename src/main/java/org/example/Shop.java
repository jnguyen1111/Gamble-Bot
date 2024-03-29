package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop {
    public EmbedBuilder shopEmbed = new EmbedBuilder();
    public Color shopEmbedColor = Color.MAGENTA;
    public String stackCashEmote = "<:cash:1000666403675840572>";
    public String moneyCashEmote = "<a:moneycash:1000225442260861018>";
    public String tradeMark = "© 2022 CsLotto Inc. All Rights Reserved.";
    public String shopEmbedImage = "https://arc-anglerfish-arc2-prod-tronc.s3.amazonaws.com/public/YPZFICVQMRGXPMWDR2HVEEMTNA.jpg";
    public String shopEmbedThumbnail = "https://c.tenor.com/rgNhzkA41qIAAAAM/catjam-cat-jamming.gif";
    public String shopEmbedDescription = "Shop at CsLotto's Megacenter today for Every Day Low Prices! <a:coinbag:1000231940793843822>\n";


    //create shop embed and return it
    public void createShopEmbed(HashMap<String, List<String>> commandList) {
        shopEmbed.setTitle(moneyCashEmote + "CsLotto'S MEGACENTER™" + moneyCashEmote);
        shopEmbed.setDescription(shopEmbedDescription);
        shopEmbed.setThumbnail(shopEmbedThumbnail);
        shopEmbed.setImage(shopEmbedImage);
        for (Map.Entry<String, List<String>> stringListEntry : commandList.entrySet()) {
            List<String> elementVal = (List<String>) ((Map.Entry) stringListEntry).getValue();
            shopEmbed.addField((String) ((Map.Entry) stringListEntry).getKey(), stackCashEmote + " Price: $" + elementVal.get(1), true);
        }
        shopEmbed.setTimestamp(Instant.now());
        shopEmbed.setFooter(tradeMark);
        shopEmbed.setColor(shopEmbedColor);
    }

    //display the shop embed to user
    public void printShopEmbed(MessageReceivedEvent event, HashMap<String, List<String>> commandList) {
        createShopEmbed(commandList);
        event.getChannel().sendMessageEmbeds(shopEmbed.build()).queue();
        shopEmbed.clear();
    }
}
