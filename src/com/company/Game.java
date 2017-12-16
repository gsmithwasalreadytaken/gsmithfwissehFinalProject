package com.company;
import com.company.*;
import java.lang.reflect.Array;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Game {

    //creating start game variables just for game piece checking and housekeeping
    private Rooms start_room;
    private Player player_chracter;
    private boolean is_weapon_bad;
    private boolean is_player_bad;
    private boolean is_room_bad;
    private boolean end_game;
    private Player bad_player;
    private Weapons bad_weapons;
    private Rooms bad_rooms;
    Scanner sc = new Scanner(System.in);
    ArrayList<Rooms> room_list = new ArrayList<Rooms>();
    ArrayList<Player> player_list = new ArrayList<Player>();
    ArrayList< Weapons> weapon_list = new ArrayList< Weapons>();
    private int turns;

    //constructor
    public Game()
    {
        turns= 1;
        is_weapon_bad=false;
        is_room_bad=false;
        is_player_bad=false;
        bad_player=null;
        bad_rooms=null;
        bad_weapons=null;
        end_game=false;
    }

    //adding weapons to the game checking to make sure there is only one weapon related to murder also, ensuring that the weapon name is not null
       public boolean add_weapons(Weapons weapon) {
        if (weapon.getName() == null) {
            return false;
        } else {
            if (weapon.isBad()==true)
            {
                if (is_weapon_bad==true)
                {
                    System.out.print("cannot add " +weapon.getName()+ " since there is already an accused weapon");
                    return false;
                }
                else{
                    is_weapon_bad=true;
                    bad_weapons=weapon;
                }
            }
            weapon_list.add(weapon);
            return true;

        }

    }

    //adding rooms to the game checking to make sure there is only one room related to murder also, ensuring that the room names are not null
    public boolean add_rooms(Rooms room) {
        if (room.getName() == null) {
            return false;
        } else {
            if (room.isBad() == true) {
                if (is_room_bad == true) {
                    System.out.print("cannot add" +room.getName() +"since there is already an accused weapon");
                    return false;
                } else {
                    is_room_bad = true;
                    bad_rooms=room;
                }
            }
            room_list.add(room);
            return true;
        }
    }

    //adding players to the game checking to make sure there is only one player related to murder also, ensuring that the player names are not null
    public boolean add_players(Player player) {
        if (player.getName() == null) {
            return false;
        } else {
            if (player.isBad()==true)
            {
                if (is_player_bad==true)
                {
                    System.out.print("cannot add" +player.getName() +"since there is already an accused weapon");
                    return false;
                }
                else{
                    is_player_bad=true;
                    bad_player=player;
                }
            }
            player_list.add(player);
            return true;

        }
    }

   //These bottom three arraylist methods are for the main method for debugging
   public ArrayList<String> getPlayer_list() {
        ArrayList<String> temp_list= new ArrayList<String>();
        for(Player player:player_list)
        {
            temp_list.add(player.getName());
        }
        return temp_list;

    }

    public ArrayList<String> getWeapon_list() {
        ArrayList<String> temp_list = new ArrayList<String>();
        for (Weapons weapon : weapon_list) {
            temp_list.add(weapon.getName());
        }
        return temp_list;

    }
    public ArrayList<String> getRoom_list() {
            ArrayList<String> temp_list= new ArrayList<String>();
            for(Rooms room: room_list)
            {
                temp_list.add(room.getName());
            }
            return temp_list;
    }

  //starting the game checking to make sure there are enough game pieces of the three categories
    public String startGame()
    {
        if (weapon_list.size()<1 || room_list.size() <1|| player_list.size()<2)
        {
            System.out.println("There are not enough items in your game either, players, rooms, or, weapons reconfigure your main and try again");
        }

        else

        {
            //prints instructions and then creates a player first asking for user's name
           get_instructions();
            System.out.println("Lets start, what is your name please note we use scanner.next meaning we will only read anything before a space");
            String user_name=sc.next();
            player_chracter= new Player(user_name,false,false);
            player_list.add(player_chracter);

           // player fist starts in the start room
            start_room=new Rooms("central_hub", false);
            room_list.add(start_room);

            for (Rooms room : room_list) {

                //iterate over values and ensuring the start room has a one way path to another room and this room has a path to at least one other room
                if(room.get_connected_rooms().size()<1)
                {
                    int random_num=(int) Math.floor(Math.random()*room_list.size());

                    while(room.equals(room_list.get(random_num)))
                    {
                        random_num = (int)Math.floor(Math.random()*room_list.size());
                    }
                    room.adddoublePath(room_list.get(random_num));
                }
                start_room.adddoublePath(room);
            }

            //moving non user players to
            for(Player player: player_list)
            {
                player.move_to_room(start_room);
                if (!player_chracter.equals(player)) {
                    if(player.isnpc==false)
                    {
                        player.isnpc=true;
                    }
                    int rand= (int)Math.floor(Math.random()*room_list.size());
                    player.move_to_room(room_list.get(rand));

                }
            }

            //checks to make sure there is one bad item per category
            if(!is_room_bad)
            {
                int random_num=(int) Math.floor(Math.random()*room_list.size());
                room_list.get(random_num).setbad(true);
                is_room_bad=true;
                bad_rooms=room_list.get(random_num);
            }
            if(!is_weapon_bad)
            {
                int random_num=(int) Math.floor(Math.random()*weapon_list.size());
                weapon_list.get(random_num).setbad(true);
                is_weapon_bad=true;
                bad_weapons=weapon_list.get(random_num);
            }
            if(!is_player_bad)
            {
                int random_num=(int) Math.floor(Math.random()*player_list.size());
                player_list.get(random_num).setbad(true);
                is_player_bad=true;
                bad_player=player_list.get(random_num);
            }

            //Start node allowing user to choose any room they want to go to- actual gameplay starting
            boolean first_turn=true;
            while (!end_game)
            {
                // First turn only allows player to move without any other options in this, the player is choosing where to go
                String answer;
                    while (first_turn==true) {
                        formatting();
                        System.out.println("Hey "+ player_chracter.getName());
                        System.out.println("For your first move, you start at the Central hub. The list of rooms are: ");
                        System.out.println(player_chracter.get_current_room().get_connected_rooms_string() +"This is the Central Hub where every room is within proximity:)");
                            answer = sc.next().toLowerCase();
                            if (player_chracter.get_current_room().get_connected_rooms().get(answer) != null) {
                                player_chracter.move_to_room(player_chracter.get_current_room().get_connected_rooms().get(answer));
                                break;
                            } else {
                                System.out.println("Try again, that is not a room you cannot enter!");
                            }
                        }

                        //first turn has ended let player know and initialize the real game
                      if (first_turn)
                        {   formatting();
                            first_turn=false;
                            System.out.println("The first turn has ended! Too bad.");
                            turns++;
                        }
                        else {
                            System.out.println("Turn ended");
                            perform_action(player_chracter);
                            turns++;
                        }
                    }
                }

        return null;
    }

    // print list of actions a player can do and introductions
    public void print_options(Player player)
    {   System.out.println("Hello, " + player.getName());
        System.out.println("You are in room "+ player.get_current_room().getName());
        System.out.println("The connected rooms are "+ player.get_current_room().get_connected_rooms_string());
        System.out.println("The weapons in your room are" +player.get_current_room().get_contained_weapons_string());
        System.out.println("The people in your room are" +player.get_current_room().get_contained_players_string());
        System.out.println("You get to do one action and move one space before your turn ends. What would you like to do?");
        System.out.println("Type 1 to talk to person see if they killed");
        System.out.println("Type 2 to inspect one of the weapon");
        System.out.println("Type 3 to inspect room");
        System.out.println("Type 4 to fight one of the people in the room. (This means you challenge one of them to a battle. If you win, you will get all the information in the room");
        System.out.println("If you lose, you will lose all the knowledge you had of any murdering gamepiece from any category \n and the the murder related item will change in its respective category");
        System.out.println("Type 5 to not do anything. However, you still have the option to move or stay put");
        System.out.println("Type 6 to check out your information about the murder so far. You will get to perform more actions after this (DOES NOT USE A TURN)");
        System.out.println("Type 7 to get the instructions (DOES NOT USE A TURN)");
        System.out.println("Type 8 to cheat and find out the murder weapon, killer, and the room where murdered occurred in... but only if you have no honor, you dirty cheater");
        System.out.println("Type 9 to accuse a person, weapon, and room. If you guess wrong, it's game over");

    }

    //action performed by user
    public void perform_action(Player player) {
        int number = 0;

        //check if the number is <1 or >5 because if 1-5 then those are turned based actions and will need to add 1 to turn. if not its non turned based like get instructions, etc.
        while ((number < 1 || number > 5) && !end_game) {
            formatting();
            System.out.println("You are on turn " + turns);
            get_info(player);
            print_options(player);

            //Make sure that the input is an integer and valid
            if (!sc.hasNextInt()) {
                sc.next();

            } else {
                try {
                    number = sc.nextInt();
                } catch (Exception e) {
                    System.out.println("that is not a number");
                }
            }

            //Check the number and based on the number entered, the user does something or gains some kind of information
            switch (number) {
                case 1:
                    // inspect a player remember always check for edge cases like if you already investigated the players
                    if (player.get_current_room().get_contained_players_string().size() < 2 && player.getSeen_players().contains(player.getName())) {
                        System.out.println("There is no one new to inspect in here you already inspected yourself redoing turn");
                        number = 100;
                        break;
                    }

                    // checking for people in the room
                    System.out.println("The people in your room are: " + player.get_current_room().get_contained_players_string());
                    System.out.println("Who do you want to investigate?");

                    //answer is the person you want to investigate unless you want to redo your choice
                    String answer = check_scanner(sc, player, player.get_current_room().get_contained_players(), player.getSeen_players());
                    if (answer.equals("back")) {
                        number = 100;
                        System.out.println("It looks like you want to select another action. Try doing something else");
                        break;
                    } else {

                        //investigate player and see if they are murderers
                        Player investigate = player.get_current_room().get_contained_players().get(answer);
                        player.setSeen_players(investigate);
                        System.out.println("The players you have investigate are " + player.getSeen_players());
                        if (investigate.isBad()) {
                            player.setFoundbadplayer(investigate);

                            //found murderer print
                            System.out.println(player.getfoundbadPlayer().getName() + " has been found to be a murderer");
                        } else {

                            //investigated person was not a murderer let user know
                            System.out.println("The player you investigated is not a murderer");
                        }
                        formatting();
                    }
                    break;

                    //Case 2 for the weapon list is exactly like players except the player is not a weapon so they cannot accuse themselves as a weapon which is why we check if weapon size is <1 here when
                    // above we checked 2
                case 2:
                    if (player.get_current_room().get_contained_weapons_string().size() < 1 || player.getSeen_weapons().containsAll(player.get_current_room().get_contained_weapons_string())) {
                        System.out.println("There are no new weapons to inspect in here. Redo your turn");
                        number = 100;
                        break;
                    }

                    //Same stuff as investigating player case 1
                    System.out.println("The weapons in your room are: " + player.get_current_room().get_contained_weapons_string());
                    System.out.println("Which one do you want to investigate?");
                    String answer2 = check_scanner(sc, player, player.get_current_room().get_contained_weapons(), player.getSeen_weapons());
                    if (answer2.equals("back")) {
                        number = 100;
                        System.out.println("looks like you want to try again, redoing turn");
                        break;
                    } else {
                        Weapons investigate2 = player.get_current_room().get_contained_weapons().get(answer2);
                        player.setSeen_weapons(investigate2);
                        System.out.println("\n The weapons you have investigated are: " + player.getSeen_weapons());
                        if (investigate2.isBad()) {
                            player.setFoundbadweapon(investigate2);
                            System.out.println("the weapon that was used for the murder was the " + player.getFoundbadweapon().getName());
                        } else {
                            System.out.println("The weapon you investigated was not used for murder");
                        }
                    }
                    formatting();
                    break;
                case 3:

                    //investigating rooms this first part same as the for weapons and players just ensure that the player has not already investigated the room
                    System.out.println(player.getSeen_rooms());
                    if (player.getSeen_rooms().contains(player.get_current_room().getName())) {
                        System.out.println("You've already inspected this room. Redoing turn");
                        number = 100;
                        break;
                    }
                    System.out.println("You are currently in " + player.get_current_room().getName());
                    Rooms investigate3 = player.get_current_room();
                    player.setSeen_rooms(investigate3);
                    System.out.println("Updating list of investigated rooms. You have been to: " + player.getSeen_rooms());
                    if (investigate3.isBad()) {

                        //tell user if the room they investigated was used for murder
                        player.setFoundbadRooms(investigate3);
                        System.out.println("the room that the murder was committed in was " + player.getFoundbadroom().getName());
                    } else {
                        //if it wasn't
                        System.out.println("the room was not used for murder");

                    }
                    formatting();
                    break;
                case 4:

                    /*if (player.get_current_room().get_contained_players_string().size() < 2) {
                        System.out.println("There is no new people to battle here try again");
                        number = 100;
                        break;
                    }
                    if (player.getSeen_rooms().size()<1 && player.getSeen_players().size()<1 && player.getSeen_rooms().size()<1)
                    {
                        System.out.println("Nice try you cannot bet anything please");
                        number = 100;
                        break;
                    }*/

                    //Check to see if you have found any murder related items if not you cannot battle and bet our chances
                    if (player.getFoundbadweapon() == null && player.getFoundbadroom() == null && player.getfoundbadPlayer() == null) {
                        System.out.println("You need to have found at least 1 gamepiece involved in murder for to bet for a battle. Sorry, try again later. Your turn was not used");
                        number = 100;
                        break;
                    }
                    if (player.get_current_room().get_contained_players().size() == 0) {
                        System.out.println("There is no one in your room to battle. Try doing something else");
                        number = 100;
                        break;
                    }
                    System.out.println("Who do you want to battle in a game of Roll Some Dice?");
                    Set<String> temp_string = new HashSet<String>(player.get_current_room().get_contained_players_string());
                    temp_string.remove(player.getName());
                    System.out.println("The players available are: " + temp_string);
                    HashSet<String> empty = new HashSet<String>();
                    HashMap<String, Player> temp = new HashMap<String, Player>(player.get_current_room().get_contained_players());
                    temp.remove(player.getName());
                    String answer3 = check_scanner(sc, player, temp, empty);
                    if (answer3.equals("back")) {
                        number = 100;
                        System.out.println("looks like you want to try again");
                        break;
                    } else {

                        System.out.println("If you lose, all your information in the category which you have the most information");
                        int dice1 = (int) Math.floor(Math.random() * 12);
                        int dice2 = (int) Math.floor(Math.random() * 12);
                        System.out.println("\n your dice was " + dice1);
                        System.out.println("Your Opponents dice was " + dice2);
                        if (dice1 > dice2) {
                            System.out.println("Your roll was greater, you win!");
                            System.out.println("The things you have learned are: ");
                            HashMap<String, Weapons> tempweapons = player.get_current_room().get_contained_weapons();
                            System.out.println("Updating your seen game pieces");
                            for (Player players : player.get_current_room().get_contained_players().values()) {
                                if (!player.getSeen_players().contains(players.getName())) {
                                    player.setSeen_players(players);
                                    System.out.println(players.getName() + " has been added to your knowledge");

                                }
                                if (players.isBad()) {
                                    player.setFoundbadplayer(players);
                                    System.out.println("Found the murderer" + player.getfoundbadPlayer().getName());
                                }

                            }
                            if (!player.getSeen_rooms().contains(player.get_current_room().getName())) {
                                player.setSeen_rooms(player.get_current_room());
                                System.out.println(player.get_current_room().getName() + " has been added to your knowledge");

                                if (player.get_current_room().isBad()) {
                                    player.setFoundbadRooms(player.get_current_room());


                                }
                            }
                            for (Weapons weapon : player.get_current_room().get_contained_weapons().values()) {
                                if (!player.getSeen_weapons().contains(weapon.getName())) {
                                    player.setSeen_weapons(weapon);
                                    System.out.println(weapon.getName() + " has been added to your knowledge");

                                }
                                if (weapon.isBad()) {
                                    player.setFoundbadweapon(weapon);
                                    System.out.println("Found the murder weapon" + player.getFoundbadweapon().getName());
                                }


                            }
                            System.out.println("Seen players are " + player.getSeen_players());
                            System.out.println("Seen rooms are " + player.getSeen_rooms());
                            System.out.println("Seen weapons are " + player.getSeen_weapons());

                        } else {
                            System.out.println("You lost");
                            if (player.getfoundbadPlayer() != null) {
                                player.getSeen_players().clear();
                                player.setFoundbadplayer(null);
                                System.out.println("The player that you thought had committed murder has been switched up");
                                System.out.println("All prior knowledge you had about the other players have been erased, \nPrinting your list of seen players " + player.getSeen_players());
                                is_player_bad = false;
                                bad_player = null;
                                for (Player players : player_list) {
                                    //Changing up the the player who committed murder also moving them around
                                    System.out.println("Moving you back to the Hospital at Central Hub to heal your battle wounds");
                                    System.out.println("Other players are have moved around due to the chaos that has ensued");
                                    players.move_to_room(start_room);
                                    if (!player_chracter.equals(players)) {
                                        int rand = (int) Math.floor(Math.random() * room_list.size());
                                        players.move_to_room(room_list.get(rand));
                                    }

                                }

                                //set a random player to be the murderer
                                int random_num = (int) Math.floor(Math.random() * player_list.size());
                                while (player.equals(player_list.get(random_num))) {
                                    random_num = (int) Math.floor(Math.random() * player_list.size());
                                }
                                player_list.get(random_num).setbad(true);
                                is_player_bad = true;
                                bad_player = player_list.get(random_num);
                            } else if (player.getFoundbadroom() != null) {

                                //changing up the room that was originoally thought to contain the murder similar to the players modification but here there is not moving rooms around
                                player.getSeen_rooms().clear();
                                player.setFoundbadRooms(null);
                                System.out.println("The room that you thought was for the murder has been switched up");
                                System.out.println("All past knowledge about rooms that you had are erased printing your list of seen rooms " + player.getSeen_rooms());
                                is_room_bad = false;
                                bad_rooms = null;
                                int random_num = (int) Math.floor(Math.random() * room_list.size());
                                room_list.get(random_num).setbad(true);
                                is_room_bad = true;
                                bad_rooms = room_list.get(random_num);
                            } else if (player.getFoundbadweapon() != null) {
                                //changing up the weapon originally thought to cause murder similarly implemented like the room

                                //player.getSeen_weapons().clear();
                                player.setFoundbadweapon(null);
                                System.out.println("The weapon that you thought was for the murder has been switched up");
                                System.out.println("All past knowledge about weapons that you had are erased printing your list of seen weapons " + player.getSeen_weapons());
                                int random_num = (int) Math.floor(Math.random() * weapon_list.size());
                                weapon_list.get(random_num).setbad(true);
                                is_weapon_bad = true;
                                bad_weapons = weapon_list.get(random_num);
                            }


                        }
                    }
                    formatting();
                    break;
                case 5:
                    formatting();
                    System.out.println("You have decided to skip an action. Either proceed to move or stay put");

                    break;
                case 6:
                    formatting();
                    get_info(player);

                    break;
                case 7:
                    formatting();
                    get_instructions();
                    break;
                case 8:
                    formatting();
                    cheater();
                    break;
                case 9:
                    String sure = "no";
                    while (sure.equals("no")) {
                        System.out.println("So you want to take a guess - just know if you guess wrong its game over");
                        System.out.println("We will prompt you one more time after your guesses to make sure you are sure. so if you mess up dont panic");
                        System.out.println("In this exact order using spaces to separate each answer reply on one line who the killer was, weapon used, and room here is a sample");
                        System.out.println("john gun balcony");
                        String killer = null;
                        String weap = null;
                        String location = null;
                        System.out.println("Enter your guess for killer");
                        killer = sc.next().toLowerCase();
                        System.out.println("Enter your guess for weapon");
                        weap = sc.next().toLowerCase();
                        System.out.println("Enter your guess for the room of murder");
                        location = sc.next().toLowerCase();


                        System.out.println("You entered:");
                        System.out.println(killer + " " + weap + " " + location);
                        System.out.println("Are you sure enter yes, no, or back (will allow you to go back to the game)");
                        sure = sc.next();
                        while (!(sure.equals("yes")) && !(sure.equals("no")) && !(sure.equals("back"))) {
                            System.out.println("That was not an option try again.");
                            System.out.println("Are you sure enter yes, no, or back (will allow you to go back to the game)");
                            sure = sc.next();
                        }
                        if (sure.equals("back")) {
                            break;
                        } else if (sure.equals("yes")) {
                            end_game = true;
                            if (killer.equals(bad_player.getName()) && weap.equals(bad_weapons.getName()) && location.equals(bad_rooms.getName())) {
                                System.out.println("CONGRATS! YOU ARE THE WINNER!!");
                                System.out.println("This game took you " + turns+ " turns!");
                                break;
                            } else {
                                System.out.println("LOSER");
                                System.out.println("YOU LOST IN" + turns + " TURNS");
                                break;
                            }

                        }

                    }

                default:
                    if(!end_game) {
                        System.out.println("That's not an option try again");
                        number = 100;
                        break;
                    }

            }
        }
        if (!end_game) {
            get_info(player);
            System.out.println("\n");
            System.out.println("Now you get to move, where would you like to go?");
            System.out.println("The options are " + player_chracter.get_current_room().get_connected_rooms_string());
            System.out.println("If you type back for this question you will NOT MOVE");
            HashSet<String> empty = new HashSet<String>();
            String answer = check_scanner(sc, player, player.get_current_room().get_connected_rooms(), empty);
            if (!answer.equals("back")) {
                player.move_to_room(player.get_current_room().get_connected_rooms().get(answer));
                System.out.println("you are moving to " + player.get_current_room().getName());

            } else {
                System.out.println("you have chosen not to move");
            }
            System.out.println("Here is your status");
            get_info(player);

        }
    }
        public String check_scanner(Scanner scan, Player player, HashMap<String,?> to_find, Set<String> to_compare ) {
            String answer = "ENTER ANSWER";
            System.out.println("Type back to go back");
            while (true) {
                try {
                    answer = sc.next();
                } catch (Exception e) {
                    System.out.println("That is not an appropriate answer");
                }
                if (answer.equals("back"))
                {
                    return "back";
                }
                if (to_find.get(answer) == null) {
                    System.out.println("That is not in the list provided. Try again");
                } else {
                    if(to_compare.contains(answer) )
                    {
                        System.out.println("You already investigated try again");
                    }
                    else {
                        return answer;
                    }
                }
            }
        }
        public void get_instructions()
        {
            System.out.print("Welcome to the Game of modified Clue!");
            System.out.println("This game is about finding out three things, who was the killer, in what room they killed, and with what weapon in the LEAST AMOUNT OF TURNS");
            System.out.println("During each turn, you can ask information about ONE gamepiece either the room you are in or the one of the game pieces in the roomn");
            System.out.println("For example, if you are in the kitchen with a Gun, and Kobe, you have the option to choose one of these gamepieces to find out whether it is was related to the murder");
            System.out.println("There is one shortcut, If you are in a room with another person, you may dice battle with them, if you win you get to find out for the specific room ");
            System.out.println("you are in and every person and weapon in that room (including yourself) if it was related to the murderer or not. However, the catch is that you need ");
            System.out.println("to have found either a murder related person, room , or weapon. Then you can bet that knowledge for a chance to learn about everything in the room");
            System.out.println("If you lose, you lose knowledge about any one of the categories which you had found relating to the murder. The category for which you found the murder item");
            System.out.println("changes the item. For example, if you found that a knife is related to the murder and you dice battle and lose, then the knife is no longer the murder weapon ");
            System.out.println("And all information on weapons gets erased.");

        }
        public void cheater()
        {
            System.out.println("Murder weapon is " + bad_weapons.getName()+ ". Murderer is " +bad_player.getName()+ ". location of murder is "+ bad_rooms.getName());
        }
        public void get_info(Player player)
        {
            System.out.println("Printing information of what you have investigated ");
            System.out.println("The Murder weapon you have found is " + ((player.getFoundbadweapon()!=null) ? player.getFoundbadweapon().getName() : "None"));
            System.out.println("The Murder room you have found is " + ((player.getFoundbadroom()!=null) ? player.getFoundbadroom().getName() : "None"));
            System.out.println("The Murder player you have found is " + ((player.getfoundbadPlayer()!=null) ? player.getfoundbadPlayer().getName() : "None"));
            System.out.println("The weapons you have investigate are " +player.getSeen_weapons());
            System.out.println("The rooms you have investigated are " +player.getSeen_rooms());
            System.out.println("The players you have investigated are "  +player.getSeen_players());

        }

        public void formatting()
        {
            System.out.println("\n //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// \n");
        }
}

