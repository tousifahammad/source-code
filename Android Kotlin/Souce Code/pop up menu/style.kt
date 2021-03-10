url: https://stackoverflow.com/questions/12636101/how-to-style-popupmenu


<style name="popupMenuSmall" parent="@android:style/Widget.PopupMenu">
    <item name="android:textSize">12sp</item>
    <item name="android:listPreferredItemHeightSmall">20dp</item>
    <item name="android:listPreferredItemPaddingLeft">5dp</item>
    <item name="android:listPreferredItemPaddingRight">5dp</item>
</style>



private fun showRiderDetails(invoiceDrawer: InvoiceDrawer) {
    invoiceDrawer.riderDetails?.let {
        val popupMenu = PopupMenu(ContextThemeWrapper(itemView.context, R.style.popupMenuStyle), itemView.ivDelivery)
        popupMenu.menu.add(1, 1, 1, "State : ${it.currentState}")
        popupMenu.menu.add(1, 2, 2, "Name : ${it.name}")
        popupMenu.menu.add(1, 3, 3, "Phone : ${it.phone}")
        popupMenu.menu.add(1, 4, 4, "Alt Phone : ${it.altPhone}")
        popupMenu.show()
    }
}