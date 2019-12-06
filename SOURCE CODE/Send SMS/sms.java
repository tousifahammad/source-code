//in manifest and runtime

<uses-permission android:name="android.permission.SEND_SMS" />

public void sendSMS(String phoneNo, String msg) {
    try {      
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, msg, null, null);    
        Toast.makeText(getApplicationContext(), "Message Sent",
              Toast.LENGTH_LONG).show();
    } catch (Exception ex) {
        Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
              Toast.LENGTH_LONG).show();
        ex.printStackTrace();
    } 
}


//how to send a message with more than 160 characters

public void sendLongSMS() {
    String phoneNumber = "0123456789";
    String message = "Hello World! Now we are going to demonstrate " + 
        "how to send a message with more than 160 characters from your Android application.";
    SmsManager smsManager = SmsManager.getDefault();
    ArrayList<String> parts = smsManager.divideMessage(message); 
    smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
}
