package com.example.travelapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    private val ERO = "ERO"
    private val AED = "AED"
    private val GBP = "GBP"
    private val Shikel = "Shikel"
    private val USD = "USD"

    val values = mapOf(
        ERO to 1.07,
        AED to 0.27,
        GBP to 1.20,
        Shikel to 3.50,
        USD to 1.0
    )

    lateinit var convertbtn: Button
    lateinit var FromDropDownMenu: AutoCompleteTextView
    lateinit var todropdownmenu: AutoCompleteTextView
    lateinit var amountEt: TextInputEditText
    lateinit var ResultEt: TextInputEditText
    lateinit var toolBar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        intilizeView()
        AboutDropDownMenu()

        toolBar.inflateMenu(R.menu.options_menu)
        toolBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.ShareAction) {
                val message = "${amountEt.text.toString()} ${FromDropDownMenu.text.toString()} = " +
                        "${ResultEt.text.toString()} ${todropdownmenu.text.toString()}"

                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type= "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, message)

                if (shareIntent.resolveActivity(packageManager) != null){
                    startActivity(shareIntent)
                }else
                {
                    Toast.makeText(this, "no App(Activity) Found to handle this intent",Toast.LENGTH_SHORT).show()
                }
            }
            true
        }


        amountEt.addTextChangedListener {
            CalculateResult()
        }

        FromDropDownMenu.setOnItemClickListener { adapterView, view, i, l ->
            CalculateResult()
        }

        todropdownmenu.setOnItemClickListener { adapterView, view, i, l ->
            CalculateResult()
        }
    }


    private fun CalculateResult() {
        if (amountEt.text.toString().isNotEmpty()) {

            val amountt = amountEt.text.toString().toDouble()
            val toValue = values[todropdownmenu.text.toString()]
            val fromValue = values[FromDropDownMenu.text.toString()]
            val result = amountt.times(toValue!!.div(fromValue!!))
            val formatResult = String.format("%.2f", result)
            ResultEt.setText(formatResult)
        } else {
            val SnakBar = Snackbar.make(amountEt, "Amount Field Required", Snackbar.LENGTH_SHORT)
            SnakBar.show()

            SnakBar.setAction("OK") {
                Toast.makeText(this, "Field Required", Toast.LENGTH_SHORT).show()
                // amountEt.setError("This Field Required")
            }
        }
    }

    private fun AboutDropDownMenu() {

        val listOfCountry = listOf(ERO, AED, GBP, Shikel, USD)
        val adapter = ArrayAdapter(this, R.layout.drop_down_menu_textview, listOfCountry)
        todropdownmenu.setAdapter(adapter)
        FromDropDownMenu.setAdapter(adapter)
    }

    private fun intilizeView() {
        amountEt = findViewById(R.id.Amount)
        ResultEt = findViewById(R.id.Result)
        todropdownmenu = findViewById(R.id.autoCompleteTextView3)
        FromDropDownMenu = findViewById(R.id.autoCompleteTextView2)
        convertbtn = findViewById(R.id.Convert_button)
        toolBar= findViewById(R.id.ToolBar)
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val menuInflater: MenuInflater = getMenuInflater()
//        menuInflater.inflate(R.menu.options_menu, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        when (item.itemId) {
//            R.id.ShareAction -> {
//                Toast.makeText(this, "It Works", Toast.LENGTH_SHORT).show()
//                }
//                R.id.SettingAction -> {
//                    Toast.makeText(this, "It Works", Toast.LENGTH_SHORT).show()
//                }
//                R.id.ContactAction -> {
//                    Toast.makeText(this, "It Works", Toast.LENGTH_SHORT).show()
//                }
//            }
//            return super.onOptionsItemSelected(item)
//        }


    }