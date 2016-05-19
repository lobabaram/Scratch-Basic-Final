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

public class RemActivity extends AppCompatActivity {
    /*
    This activity is for making Rem Statement.
    It shows the required input for making Rem Statement. all the section must be filled in.
    Shows current statement list at the bottom of the page so user can see and check the line number and so on.
     */
    HashMap<String, String> dict_variable = new HashMap<String, String>();
    HashMap<String, String> dict_statement = new HashMap<String, String>();
    List<String> list_Statement = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rem);

        Intent intent_Rem = getIntent();
        if (getIntent().getExtras() != null){
            @SuppressWarnings("unchecked")
            HashMap<String, String> temp_dict_variable = (HashMap<String, String>) intent_Rem.getSerializableExtra("dict_variable");
            @SuppressWarnings("unchecked")
            HashMap<String, String> temp_dict_statement = (HashMap<String, String>) intent_Rem.getSerializableExtra("dict_statement");
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
        EditText text_LineNo = (EditText) findViewById(R.id.Rem_LineNo);
        EditText text_Comment = (EditText) findViewById(R.id.Rem_Comment);

        String LineNo = text_LineNo.getText().toString();
        String Comment = text_Comment.getText().toString();

        if(!LineNo.isEmpty()){
            if(!Comment.isEmpty()){
                Intent intent_main = new Intent(getApplicationContext(),MainActivity.class);
                String temp = "REM ";
                temp = temp.concat(Comment);
                dict_statement.put(LineNo,temp);
                intent_main.putExtra("dict_statement",dict_statement);
                intent_main.putExtra("dict_variable",dict_variable);
                startActivity(intent_main);
            }
            else{Toast.makeText(getApplicationContext(),"Comment is not given yet",Toast.LENGTH_LONG).show();}
        }
        else{Toast.makeText(getApplicationContext(),"Line Number is not given yet",Toast.LENGTH_LONG).show();}
    }

    public void onClick_Cancel(View v) {
        //cancel the activity here and go back
        finish();
    }
}
