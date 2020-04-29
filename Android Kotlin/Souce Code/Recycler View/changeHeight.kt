

private fun changeRecyclerViewHeight() {
        val layoutParams: ViewGroup.LayoutParams = rootView.rv_menu_items_list.layoutParams
        val set = ConstraintSet()

        if (currentMenuItemList.size < 5) {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            rootView.rv_menu_items_list.layoutParams = layoutParams

            set.clone(rootView.parent_constraint)
            set.clear(rootView.rv_menu_items_list.id, ConstraintSet.BOTTOM)
            set.applyTo(rootView.parent_constraint)

        } else {
            layoutParams.height = 0
            rootView.rv_menu_items_list!!.layoutParams = layoutParams

            set.clone(rootView.parent_constraint)
            set.connect(rootView.rv_menu_items_list.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            set.applyTo(rootView.parent_constraint)

            val margins = (layoutParams as ConstraintLayout.LayoutParams).apply {
                leftMargin = 0
                rightMargin = 0
                topMargin = 24
                bottomMargin = 15
            }
            rootView.rv_menu_items_list!!.layoutParams = margins
        }
    }