package com.example.holotravels.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.holotravels.models.GuideModel
import com.example.holotravels.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etGuideName : EditText
    private lateinit var etGuidePNum : EditText
    private lateinit var etGuideLan : EditText
    private lateinit var etGuideHours : EditText
    private lateinit var btnSaveData : Button

    private lateinit var dbRef : DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etGuideName = findViewById(R.id.etGuideName)
        etGuidePNum = findViewById(R.id.etGuidePNum)
        etGuideLan = findViewById(R.id.etGuideLan)
        etGuideHours = findViewById(R.id.etGuideHours)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Guides")

        btnSaveData.setOnClickListener {
            saveGuideData()
        }
    }

    private fun saveGuideData() {

        //getting values
        val guideName = etGuideName.text.toString()
        val guidePNum = etGuidePNum.text.toString()
        val guideLan = etGuideLan.text.toString()
        val guideHours = etGuideHours.text.toString()

        if (guideName.isEmpty()) {
            etGuideName.error = "Please enter Name"
        }

        if (guidePNum.isEmpty()) {
            etGuidePNum.error = "Please enter PhoneNumber"
        }

        if (guideLan.isEmpty()) {
            etGuideLan.error = "Please enter Languages"
        }

        if (guideHours.isEmpty()) {
            etGuideHours.error = "Please enter Rate"
        }

        //create unique id
        val guideId = dbRef.push().key!!

        val guide = GuideModel(guideId, guideName, guidePNum, guideLan, guideHours)

        dbRef.child(guideId).setValue(guide)
            .addOnCompleteListener{
                Toast.makeText(this, "Data inserted Successfully!", Toast.LENGTH_LONG).show()

                etGuideName.text.clear()
                etGuidePNum.text.clear()
                etGuideLan.text.clear()
                etGuideHours.text.clear()

            }.addOnFailureListener{err ->
                Toast.makeText(this, "Error!! ${err.message}", Toast.LENGTH_LONG).show()

            }

    }
}