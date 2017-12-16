package com.company;
import java.util.*;
public class Rooms extends GamePiece {
    HashMap<String,Rooms> connected_rooms= new HashMap<String,Rooms>();
    HashMap<String,Weapons> contained_weapons= new HashMap<String,Weapons>();
    HashMap<String,Player> contained_players=new HashMap<String,Player>();

    public Rooms(String name, Boolean isbad)
    {
        super(name.toLowerCase(),isbad);
    }
    public void addonewaypath(Rooms room)
    {
        connected_rooms.put(room.getName(),room);
    }
    public void adddoublePath(Rooms room)
    {
        connected_rooms.put(room.getName(),room);
        room.addonewaypath(this);

    }
    public void place_player(Player player)
    {
        contained_players.put(player.getName(),player);
    }
    public void remove_player(Player player)
    {
        contained_players.remove(player.getName());
    }
    public void place_weapon(Weapons weapon)
    {
        contained_weapons.put(weapon.getName(),weapon);
    }
    public Set<String> get_contained_weapons_string()
    {
        return contained_weapons.keySet();
    }
    public Set<String> get_connected_rooms_string()
    {
        return connected_rooms.keySet();
    }
    public Set<String> get_contained_players_string()
    {
        return contained_players.keySet();
    }
    public HashMap<String,Rooms> get_connected_rooms() {
        return connected_rooms;
    }
    public HashMap<String,Player> get_contained_players() {
        return contained_players;
    }
    public HashMap<String,Weapons> get_contained_weapons() {
        return contained_weapons;
    }

}
