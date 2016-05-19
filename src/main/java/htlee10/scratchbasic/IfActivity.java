package htlee10.scratchbasic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class IfActivity extends AppCompatActivity {
    /*
    This Activity is for making If Statement.
    It shows the required input for making if Statement. all the section must be filled in
    Shows current statement list at the bottom of the page so user can see and check the line number and so on.
     */
    HashMap<String, String> dict_variable = new HashMap<String, String>();
    HashMap<String, String> dict_statement = new HashMap<String, String>();
    List<String> list_Statement = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_if);

        Intent intent_If = getIntent();

        if (getIntent().getExtras() != null){
            @SuppressWarnings("unchecked")
            HashMap<String, String> temp_dict_variable = (HashMap<String, String>) intent_If.getSerializableExtra("dict_variable");
            @SuppressWarnings("unchecked")
            HashMap<String, String> temp_dict_statement = (HashMap<String, String>) intent_If.getSerializableExtra("dict_statement");
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
        EditText text_LineNo = (EditText) findViewById(R.id.If_LineNo);
        EditText text_lhs_value = (EditText) findViewById(R.id.If_Lhs_value);
        EditText text_operation = (EditText) findViewById(R.id.If_Operation);
        EditText text_rhs_value = (EditText) findViewById(R.id.If_Rhs_value);
        EditText text_Goto = (EditText) findViewById(R.id.If_Goto);

        String LineNo = text_LineNo.getText().toString();
        String lhs_value = text_lhs_value.getText().toString();
        String operation = text_operation.getText().toString();
        String rhs_value = text_rhs_value.getText().toString();
        String goto_no = text_Goto.getText().toString();

        if(operation.equals("==") || operation.equals(">")){
            if(!LineNo.isEmpty()) {
                if(!lhs_value.isEmpty()){
                    if(!rhs_value.isEmpty()){
                        if(!goto_no.isEmpty()){
                            Intent intent_main = new Intent(getApplicationContext(),MainActivity.class);
                            String temp = "IF "+lhs_value+" "+operation+" "+rhs_value+" GOTO "+goto_no;
                            dict_statement.put(LineNo,temp);
                            intent_main.putExtra("dict_statement",dict_statement);
                            intent_main.putExtra("dict_variable",dict_variable);
                            startActivity(intent_main);
                        }
                        else{Toast.makeText(getApplicationContext(),"Value of goto which line is not given",Toast.LENGTH_LONG).show();}
                    }
                    else{Toast.makeText(getApplicationContext(),"Right-hand-side value is not given",Toast.LENGTH_LONG).show();}
                }
                else{Toast.makeText(getApplicationContext(),"Left-hand-side value is not given",Toast.LENGTH_LONG).show();}
            }
            else{Toast.makeText(getApplicationContext(),"Line Number is not given",Toast.LENGTH_LONG).show();}
        }
        else{Toast.makeText(getApplicationContext(),"Operation should be == or >",Toast.LENGTH_LONG).show();}
    }

    public void onClick_Cancel(View v){
        //cancel the activity here and go back
        finish();
    }
}
