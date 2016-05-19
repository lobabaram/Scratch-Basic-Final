package htlee10.scratchbasic;

import java.util.HashMap;

/**
 * Created by Hun Tae Lee on 10/05/2016.
 */
public class Let_function {
    public static String Let_statement(String[] temp, HashMap<String, String> dict_variable){
        //Let_statement has format of {LET, variable, = value , op, value}
        //String[] temp contains Statements except line number, which was separated by spaces
        //dict_variable is the hashmap that contains the variables created in ScratchBasic
        //Initialising
        int int_result=0;
        String str_result;
        String op = temp[4];
        int lhs_value;
        int rhs_value;
        String lhs_string;
        String rhs_string;

        //left handside value
        try{
            lhs_value = Integer.parseInt(temp[3]);
        }
        catch (NumberFormatException e){
            lhs_string = dict_variable.get(temp[3]);
            if(lhs_string == null){ //variable is not exist in Dictionary
                return null;
            }
            lhs_value = Integer.parseInt(lhs_string);
        }

        //right handside value
        try{
            rhs_value = Integer.parseInt(temp[5]);
        }
        catch (NumberFormatException e){
            rhs_string = dict_variable.get(temp[5]);
            if(rhs_string == null){//variable is not exist in Dictionary
                return null;
            }
            rhs_value = Integer.parseInt(rhs_string);
        }

        //operation
        if (op.equals("+")){
            int_result = lhs_value + rhs_value;
        }
        else if (op.equals("-")){
            int_result = lhs_value - rhs_value;
        }
        else if (op.equals("==")){
            if (lhs_value == rhs_value){
                int_result = 1;
            }
            else{
                int_result = 0;
            }
        }
        else if (op.equals(">")){
            if (lhs_value > rhs_value){
                int_result = 1;
            }
            else{
                int_result = 0;
            }
        }
        str_result = Integer.toString(int_result);
        dict_variable.put(temp[1], str_result);
        return temp[1];
    }
}
