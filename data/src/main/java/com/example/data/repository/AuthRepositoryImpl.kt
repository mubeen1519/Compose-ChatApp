package com.example.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.core.text.isDigitsOnly
import com.example.domain.model.UsersModel
import com.example.domain.repository.AuthRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseMessaging: FirebaseMessaging,
    private val fireStore : FirebaseFirestore,
    private val profileUpdates:UserProfileChangeRequest.Builder,
    private val userModel: UsersModel,
    ): AuthRepository {

     @SuppressLint("SuspiciousIndentation")
     override fun signUp(firstName: String, lastName: String, email: String, password: String):Flow<String> = flow{

       val createUser: Task<AuthResult> =  firebaseAuth.createUserWithEmailAndPassword(email, password)
            if (createUser.await().user!=null)
                userProfile(firstName, lastName, email).collect{
                    emit(it)

                }
            else
                emit(createUser.exception!!.localizedMessage!!.toString())
    }

    override fun signIn(email: String, password: String):Flow<String> = flow{

        val logInUser: Task<AuthResult> =  firebaseAuth.signInWithEmailAndPassword(email, password)
        if (logInUser.await().user!=null)
            emit("Sign In Successful")
        else
            emit(logInUser.exception!!.localizedMessage!!.toString())
    }




    private fun userProfile(firstName: String, lastName: String, email: String):Flow<String> = flow{
        try {
            if (firebaseAuth.currentUser != null) {
                firebaseMessaging.token.await().let { token ->
                    val updateUserName =
                        profileUpdates.setDisplayName("$firstName $lastName").build()
                    coroutineScope {
                        val job = async {
                            firebaseAuth.currentUser!!.updateProfile(updateUserName)
                        }
                        job.await()
                        userModel.name = "$firstName $lastName"
                        userModel.email = email
                        userModel.userId = firebaseAuth.currentUser!!.uid
                        userModel.type = "offline"
                        userModel.lastSeen = FieldValue.serverTimestamp()
                        userModel.imageProfile = ""
                        userModel.fcmToken = token
                        fireStore.collection("users").add(userModel)
                        emit("Signed up Success, Welcome")

                    }
                }
            }
        }catch (e:Exception){
            emit(e.localizedMessage!!.toString())
        }



    }

    override fun updateUserStatus(userStatus:String, lastSeen: Any?){
        val docRef: Query = fireStore.collection("users").whereEqualTo("userId",firebaseAuth.currentUser!!.uid)
        docRef.get().addOnSuccessListener { documents ->
            val list: MutableList<String> = ArrayList()
            for (document in documents) {

                list.add(document.id)
            }
            if (lastSeen == null){
                // mean user is online
                for (id in list) {
                    fireStore.collection("users").document(id).update("type", userStatus)
                        .addOnSuccessListener { Log.d("ChatActivity", "type Updated!") }
                }
            }
            else{
                for (id in list) {
                    fireStore.collection("users").document(id).update("type", userStatus)
                        .addOnSuccessListener { Log.d("ChatActivity", "type Updated!") }
                    fireStore.collection("users").document(id).update("lastSeen", lastSeen)
                        .addOnSuccessListener { Log.d("ChatActivity", "lastSeen Updated!") }
                }
            }

        }
            .addOnFailureListener { exception ->
                Log.d("ChatActivity", "Error getting documents: $exception",)
            }
    }

    override fun forgetPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("TAG","task is sucesfull")
            } else {
                Log.d("TAG","${it.exception?.message}")

            }
        }
    }

}