package com.motycka.edu.menu

import com.motycka.edu.menu.models.MenuItem

class MenuRepository {
    private val items = mutableMapOf<Int, MenuItem>()
    private var nextId = 1

    init {
        // Add some sample items
        addItem(
            MenuItem(
                id = 1,
                name = "Espresso",
                description = "Regular espresso shot",
                price = 2.50
            )
        )
        addItem(
            MenuItem(
                id = 2,
                name = "Double Espresso",
                description = "Double shot of espresso",
                price = 3.00
            )
        )
        addItem(
            MenuItem(
                id = 3,
                name = "Cappuccino",
                description = "Espresso with steamed milk and foam",
                price = 3.50
            )
        )
        addItem(
            MenuItem(
                id = 4,
                name = "Latte",
                description = "Espresso with steamed milk",
                price = 3.75
            )
        )
        addItem(
            MenuItem(
                id = 5,
                name = "Mocha",
                description = "Espresso with steamed milk and chocolate",
                price = 4.00
            )
        )
        addItem(
            MenuItem(
                id = 6,
                name = "Americano",
                description = "Espresso with hot water",
                price = 2.75
            )
        )
        addItem(
            MenuItem(
                id = 7,
                name = "Macchiato",
                description = "Espresso with a dollop of foam",
                price = 2.80
            )
        )
        addItem(
            MenuItem(
                id = 8,
                name = "Flat White",
                description = "Espresso with steamed milk and microfoam",
                price = 3.60
            )
        )
    }

    fun getAllItems(): List<MenuItem> {
        return items.values.toList()
    }

    fun getItem(id: Int): MenuItem? {
        return items[id]
    }

    fun addItem(menuItem: MenuItem): MenuItem {
        val newItem = if (menuItem.id == 0) {
            menuItem.copy(id = nextId++)
        } else {
            nextId = maxOf(nextId, menuItem.id + 1)
            menuItem
        }
        items[newItem.id] = newItem
        return newItem
    }

    fun updateItem(id: Int, menuItem: MenuItem): MenuItem? {
        if (!items.containsKey(id)) return null
        val updatedItem = menuItem.copy(id = id)
        items[id] = updatedItem
        return updatedItem
    }

    fun deleteItem(id: Int): Boolean {
        return items.remove(id) != null
    }
}
