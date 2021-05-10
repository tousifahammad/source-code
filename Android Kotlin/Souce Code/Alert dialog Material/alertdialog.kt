    fun logout(context: Context) {
        MaterialAlertDialogBuilder(context, R.style.RoundedCornersDialog)
            .setTitle("Logout")
            .setMessage("Are you sure, you want to logout?")
            .setPositiveButton("Logout") { dialog, which ->
                PreferenceProvider(context).saveInt(PreferenceProvider.LAST_LOGGED_IN_RESTAURANT_ID, 0)
                ActivityController.gotToActivityWithoutStack(context, SplashActivity::class.java)
            }
            .setNegativeButton("Not now", null)
            .show();
    }
	
	
	inline fun Context.alertDialog(message: String = "", title: String = "", positiveMsg: String = "Yes", cancelable: Boolean = true, crossinline positiveFun: () -> Unit = {}, crossinline negativeFun: () -> Unit = {}) {
		MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme).apply {
			if (title.isNotEmpty()) setTitle(title)
			if (message.isNotEmpty()) setMessage(message)
			setCancelable(cancelable)
			setPositiveButton(positiveMsg) { _, _ ->
				positiveFun()
			}
			setNegativeButton("No") { _, _ ->
				negativeFun()
			}
			show()
		}
	}


	fun logout(context: Context) {
        context.alertDialog("Do you want to logout? ", positiveMsg = "Logout", positiveFun = {
            context.gotToActivityWithoutStack(AuthActivity::class.java)
        })
    }