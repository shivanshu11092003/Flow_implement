package com.example.flowimplement

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        GlobalScope.launch {
            val data = producer()
            /** flow ko consume karne ke liye hamre pass hai ek function collect naam ka aur collect ke andar hame lambda pass karna hoga**/
            //consumer
            /** flow me  jab tak koi consumer nhi hoga tab tak no producer **/
            data
                //Event
                .onStart {
                    emit(-1)
                    Log.w("Flow Implement","Starting of emission")

                }
                .onCompletion {
                    emit(10)

                    Log.w("Flow Implement","Completed")

                }
                .onEach {

                    Log.w("Flow Implement","emit of ${it.toString()}")
                }
                //operator
                .map {
                    it*100
                }
                .filter {
                    it<700
                }
                .collect{
                //colllect ke andar ham consume kar rhe honge
                Log.w("Flow Implement",it.toString())
            }
        }
    }
    //producer
    fun producer() : Flow<Int> = flow{
        val list = listOf(1,2,5,6,7,4,2,6,8,9)
        list.forEach {
            delay(1000)
            emit(it)
        }

    }


}