					if (!progressBar.isVisible) progressBar.visibility = View.VISIBLE
                    Coroutines.io {
                        parentItem.childItems = viewModel.getChildItems(parentItem.id, parentItem)
                        Coroutines.main {
                            if (progressBar.isVisible) progressBar.visibility = View.INVISIBLE
                            showChildData()
                        }
                    }
					
					
					
					
	fun getChildItems(menuCategoryId: Int, parent: Parent): ArrayList<Child> {
        val childList = ArrayList<Child>()
        try {
            MenuItemsRepository().getAllDataByField("menuCategoryId", menuCategoryId)?.let {
                if (it.size > 0) {
                    it.forEach { menuItem ->
                        childList.add(Child(menuItem, parent))
                    }
                }
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : getMenuCategories()", error)
        }
        return childList
    }