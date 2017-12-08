package ru.ilyayudov.mas

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import ru.ilyayudov.mas.DataModels.Toponym
import ru.ilyayudov.mas.DataModels.ToponymType
import ru.ilyayudov.mas.Services.AnagramSolver
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.widget.AdapterView


class MainActivity : AppCompatActivity() {

    private val anagramSolver = AnagramSolver()
    private val toponymData = listOf<Toponym>(
            Toponym("Абрикосовая", ToponymType.Street, "qwertyuiop".toSortedSet(), "geo:37.7749,-122.4194"),
            Toponym("Апельсиновая", ToponymType.Street, "asdfghjkl".toSortedSet(), "geo:37.7749,-122.4194"),
            Toponym("Вишнёвый", ToponymType.Lane, "zxcvbnm".toSortedSet(), "geo:37.7749,-122.4194")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        typeSpinnerView.adapter = ArrayAdapter<ToponymType>(this, R.layout.support_simple_spinner_dropdown_item, ToponymType.values())

        searchBtnView.setOnClickListener { view ->
            val anagram = anagramView.text.toString()
            val type = typeSpinnerView.selectedItem as ToponymType
            val full = fullTextSearchRadioButtonView.isChecked
            val results = anagramSolver.Solve(toponymData, anagram, type, full)

            searchResultListView.adapter = ArrayAdapter<Toponym>(this, R.layout.support_simple_spinner_dropdown_item, results)
            searchResultListView.setOnItemClickListener( { parent, view, position, id ->
                val toponym = parent.getItemAtPosition(position) as Toponym
                val gmmIntentUri = Uri.parse(toponym.uri)
                val intent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                startActivity(intent)
            } )

            if(results.any()) {
                noResultsTextView.visibility = View.GONE
            } else {
                noResultsTextView.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
