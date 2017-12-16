package com.company;
import java.util.*;
public class Weapons extends GamePiece {
    Rooms current_room;
    public Weapons(String name, Boolean isbad,Rooms place_in_room)
    {
        super(name.toLowerCase(),isbad);
        current_room=place_in_room;
        place_in_room.place_weapon(this);
    }


}
