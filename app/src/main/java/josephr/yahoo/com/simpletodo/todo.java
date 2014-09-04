package josephr.yahoo.com.simpletodo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class todo extends Activity {

    private ArrayAdapter<String> todoAdapter;
    private ArrayList<String> todoItems;
    private ListView listViewItems;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        readItems();
        listViewItems = (ListView) findViewById(R.id.listView);
        text = (EditText) findViewById(R.id.txNewItemText);
        todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);

        listViewItems.setAdapter(todoAdapter);
        setUpListViewListener();
    }

    private void setUpListViewListener() {
        listViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View item, int pos, long l) {
                todoItems.remove(pos);
                writeItems();
                todoAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void readItems() {

        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch(IOException e){
            todoItems = new ArrayList<String>();
        }

    }


    public void addItem(View v) {

        todoItems.add(getValueFromTextField());
        writeItems();
        text.setText("");

    }

    private void writeItems() {

        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, todoItems);
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    public String getValueFromTextField() {

        return text.getText().toString();

    }
}
