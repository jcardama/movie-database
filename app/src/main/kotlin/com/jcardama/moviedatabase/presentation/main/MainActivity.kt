package com.jcardama.moviedatabase.presentation.main

import android.os.Bundle
import com.jcardama.moviedatabase.R
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

}
