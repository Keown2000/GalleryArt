package keown.cantrell.artgallery

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import keown.cantrell.artgallery.helpers.readImage
import keown.cantrell.artgallery.helpers.readImageFromPath
import keown.cantrell.artgallery.helpers.showImagePicker
import keown.cantrell.artgallery.main.MainApp
import keown.cantrell.artgallery.models.PaintingModel
import keown.cantrell.grade.ProgressFragment




import org.jetbrains.anko.AnkoLogger

import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_painting_list.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.card_painting.*
import kotlinx.android.synthetic.main.card_painting.Paint
import kotlinx.android.synthetic.main.card_painting.Other
import kotlinx.android.synthetic.main.card_painting.description
import kotlinx.android.synthetic.main.card_painting.paintingTitle

import org.jetbrains.anko.toast

class PaintingActivity : AppCompatActivity(), AnkoLogger {
    lateinit var homeFragment: HomeFragment
    lateinit var favoriteFragment:FavoriteFragment
    lateinit var progressFragment: ProgressFragment
    var painting = PaintingModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)  //inflates the layout
        app = application as MainApp
        var edit = false

        btnAdd.setOnClickListener() {
            painting.title = paintingTitle.text.toString()
            painting.description = description.text.toString()
            painting.Other = Other.text.toString()
            painting.Paint = Paint.text.toString()
            if (painting.title.isEmpty()) {
                toast("Please enter the first field")
            } else {
                if (edit) {
                    app.paintings.update(painting.copy())  //if first field is filled, it will allow paintings to update
                } else {
                    app.paintings.create(painting.copy())
                }
            }

            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        //creating a bottom navigation to transition through fragments using a fragment manager and using the item IDs
        val bottomNavigation : BottomNavigationView = findViewById(R.id.btm_nav)
        bottomNavigation.setOnNavigationItemSelectedListener{ item ->

            when (item.itemId) {


                R.id.home -> {
                    homeFragment = HomeFragment()
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_layout, homeFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()

                }
                R.id.favorite -> {
                    favoriteFragment = FavoriteFragment()
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_layout, favoriteFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()

                }
                R.id.progress -> {
                    progressFragment = ProgressFragment()
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_layout, progressFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()

                }
            }
            true
        }


        if (intent.hasExtra("painting_edit")) {
            edit = true
            painting = intent.extras?.getParcelable<PaintingModel>("painting_edit")!!
            paintingTitle.setText(painting.title)
            description.setText(painting.description)
            Other.setText(painting.Other)
            Paint.setText(painting.Paint)
            paintingImage.setImageBitmap(readImageFromPath(this, painting.image))
            if (painting.image != null) {
                chooseImage.setText(R.string.change_painting_image)
            }
            btnAdd.setText(R.string.save_painting)
        }
        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)   //if image button presses.. it will open the image picker to open
        }
    }


    //function to allow the exit and delete button to be viewed through the navigation bar using an inflater
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_painting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //allows image to be viewed in the list activity and allows it to be saved in the main activity
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    painting.image = data.getData().toString()
                    paintingImage.setImageBitmap(readImage(this, resultCode, data))
                }
            }
        }
    }

    //function to allow a delete or edit event to happen through clickable item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                app.paintings.delete(painting)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}



