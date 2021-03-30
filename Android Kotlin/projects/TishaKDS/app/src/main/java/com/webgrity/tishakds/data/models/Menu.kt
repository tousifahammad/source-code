package com.webgrity.tishakds.data.models

import com.webgrity.tishakds.data.entities.MenuCategory
import com.webgrity.tishakds.data.entities.MenuItems

class Menu {
    var type: Int = 0   // 1 = MENU_CATEGORY / 2 = MENU_ITEM
    var menuCategory: MenuCategory? = null
    var isExpanded: Boolean = false
    var menuItems: MenuItems? = null
}