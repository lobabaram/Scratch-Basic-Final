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

public class RemoveActivity extends AppCompatActivity {
    /*
    This Activity is for removing statement.
    Line number of the statement must be entered in order to remove it from the list.
    Shows current statement list at the bottom of the page so user can see and check the line number and so on.
     */
    HashMap<String, String> dict_variable = new HashMap<String, String>();
    HashMap<String, String> dict_statement = new HashMap<String, String>();
    List<String> list_Statement = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        Intent intent_Remove = getIntent();

        //Getting Statement_List from MainActivity if it was not startup
        if (getIntent().getExtras() != null){
            @SuppressWarnings("unchecked")
            HashMap<String, String> temp_dict_variable = (HashMap<String, String>) intent_Remove.getSerializableExtra("dict_variable");
            @SuppressWarnings("unchecked")
            HashMap<String, String> temp_dict_statement = (HashMap<String, String>) intent_Remove.getSerializableExtra("dict_statement");
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
        EditText text_LineNo = (EditText) findViewById(R.id.Remove_LineNo);

        String LineNo = text_LineNo.getText().toString();

        if(!LineNo.isEmpty()){
            dict_statement.remove(LineNo);
            Intent intent_main = new Intent(getApplicationContext(),MainActivity.class);
            intent_main.putExtra("dict_statement",dict_statement);
            intent_main.putExtra("dict_variable",dict_variable);
            startActivity(intent_main);
        }
        else{
            Toast.makeText(getApplicationContext(),"Line Number is not given yet",Toast.LENGTH_LONG).show();}
    }

    public void onClick_Cancel(View v) {
        //cancel the activity here and go back
        finish();
    }
}
