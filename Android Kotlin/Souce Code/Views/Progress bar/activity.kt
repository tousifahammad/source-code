    
	private var alertDialog: MyProgressDialog? = null
	
	private fun showProgress() {
        try {
            if (alertDialog == null) alertDialog = MyProgressDialog(this)

            alertDialog?.let {
                if (!it.isShowing) it.show()
                else if (it.isShowing) it.dismiss()
            }
        } catch (error: Exception) {
        }
    }