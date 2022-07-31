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

class StudentregisterActivity : AppCompatActivity() {
    private lateinit var btnregister: Button
    private lateinit var etregisteremail: EditText
    private lateinit var etregisterpassword: EditText
    private lateinit var tvlogin: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentregister)
        btnregister = findViewById(R.id.btnregister)
        etregisteremail = findViewById(R.id.etemail)
        etregisterpassword = findViewById(R.id.etpassword)
        tvlogin = findViewById(R.id.tvlogin)
        tvlogin.setOnClickListener {
            startActivity(Intent(this@StudentregisterActivity, StudentloginActivity::class.java))
        }
        btnregister.setOnClickListener{
            when {
                TextUtils.isEmpty(etregisteremail.text.toString(). trim{it <=' '}) -> {
                    Toast.makeText(
                        this@StudentregisterActivity,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etregisterpassword.text.toString(). trim{it <= ' '}) ->{
                    Toast.makeText(
                        this@StudentregisterActivity,
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
                                        this@StudentregisterActivity,
                                        "You are registered successfully.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent =
                                        Intent(this@StudentregisterActivity, MapsActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    Toast.makeText(
                                        this@StudentregisterActivity,
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