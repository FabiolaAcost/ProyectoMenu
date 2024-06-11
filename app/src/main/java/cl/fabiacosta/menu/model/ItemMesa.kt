package cl.fabiacosta.menu.model

class ItemMesa(val itemMenu: ItemMenu, val cantidad: Int) {
    fun calcularSubtotal(): Int{
        return itemMenu.precio.toInt() * cantidad
    }
}