

private fun showRiderDetails() {
    val popupMenu = PopupMenu(itemView.context, itemView)
    popupMenu.menu.add(1, 1, 1, "Accept 1")
    popupMenu.menu.add(1, 2, 2, "Accept 2")
    popupMenu.menu.add(1, 3, 3, "Accept 3")
    popupMenu.show()
    popupMenu.setOnMenuItemClickListener { item ->
        when (item.itemId) {
        }
        false
    }
}