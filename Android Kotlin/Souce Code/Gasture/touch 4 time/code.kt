    private var count = 0
    private var startMillis: Long = 0
	
	@SuppressLint("ClickableViewAccessibility")
    private fun initTouch() {
        tv_go_back.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                //get system current milliseconds
                val time = System.currentTimeMillis()

                //if it is the first time, or if it has been more than 3 seconds since the first tap ( so it is like a new try), we reset everything
                if (startMillis == 0L || time - startMillis > 3000) {
                    startMillis = time
                    count = 1
                } else { //  time-startMillis< 3000
                    count++
                }
                if (count == 4) {
                    //do whatever you need
                    ActivityController.gotToActivityWithoutStack(this, SelectTableActivity::class.java)
                }
                return@setOnTouchListener true
            }
            return@setOnTouchListener false
        }
    }