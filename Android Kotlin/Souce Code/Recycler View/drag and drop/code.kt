    private lateinit var rootView: View
    private var allMenuItemList: ArrayList<MenuItems> = ArrayList()
    private var currentMenuItemList: ArrayList<MenuItems> = ArrayList()
    private var adapterMenuItems: MenuItemsListAdapter? = null
    private var modifiedItemIdList: ArrayList<MenuItems> = ArrayList()



private fun setUpRecyclerView() {
        adapterMenuItems = MenuItemsListAdapter(context!!, currentMenuItemList, this)
        rootView.rv_menu_items_list.layoutManager = LinearLayoutManager(requireContext())
        rootView.rv_menu_items_list.adapter = adapterMenuItems


        val itemTouchHelper = ItemTouchHelper(object : RecyclerViewDragDetector() {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                if (isSortingEnable) {
                    moveItem(viewHolder.adapterPosition, target.adapterPosition)
                    //saveMenuItemsPositions()
                }
                return true
            }
        })
        itemTouchHelper.attachToRecyclerView(rootView.rv_menu_items_list)
    }
	
	
	
	fun moveItem(fromPosition: Int, toPosition: Int) {
        //save change position and its state
        val fromSortBy = currentMenuItemList[fromPosition].sortby
        val toSortBy = currentMenuItemList[toPosition].sortby

        currentMenuItemList[fromPosition].sortby = toSortBy
        currentMenuItemList[toPosition].sortby = fromSortBy

        modifiedItemIdList.add(currentMenuItemList[fromPosition])
        modifiedItemIdList.add(currentMenuItemList[toPosition])

        //change position in the recycler view
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(currentMenuItemList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(currentMenuItemList, i, i - 1)
            }
        }
        adapterMenuItems?.notifyItemMoved(fromPosition, toPosition)
    }