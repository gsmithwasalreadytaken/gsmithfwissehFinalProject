package com.company;

public class Main {

    public static void main(String[] args) {
        Game new_Game=new Game();
        Player Giannis= new Player("Giannis",false, true);
        Player Kyrie= new Player("Kyrie", false, true);
        Player LeBron= new Player("LeBron", false, true);
        Player Kobe= new Player ("Kobe",false, true);
        Player Scalabrine= new Player("Scalabrine",false,true);
        new_Game.add_players(Giannis);
        new_Game.add_players(Kyrie);
        new_Game.add_players(LeBron);
        new_Game.add_players(Kobe);
        new_Game.add_players(Scalabrine);
        Rooms TrapHouse= new Rooms("TrapHouse", false );
        Rooms Garden= new Rooms("Garden", false);
        Rooms Pool= new Rooms("Pool",false);
        Rooms CandyShop= new Rooms("CandyShop",false);
        Rooms Park= new Rooms("Park",false);
        Rooms Street =new Rooms ("Street",false);
        new_Game.add_rooms(TrapHouse);
        new_Game.add_rooms(Garden);
        new_Game.add_rooms(Pool);
        new_Game.add_rooms(CandyShop);
        new_Game.add_rooms(Park);
        new_Game.add_rooms(Street);
        Street.adddoublePath(TrapHouse);
        TrapHouse.addonewaypath(Park);
        CandyShop.addonewaypath(Garden);
        Park.addonewaypath(CandyShop);
        Pool.addonewaypath(Street);
        Garden.adddoublePath(Pool);
        Weapons Gun= new Weapons("Gun", false, Park);
        Weapons Lightsaber= new Weapons("Lightsaber",false, CandyShop);
        Weapons Fork= new Weapons ("Fork", false ,Street);
        Weapons PoisonousDart= new Weapons ("Poisonous Dart", false, Pool);
        Weapons Dog=new Weapons("Dog",false,TrapHouse);
        new_Game.add_weapons(Gun);
        new_Game.add_weapons(Lightsaber);
        new_Game.add_weapons(Fork);
        new_Game.add_weapons(PoisonousDart);
        new_Game.add_weapons(Dog);
        System.out.println(new_Game.getPlayer_list());
        System.out.println(new_Game.getRoom_list());
        System.out.println(new_Game.getWeapon_list());
        new_Game.startGame();
    }
}
