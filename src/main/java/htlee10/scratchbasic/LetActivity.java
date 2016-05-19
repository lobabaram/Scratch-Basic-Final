package htlee10.scratchbasic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class LetActivity extends AppCompatActivity {
    /*
    This Activity is for makaing Let Statement
    It shows the required input for making Let Statement. all the section must be filled in.
    Shows current statement list at the bottom of the page so user can see and check the line number and so on.
     */
    HashMap<String, String> dict_variable = new HashMap<String, String>();
    HashMap<String, String> dict_statement = new HashMap<String, String>();
    List<String> list_Statement = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_let);

        Intent intent_Let = getIntent();
        if (getIntent().getExtras() != null){
            @SuppressWarnings("unchecked")
            HashMap<String, String> temp_dict_variable = (HashMap<String, String>) intent_Let.getSerializableExtra("dict_variable");
            @SuppressWarnings("unchecked")
            HashMap<String, String> temp_dict_statement = (HashMap<String, String>) intent_Let.getSerializableExtra("dict_statement");
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

    public void onClick_Confirm(View v){
        //Initialisation
        EditText text_LineNo = (EditText) findViewById(R.id.Let_LineNo);
        EditText text_variable = (EditText) findViewById(R.id.Let_variable);
        EditText text_first_val = (EditText) findViewById(R.id.Let_first_value);
        EditText text_operation = (EditText) findViewById(R.id.Let_operation);
        EditText text_second_val = (EditText) findViewById(R.id.Let_second_value);

        //Getting Input from user
        String LineNo = text_LineNo.getText().toString();
        String variable = text_variable.getText().toString();
        String first_val = text_first_val.getText().toString();
        String operation = text_operation.getText().toString();
        String second_val = text_second_val.getText().toString();

        if(operation.equals("+") || operation.equals("-") || operation.equals("==") || operation.equals(">")){
            if(!variable.isEmpty()){
                if(!first_val.isEmpty()){
                    if(!second_val.isEmpty()){
                        if(!LineNo.isEmpty()){
                            Intent intent_main = new Intent(getApplicationContext(),MainActivity.class);
                            String temp = "LET "+variable+" = "+first_val+" "+operation+" "+second_val;
                            dict_statement.put(LineNo,temp);
                            intent_main.putExtra("dict_statement",dict_statement);
                            intent_main.putExtra("dict_variable",dict_variable);
                            startActivity(intent_main);
                        }
                        else{Toast.makeText(getApplicationContext(),"Line Number is not given",Toast.LENGTH_LONG).show();}
                    }
                    else{Toast.makeText(getApplicationContext(),"Second Value is not given",Toast.LENGTH_LONG).show();}
                }
                else {Toast.makeText(getApplicationContext(), "First Value is not given", Toast.LENGTH_LONG).show();}
            }
            else{Toast.makeText(getApplicationContext(),"variable is not given",Toast.LENGTH_LONG).show();}
        }
        else{Toast.makeText(getApplicationContext(),"Operation should be + or - or == or >",Toast.LENGTH_LONG).show();}
    }


    public void onClick_Cancel(View v) {
        //cancel the activity here and go back
        finish();
    }
}
