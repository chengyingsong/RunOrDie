package com.example.runordie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.*


//TODO: github版本控制
class MainActivity : AppCompatActivity() {
    lateinit var Manarray:IntArray
    lateinit var Womenarray:IntArray
    private var builderForCustom: CustomDialog.Builder?=null
    private var mDialog:CustomDialog ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Manbutton = findViewById<Button>(R.id.ManButton)
        val WomenButton = findViewById<Button>(R.id.WomenButton)
        val SetNumButton = findViewById<Button>(R.id.SetNumBottun)
        var TotalNum:Int = 0
        var ManNum:Int =0


        var ManIndex  = 0
        var WomenIndex = 0
        var ManInA = 0
        var ManInB = 0
        var WomenInA = 0
        var WomenInB = 0

        builderForCustom = CustomDialog.Builder(this)

        SetNumButton.setOnClickListener {
            TotalNum = findViewById<EditText>(R.id.TotalNum).text.toString().toInt()
            ManNum = findViewById<EditText>(R.id.ManNum).text.toString().toInt()
            Manarray = (1..ManNum!!).shuffled().toIntArray()
            Womenarray = (1..(TotalNum!! -ManNum)).shuffled().toIntArray()
            ManIndex  = 0
            WomenIndex = 0
            ManInA = 0
            ManInB = 0
            WomenInA = 0
            WomenInB = 0
            Toast.makeText(this, "总人数为$TotalNum\n男生人数为$ManNum",Toast.LENGTH_LONG).show()
        }

        //TODO: Toast改成提示框
        Manbutton.setOnClickListener {
            // 男生分组
            if (ManIndex < ManNum!!) {
                if(Manarray[ManIndex] <= (ManNum / 2)){//相对少的一组
                    showSingleButtonDialog("分院结果","格兰芬多！！","我接受",View.OnClickListener {
                        mDialog!!.dismiss()
                    })
                    ManInA++
                }else{
                    showSingleButtonDialog("分院结果","斯莱特林！！","我接受",View.OnClickListener {
                        mDialog!!.dismiss()
                    })
                    ManInB++
                 }
                ManIndex++
            } else{
            Toast.makeText(this, "别按了！人都分完了，有$ManInA 个格兰芬多，有$ManInB 个斯莱特林", Toast.LENGTH_LONG).show()
        }
        }

        WomenButton.setOnClickListener {
            // 女生分组
            if (WomenIndex < TotalNum!!-ManNum!!) {
                if (Womenarray[WomenIndex] > ((TotalNum - ManNum) / 2)) { //相对多的一组
                    showSingleButtonDialog("分院结果","格兰芬多！！","我接受",View.OnClickListener {
                        mDialog!!.dismiss()
                    })
                    WomenInA++
                } else {
                    showSingleButtonDialog("分院结果","斯莱特林！！","我接受",View.OnClickListener {
                        mDialog!!.dismiss()
                    })
                    WomenInB++
                }
                WomenIndex++
            } else{
                Toast.makeText(this, "别按了！人都分完了,有$WomenInA 个格兰芬多，有$WomenInB 个斯莱特林", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showSingleButtonDialog(title: String, alertText: String, btnText: String, onClickListener:View.OnClickListener) {
        mDialog = builderForCustom!!.setTitle(title)
            .setMessage(alertText)
            .setSingleButton(btnText, onClickListener)
            .createSingleButtonDialog()
        mDialog!!.show()
    }

    private fun showTwoButtonDialog(title: String, alertText: String, confirmText: String, cancelText: String, conFirmListener: View.OnClickListener, cancelListener: View.OnClickListener) {
        mDialog = builderForCustom!!.setTitle(title)
            .setMessage(alertText)
            .setPositiveButton(confirmText, conFirmListener)
            .setNegativeButton(cancelText, cancelListener)
            .createTwoButtonDialog()
        mDialog!!.show()
    }
}