package com.example.holotravels.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.holotravels.R
import com.example.holotravels.models.GuideModel
import com.google.firebase.database.FirebaseDatabase

class GuideDetailsActivity : AppCompatActivity() {

    private lateinit var tvGuideId: TextView
    private lateinit var tvGuideName: TextView
    private lateinit var tvGuidePNum: TextView
    private lateinit var tvGuideLan: TextView
    private lateinit var tvGuideHours: TextView

    private lateinit var btnUpdate: TextView
    private lateinit var btnDelete: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_details)



        initView()
        setValuesToView()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("guideId").toString(),
                intent.getStringExtra("guideName").toString()

            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("guideId").toString()
            )
        }
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Guides").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Guide details Deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {error ->
            Toast.makeText(this, "Deleting Error ${error.message}", Toast.LENGTH_LONG).show()

        }
    }

    private fun initView(){
        tvGuideId = findViewById(R.id.textView4)
        tvGuideName = findViewById(R.id.tvGuideName)
        tvGuidePNum = findViewById(R.id.tvGuidePNum)
        tvGuideLan = findViewById(R.id.textView2)
        tvGuideHours = findViewById(R.id.tvGuideHours)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToView() {
        tvGuideId.text = intent.getStringExtra("guideId")
        tvGuideName.text = intent.getStringExtra("guideName")
        tvGuidePNum.text = intent.getStringExtra("guidePNum")
        tvGuideLan.text = intent.getStringExtra("guideLan")
        tvGuideHours.text = intent.getStringExtra("guideHours")

    }

    private fun openUpdateDialog(
        guideId : String,
        guideName : String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etGuideName = mDialogView.findViewById<EditText>(R.id.etGuideName)
        val etGuidePNum = mDialogView.findViewById<EditText>(R.id.etGuidePNum)
        val etGuideLan = mDialogView.findViewById<EditText>(R.id.etGuideLan)
        val etGuideHours = mDialogView.findViewById<EditText>(R.id.etGuideHours)

        val btnUpdateDate = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etGuideName.setText(intent.getStringExtra("guideName").toString())
        etGuidePNum.setText(intent.getStringExtra("guidePNum").toString())
        etGuideLan.setText(intent.getStringExtra("guideLan").toString())
        etGuideHours.setText(intent.getStringExtra("guideHours").toString())

        mDialog.setTitle("Updating $guideName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateDate.setOnClickListener {
            updateGuideData(
                guideId,
                etGuideName.text.toString(),
                etGuidePNum.text.toString(),
                etGuideLan.text.toString(),
                etGuideHours.text.toString()

            )

            Toast.makeText(applicationContext, "Guide data updated", Toast.LENGTH_LONG).show()

            //setting updated data to textView
            tvGuideName.text = etGuideName.text.toString()
            tvGuidePNum.text = etGuidePNum.text.toString()
            tvGuideLan.text = etGuideLan.text.toString()
            tvGuideHours.text = etGuideHours.text.toString()

            alertDialog.dismiss()

        }

    }

    private fun updateGuideData(
        id: String,
        name: String,
        pNum: String,
        language: String,
        hours: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Guides").child(id)
        val guideInfo = GuideModel(id, name, pNum, language, hours)
        dbRef.setValue(guideInfo)
    }
}