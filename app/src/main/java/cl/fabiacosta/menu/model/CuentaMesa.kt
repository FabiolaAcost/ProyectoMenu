package cl.fabiacosta.menu.model

class CuentaMesa(private val mesa: Int, var aceptaPropina: Boolean = true) {
    private val items: MutableList<ItemMesa> = mutableListOf()

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        items.add(ItemMesa(itemMenu, cantidad))
    }

    fun agregarItem(itemMesa: ItemMesa) {
        items.add(itemMesa)
    }

    fun calcularTotalSinPropina(): Int {
        return items.sumBy { it.calcularSubtotal() }
    }

    fun calcularPropina(): Int {
        return if (aceptaPropina) (calcularTotalSinPropina() * 0.1).toInt() else 0
    }

    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }
}