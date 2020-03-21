package net.simplifiedcoding.mvvmsampleapp.ui.splash


class SplashActivity : AppCompatActivity(), DialogNewRestaurantListener {
	


	fun onNewRestaurantClick(view: View) {
        DialogNewRestaurant(view.context, this).show()
    }


    override fun onStartClicked() {
        Intent(this, AuthActivity::class.java).also {
            startActivity(it)
        }
    }
}
