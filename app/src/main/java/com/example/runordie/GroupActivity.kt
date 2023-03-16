package com.example.runordie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GroupActivity:AppCompatActivity() {
    private lateinit var manArray:IntArray
    private lateinit var womenArray:IntArray
    private var builderForCustom: CustomDialog.Builder?=null
    private var mDialog:CustomDialog ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.group)

        Log.i("Jump Information","In Group")

        val manButton = findViewById<Button>(R.id.ManButton)
        val womenButton = findViewById<Button>(R.id.WomenButton)
        val setNumButton = findViewById<Button>(R.id.SetNumBottun)
        var totalNum = 0
        var manNum =0


        var manIndex  = 0
        var womenIndex = 0

        builderForCustom = CustomDialog.Builder(this)

        setNumButton.setOnClickListener {
            totalNum = findViewById<EditText>(R.id.TotalNum).text.toString().toInt()
            manNum = findViewById<EditText>(R.id.ManNum).text.toString().toInt()
            manArray = (1..manNum).shuffled().toIntArray()
            womenArray = (1..(totalNum -manNum)).shuffled().toIntArray()
            manIndex  = 0
            womenIndex = 0
            Toast.makeText(this, "总人数为$totalNum\n男生人数为$manNum", Toast.LENGTH_LONG).show()
        }


        manButton.setOnClickListener {
            // 男生分组
            if (manIndex < manNum) {
                if(manArray[manIndex] <= (manNum / 2)){//相对少的一组
                    showSingleButtonDialog("分院结果","格兰芬多！！","我接受", View.OnClickListener {
                        mDialog!!.dismiss()
                    })

                }else{
                    showSingleButtonDialog("分院结果","斯莱特林！！","我接受", View.OnClickListener {
                        mDialog!!.dismiss()
                    })
                }
                manIndex++
            } else{
                Toast.makeText(this, "别按了！人都分完了", Toast.LENGTH_LONG).show()
            }
        }

        womenButton.setOnClickListener {
            // 女生分组
            if (womenIndex < totalNum-manNum) {
                if (womenArray[womenIndex] > ((totalNum - manNum) / 2)) { //相对多的一组
                    showSingleButtonDialog("分院结果","格兰芬多！！","我接受", View.OnClickListener {
                        mDialog!!.dismiss()
                    })
                } else {
                    showSingleButtonDialog("分院结果","斯莱特林！！","我接受", View.OnClickListener {
                        mDialog!!.dismiss()
                    })

                }
                womenIndex++
            } else{
                Toast.makeText(this, "别按了！人都分完了", Toast.LENGTH_LONG).show()
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