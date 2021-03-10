private fun inflateReasons(context: Context, onlineInvoice: OnlineInvoice) {
    val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    binding.llreject.removeAllViews()

    cancelReasons.keys.forEach {
        val myLayout: View = inflater.inflate(R.layout.row_underline_text, binding.llreject, false)
        val textView: TextView = myLayout.findViewById(R.id.tvUnderlineText)
        textView.text = it
        textView.setOnClickListener {
            vm.onReasonClick(textView, onlineInvoice)
        }
        binding.llreject.addView(myLayout)
    }
}