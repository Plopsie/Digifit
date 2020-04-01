package com.example.digigit

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity: AppCompatActivity() {
    internal lateinit var myEmail:String
    internal lateinit var myPassword:String
    internal lateinit var etEmail: EditText
    internal lateinit var etPassword: EditText
    internal lateinit var etName:EditText
    internal lateinit var etAge:EditText
    internal lateinit var etWeight:EditText
    internal lateinit var etHeight:EditText
    internal lateinit var myName:String
    internal lateinit var myAge:String
    internal lateinit var myHeight:String
    internal lateinit var myWeight:String
    internal lateinit var register: Button
    internal lateinit var myAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    internal lateinit var rgActivity:RadioGroup
    internal lateinit var rgGender:RadioGroup
    internal lateinit var myActivity:String
    internal lateinit var myGender:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        register = findViewById(R.id.regButton)
        etEmail = findViewById(R.id.myEmail)
        etPassword = findViewById(R.id.myPassword)
        etName = findViewById(R.id.name)
        etWeight = findViewById(R.id.weight)
        etAge = findViewById(R.id.age)
        etHeight = findViewById(R.id.height)
        rgActivity = findViewById(R.id.activity)
        rgGender = findViewById(R.id.gender)
        myAuth = FirebaseAuth.getInstance()

        register.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {

                myName = etName.getText().toString().trim()
                myWeight = etWeight.getText().toString().trim()
                myAge = etAge.getText().toString().trim()
                myHeight = etHeight.getText().toString().trim()
                myEmail = etEmail.getText().toString().trim()
                myPassword = etPassword.getText().toString().trim()
                var activityId: Int = rgActivity.checkedRadioButtonId
                if (activityId!=-1){ // If any radio button checked from radio group
                    // Get the instance of radio button using id
                    val radio:RadioButton = findViewById(activityId)
                    myActivity = radio.text.toString()
                }
                var genderId: Int = rgGender.checkedRadioButtonId
                if (genderId!=-1){ // If any radio button checked from radio group
                    // Get the instance of radio button using id
                    val radio:RadioButton = findViewById(genderId)
                    myGender = radio.text.toString()
                }

                addUser()
                CreateUser()

               /* val regIntent = Intent(this@RegisterActivity, MainActivity::class.java)
                startActivity(regIntent)*/







            }
        })

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true)

        // Firestore
        firestore = Firebase.firestore
    }

     fun addUser() {
         val user = com.example.digigit.model.User()
         user.name = myName
         user.weight = myWeight
         user.age = myAge
         user.height = myHeight
         user.activity = myActivity
         user.gender = myGender



         firestore.collection("Users").add(user)


    }
    private fun CreateUser() {
        myAuth.createUserWithEmailAndPassword(myEmail, myPassword).addOnCompleteListener(object:
            OnCompleteListener<AuthResult> {
            override fun onComplete(@NonNull task: Task<AuthResult>) {
                if (task.isSuccessful())
                {
                    val homeIntent = Intent(this@RegisterActivity, MainActivity::class.java)
                    startActivity(homeIntent)
                    finish()
                }
                else
                {
                    Toast.makeText(this@RegisterActivity, "Error is " + task.getException(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}