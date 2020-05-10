package com.naemo.zozi.db.room

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val accNum: String,
    val bank: String
)

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT* FROM user")
    fun loadUser(): LiveData<User>

    @Query("DELETE FROM user")
    fun deleteUser()
}

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userdao(): UserDao

    companion object {
        @Volatile
        private var instance: UserDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, UserDatabase::class.java, "City_Forecast")
                .fallbackToDestructiveMigration()
                .build()
    }
}

class UserRepository(application: Application) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    private var userdao: UserDao? = null

    init {
        val dbDatabase = UserDatabase.invoke(application)
        userdao = dbDatabase.userdao()
    }


    fun getUser(): LiveData<User>? {
        return userdao?.loadUser()
    }

    fun saveUser(user: User) {
        launch {
            save(user)
        }
    }

    private suspend fun save(user: User) {
        withContext(IO){
            userdao?.insertUser(user)
        }
    }

}