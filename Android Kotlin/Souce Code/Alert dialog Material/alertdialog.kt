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