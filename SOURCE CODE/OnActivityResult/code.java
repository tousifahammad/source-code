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
In custom dialog activity use this code to set result

Intent intent = getIntent();
intent.putExtra("key", value);
setResult(RESULT_OK, intent);
finish();
