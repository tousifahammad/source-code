    public static void gotToActivityNoBack(Activity activity, Class<?> cls) {
        try {
            Intent intent = new Intent(activity, cls);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            activity.finish();
        } catch (Exception e) {
            Alert.showError(activity, e.getMessage());
        }
    }
