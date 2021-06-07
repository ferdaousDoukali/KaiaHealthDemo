package com.kaiahealth.demo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kaiahealth.demo.databinding.ActivityMainBinding
import com.kaiahealth.demo.utils.viewBindingDelegate

class MainActivity : AppCompatActivity() {

    private val viewBinding by viewBindingDelegate(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

    }
}