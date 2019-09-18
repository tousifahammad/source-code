
public class UpcomingActivity extends AppCompatActivity implements SearchEventListener {

    TextView tv_reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);
		
		tv_reason = findViewById(R.id.tv_reason);
		
		        tv_reason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedMethods.openSearch(UpcomingActivity.this, list, R.id.rl_search_root_view, "Select Reason");
            }
        });
    }

    }


    @Override
    public void searchEvent(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();

        if (data != null)
            tv_reason.setText(data);
    }
}
