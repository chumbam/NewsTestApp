package com.example.newsapptest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapptest.R
import com.example.newsapptest.databinding.FragmentArticleBinding
import com.example.newsapptest.ui.MainNewsActivity
import com.example.newsapptest.ui.NewsViewModel


class ArticleFragment : Fragment() {

    lateinit var viewModel: NewsViewModel
    lateinit var mBinding: FragmentArticleBinding
    // Получаем аргументы которые мы передаем с разных фрагментов через Nav Components
    private val arguments: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentArticleBinding.inflate(layoutInflater)
        return mBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel  = (activity as MainNewsActivity).viewModel
        val articleInstance = arguments.article

        mBinding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(articleInstance.url)
        }
    }

}