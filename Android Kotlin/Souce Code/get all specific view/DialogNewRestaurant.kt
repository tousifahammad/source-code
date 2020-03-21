

    private fun addTextChangeListener() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                isAnyFieldEmpty()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        }

        //get all the edit text
        for (view: View in SharedMethods.getAllChildrenFromViewGroup(ll_container)!!) {
            if (view is EditText) {
                view.addTextChangedListener(textWatcher)
            }
        }
    }


    private fun isAnyFieldEmpty(): Boolean {
        if (et_name?.text.isNullOrEmpty()) return true
        if (et_address?.text.isNullOrEmpty()) return true
        if (et_city?.text.isNullOrEmpty()) return true
        if (et_state?.text.isNullOrEmpty()) return true
        if (et_country?.text.isNullOrEmpty()) return true
        if (et_pin?.text.isNullOrEmpty()) return true
        if (et_telephone_number?.text.isNullOrEmpty()) return true
        if (et_email?.text.isNullOrEmpty()) return true
        if (et_website?.text.isNullOrEmpty()) return true
        if (et_licence_key?.text.isNullOrEmpty()) return true
        return false
    }
}