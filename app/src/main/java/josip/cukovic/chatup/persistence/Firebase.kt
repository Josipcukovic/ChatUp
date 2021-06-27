package josip.cukovic.chatup.persistence



import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import josip.cukovic.chatup.ChatUpApplication
import josip.cukovic.chatup.activities.AuthActivity
import josip.cukovic.chatup.activities.ChatActivity
import josip.cukovic.chatup.fragments.FragmentChat
import josip.cukovic.chatup.fragments.FragmentUsers
import josip.cukovic.chatup.manager.PreferenceManager
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
    fun loadUsers(){
    childEventListenerData = object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
               // val userId = snapshot.key
                val userData =  snapshot.getValue() as HashMap<String, String>

                    val korisnik =  User(userData.get("name").toString(), userData.get("email").toString(), userData.get("id").toString())
                    UserRepository.add(korisnik)
                    FragmentUsers.adapter.dataAdded(UserRepository.users)

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}

        }
         usersDbRef.addChildEventListener(childEventListenerData as ChildEventListener)

    }


    fun loadMessages(){
        childEventListenerMessages = object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val messageData =  snapshot.getValue() as HashMap<String, String>
///kasnije refaktoriraj
                val recieverId = messageData.get("receiverId").toString()
                val senderId = messageData.get("senderId").toString()
                val chosenUserId = UserRepository.userId
                val currentUser = getCurrentUserId()
///provjeri jel smije vidjet poruku uopce
                if((recieverId == currentUser && senderId == chosenUserId) || (recieverId == chosenUserId && senderId == currentUser )){
                    val poruka =  Message(messageData.get("textMessage").toString(), messageData.get("senderId").toString(), messageData.get("receiverId").toString(),messageData["messageSeen"].toString())

                    ///update seen
                    if ((chosenUserId == senderId) && (currentUser == recieverId) && (poruka.messageSeen == "false")) {
                        messagesDbRef.child(snapshot.key.toString()).child("messageSeen").setValue("true")
                    }

                    MessageRepository.addMessage(poruka)
                    ChatActivity.adapter.dataAdded(MessageRepository.messages)
                    ChatActivity.recyclerView.scrollToPosition(ChatActivity.adapter.itemCount-1)
                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}

        }
        messagesDbRef.addChildEventListener(childEventListenerMessages as ChildEventListener)
    }

    fun updateUnreadMessage() {

        messagesDbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                MessageRepository.removeAllUnreadMessages()
                for (postSnapshot in dataSnapshot.children) {
                    val messageData = postSnapshot.getValue() as HashMap<String, String>

                    val recieverId = messageData.get("receiverId").toString()
                    val senderId = messageData.get("senderId").toString()
                    val chosenUserId = UserRepository.userId
                    val currentUser = getCurrentUserId()

                    if ((currentUser == recieverId) && (messageData["messageSeen"].toString() == "false") && chosenUserId != senderId) {
                        val message = Message(messageData["textMessage"].toString(), messageData["senderId"].toString(), messageData["receiverId"].toString(), messageData["messageSeen"].toString())
                        MessageRepository.addUnreadMessage(message)

                    }
                    FragmentChat.adapter.dataChanged(MessageRepository.unreadMessages)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(ChatUpApplication.ApplicationContext, "Couldn't fetch messages", Toast.LENGTH_SHORT).show()
            }
        })
    }

  fun createUser(email: String, password: String, userName: String){

      val context = ChatUpApplication.ApplicationContext

      authFirebase.createUserWithEmailAndPassword(email, password)
              .addOnCompleteListener{ task: Task<AuthResult> ->
                  if(task.isSuccessful){
                      Toast.makeText(context, "Your profile is being created", Toast.LENGTH_LONG).show()
                      PreferenceManager().saveEmail(email)
                      PreferenceManager().savePassword(password)
                     addUserToDatabase(email, userName)
                  }else{
                      Toast.makeText(context, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                  }
              }

  }

    private fun addUserToDatabase(email: String, userName: String){
        val context = ChatUpApplication.ApplicationContext
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
                                val intent = Intent(context, AuthActivity::class.java)
                                ///zatvori sve prijasnje activity-e
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(context, intent, null)

                            }else{
                                //user is not in database
                            }
                        }
                    }else{
                        Toast.makeText(context, "Error has occured", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    fun saveMessage(message: String, senderId: String, receiverId: String, isSeen:String){

        val message = Message(message,senderId,receiverId, isSeen)
        messagesDbRef.push().setValue(message).addOnCompleteListener{task: Task<Void> ->
            if(task.isSuccessful){
                ///message is in base
            }
        }
    }

    fun loginUser(email: String, password: String){
         val context = ChatUpApplication.ApplicationContext
        authFirebase.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task: Task<AuthResult> ->
                if (task.isSuccessful){
                    ///spremi podatke
                    PreferenceManager().saveEmail(email)
                    PreferenceManager().savePassword(password)

                    val intent = Intent(context, AuthActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(context, intent, null)

                }else{
                    Toast.makeText(context, task.exception!!.message.toString(), Toast.LENGTH_LONG).show()

                }
            }
    }

    fun logOut(){
        UserRepository.clearAllUsers()
        usersDbRef.removeEventListener(childEventListenerData as ChildEventListener)
        PreferenceManager().saveEmail("default")
        PreferenceManager().savePassword("default")
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

    fun unsubscribeUserListener(){
        UserRepository.clearAllUsers()
        usersDbRef.removeEventListener(childEventListenerData as ChildEventListener)
    }
}