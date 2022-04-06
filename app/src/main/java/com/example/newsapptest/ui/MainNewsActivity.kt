package com.example.newsapptest.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapptest.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNewsActivity : AppCompatActivity() {

    val viewModel by viewModels<NewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_news)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val newsNavHostContainer: FragmentContainerView = findViewById(R.id.newsNavHostFragment)

        bottomNavigationView.setupWithNavController(newsNavHostContainer.findNavController())
    }


}