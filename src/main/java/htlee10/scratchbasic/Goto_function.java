package htlee10.scratchbasic;

import java.util.HashMap;

/**
 * Created by Hun Tae Lee on 10/05/2016.
 */
public class Goto_function {
    public static int Goto_statement(String[] temp, HashMap<String, String> dict_variable){
        //Has format of {GOTO, value}
        //String[] temp contains Statements except line number, which was separated by spaces
        //dict_variable is the hashmap that contains the variables created in ScratchBasic
        int goto_lineNo;
        String str_goto_lineNo;

        try{ //if value can be changed to integer, use it
            goto_lineNo = Integer.parseInt(temp[1]);
        }
        catch (NumberFormatException e){ //if value is not an integer, find it from dict_variable.
            str_goto_lineNo = dict_variable.get(temp[1]);
            if(str_goto_lineNo == null){
                return goto_lineNo = -1;
            }
            goto_lineNo = Integer.parseInt(str_goto_lineNo);
        }
        return goto_lineNo; //returns line number that will go to.
    }
}
