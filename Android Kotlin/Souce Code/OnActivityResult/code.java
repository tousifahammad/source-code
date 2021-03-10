public static final int REQUEST_CODE = 1;

//From activity


    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {

            }
        }
    }

	rootView.btn_scan_barcode.setOnClickListener {
		Intent(view.context, CourseReorderActivity::class.java).apply {
                this.putExtra("invoiceId", viewModel.invoice.id)
                resultLauncher.launch(this)
            }
	}



//in another activity

    fun onBackClick(view: View) {
        intent.apply {
            putExtra("sentList", vm.orderList.filter { it.sentAt != null }.toString())
            setResult(RESULT_OK, this)
            finish()
        }
    }



============================================================================
// in Fragment

To get the result in your fragment make sure you call startActivityForResult(intent,111); 
instead of getActivity().startActivityForResult(intent,111); inside your fragment.