package com.webgrity.tisha.data.models

import com.webgrity.tisha.data.entities.MenuCategory
import com.webgrity.tisha.data.entities.MenuItems

class Menu {
    var type: Int = 0   // 1 = MENU_CATEGORY / 2 = MENU_ITEM
    var menuCategory: MenuCategory? = null
    var isExpanded: Boolean = false
    var menuItems: MenuItems? = null
}