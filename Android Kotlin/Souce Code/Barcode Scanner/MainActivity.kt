package com.example.demobarcodescanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private val REQUEST_CODE_BARCODE_SCANNER = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootView.btn_scan_barcode.setOnClickListener {
            startActivityForResult(Intent(activity, BarcodeScannerActivity::class.java), REQUEST_CODE_BARCODE_SCANNER)
        }
    }
	
	
	    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == REQUEST_CODE_BARCODE_SCANNER){
                try {
                    rootView.et_upc_barcode.setText(data!!.getStringExtra("barcode"))
                } catch (error: Exception) {
                  Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
                }
            }
        }
    }
}
