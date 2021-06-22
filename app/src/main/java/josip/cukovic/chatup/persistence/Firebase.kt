package josip.cukovic.chatup.persistence

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import josip.cukovic.chatup.activities.AuthActivity
import josip.cukovic.chatup.model.User


object Firebase {
    private var authFirebase = FirebaseAuth.getInstance()
    private var db = FirebaseDatabase.getInstance()
    private val usersDbRef = db.getReference("Users")

  fun createUser(email: String, password: String, userName: String, context: Context){

      authFirebase.createUserWithEmailAndPassword(email, password)
              .addOnCompleteListener{ task: Task<AuthResult> ->
                  if(task.isSuccessful){
                     addUserToDatabase(email, password, userName, context)
                  }else{
                      Toast.makeText(context, "imamo Problem", Toast.LENGTH_SHORT).show()
                  }
              }
  }
    private fun addUserToDatabase(email: String, password: String, userName: String, context: Context){

        val currentUser = authFirebase.currentUser
        val currentUserId = currentUser!!.uid
        val userNode = usersDbRef.child(currentUserId)
        val user = User(userName, email, password)
///update user displayName
        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(userName).build()
///ako je uspjesno dodaj ga u bazu i pusti u app
        currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener{
                    task: Task<Void> ->
                    if(task.isSuccessful){
                        userNode.setValue(user).addOnCompleteListener{ task: Task<Void> ->

                            if(task.isSuccessful){
                                Toast.makeText(context, "hurraaaj", Toast.LENGTH_SHORT).show()

                                val intent = Intent(context, AuthActivity::class.java)
                                ///zatvori sve prijasnje activity-e
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(context, intent, null)

                            }else{
                                Toast.makeText(context, "nije u bazi", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(context, "nemas srece", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    fun loginUser(email: String, password: String, context: Context){
        authFirebase.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task: Task<AuthResult> ->
                if (task.isSuccessful){
                    val intent = Intent(context, AuthActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(context, intent, null)

                }else{
                    Toast.makeText(context, task.exception.toString(), Toast.LENGTH_LONG).show()

                }
            }
    }

    fun logOut(){
        authFirebase.signOut()
    }

}