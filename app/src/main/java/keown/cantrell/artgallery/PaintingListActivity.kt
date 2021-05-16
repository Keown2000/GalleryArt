package keown.cantrell.artgallery


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import keown.cantrell.artgallery.main.MainApp
import keown.cantrell.artgallery.models.PaintingModel
import kotlinx.android.synthetic.main.activity_painting_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult

class PaintingListActivity : AppCompatActivity(), PaintingListener {
    private lateinit var editTextSearch: EditText //calling the variable for the search function
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_painting_list)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = PaintingAdapter(app.paintings.findAll(), this)

        //
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navignation)

        // home selected as default

        // home selected as default
        bottomNavigationView.selectedItemId = R.id.first

        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.second -> {
                    startActivity(Intent(applicationContext, ImagesActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.third -> {
                    startActivity(Intent(applicationContext, ContactMe::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.fourth -> {
                    startActivity(Intent(applicationContext, profile::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })


        editTextSearch = findViewById(R.id.et_search)
        editTextSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                filterList(s.toString());

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

    }

    private fun filterList(filterItem: String) {

        var tempList:MutableList<PaintingModel> = ArrayList()
        for (m in app.paintings.findAll()){

            if(filterItem in m.title){

                tempList.add(m)

            }
        }
        (recyclerView.adapter as PaintingAdapter).updateList(tempList)
        (recyclerView.adapter as PaintingAdapter).notifyDataSetChanged()}


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.nav_upload -> startActivityForResult<PaintingActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPaintingClick(painting: PaintingModel) {
        startActivityForResult(intentFor<PaintingActivity>().putExtra("painting_edit", painting), 0)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}

