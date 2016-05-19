package htlee10.scratchbasic;

import java.util.HashMap;

/**
 * Created by Hun Tae Lee on 10/05/2016.
 */
public class If_function {
    public static int If_statement(int i, String[] temp, HashMap<String, String> dict_variable){
        // temp format is {IF, lhs,op(== or >),rhs,GOTO,value}
        //String[] temp contains Statements except line number, which was separated by spaces
        //dict_variable is the hashmap that contains the variables created in ScratchBasic

        int goto_lineNo = i;
        String str_goto_lineNo;
        int int_result=0;
        String op = temp[2];
        int lhs_value;
        int rhs_value;
        String lhs_string;
        String rhs_string;

        //left handside value
        try{
            lhs_value = Integer.parseInt(temp[1]);
        }
        catch (NumberFormatException e){
            lhs_string = dict_variable.get(temp[1]);
            if(lhs_string == null){ //if variable does not exist in dictionary
                return goto_lineNo = -1;
            }
            lhs_value = Integer.parseInt(lhs_string);
        }

        //right handside value
        try{
            rhs_value = Integer.parseInt(temp[3]);
        }
        catch (NumberFormatException e){
            rhs_string = dict_variable.get(temp[3]);
            if(rhs_string == null){ //if variable does not exist in dictionary
                return goto_lineNo = -1;
            }
            rhs_value = Integer.parseInt(rhs_string);
        }

        //Operations
        if (op.equals("==")){
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
        //change index depends on the result
        if (int_result == 1){
            try{
                goto_lineNo = Integer.parseInt(temp[5]);
            }
            catch (NumberFormatException e){
                str_goto_lineNo = dict_variable.get(temp[5]);
                goto_lineNo = Integer.parseInt(str_goto_lineNo);
            }
        } else if (int_result == 0){
            goto_lineNo = i+1;
        }
        return goto_lineNo;
    }
}
