package com.example.myapplication
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {


    private lateinit var btnviewcategory: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category)

       val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

     btnviewcategory =  findViewById(R.id.button3)
        btnviewcategory.setOnClickListener {
            val intent = Intent(this, Category1::class.java)
            startActivity(intent)
        }

    }
}