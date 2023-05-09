package com.example.holotravels.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.holotravels.R
import com.example.holotravels.models.UserModel
import com.google.firebase.database.FirebaseDatabase
import java.util.zip.Inflater

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var tvUserId: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvUserPNum: TextView
    private lateinit var tvUserEmail: TextView
    private lateinit var tvUserCountry: TextView

    private lateinit var btnUpdate: TextView
    private lateinit var btnDelete: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        initView()
        setValuesToView()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("userId").toString(),
                intent.getStringExtra("userName").toString()

            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("userId").toString()
            )
        }


    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Users").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "User data deleted!", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {error ->
            Toast.makeText(this, "Deleting error ${error.message}", Toast.LENGTH_LONG).show()

        }
    }

    private fun initView(){
        tvUserId = findViewById(R.id.textView4)
        tvUserName = findViewById(R.id.tvUserName)
        tvUserPNum = findViewById(R.id.tvUserPNum)
        tvUserEmail = findViewById(R.id.textView2)
        tvUserCountry = findViewById(R.id.tvUserCountry)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

    }

    private fun setValuesToView() {
        tvUserId.text = intent.getStringExtra("userId")
        tvUserName.text = intent.getStringExtra("userName")
        tvUserPNum.text = intent.getStringExtra("userPNum")
        tvUserEmail.text = intent.getStringExtra("userEmail")
        tvUserCountry.text = intent.getStringExtra("userCountry")

    }

    private fun openUpdateDialog(
        userId: String,
        userName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etUserName = mDialogView.findViewById<EditText>(R.id.etUserName)
        val etUserPNum = mDialogView.findViewById<EditText>(R.id.etUserPNum)
        val etUserEmail = mDialogView.findViewById<EditText>(R.id.etUserEmail)
        val etUserCountry = mDialogView.findViewById<EditText>(R.id.etUserCountry)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etUserName.setText(intent.getStringExtra("userName").toString())
        etUserPNum.setText(intent.getStringExtra("userPNum").toString())
        etUserEmail.setText(intent.getStringExtra("userEmail").toString())
        etUserCountry.setText(intent.getStringExtra("userCountry").toString())

        mDialog.setTitle("updating $userName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateUserData(
                userId,
                etUserName.text.toString(),
                etUserPNum.text.toString(),
                etUserEmail.text.toString(),
                etUserCountry.text.toString()
            )

            Toast.makeText(applicationContext, "User data updated", Toast.LENGTH_LONG).show()

            //we are setting updated values to textView
            tvUserName.text = etUserName.text.toString()
            tvUserPNum.text = etUserPNum.text.toString()
            tvUserEmail.text = etUserEmail.text.toString()
            tvUserCountry.text = etUserCountry.text.toString()

            alertDialog.dismiss()

        }

    }
    private fun updateUserData(
        id: String,
        name: String,
        pNum: String,
        email: String,
        country: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Users").child(id)
        val userInfo = UserModel(id, name, pNum, email, country)
        dbRef.setValue(userInfo)
    }

}