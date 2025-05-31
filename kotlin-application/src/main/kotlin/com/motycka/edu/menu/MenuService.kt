package com.motycka.edu.menu

import com.motycka.edu.menu.models.MenuItem

class MenuService(
    private val menuRepository: MenuRepository
) {

    fun getMenuItems(): List<MenuItem> {
        return menuRepository.getAllItems()
    }

    fun getMenuItemById(id: Int): MenuItem? {
        return menuRepository.getItem(id)
    }

    fun addMenuItem(menuItem: MenuItem) {
        TODO()
    }

    fun deleteMenuItem(id: Int) {
        TODO()
    }

}
