package com.example.myapplication
import android.os.Bundle

import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.res.Configuration
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class CarDetails : AppCompatActivity() {

    private lateinit var backbutton: Button


    private lateinit var id:TextView
    private lateinit var textView8: TextView
    private lateinit var textView12: TextView
    private lateinit var textView14: TextView
    private lateinit var textView22: TextView
    private lateinit var textView16: TextView
    private lateinit var textView18: TextView
    private lateinit var textView20: TextView

    private lateinit var btnupdate: Button
    private lateinit var btndelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_details)

        backbutton = findViewById(R.id.button10)



        backbutton.setOnClickListener {
            val intent = Intent(this, Category1::class.java)
            startActivity(intent)
        }


        initView()
        setValuesToViews()

       btnupdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("carId").toString(),
                intent.getStringExtra("carModel").toString()

            )
        }
        btndelete.setOnClickListener{
            deleteRecord(
              intent.getStringExtra("carId").toString()
            )
        }

    }
    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Cars").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Car Data deleted",Toast.LENGTH_LONG).show()
            val intent = Intent(this, Category1::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error ->
            Toast.makeText(this, "Error Deleting ${error.message}",Toast.LENGTH_LONG).show()
        }
    }

   private fun initView(){

       id = findViewById(R.id.textView4)
       textView8 = findViewById(R.id.textView8)
       textView12 = findViewById(R.id.textView12)
       textView14 = findViewById(R.id.textView14)
       textView22 = findViewById(R.id.textView22)
       textView16 = findViewById(R.id.textView16)
       textView18 = findViewById(R.id.textView18)
       textView20 = findViewById(R.id.textView20)

       btndelete = findViewById(R.id.button7)
       btnupdate = findViewById(R.id.button8)


    }

    private fun setValuesToViews() {
        id.text = intent.getStringExtra("carId")
        textView8.text = intent.getStringExtra("carModel")
        textView12.text = intent.getStringExtra("carNum")
        textView14.text = intent.getStringExtra("carCap")
        textView22.text = intent.getStringExtra("carPrice")
        textView16.text = intent.getStringExtra("PName")
        textView18.text = intent.getStringExtra("PPhone")
        textView20.text = intent.getStringExtra("PEmail")

    }
    private fun openUpdateDialog(
        carId:String,
        carModel:String
    ){
            val mDialog = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val mDialogView = inflater.inflate(R.layout.update,null)
            mDialog.setView(mDialogView)

        val etModel = mDialogView.findViewById<EditText>(R.id.editTextTextPersonName6)
        val etNum = mDialogView.findViewById<EditText>(R.id.editTextTextPersonName7)
        val etCap = mDialogView.findViewById<EditText>(R.id.editTextTextPersonName8)
        val etPrice = mDialogView.findViewById<EditText>(R.id.editTextTextPersonName9)
        val etName = mDialogView.findViewById<EditText>(R.id.editTextTextPersonName10)
        val etPhone = mDialogView.findViewById<EditText>(R.id.editTextTextPersonName11)
        val etEmail = mDialogView.findViewById<EditText>(R.id.editTextTextPersonName12)
        val etButton = mDialogView.findViewById<Button>(R.id.button50)

        etModel.setText(intent.getStringExtra("carModel").toString())
        etNum.setText(intent.getStringExtra("carNum").toString())
        etCap.setText(intent.getStringExtra("carCap").toString())
        etPrice.setText(intent.getStringExtra("carPrice").toString())
        etName.setText(intent.getStringExtra("PName").toString())
        etPhone.setText(intent.getStringExtra("PPhone").toString())
        etEmail.setText(intent.getStringExtra("PEmail").toString())

        //mDialog.setTitle("Updating $carModel Record")
        val alertDialog = mDialog.create()
        alertDialog.show()

        etButton.setOnClickListener{
            updateCardata(
                carId,
                etModel.text.toString(),
                etNum.text.toString(),
                etCap.text.toString(),
                etPrice.text.toString(),
                etName.text.toString(),
                etPhone.text.toString(),
                etEmail.text.toString()
            )
            Toast.makeText(applicationContext,"Car Data Updated",Toast.LENGTH_LONG).show()

            textView8.text = etModel.text.toString()
            textView12.text = etNum.text.toString()
            textView14.text = etCap.text.toString()
            textView22.text = etPrice.text.toString()
            textView16.text = etName.text.toString()
            textView18.text = etPhone.text.toString()
            textView20.text = etEmail.text.toString()

            alertDialog.dismiss()
        }

    }
    private fun updateCardata(
        id: String,
        model: String,
        number: String,
        capacity: String,
        price: String,
        name: String,
        phone: String,
        email: String


    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Cars").child(id)
        val carinfo = CarModel(id,model,number,capacity,price,name,phone,email)
        dbRef.setValue(carinfo)
    }
}

