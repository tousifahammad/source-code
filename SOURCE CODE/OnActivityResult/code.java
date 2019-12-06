public static final int REQUEST_CODE = 1;

//Call your custom dialog activity using intent

Intent intent = new Intent(Activity.this,CustomDialogActivity.class);
startActivityForResult(intent , REQUEST_CODE);


//Now use onActivityResult to retrieve the result

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {

                String requiredValue = data.getStringExtra("key");
            }
        } catch (Exception ex) {
            Toast.makeText(Activity.this, ex.toString(), Toast.LENGTH_SHORT).show();
        }

    }


//In custom dialog activity use this code to set result


Intent intent = getIntent();
intent.putExtra("key", value);
setResult(RESULT_OK, intent);
finish();




============================================================================
From your FirstActivity call the SecondActivity using startActivityForResult() method

For example:

Intent i = new Intent(this, SecondActivity.class);
startActivityForResult(i, 1);

//In your SecondActivity set the data which you want to return back to FirstActivity. If you don't want to return back, don't set any.

//For example: In SecondActivity if you want to send back data:

Intent returnIntent = new Intent();
returnIntent.putExtra("result",result);
setResult(Activity.RESULT_OK,returnIntent);
finish();



//If you don't want to return data:

Intent returnIntent = new Intent();
setResult(Activity.RESULT_CANCELED, returnIntent);
finish();


//Now in your FirstActivity class write following code for the onActivityResult() method.

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    if (requestCode == 1) {
        if(resultCode == Activity.RESULT_OK){
            String result=data.getStringExtra("result");
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result
        }
    }
}//onActivityResult

