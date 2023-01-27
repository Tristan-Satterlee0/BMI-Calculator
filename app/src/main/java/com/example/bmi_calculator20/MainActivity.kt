package com.example.bmi_calculator20

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.bmi_calculator20.databinding.ActivityMainBinding
import java.math.RoundingMode
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.CalculateButton.setOnClickListener {calculateBMI()}

        binding.weightEnterEditText.setOnKeyListener{ view, keyCode, _ ->
            handleKeyEvent(view, keyCode)}

        binding.heightEnterEditText.setOnKeyListener{view, keyCode, _ ->
            handleKeyEvent(view, keyCode)}

        binding.heightInchesEnterEditText.setOnKeyListener{view, keyCode, _ ->
            handleKeyEvent(view, keyCode)}
    }

    private fun calculateBMI() {
        //converting entered metrics to strings
        val heightFeet = binding.heightEnterEditText.text.toString()
        val heightInches = binding.heightInchesEnterEditText.text.toString()
        val weight = binding.weightEnterEditText.text.toString()

        //converting entered metrics from strings to Doubles
        val dubHeight = heightFeet.toDouble()
        val dubHeightInches = heightInches.toDouble()
        val dubWeight = weight.toDouble()

        //converter entered height to inches
        val heightInInches = (dubHeight * 12) + dubHeightInches

        /*
        bmi will be made a double
        BMI is calculated at BMI = 703 * (weight / in^2)
         */
        val bmi = 703 * (dubWeight / (heightInInches * heightInInches)).toDouble()

        //rounds the bmi to nearest tenths place
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING

        val formattedBMI = df.format(bmi)

        //set values for the 4 results of the BMI scale
        val underweight: String = " Underweight"
        val normalWeight: String = " Normal Weight"
        val overweight: String = " Overweight"
        val obese: String = " Obese"

        //if statement determining if you are one of the 4 categories
        if(bmi <= 18.5) {
            binding.BMIResult.text =
                getString(R.string.bmi_number, formattedBMI) + underweight
        } else if (bmi <= 24.9) {
            binding.BMIResult.text =
                getString(R.string.bmi_number, formattedBMI) + normalWeight
        } else if (bmi <= 29.9) {
            binding.BMIResult.text =
                getString(R.string.bmi_number, formattedBMI) + overweight
        } else {
            binding.BMIResult.text = getString(R.string.bmi_number, formattedBMI) + obese
        }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            //hide keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
    //maybe adding a comment will let me commit so i can send the files to GitHub?
}