public static String getSosMessage(Context context) {
        String message = null;
        try {
            //https://developers.google.com/maps/documentation/urls/guide
            //https://www.google.com/maps/search/?api=1&query=47.5951518,-122.3316393

            message = "Location- https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude;

            message = Uri.parse(message)
                    .buildUpon()
                    .build().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("1111", "SOS Message: " + message);
        return message;
    }
