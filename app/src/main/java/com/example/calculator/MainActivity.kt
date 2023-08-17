package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.view.isGone
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.time.LocalDate


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    var lastnumberic=false
    var stateError  =false
    var lastDot=false
    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun equalclick(view: View) {
       onequal()
        binding.dataTextView.text=binding.answerTextview.text.toString().drop(1)
    }

    fun digitclick(view: View) {
        if(stateError){
            binding.dataTextView.text=(view as Button).text
            stateError=false
        }
        else{
            binding.dataTextView.append((view as Button).text)

        }
        lastnumberic=true
        onequal()
    }

    fun Allclearclick(view: View) {
       binding.dataTextView.text=""
        binding.answerTextview.text=""
        var lastnumberic=false
        var stateError  =false
        var lastDot=false
        binding.answerTextview.visibility=View.GONE
    }


    fun operatorclick(view: View) {
        if(!stateError&&lastnumberic){
            binding.dataTextView.append((view as Button).text)

           lastDot=false
            lastnumberic=false
            onequal()
        }
    }

    fun backspaceclick(view: View) {
        binding.dataTextView.text=binding.dataTextView.text.toString().dropLast( 1)
        try{
            val lastchar=binding.dataTextView.text.toString().last()
            if(lastchar.isDigit()){
                onequal()
            }

        }
        catch(e:Exception){
            binding.answerTextview.text=""
            binding.answerTextview.visibility=View.GONE
            Log.e("error in last char",e.toString())
        }

    }

    fun clearclick(view: View) {
        binding.dataTextView.text = ""
        lastnumberic=false

    }

    fun onequal(){
        if(lastnumberic&&!stateError){
            val txt=binding.dataTextView.text.toString()
            expression=ExpressionBuilder(txt).build()

            try {
                val result=expression.evaluate()
                binding.answerTextview.visibility=View.VISIBLE
                binding.answerTextview.text="="+ result.toString()

            }
            catch (ex:ArithmeticException){
                Log.e("Error",ex.toString())
                binding.answerTextview.text= "Error"
                stateError=true
                lastnumberic=false
            }
        }
    }
}