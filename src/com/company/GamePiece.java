package com.company;
import java.util.*;
public abstract class GamePiece {
    private String name;
    private Boolean isbad;

    public GamePiece(String name, Boolean isbad)
    {
       this.name=name.toLowerCase();
       this.isbad=isbad;

    }
    protected String getName()
    {
        return name;
    }
    protected Boolean isBad()
    {
        return isbad;
    }
    protected void setbad(boolean bad)
    {
        isbad=bad;
    }

}
