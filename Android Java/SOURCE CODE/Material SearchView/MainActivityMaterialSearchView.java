
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivityMaterialSearchView extends AppCompatActivity {

    List<String> list;
    MaterialSearchView searchView;
    ListView listView;
    ArrayAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_material_search_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Material SearchView");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });

        /**Init Views**/
        listView = (ListView) findViewById(R.id.listView);
        searchView = (MaterialSearchView) findViewById(R.id.searchView);

        /**Prepare List**/
        prepareData();

        /**Adaptor to set it into ListView**/
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        /**When Click on Item**/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "Single CLicked on : " + adapter.getItem(position).toString(), Snackbar.LENGTH_SHORT).show();
            }
        });

        /**When Long Click on Item**/
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "Long CLicked on : " + adapter.getItem(position).toString(), Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**Inflate Menu**/
        getMenuInflater().inflate(R.menu.lib_menu, menu);

        /**Find Search Menu**/
        MenuItem searchItem = menu.findItem(R.id.search);
        /**Set SearchItem into MaterialSearchView**/
        searchView.setMenuItem(searchItem);
        searchView.setHint("Search...");
        /**Set a Listener**/
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /**When Click on Submit Button then Close the Search and Clear the Focus**/
                searchView.clearFocus();
                searchView.closeSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<String> newList = new ArrayList<>();
                /**For Loop to Check String in List one by one**/
                for (int i = 0; i < list.size(); i++) {

                    /**If typed string match with the data in a List then add it into new List**/
                    if (list.get(i).contains(newText)) {
                        newList.add(list.get(i));
                    }
                    /**After Perparing a newList, Set the List into ListView**/
                    adapter = new ArrayAdapter(MainActivityMaterialSearchView.this, android.R.layout.simple_list_item_1, newList);
                    listView.setAdapter(adapter);
                }
                return true;
            }
        });
        return true;
    }

    private void prepareData() {
        list = new ArrayList<>();
        /**Add Items in List**/
        list.add("Alpha");
        list.add("Beta");
        list.add("CupCake");
        list.add("Donut");
        list.add("Eclair");
        list.add("Froyo");
        list.add("GingerBread");
        list.add("HoneyComb");
        list.add("Ice Cream Sandwich");
        list.add("JellyBean");
        list.add("KitKat");
        list.add("Lollipop");
        list.add("MarshMellow");
        list.add("Nougat");
        list.add("Oreo");
    }
}
