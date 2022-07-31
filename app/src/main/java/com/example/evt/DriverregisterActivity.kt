package com.example.evt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class DriverregisterActivity : AppCompatActivity() {
    private lateinit var btnregister: Button
    private lateinit var etregisteremail: EditText
    private lateinit var etregisterpassword: EditText
    private lateinit var tvlogin: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driverregister)
        btnregister = findViewById(R.id.btnregister)
        etregisteremail = findViewById(R.id.etemail)
        etregisterpassword = findViewById(R.id.etpassword)
        tvlogin = findViewById(R.id.tvlogin)
        tvlogin.setOnClickListener {
            startActivity(Intent(this@DriverregisterActivity, DriverloginActivity::class.java))
        }
        btnregister.setOnClickListener{
            when {
                TextUtils.isEmpty(etregisteremail.text.toString(). trim{it <=' '}) -> {
                    Toast.makeText(
                        this@DriverregisterActivity,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etregisterpassword.text.toString(). trim{it <= ' '}) ->{
                    Toast.makeText(
                        this@DriverregisterActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = etregisteremail.text.toString(). trim{it <=' '}
                    val password: String = etregisterpassword.text.toString(). trim{it <=' '}

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->
                                if(task.isSuccessful){
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this@DriverregisterActivity,
                                        "You are registered successfully.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent =
                                        Intent(this@DriverregisterActivity, MapsActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    Toast.makeText(
                                        this@DriverregisterActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }
                        )

                }
            }
        }
    }
}