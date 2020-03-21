    var viewList = ArrayList<TextView>()
	    
		
	fun onClicked(view: View) {
        when (view.id) {
            tv_new_restaurant.id -> {
                DialogNewRestaurant(view.context, this).show()
            }
            tv_new_restaurant.id -> {
                ActivityController.gotToActivity(this, AuthActivity::class.java)
            }
            tv_new_restaurant.id -> {
                toast("Not available")
            }
        }

        changeViewColor(view)
    }
	
	
	private fun changeViewColor(view: View) {
        (view as TextView).also {
            it.setBackgroundResource(R.drawable.bg_round_color_primary)
            it.setTextColor(resources.getColor(R.color.White))

            if (!viewList.contains(it)) {
                viewList.add(it)
            }

            for (mTextView in viewList) {
                if (mTextView.id != it.id) {
                    mTextView.setBackgroundResource(R.drawable.bg_round_white)
                    mTextView.setTextColor(resources.getColor(R.color.colorPrimary))
                }
            }
        }
    }