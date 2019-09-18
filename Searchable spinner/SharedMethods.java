
//use this class to write methods, which are used in many other classes
public class SharedMethods {


    public static void openSearch(Activity activity, ArrayList<Search> saloon_list, int container_view_id, String title) {
        try {
            if (saloon_list.size() > 0) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("saloon_list", saloon_list);
                bundle.putString("title", title);
                //bundle.putStringArrayList("code_list", biler_code_list);

                Fragment fragment = new SearchFragment();
                fragment.setArguments(bundle);

                FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
                transaction.replace(container_view_id, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
            } else {
                //Alert.showError(activity, "No data found");
            }
        } catch (Exception e) {
            Alert.showError(activity, e.getMessage());
        }
    }


}
