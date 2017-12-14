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
import ru.ilyayudov.mas.Services.AnagramSolver
import android.content.res.XmlResourceParser


class MainActivity : AppCompatActivity() {

    private val anagramSolver = AnagramSolver()
    private lateinit var toponymData : List<Toponym>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val parser = resources.getXml(R.xml.as_addrobj_msk_only_short)
        toponymData = parse(parser)

        searchBtnView.setOnClickListener { view ->
            val anagram = anagramView.text.toString()
            val full = fullTextSearchRadioButtonView.isChecked
            val results = anagramSolver.Solve(toponymData, anagram, full)

            searchResultListView.adapter = ArrayAdapter<Toponym>(this, R.layout.support_simple_spinner_dropdown_item, results)
            searchResultListView.setOnItemClickListener( { parent, view, position, id ->
                val toponym = parent.getItemAtPosition(position) as Toponym
                //val gmmIntentUri = Uri.parse(toponym.uri)
                //val intent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                //startActivity(intent)
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

    fun parse(parser: XmlResourceParser) : List<Toponym> {
        val result = mutableListOf<Toponym>()

        var eventType = parser.eventType
        parser.next()

        while (eventType != XmlResourceParser.END_DOCUMENT) {
            eventType = parser.eventType
            if(eventType == XmlResourceParser.START_TAG && parser.name == "OBJECT") {
                val name = parser.getAttributeValue(null,"NAME")
                val type = parser.getAttributeValue(null,"TYPE")
                if(name != null) {
                    val toponym = Toponym(name, type)
                    result.add(toponym)
                }
            }
            parser.next()
        }

        return result
    }
}
