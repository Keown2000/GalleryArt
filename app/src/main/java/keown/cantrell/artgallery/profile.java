package keown.cantrell.artgallery;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class profile extends AppCompatActivity {
    TextView tvname,tvusername,tvemail,tvphone,tvbio,tvedit;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    personalinfo personalinfo;
    ImageView imageView;

    private int STORAGE_PERMISSION_CODE = 1;
    public static  String pname="";
    public static  String pusername="";
    public static  String pphone="";
    public static  String pemail="";
    public static  String pbio="";
    public static  String pimage="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        tvname=findViewById(R.id.viewname);
        tvusername=findViewById(R.id.viewusername);
        tvemail=findViewById(R.id.viewemail);
        tvphone=findViewById(R.id.viewphone);
        tvbio=findViewById(R.id.viewbio);
        tvedit=findViewById(R.id.edit);
        imageView=findViewById(R.id.imageprofile);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("data");
///functions

        tvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), edit.class));
                //getActivity().finish();
            }
        });
        ////fetching fro mfirebse
        personalinfo = new personalinfo();
        databaseReference.addValueEventListener(new ValueEventListener() {


            @RequiresApi(api = Build.VERSION_CODES.O_MR1)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    personalinfo detail = dataSnapshot.getValue(personalinfo.class);

                    String name = detail.getName();
                    String username = detail.getUsername();
                    String email = detail.getEmail();
                    String phone = detail.getPhone();
                    String bio = detail.getBio();
                    String imageurl = detail.getImageUrl();

                    if (name!=null) {
                        tvname.setText(name);
                    }
                    if (username!=null) {
                        tvusername.setText(username);
                    }
                    if (email!=null) {
                        tvemail.setText(email);
                    }
                    if (phone!=null) {
                        tvphone.setText(phone);
                    }
                    if (bio!=null) {
                        tvbio.setText(bio);
                    }

                    pname=name;
                    pusername=username;
                    pphone=phone;
                    pemail=email;
                    pbio=bio;
                    pimage=imageurl;
//
                    if (imageurl!=null && !imageurl.isEmpty()) {
                        Picasso.get().load(imageurl).into(imageView);
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // [START_EXCLUDE]
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_navignation);

        // home selected as default
        bottomNavigationView.setSelectedItemId(R.id.fourth);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.second:
                        startActivity(new Intent(getApplicationContext(),ImagesActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.first:
                        startActivity(new Intent(getApplicationContext(), keown.cantrell.artgallery.PaintingListActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.third:
                        startActivity(new Intent(getApplicationContext(),ContactMe.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;


                }

                return false;
            }
        });
    }




}
