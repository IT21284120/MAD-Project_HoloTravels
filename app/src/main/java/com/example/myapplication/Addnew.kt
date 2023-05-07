package com.example.myapplication
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Addnew : AppCompatActivity() {

    private lateinit var model: EditText
    private lateinit var vnum: EditText
    private lateinit var scap: EditText
    private lateinit var price: EditText
    private lateinit var name: EditText
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var btnSaveData:Button



    private lateinit var dbRef: DatabaseReference
    private lateinit var backbutton: Button
    private lateinit var addbackbutton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addnew)

        model = findViewById(R.id.editTextTextPersonName)
        vnum = findViewById(R.id.editTextTextPersonName2)
        scap = findViewById(R.id.editTextTextPersonName3)
        price = findViewById(R.id.editTextTextPersonName4)
        name = findViewById(R.id.editTextTextPersonName5)
        phone = findViewById(R.id.editTextPhone)
        email = findViewById(R.id.editTextTextEmailAddress)
        btnSaveData = findViewById(R.id.button)

        dbRef = FirebaseDatabase.getInstance().getReference("Cars")
        btnSaveData.setOnClickListener {
            saveCarData()
        }

        backbutton = findViewById(R.id.button2)


        backbutton.setOnClickListener {
            val intent = Intent(this, Category1::class.java)
            startActivity(intent)
        }

    }
    private fun saveCarData() {
        val cmodel = model.text.toString()
        val vnumber = vnum.text.toString()
        val seatc = scap.text.toString()
        val cprice = price.text.toString()
        val pname = name.text.toString()
        val pphone = phone.text.toString()
        val pemail = email.text.toString()

        if (cmodel.isEmpty()) {
            model.error = "Please enter model"
        }
        if (vnumber.isEmpty()) {
            vnum.error = "Please enter vehicle number"
        }
        if (seatc.isEmpty()) {
            scap.error = "Please enter seat capacity"
        }
        if (cprice.isEmpty()) {
            price.error = "Please enter price"
        }
        if (pname.isEmpty()) {
            name.error = "Please enter name"
        }
        if (pphone.isEmpty()) {
            phone.error = "Please enter phone number"
        }
        if (pemail.isEmpty()) {
            email.error = "Please enter email address"
        }

        val carId = dbRef.push().key!!

        val car = CarModel(carId, cmodel, vnumber, seatc, cprice, pname, pphone, pemail)
        dbRef.child(carId).setValue(car)
            .addOnCompleteListener{
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                model.text.clear()
                vnum.text.clear()
                scap.text.clear()
                price.text.clear()
                name.text.clear()
                phone.text.clear()
                email.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}