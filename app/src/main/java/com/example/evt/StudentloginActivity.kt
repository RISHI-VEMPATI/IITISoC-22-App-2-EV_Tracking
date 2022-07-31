package com.example.evt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class StudentloginActivity : AppCompatActivity() {
    private lateinit var btnlogin: Button
    private lateinit var etloginemail: EditText
    private lateinit var etloginpassword: EditText
    private lateinit var tvregister: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentlogin)
        btnlogin = findViewById(R.id.btnlogin)
        etloginemail = findViewById(R.id.etloginemail)
        etloginpassword = findViewById(R.id.etloginpassword)
        tvregister = findViewById(R.id.tvregister)

        tvregister.setOnClickListener {

            startActivity(Intent(this@StudentloginActivity, StudentregisterActivity::class.java))
        }
        btnlogin.setOnClickListener{
            when {
                TextUtils.isEmpty(etloginemail.text.toString(). trim{it <=' '}) -> {
                    Toast.makeText(
                        this@StudentloginActivity,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etloginpassword.text.toString(). trim{it <= ' '}) ->{
                    Toast.makeText(
                        this@StudentloginActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = etloginemail.text.toString().trim { it <= ' ' }
                    val password: String = etloginpassword.text.toString().trim { it <= ' ' }
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener{ task ->
                            if(task.isSuccessful){
                                Toast.makeText(
                                    this@StudentloginActivity,
                                    "You are logged in successfully.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent =
                                    Intent(this@StudentloginActivity, MapsActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra(
                                    "user_id",
                                    FirebaseAuth.getInstance().currentUser!!.uid
                                )
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            }else{
                                Toast.makeText(
                                    this@StudentloginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }
}