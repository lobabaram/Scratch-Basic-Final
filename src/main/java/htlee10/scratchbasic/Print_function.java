package htlee10.scratchbasic;

import java.util.HashMap;

/**
 * Created by Hun Tae Lee on 10/05/2016.
 */
public class Print_function {
    public static String Print_statement(String[] temp, HashMap<String, String> dict_variable){
        //format is {PRINT, X} where X is value
        //String[] temp contains Statements except line number, which was separated by spaces
        //dict_variable is the hashmap that contains the variables created in ScratchBasic
        String print_var = dict_variable.get(temp[1]);
        //If not in dictionary, just print it
        if(print_var == null){
            print_var = temp[1];
        }
        System.out.println(print_var);

        return print_var;
    }
}
