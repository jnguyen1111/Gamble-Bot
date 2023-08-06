package org.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SnakeEye {

        public String thumbnailUrl;
        public String thumbnailUrl2;
        public int bonusMultiplier = 36;
        public int compGuess;
        public int compGuess2;
        public int userReq = 0;
        public int userBalance = 0;
        public int snakeEyesMinAmount = 5000;
        public int snakeEyesMaxAmount = 30000;

        //reset variables
        public void clearGame(){
            thumbnailUrl = "";
            thumbnailUrl2 = "";
            compGuess = 0;
            userReq = 0;
            userBalance = 0;
        }

        //roll the dice and determine if the user won
        public boolean didUserWin() {

            //calculate number between 1-6
            compGuess = new Random().nextInt(6) + 1;
            compGuess2 = new Random().nextInt(6) + 1;

            //assign the appropriate url for dice roll
            switch (compGuess){
                case 1:
                    thumbnailUrl = "https://cdn.discordapp.com/attachments/954548409396785162/982550126092513300/diceroll1.gif";
                    break;
                case 2:
                    thumbnailUrl = "https://cdn.discordapp.com/attachments/954548409396785162/982550124314112050/diceroll2.gif";
                    break;
                case 3:
                    thumbnailUrl = "https://cdn.discordapp.com/attachments/954548409396785162/982550124586745906/diceroll3.gif";
                    break;
                case 4:
                    thumbnailUrl = "https://cdn.discordapp.com/attachments/954548409396785162/982550124880338964/diceroll4.gif";
                    break;
                case 5:
                    thumbnailUrl = "https://cdn.discordapp.com/attachments/954548409396785162/982550125152960542/diceroll5.gif";
                    break;
                case 6:
                    thumbnailUrl = "https://cdn.discordapp.com/attachments/954548409396785162/982550125534646312/diceroll6.gif";
                    break;
                default:
                    break;
            }

            switch (compGuess2){
                case 1:
                    thumbnailUrl2 = "https://cdn.discordapp.com/attachments/954548409396785162/982550126092513300/diceroll1.gif";
                    break;
                case 2:
                    thumbnailUrl2 = "https://cdn.discordapp.com/attachments/954548409396785162/982550124314112050/diceroll2.gif";
                    break;
                case 3:
                    thumbnailUrl2 = "https://cdn.discordapp.com/attachments/954548409396785162/982550124586745906/diceroll3.gif";
                    break;
                case 4:
                    thumbnailUrl2 = "https://cdn.discordapp.com/attachments/954548409396785162/982550124880338964/diceroll4.gif";
                    break;
                case 5:
                    thumbnailUrl2 = "https://cdn.discordapp.com/attachments/954548409396785162/982550125152960542/diceroll5.gif";
                    break;
                case 6:
                    thumbnailUrl2 = "https://cdn.discordapp.com/attachments/954548409396785162/982550125534646312/diceroll6.gif";
                    break;
                default:
                    break;
            }

            if(compGuess == 1 && compGuess2 == 1){
                return true;
            }
            return false;
        }

        //validate user input before calculating the winner
        public boolean validInput(String userBetReq, DataBase server, MessageReceivedEvent event){
            String user =  "<@" +event.getMember().getId() + ">";

            try{
                //check users requests if its more than needed then do not allow them to gamble else allow
                int request = Integer.parseInt(userBetReq);
                int balance = server.getUserCredits(String.valueOf(event.getMember().getIdLong()));

                //handle if user requests less than 0 throw error
                if (request < snakeEyesMinAmount ||  request > snakeEyesMaxAmount){
                    event.getChannel().sendMessage("Error: please specify a valid amount you would like to bet range "
                            + snakeEyesMinAmount + "-" + snakeEyesMaxAmount + " use &help for more info " + user).queue();
                    return false;
                }
                //check if user has enough funds
                else if(request > balance){
                    event.getChannel().sendMessage("Error Insufficient Funds. " + user).queue();
                    return false;
                }

                //once error handling above is passed then assign value to variables defined outside
                userReq = request;
                userBalance = balance;

            }catch(NumberFormatException e){
                event.getChannel().sendMessage("Error: please specify a valid amount you would like to bet range "
                        + snakeEyesMinAmount + "-" + snakeEyesMaxAmount + " use &help for more info " + user).queue();
                return false;
            }

            return true;
        }

        //updates users credits
        public void updateCredits(DataBase server,MessageReceivedEvent event, int userReq, boolean addCredit){
            int creditVal = server.getUserCredits(String.valueOf(event.getMember().getIdLong()));

            //if addCredit is true add to credits else subtract
            if(addCredit){ creditVal += userReq; }
            else{ creditVal -= userReq; }

            server.updateUserCredits(String.valueOf(event.getMember().getIdLong()),creditVal);
        }

        public void rollSnakeDice(DataBase server,MessageReceivedEvent event,String betAmount){
            String user =  "<@" +event.getMember().getId() + ">";
            //check valid input
            if(validInput(betAmount,server,event)){
                //check if user won
                if(didUserWin()){
                    event.getChannel().sendMessage(thumbnailUrl).queue();
                    event.getChannel().sendMessage(thumbnailUrl2).queue();
                    int finalAmount = userReq * bonusMultiplier;
                    event.getChannel().sendMessage(":tada: :tada: :tada: :tada: :partying_face: Snake Eyes!!! Congrats, you won! :partying_face: :tada: :tada: :tada: :tada:\nAmount: "
                            + finalAmount +"\nhttps://c.tenor.com/nBX1KXnHfqQAAAAC/fishpog.gif").queueAfter(4, TimeUnit.SECONDS);
                    updateCredits(server,event, finalAmount, true);
                }
                else{
                    event.getChannel().sendMessage(thumbnailUrl).queue();
                    event.getChannel().sendMessage(thumbnailUrl2).queue();
                    event.getChannel().sendMessage("You Lost :angry:. " + user).queueAfter(4, TimeUnit.SECONDS);
                    updateCredits(server,event,userReq,false);
                }
            }
            //reset object
            clearGame();
        }
}
