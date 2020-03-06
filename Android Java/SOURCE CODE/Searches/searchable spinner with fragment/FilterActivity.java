public class FilterActivity extends AppCompatActivity implements SpinnerEventListener {
    ArrayList<String> category_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


        category_list.add("aaaaa");
        category_list.add("1321sadasd3");
        category_list.add("123131asdsd23");
    }


    public void onCategoryClicked(View view) {
        SharedMethods.openSpinner(this, category_list, R.id.cl_root_view, "Select category");
    }


    @Override
    public void onItemClicked(String data, int position) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }


}
