fun AppCompatActivity.addNotificationWidget() {
    try {
        LinearLayout(this).let {
            it.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            it.orientation = LinearLayout.VERTICAL
            it.id = View.generateViewId()
            val viewGroup = window.decorView.findViewById(android.R.id.content) as ViewGroup
            viewGroup.addView(it)

            supportFragmentManager
                .beginTransaction()
                .add(it.id, NotificationFragment(), "NotificationFragment")
                .disallowAddToBackStack()
                .commit()
        }
    } catch (error: Exception) {
        error.printStackTrace()
    }
}