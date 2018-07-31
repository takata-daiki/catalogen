/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package poker;

import java.util.*;
import jpl.*;

/**
 *
 * @author USUARIO
 */

public enum Rank { R1,R2,R3,R4,R5,R6,R7,R8,R9,R10,JACK,QUEEN,KING,ACE};

class PRank
{
    static public String rankToString(Rank r)
    {
        switch(r)
        {
            case R2: return "2";
            case R3: return "3";
            case R4: return "4";
            case R5: return "5";
            case R6: return "6";
            case R7: return "7";
            case R8: return "8";
            case R9: return "9";
            case R10: return "10";
            case JACK: return "jack";
            case QUEEN: return "queen";
            case KING: return "king";
            case ACE: return "ace";
            default: return "";
        }
    }

    static public Rank stringToRank(String s)
    {
        if(s.compareTo("2")==0) return Rank.R2;
        else if(s.compareTo("3")==0) return Rank.R3;
        else if(s.compareTo("4")==0) return Rank.R4;
        else if(s.compareTo("5")==0) return Rank.R5;
        else if(s.compareTo("6")==0) return Rank.R6;
        else if(s.compareTo("7")==0) return Rank.R7;
        else if(s.compareTo("8")==0) return Rank.R8;
        else if(s.compareTo("9")==0) return Rank.R9;
        else if(s.compareTo("10")==0) return Rank.R10;
        else if(s.compareTo("jack")==0) return Rank.JACK;
        else if(s.compareTo("queen")==0) return Rank.QUEEN;
        else if(s.compareTo("king")==0) return Rank.KING;
        else if(s.compareTo("ace")==0) return Rank.ACE;
        else return Rank.R1;
    }
}