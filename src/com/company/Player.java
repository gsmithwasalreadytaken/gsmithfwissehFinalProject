package com.company;
import java.util.*;
public class Player extends GamePiece{
    private Rooms current_room;
    private Weapons current_weapon;
    boolean isnpc;
    private Weapons foundbadweapon;
    private Rooms foundbadroom;
    private Player foundbadplayer;
    private HashMap<String,Rooms> seen_rooms;
    private HashMap<String,Weapons> seen_weapons;
    private HashMap<String,Player> seen_players;
    private HashMap<String,Integer> player_relationships;

    public Player(String name,Boolean isbad, boolean isnpc)
    {
        super(name.toLowerCase(), isbad);
        this.isnpc=isnpc;
        seen_players= new HashMap<String,Player>();
        seen_rooms= new HashMap<String, Rooms>();
        seen_weapons= new HashMap<String,Weapons>();
        player_relationships= new HashMap<String, Integer>();
        foundbadplayer=null;
        foundbadweapon=null;
        foundbadroom=null;
    }


    public Rooms get_current_room(){
        return current_room;
    }
    public void move_to_room(Rooms room)
    {
        if(current_room!=null)
        {
            current_room.remove_player(this);}
        current_room= room;
        room.place_player(this);
    }

    public void setSeen_players(Player player)
    {
        seen_players.put(player.getName(),player);
    }
    public void setSeen_rooms(Rooms room)
    {
        seen_rooms.put(room.getName(),room);
    }
    public void setSeen_weapons(Weapons weapon)
    {
        seen_weapons.put(weapon.getName(),weapon);
    }
    public void setFoundbadweapon(Weapons weapon)
    {
        foundbadweapon=weapon;
    }
    public void setFoundbadRooms(Rooms room)
    {
           foundbadroom=room;
    }
    public void setFoundbadplayer(Player player)
    {
       foundbadplayer=player;
    }
    public Weapons getFoundbadweapon()
    {
        return foundbadweapon;
    }
    public Player getfoundbadPlayer()
    {
        return foundbadplayer;
    }
    public Rooms getFoundbadroom()
    {
        return foundbadroom;
    }
    public Set<String> getSeen_players() {
        return seen_players.keySet();
    }
    public Set<String> getSeen_rooms() {
        return seen_rooms.keySet();
    }
    public Set<String> getSeen_weapons() {
        return seen_weapons.keySet();
    }
}
