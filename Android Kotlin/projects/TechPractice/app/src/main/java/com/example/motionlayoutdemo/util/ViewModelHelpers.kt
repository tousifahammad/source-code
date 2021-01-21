@file:Suppress("UNCHECKED_CAST")

package com.example.motionlayoutdemo.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <T:ViewModel,A,B,C,D,E> getViewModelFactory(constructor: (A,B,C,D,E) -> T): (A,B,C,D,E) -> ViewModelProvider.NewInstanceFactory{
    return {arg1:A,arg2:B,arg3:C,arg4:D,arg5:E ->
        object : ViewModelProvider.NewInstanceFactory(){
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return constructor(arg1,arg2,arg3,arg4,arg5) as T
            }
        }
    }
}
fun <T:ViewModel,A,B,C,D,E,F,G,H,I> getViewModelFactory(constructor: (A,B,C,D,E,F,G,H,I) -> T): (A,B,C,D,E,F,G,H,I) -> ViewModelProvider.NewInstanceFactory{
    return {arg1:A,arg2:B,arg3:C,arg4:D,arg5:E,arg6:F,arg7:G,arg8:H,arg9:I ->
        object : ViewModelProvider.NewInstanceFactory(){
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return constructor(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9) as T
            }
        }
    }
}

fun <T:ViewModel,A,B,C,D,E,F,G,H,I,J,K,L> getViewModelFactory(constructor: (A,B,C,D,E,F,G,H,I,J,K,L) -> T): (A,B,C,D,E,F,G,H,I,J,K,L) -> ViewModelProvider.NewInstanceFactory{
    return {arg1:A,arg2:B,arg3:C,arg4:D,arg5:E,arg6:F,arg7:G,arg8:H,arg9:I,arg10:J,arg11:K,arg12:L ->
        object : ViewModelProvider.NewInstanceFactory(){
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return constructor(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12) as T
            }
        }
    }
}
