

class MenuOrderActivity : AppCompatActivity() {
    
    private val mInterval = 5000 // 5 seconds by default, can be changed later
    private var mHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mHandler = Handler()
        startRepeatingTask()
    }

	//use onReasume and onPause to  use in forground
	
    override fun onDestroy() {
        super.onDestroy()
        stopRepeatingTask()
    }

    var mStatusChecker: Runnable = object : Runnable {
        override fun run() {
            try {
                //updateStatus() //this function can change value of mInterval.
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler!!.postDelayed(this, mInterval.toLong())
            }
        }
    }

    private fun startRepeatingTask() {
        mStatusChecker.run()
    }

    private fun stopRepeatingTask() {
        mHandler!!.removeCallbacks(mStatusChecker)
    }

}