package com.example.holotravels.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.holotravels.models.UserModel
import com.example.holotravels.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etUserName : EditText
    private lateinit var etUserPNum : EditText
    private lateinit var etUserEmail : EditText
    private lateinit var etUserCountry : EditText
    private lateinit var btnSaveData : Button

    private lateinit var dbRef : DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etUserName = findViewById(R.id.etUserName)
        etUserPNum = findViewById(R.id.etUserPNum)
        etUserEmail = findViewById(R.id.etUserEmail)
        etUserCountry = findViewById(R.id.etUserCountry)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        btnSaveData.setOnClickListener {
            saveUserData()
        }
    }

    private fun saveUserData() {

        //getting values
        val userName = etUserName.text.toString()
        val userPNum = etUserPNum.text.toString()
        val userEmail = etUserEmail.text.toString()
        val userCountry = etUserCountry.text.toString()

        if (userName.isEmpty()) {
            etUserName.error = "Please enter Name"
        }

        if (userPNum.isEmpty()) {
            etUserPNum.error = "Please enter PhoneNumber"
        }

        if (userEmail.isEmpty()) {
            etUserEmail.error = "Please enter Email"
        }

        if (userCountry.isEmpty()) {
            etUserCountry.error = "Please enter Country"
        }

        //create unique id
        val userId = dbRef.push().key!!

        val user = UserModel(userId, userName,userPNum, userEmail, userCountry)

        dbRef.child(userId).setValue(user)
            .addOnCompleteListener{
                Toast.makeText(this, "Data Inserted Successfully!", Toast.LENGTH_LONG).show()

                etUserName.text.clear()
                etUserPNum.text.clear()
                etUserEmail.text.clear()
                etUserCountry.text.clear()

            }.addOnFailureListener{err ->
                Toast.makeText(this, "Error!! ${err.message}", Toast.LENGTH_LONG).show()

            }

    }
}