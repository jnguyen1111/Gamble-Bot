package org.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;

/*
Methods:
clearGame -> resets class variables for next calculation or validation

getCritter -> retrive the emoji of the critter you captured type (String)

didUserWin -> returns a boolean value if user won money type (Boolean)

goFish -> picks a random critter from list if the key was 0 the user lost 10 credits if not they won the key value amount

validBalance -> checks to see if user has enough money to play the game

Purpose of class:
To calculate if user won from what emoji they caught
*/
public class Fishing {
    HashMap<Integer, List<String>> critterList = new HashMap<Integer,List<String>>();
    ArrayList<Integer> rewardPointsList = new ArrayList<Integer>(Arrays.asList(0,15,20,25,30,40,50,60,85,125,275));
    List<String> critterTable = new ArrayList<String>();
    public String critterChosen;
    public Integer compGuess;
    public Boolean didUserWinMoney = false;
    public int userReq = 0;
    public int userBalance = 0;

    //constructor
    public Fishing(){
       critterList.put(0,new ArrayList<String>(Arrays.asList(":boot:",":shirt:",":athletic_shoe:")));
       critterList.put(15,new ArrayList<String>(Arrays.asList(":blowfish:",":lizard:",":frog:",":snake:")));
       critterList.put(20,new ArrayList<String>(Arrays.asList(":squid:",":shrimp:")));
       critterList.put(25,new ArrayList<String>(Arrays.asList(":crab:")));
       critterList.put(30,new ArrayList<String>(Arrays.asList(":tropical_fish:")));
       critterList.put(40,new ArrayList<String>(Arrays.asList(":lobster:")));
       critterList.put(50,new ArrayList<String>(Arrays.asList(":dolphin:")));
       critterList.put(60,new ArrayList<String>(Arrays.asList(":shark:")));
       critterList.put(85,new ArrayList<String>(Arrays.asList(":whale:")));
       critterList.put(125,new ArrayList<String>(Arrays.asList(":coin:")));
       critterList.put(275,new ArrayList<String>(Arrays.asList(":crown:")));
    }

    //reset the game
    public void clearGame(){
        critterChosen = "";
        critterTable = null;
        didUserWinMoney = false;
        userReq = 0;
        userBalance = 0;
    }

    //obtain the specific emoji the user "fished"
    public String getCritter(){
        return critterChosen;
    }

    //returns a boolean value if user won
    public boolean didUserWin(){
       return didUserWinMoney;
    }

    //obtain a random critter from fishing
    public void goFish() {
        compGuess = rewardPointsList.get(new Random().nextInt(rewardPointsList.size()));
        if (compGuess != 0){
            userReq = compGuess;
            didUserWinMoney = true;
        }
        critterTable = critterList.get(compGuess);
        critterChosen = critterTable.get(new Random().nextInt(critterTable.size()));
    }

    //check if user has enough
    public boolean validBalance(DataBase server, MessageReceivedEvent event) {
        try {
            //check users requests if its more than needed then do not allow them to gamble else allow
            int request = 20;
            int balance = Integer.valueOf(server.getUserCredits(String.valueOf(event.getMember().getIdLong())));

            //check if user has enough funds
            if (request > balance) {
                event.getChannel().sendMessage("Error Insufficient Funds").queue();
                return false;
            }

            //once error handling above is passed then assign value to variables defined outside
            userReq = request;
            userBalance = balance;

        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("Error, there was a problem with finishing your request please try again.").queue();
            return false;
        }

        return true;
    }
}