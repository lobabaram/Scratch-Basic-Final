package htlee10.scratchbasic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /*
    This is MainActivity that user will encounter firstly when start the application.
    It is Main Screen. User can build up the ScratchBasic from this page
    When button pressed, it will move to another activities so that user can make some statement.
    Remove, Reset, Run, Exit will not move to another activities but will do another action when pressed.
     */
    HashMap<String, String> dict_variable = new HashMap<String, String>(); //Storing variable
    HashMap<String, String> dict_statement = new HashMap<String, String>(); //Storing statement
    List<String> list_Statement = new ArrayList<String>(); // will contain combined  Statement (LineNo + Temp_Statement)
    int global_index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent_main = getIntent();
        //Getting Statement_List from other Activities if it was not startup
        if (getIntent().getExtras() != null){
            @SuppressWarnings("unchecked") //temp_dict_variable and temp_dict_statement gets the intented dictionary that is come from other activities
            HashMap<String, String> temp_dict_variable = (HashMap<String, String>) intent_main.getSerializableExtra("dict_variable");
            @SuppressWarnings("unchecked")
            HashMap<String, String> temp_dict_statement = (HashMap<String, String>) intent_main.getSerializableExtra("dict_statement");
            //move contents in temp dictionaries to dict_variable and dict_statement
            dict_variable.putAll(temp_dict_variable);
            dict_statement.putAll(temp_dict_statement);
        }


        // Putting Dictionary to list for display
        if(dict_statement != null){
            List<String> list_LineNo = new ArrayList<String>(dict_statement.keySet());
            List<String> list_temp_statement = new ArrayList<String>(dict_statement.values());

            Integer dict_size = dict_statement.size();
            Integer i =0;
            while(i < dict_size){
                String temp = list_LineNo.get(i)+" "+list_temp_statement.get(i);
                list_Statement.add(temp);
                i++;
            }
        }

        //Sorting ArrayList
        Collections.sort(list_Statement);

        //Displaying Statement_list to ListView
        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list_Statement);

        ListView Statement_View = (ListView) findViewById(R.id.Statement_View);
        Statement_View.setAdapter(Adapter);

    }
    //adding Goto Statement
    public void onClick_Goto(View v){
        Intent intent_Goto = new Intent(MainActivity.this, GotoActivity.class);
        intent_Goto.putExtra("dict_statement", dict_statement);
        intent_Goto.putExtra("dict_variable",dict_variable);
        startActivity(intent_Goto);
    }
    //adding Let Statement
    public void onClick_Let(View v){
        Intent intent_Let = new Intent(MainActivity.this, LetActivity.class);
        intent_Let.putExtra("dict_statement", dict_statement);
        intent_Let.putExtra("dict_variable",dict_variable);
        startActivity(intent_Let);
    }
    //Adding Print Statement
    public void onClick_Print(View v){
        Intent intent_Print = new Intent(MainActivity.this, PrintActivity.class);
        intent_Print.putExtra("dict_statement", dict_statement);
        intent_Print.putExtra("dict_variable",dict_variable);
        startActivity(intent_Print);
    }
    //Adding If Statement
    public void onClick_If(View v){
        Intent intent_If = new Intent(MainActivity.this, IfActivity.class);
        intent_If.putExtra("dict_statement", dict_statement);
        intent_If.putExtra("dict_variable",dict_variable);
        startActivity(intent_If);
    }
    public void onClick_Rem(View v){
        Intent intent_Rem = new Intent(MainActivity.this, RemActivity.class);
        intent_Rem.putExtra("dict_statement", dict_statement);
        intent_Rem.putExtra("dict_variable",dict_variable);
        startActivity(intent_Rem);
    }

    public void onClick_Remove(View v){
        Intent intent_Remove = new Intent(MainActivity.this, RemoveActivity.class);
        intent_Remove.putExtra("dict_statement", dict_statement);
        intent_Remove.putExtra("dict_variable",dict_variable);
        startActivity(intent_Remove);
    }

    public void onClick_Reset(View v){
        Intent intent_restart = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent_restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent_restart);
    }

    public void onClick_exit(View v){
        //Terminating the program
        Intent intent_exit = new Intent(Intent.ACTION_MAIN);
        intent_exit.addCategory(Intent.CATEGORY_HOME);
        intent_exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent_exit);
    }
    //--------------------------------------------------Running the ScratchBasic-----------------------------------------------------
    public void onClick_Run(View v){
        if(dict_statement.isEmpty()){
            Toast.makeText(getApplicationContext(),"No Statement entered",Toast.LENGTH_LONG).show();
        }
        else{
            //finding minimum line number to start with.
            if(global_index == 0){ //only run once at first click
                List<String> temp_list = new ArrayList<String>(dict_statement.keySet());
                Collections.sort(temp_list);
                global_index = Integer.parseInt(temp_list.get(0));
            }
            //Display current statement
            TextView current_statement = (TextView) findViewById(R.id.current_statement);
            String statement_LineNo = Integer.toString(global_index);
            String statement_content = dict_statement.get(statement_LineNo);
            if(statement_content!=null){
                current_statement.setText(statement_LineNo+" "+statement_content);

                //actual running depends on what statement it is.
                String[] temp_statement = dict_statement.get(statement_LineNo).split("\\s+");

                //GOTO Statement
                if(temp_statement[0].equals("GOTO")){
                    global_index = Goto_function.Goto_statement(temp_statement,dict_variable);
                    if(global_index == -1){//one of the variable is not exist in Dictionary
                        Toast.makeText(getApplicationContext(),"One of the variable is not exist in Dictionary. Index reset to 0",Toast.LENGTH_LONG).show();
                        global_index = 0;
                    }
                }
                //LET Statement
                if(temp_statement[0].equals("LET")){
                    String added = Let_function.Let_statement(temp_statement,dict_variable);
                    if(added == null){//one of the variable is not exist in Dictionary
                        Toast.makeText(getApplicationContext(),"One of the variable is not exist in Dictionary. Index reset to 0",Toast.LENGTH_LONG).show();
                        global_index = 0;
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"The variable "+added+" = "+dict_variable.get(added)+" is added to variable dictionary",Toast.LENGTH_LONG).show();
                        global_index++;
                    }
                }
                //PRINT Statement
                if(temp_statement[0].equals("PRINT")){
                    String print_var = Print_function.Print_statement(temp_statement,dict_variable);
                    Toast.makeText(getApplicationContext(),"Value "+print_var+" is printed here and console also.",Toast.LENGTH_LONG).show();
                    global_index++;
                }
                //IF Statement
                if(temp_statement[0].equals("IF")){
                    global_index = If_function.If_statement(global_index,temp_statement,dict_variable);
                    if(global_index == -1){ //one of the variable is not exist in Dictionary
                        Toast.makeText(getApplicationContext(),"One of the variable is not exist in Dictionary. Index reset to 0",Toast.LENGTH_LONG).show();
                        global_index = 0;
                    }
                }
                if(temp_statement[0].equals("REM")){
                    global_index++;
                }

            }
            else{Toast.makeText(getApplicationContext(),"Statement for current line number does not exit / line number exceeded",Toast.LENGTH_LONG).show();

            }



        }



    }
}
