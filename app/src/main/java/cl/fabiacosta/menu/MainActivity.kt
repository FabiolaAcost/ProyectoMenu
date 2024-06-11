package cl.fabiacosta.menu

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cl.fabiacosta.menu.model.CuentaMesa
import cl.fabiacosta.menu.model.ItemMenu
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var etPedido1: EditText
    private lateinit var etPedido2: EditText
    private lateinit var tvPrecio1: TextView
    private lateinit var tvPrecio2: TextView
    private lateinit var tvTotalComida: TextView
    private lateinit var tvMontoPropina: TextView
    private lateinit var tvMontoTotal: TextView
    private lateinit var activarPropina: Switch

    private lateinit var cuentaMesa: CuentaMesa
    private lateinit var pastelDeChoclo: ItemMenu
    private lateinit var cazuela: ItemMenu

    private lateinit var numberFormat: NumberFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etPedido1 = findViewById(R.id.etPedido1)
        etPedido2 = findViewById(R.id.etPedido2)
        tvPrecio1 = findViewById(R.id.tvPrecio1)
        tvPrecio2 = findViewById(R.id.tvPrecio2)
        tvTotalComida = findViewById(R.id.tvTotalComida)
        tvMontoPropina = findViewById(R.id.tvMontoPropina)
        tvMontoTotal = findViewById(R.id.tvMontoTotal)
        activarPropina = findViewById(R.id.activarPropina)

        pastelDeChoclo = ItemMenu("Pastel de Choclo", "12000")
        cazuela = ItemMenu("Cazuela", "10000")

        cuentaMesa = CuentaMesa(1)

        etPedido1.addTextChangedListener(textWatcher)
        etPedido2.addTextChangedListener(textWatcher)
        activarPropina.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina = isChecked
            calcularTotales()
        }

        numberFormat = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            calcularTotales()
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    private fun calcularTotales() {
        val cantidad1 = etPedido1.text.toString().toIntOrNull() ?: 0
        val cantidad2 = etPedido2.text.toString().toIntOrNull() ?: 0

        cuentaMesa = CuentaMesa(1, activarPropina.isChecked)
        if (cantidad1 > 0) {
            cuentaMesa.agregarItem(pastelDeChoclo, cantidad1)
        }
        if (cantidad2 > 0) {
            cuentaMesa.agregarItem(cazuela, cantidad2)
        }

        val totalComida = cuentaMesa.calcularTotalSinPropina()
        val propina = cuentaMesa.calcularPropina()
        val totalConPropina = cuentaMesa.calcularTotalConPropina()

        val totalComidaFormatted = numberFormat.format(totalComida)
        val propinaFormatted = numberFormat.format(propina)
        val totalConPropinaFormatted = numberFormat.format(totalConPropina)

        tvPrecio1.text = if (cantidad1 > 0) numberFormat.format(cantidad1 * pastelDeChoclo.precio.toInt()) else numberFormat.format(0)
        tvPrecio2.text = if (cantidad2 > 0) numberFormat.format(cantidad2 * cazuela.precio.toInt()) else numberFormat.format(0)
        tvTotalComida.text = totalComidaFormatted
        tvMontoPropina.text = propinaFormatted
        tvMontoTotal.text = totalConPropinaFormatted
    }
}