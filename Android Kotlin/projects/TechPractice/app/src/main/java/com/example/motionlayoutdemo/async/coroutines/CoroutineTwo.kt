package com.example.motionlayoutdemo.async.coroutines

import com.example.motionlayoutdemo.util.logD
import kotlinx.coroutines.*

/**
 * Use withContext when you do not need the parallel execution.
 * Use async only when you need the parallel execution.
 * Both withContext and async can be used to get the result which is not possible with the launch.
 * Use withContext to return the result of a single task.
 * Use async for results from multiple tasks that run in parallel.
 */

class CoroutineTwo {
    init {
        GlobalScope.launch(Dispatchers.Main) {
            logD("userOne: async")
            val userOne = async(Dispatchers.IO) {
                fetchFirstUser()
            }


            logD("userTwo: async")
            val userTwo = async(Dispatchers.IO) {
                fetchSecondUser()
            }

            //here userOne not User obj ref. its a Deferred user
            showUsers(userOne.await(), userTwo.await()) // back on UI thread
        }
    }

    private suspend fun fetchFirstUser(): User {
        delay(2000)
        return User("FirstUser")
    }

    private suspend fun fetchSecondUser(): User {
        delay(2000)
        return User("SecondUser")
    }

    private fun showUsers(userOne: User, userTwo: User) {
        logD("userOne: " + userOne.name)
        logD("userTwo: " + userTwo.name)
    }
}