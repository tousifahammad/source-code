public static final int REQUEST_CODE = 1;

//From activity

rootView.btn_scan_barcode.setOnClickListener {
    startActivityForResult(Intent(this, BarcodeScannerActivity::class.java), REQUEST_CODE)
}


//Now use onActivityResult to retrieve the result

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == REQUEST_CODE_BARCODE_SCANNER){
             context?.toast(data!!.getStringExtra("key"))
            }
        }
    }


//in another activity

Intent intent = getIntent();
intent.putExtra("key", value);
setResult(RESULT_OK, intent);
finish();




============================================================================
// in Fragment

To get the result in your fragment make sure you call startActivityForResult(intent,111); 
instead of getActivity().startActivityForResult(intent,111); inside your fragment.