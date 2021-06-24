package josip.cukovic.chatup.persistence

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import josip.cukovic.chatup.activities.AuthActivity
import josip.cukovic.chatup.adapters.MessagesRecyclerAdapter
import josip.cukovic.chatup.adapters.UsersRecyclerAdapter
import josip.cukovic.chatup.model.Message
import josip.cukovic.chatup.model.User



object Firebase {
    private var authFirebase = FirebaseAuth.getInstance()
    private var db = FirebaseDatabase.getInstance()
    private val usersDbRef = db.getReference("Users")
    private val messagesDbRef = db.getReference("Messages")
    private var childEventListenerData: ChildEventListener? = null
    private var childEventListenerMessages: ChildEventListener? = null

///pokusaj dohvacanja podataka
    fun loadData(adapter: UsersRecyclerAdapter,recyclerView: RecyclerView){
    childEventListenerData = object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
               // val userId = snapshot.key
                val userData =  snapshot.getValue() as HashMap<String, String>
                //if(userId != getCurrentUserId()){
                    val korisnik =  User(userData.get("name").toString(), userData.get("email").toString(), userData.get("id").toString())
                    UserRepository.add(korisnik)
                    adapter.dataAdded(UserRepository.users)
                    recyclerView.scrollToPosition(adapter.itemCount-1)
               // }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
         usersDbRef.addChildEventListener(childEventListenerData as ChildEventListener)

    }


    fun loadMessages(adapter: MessagesRecyclerAdapter, recyclerView: RecyclerView){
        childEventListenerMessages = object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val userData =  snapshot.getValue() as HashMap<String, String>
                //val poruka =  Message(userData.get("textMessage").toString(), userData.get("senderId").toString(), userData.get("receiverId").toString())

///kasnije refaktoriraj
                val recieverId = userData.get("receiverId").toString()
                val senderId = userData.get("senderId").toString()
                val chosenUserId = UserRepository.userId
                val currentUser = getCurrentUserId()
///provjeri jel smije vidjet poruku uopce
                if((recieverId == currentUser && senderId == chosenUserId) || (recieverId == chosenUserId && senderId == currentUser )){
                    val poruka =  Message(userData.get("textMessage").toString(), userData.get("senderId").toString(), userData.get("receiverId").toString())
                    MessageRepository.add(poruka)
                    adapter.dataAdded(MessageRepository.messages)
                    recyclerView.scrollToPosition(adapter.itemCount-1)
                }


            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        messagesDbRef.addChildEventListener(childEventListenerMessages as ChildEventListener)

    }



  fun createUser(email: String, password: String, userName: String, context: Context){

      authFirebase.createUserWithEmailAndPassword(email, password)
              .addOnCompleteListener{ task: Task<AuthResult> ->
                  if(task.isSuccessful){
                     addUserToDatabase(email, userName, context)
                  }else{
                      Toast.makeText(context, "imamo Problem", Toast.LENGTH_SHORT).show()
                  }
              }
  }

    private fun addUserToDatabase(email: String, userName: String, context: Context){

        val currentUser = authFirebase.currentUser
        val currentUserId = currentUser!!.uid
        val userNode = usersDbRef.child(currentUserId)
        val user = User(userName, email, currentUserId)
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

    fun saveMessage(message: String, senderId: String, receiverId: String, context: Context){
        val message = Message(message,senderId,receiverId)
        messagesDbRef.push().setValue(message).addOnCompleteListener{task: Task<Void> ->
            if(task.isSuccessful){
                Toast.makeText(context, "poruka je u bazi", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "nije u bazi", Toast.LENGTH_SHORT).show()
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
        UserRepository.clearThemAll()
        usersDbRef.removeEventListener(childEventListenerData as ChildEventListener)
        authFirebase.signOut()
    }

    fun getCurrentUserName(): String{
        return authFirebase.currentUser!!.displayName.toString()
    }

    fun getCurrentUserEmail(): String{
        return authFirebase.currentUser!!.email.toString()
    }

    fun getCurrentUserId(): String{
        return authFirebase.currentUser!!.uid
    }

    fun unsubscribeMessageListener(){
        MessageRepository.removeAllMessages()
        messagesDbRef.removeEventListener(childEventListenerMessages as ChildEventListener)
    }
}