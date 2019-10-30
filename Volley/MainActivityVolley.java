
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivityVolley extends AppCompatActivity {

    String GET_URL = "https://api.github.com/users/hadley/orgs";

    ListView listView;
    ProgressBar progress;
    ArrayList<String> list;
    Button fetch;
    ArrayAdapter adapter;
    RequestQueue mRequestQueue;
    StringRequest mStringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_volley);

        listView = (ListView) findViewById(R.id.listView);
        progress = (ProgressBar) findViewById(R.id.progress);

        fetch = (Button) findViewById(R.id.fetch);
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    progress.setVisibility(View.VISIBLE);
                    sendAndRequestResponse();
                } else {
                    Toast.makeText(getApplicationContext(), "No Connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Check for Internet Connection, return true if connected else false
     **/
    private boolean isOnline() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void sendAndRequestResponse() {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, GET_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /**Parse JSON**/
                try {
                    JSONArray array = new JSONArray(response);
                    list = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);

                        /**Add Items one by one in string and set it into list**/
                        String item = "Login Name : " + ob.getString("login") + "\n" +
                                "ID : " + ob.getInt("id") + "\n" +
                                "URL : " + ob.getString("url");

                        list.add(item);
                    }
                    /**Pass the list to the adapter**/
                    adapter = new ArrayAdapter(MainActivityVolley.this, android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(adapter);
                    listView.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progress.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
            }
        });

        /*Add Request into RequestQueue*/
        mRequestQueue.add(mStringRequest);
    }
}
