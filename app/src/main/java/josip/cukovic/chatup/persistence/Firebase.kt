package josip.cukovic.chatup.persistence

import android.content.Context
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import josip.cukovic.chatup.model.User

object Firebase {
    private var authFirebase = FirebaseAuth.getInstance()
    private var db = FirebaseDatabase.getInstance()
    private val usersDbRef = db.getReference("Users")

  fun createUser(email : String,password : String,userName: String,context: Context){
      authFirebase.createUserWithEmailAndPassword(email,password)
              .addOnCompleteListener{
                  task: Task<AuthResult> ->
                  if(task.isSuccessful){
                      val currentUser = authFirebase.currentUser
                      val currentUserId = currentUser!!.uid
                      val userNode = usersDbRef.child(currentUserId)

                      val user = User(userName,email,password)
                      userNode.setValue(user).addOnCompleteListener{
                          task: Task<Void> ->
                          if(task.isSuccessful){
                              Toast.makeText(context, "hurraaaj", Toast.LENGTH_SHORT).show()
                          }else{
                              Toast.makeText(context, "nije u bazi", Toast.LENGTH_SHORT).show()
                          }
                      }
                  }else{
                      Toast.makeText(context, "imamo Problem", Toast.LENGTH_SHORT).show()
                  }
              }
  }

    /*authFirebase.signInWithEmailAndPassword("lala@gmail.com","123456")
                .addOnCompleteListener{
                    task : Task<AuthResult> ->
                    if(task.isSuccessful){
                        Toast.makeText(this, "nez, logiran je", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, "nez, nije logiran", Toast.LENGTH_SHORT).show()
                    }
                }*/


}