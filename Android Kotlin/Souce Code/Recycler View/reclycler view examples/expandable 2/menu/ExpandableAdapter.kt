package com.webgrity.tisha.ui.activities.menuOrder.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.webgrity.tisha.R
import com.webgrity.tisha.app.SharedMethods
import com.webgrity.tisha.data.entities.MenuCategory
import com.webgrity.tisha.data.entities.MenuItems
import com.webgrity.tisha.data.models.Menu
import com.webgrity.tisha.data.socketmodels.OrderData
import com.webgrity.tisha.ui.activities.menuOrder.MenuOrderViewModel
import util.Coroutines
import util.addReadMore

class ExpandableAdapter(private val menuList: ArrayList<Menu>, private val viewModel: MenuOrderViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var lastOpenedMenu: Menu? = null
    private var lastOpenedMenuList = ArrayList<Menu>()


    override fun getItemCount() = menuList.size

    override fun getItemViewType(position: Int): Int {
        return menuList[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MenuType.MENU_ITEM -> MenuItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_menu_item, parent, false))
            else -> MenuCategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_menu_category_center, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {

            MenuType.MENU_ITEM -> {
                val menuItemViewHolder = holder as MenuItemViewHolder
                menuItemViewHolder.menuItems = menuList[position].menuItems!!
                menuItemViewHolder.bind()
            }
            else -> {
                val menuCategoryViewHolder = holder as MenuCategoryViewHolder
                menuCategoryViewHolder.menu = menuList[position]
                menuCategoryViewHolder.menuCategory = menuList[position].menuCategory!!
                menuCategoryViewHolder.bind()
            }
        }
    }

    inner class MenuCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var menu: Menu
        lateinit var menuCategory: MenuCategory
        private val newChildList = ArrayList<Menu>()

        //private var startPosition = 0
        //var count = 0

        private val title: TextView = itemView.findViewById(R.id.title)
        private val ivExpand: ImageView = itemView.findViewById(R.id.iv_expand)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

        init {
            itemView.setOnClickListener {
                menu.isExpanded = !menu.isExpanded
                updateViewState(menu.isExpanded)

                if (menu.isExpanded) {
                    expandMenu()
                } else {
                    collapseMenu(menu, "self")
                }
            }
        }

        private fun expandMenu() {
            if (!progressBar.isVisible) progressBar.visibility = View.VISIBLE
            Coroutines.io {
                if (newChildList.isEmpty()) {
                    viewModel.getItemsByCatId(menuCategory.id).forEach {
                        Menu().apply {
                            this.type = MenuType.MENU_ITEM
                            this.menuItems = it
                            newChildList.add(this)
                        }
                    }
                }
                Coroutines.main {
                    if (progressBar.isVisible) progressBar.visibility = View.INVISIBLE
                    showMenuItems()
                }
            }
        }

        private fun showMenuItems() {
            menuList.addAll(menuList.indexOf(menu) + 1, newChildList)
            notifyItemRangeInserted(menuList.indexOf(menu) + 1, newChildList.size)

            collapseMenu(menu, "other")
            lastOpenedMenuList.clear()
            lastOpenedMenuList.addAll(newChildList)
            lastOpenedMenu = menu
        }

        private fun collapseMenu(clickedMenu: Menu, type: String) {
            if (lastOpenedMenuList.size == 0) return

            if (type == "self") {
                menuList.removeAll(lastOpenedMenuList)
                notifyItemRangeRemoved(menuList.indexOf(clickedMenu) + 1, lastOpenedMenuList.size)
                lastOpenedMenu = null

            } else if (type == "other") {
                lastOpenedMenu?.let {
                    it.isExpanded = false
                    menuList.removeAll(lastOpenedMenuList)
                    notifyItemRangeRemoved(menuList.indexOf(it) + 1, lastOpenedMenuList.size)
                    notifyItemChanged(menuList.indexOf(it))
                }
            }
        }


        fun bind() {
            title.text = menuCategory.name
        }

        private fun updateViewState(isExpanded: Boolean) {
            if (isExpanded) {
                ivExpand.rotation = 90f
            } else {
                ivExpand.rotation = 0f
            }
        }
    }

    inner class MenuItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var menuItems: MenuItems
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        private val ivImage: ImageView = itemView.findViewById(R.id.iv_image)
        private val btnAdd: Button = itemView.findViewById(R.id.btn_add)
        private val llChangeQuantity: LinearLayout = itemView.findViewById(R.id.ll_change_quantity)
        private val tvMinus: TextView = itemView.findViewById(R.id.tv_minus)
        private val tvPlus: TextView = itemView.findViewById(R.id.tv_plus)
        private val tvQuantity: TextView = itemView.findViewById(R.id.tv_quantity)
        private val tvCustomisable: TextView = itemView.findViewById(R.id.tv_customisable)

        init {
            //initClickListener()
        }

        fun bind() {
            menuItems?.let { menuItems ->
                tvName.text = menuItems.name
                tvDescription.text = menuItems.description
                tvDescription.addReadMore(110)
                SharedMethods.setImageFromByteArray(ivImage, menuItems.image)
                var isCustomisable = false
                if (viewModel.checkForcedModifier(menuItems.id)) {
                    tvCustomisable.visibility = View.VISIBLE
                    isCustomisable = true
                } else tvCustomisable.visibility = View.GONE

                btnAdd.setOnClickListener {
                    createOrder(menuItems, isCustomisable)
                }
            }
        }

        private fun createOrder(menuItems: MenuItems, isCustomisable: Boolean) {
            OrderData().apply {
                this.menuCategoryId = menuItems.menuCategoryId
                this.menuItemId = menuItems.id
                this.name = menuItems.name
                this.isCustomisable = isCustomisable
                this.actualPrice = menuItems.price
                this.price = menuItems.price
                this.quantity = 1

                if (isCustomisable)
                    viewModel.openAttributeDialogLD.value = this
                else
                    viewModel.createOrUpdateOrder(this)
            }
        }

        /*private fun initClickListener() {

            tvMinus.setOnClickListener {
                var quantity = tvQuantity.text.toString().toInt()
                if (quantity > 0) quantity -= 1
                if (quantity == 0) {
                    btnAdd.toggleVisibility()
                    //llChangeQuantity.toggleVisibility()

                    //viewModel.removeFromOrderList()
                }
                tvQuantity.text = quantity.toString()
            }

            tvPlus.setOnClickListener {
                increaseQuantityByOne()
            }
        }*/

        private fun increaseQuantityByOne() {
            var quantity = tvQuantity.text.toString().toInt()
            quantity += 1
            tvQuantity.text = quantity.toString()
        }
    }
}
