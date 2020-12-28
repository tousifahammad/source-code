    //infalte in UI
	
    private fun setUpFab() {
        viewModel.fabAdapter = FabAdapter(viewModel.tempFabList) { fab ->
            if (fab.type == "order" && fab.count > 0) {
                Intent(this, RegisterTakeawayActivity::class.java).also {
                    it.putExtra("position", 3)
                    startActivity(it)
                }
            } else if (fab.type == "bill") {
                viewModel.gotoNextScreen(this, fab.invoiceId)
            }
        }
        rv_fab.adapter = viewModel.fabAdapter

        tv_fab.setOnClickListener {
            it.clickEffect()
            if (viewModel.tempFabList.size == 0) {
                viewModel.tempFabList.addAll(viewModel.fabList)
            } else {
                viewModel.tempFabList.clear()
            }
            viewModel.fabAdapter?.notifyDataSetChanged()
        }
    }
	
	
	private fun updateFab() {
        var count = 0
        try {
            viewModel.fabList.forEach { count += it.count }
            if (count > 0) {
                include_fab.visible()
                tv_fab.text = count.toString()

                viewModel.tempFabList.clear()
                viewModel.tempFabList.addAll(viewModel.fabList)
                viewModel.fabAdapter?.notifyDataSetChanged()
            } else {
                include_fab.gone()
            }
        } catch (error: Exception) {
            logD("Error:  ==> $error")
        }
    }