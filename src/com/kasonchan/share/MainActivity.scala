package com.kasonchan.share

import android.os.Bundle
import android.app.Activity
import android.view.Menu
import android.widget.SeekBar
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView._
import android.text.TextWatcher
import android.text.Editable
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.SeekBar.OnSeekBarChangeListener._
import android.widget.Toast
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup

/**
 * Created by kasonchan on 3/8/2015.
 */
class MainActivity extends Activity {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    //    Hide default action bar
    super.getActionBar.hide()

    //      Show content view
    setContentView(R.layout.activity_main)

    //    Initialize components
    val afterTax: EditText = findViewById(R.id.after_tax).asInstanceOf[EditText]
    val tips: SeekBar = findViewById(R.id.add_tips_seekbar).asInstanceOf[SeekBar]
    val tipsAmount: TextView = findViewById(R.id.tips_amount).asInstanceOf[TextView]
    val afterTips: TextView = findViewById(R.id.after_tips).asInstanceOf[TextView]
    val splits: SeekBar = findViewById(R.id.split_seekbar).asInstanceOf[SeekBar]
    val splitAmount: TextView = findViewById(R.id.split_amount).asInstanceOf[TextView]
    val afterSplits: TextView = findViewById(R.id.after_splits).asInstanceOf[TextView]

    //    Format decimal number to two decimal places
    val decimalFormat: java.text.DecimalFormat = new java.text.DecimalFormat("#.00")

    //  Toast layout ando configuration
    val inflater = getLayoutInflater()
    val layout = inflater.inflate(R.layout.toast,
      findViewById(R.id.toast_layout).asInstanceOf[ViewGroup])
    val toastMsg: TextView = layout.findViewById(R.id.toast_msg).asInstanceOf[TextView]

    //  Toast message with short period of time
    val toast: Toast = new Toast(getApplicationContext())
    toast.setDuration(Toast.LENGTH_SHORT)
    toast.setView(layout)

    /**
     * After tax input watcher
     */
    val afterTaxWatcher = new TextWatcher() {

      //  After total amount is input
      override def afterTextChanged(s: Editable) = {

        //  Get the value from afterTax
        val afterTaxValue = getEditTextValue(afterTax)

        if (afterTaxValue.matches("")) {

          //  Set toast text
          toastMsg.setText("Invalid input - empty amount")

          //  Show toast
          toast.show()

          // Set after tips to empty
          afterTips.setText("")
        } else if (afterTaxValue.matches("0")) {
          //  Set toast text
          toastMsg.setText("Invalid input - amount can not be 0")

          //  Show toast
          toast.show()

          // Set after tips to empty
          afterTips.setText("")
        } else if (afterTaxValue.matches("0.0*")) {
          //  Set toast text
          toastMsg.setText("Invalid input - amount can not be 0")

          //  Show toast
          toast.show()

          // Set after tips to empty
          afterTips.setText("")
        } else {
          afterTips.setText(afterTaxValue)
        }
      }

      override def beforeTextChanged(s: CharSequence,
        start: Int,
        count: Int,
        after: Int) = {
      }

      override def onTextChanged(s: CharSequence,
        start: Int,
        before: Int,
        count: Int) = {
      }
    }

    afterTax.addTextChangedListener(afterTaxWatcher)

    /**
     * Add tips listener
     */
    val tipsListener: OnSeekBarChangeListener = new OnSeekBarChangeListener() {

      override def onStopTrackingTouch(seekBar: SeekBar) = {
        val tipsValue: Int = seekBar.getProgress

        val afterTaxValue = afterTax.getText()

        if (afterTaxValue.toString().equals("")) {
          // Set toast text
          toastMsg.setText("Invalid input - empty amount")

          // Show toast
          toast.show()

          // Set after tips to empty
          afterTips.setText("")
        } else if (afterTaxValue.toString().equals("0")) {
          //  Set toast text
          toastMsg.setText("Invalid input - amount can not be 0")

          // Show toast
          toast.show()

          // Set after tips to empty
          afterTips.setText("")
        } else if (afterTaxValue.toString().equals("0.00")) {
          //  Set toast text
          toastMsg.setText("Invalid input - amount can not be 0")

          // Show toast
          toast.show()

          // Set after tips to empty
          afterTips.setText("")
        } else {
          //  Calculate amount with added tips
          val afterTipsValue =
            java.lang.Double.parseDouble(afterTaxValue.toString()) *
              (1 + (tipsValue / 100.00))

          //  Show selected tips
          tipsAmount.setText(String.valueOf(tipsValue).concat("%"))

          //  Show the amount with added tips
          afterTips.setText(String.valueOf(decimalFormat.format(afterTipsValue)))
        }
      }

      override def onStartTrackingTouch(seekBar: SeekBar) = {
        val tipsValue: Int = seekBar.getProgress

        tipsAmount.setText(String.valueOf(tipsValue).concat("%"))
      }

      override def onProgressChanged(seekBar: SeekBar,
        tipsValue: Int,
        fromUser: Boolean) = {
        tipsAmount.setText(String.valueOf(tipsValue).concat("%"))
      }
    }

    tips.setOnSeekBarChangeListener(tipsListener)

    /**
     * Split check listener
     */
    val splitsListener: OnSeekBarChangeListener = new OnSeekBarChangeListener() {

      override def onStopTrackingTouch(seekBar: SeekBar) = {
        val splitsValue: Int = seekBar.getProgress + 2

        val afterTipsValue = afterTips.getText()

        if (afterTipsValue.toString().equals("")) {
          // Set toast text
          toastMsg.setText("Invalid input - empty amount")

          // Show toast
          toast.show()

          // Set after tips to empty
          afterSplits.setText("")
        } else if (afterTipsValue.toString().equals("0")) {
          //  Set toast text
          toastMsg.setText("Invalid input - amount can not be 0")

          // Show toast
          toast.show()

          // Set after tips to empty
          afterSplits.setText("")
        } else if (afterTipsValue.toString().equals("0.00")) {
          //  Set toast text
          toastMsg.setText("Invalid input - amount can not be 0")

          // Show toast
          toast.show()

          // Set after tips to empty
          afterSplits.setText("")
        } else {
          //  Calculate amount with added tips
          val afterSplitsValue =
            java.lang.Double.parseDouble(afterTipsValue.toString()) / splitsValue

          //  Show selected splits
          splitAmount.setText(String.valueOf(splitsValue))

          //  Show the amount with added tips
          afterSplits.setText(String.valueOf(decimalFormat.format(afterSplitsValue)))
        }
      }

      override def onStartTrackingTouch(seekBar: SeekBar) = {
        val splitsValue: Int = seekBar.getProgress

        splitAmount.setText(String.valueOf(splitsValue))
      }

      override def onProgressChanged(seekBar: SeekBar,
        splitsValue: Int,
        fromUser: Boolean) = {
        splitAmount.setText(String.valueOf(splitsValue + 2))
      }
    }

    splits.setOnSeekBarChangeListener(splitsListener)
  }

  def getEditTextValue(editText: EditText): String = {
    editText.getText.toString
  }

  def getTextViewValue(textView: TextView): Double = {
    val textViewValue = textView.getText.toString

    if (textViewValue.matches("")) 0
    else textViewValue.toString().toDouble
  }

  def getSeekBarValue(seekBar: SeekBar): Int = {
    val seekBarValue = seekBar.getProgress

    seekBarValue
  }

}