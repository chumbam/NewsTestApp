package com.example.newsapptest.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapptest.R
import com.example.newsapptest.adapters.ArticlesAdapter
import com.example.newsapptest.databinding.FragmentBreakingNewsBinding
import com.example.newsapptest.databinding.FragmentSearchNewsBinding
import com.example.newsapptest.ui.MainNewsActivity
import com.example.newsapptest.ui.NewsViewModel
import com.example.newsapptest.utils.Constants
import com.example.newsapptest.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchNewsFragment : Fragment() {

    val TAG = "SearchNewsFragment"

    lateinit var viewModel: NewsViewModel
    lateinit var articlesAdapter: ArticlesAdapter
    lateinit var mBinding: FragmentSearchNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSearchNewsBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainNewsActivity).viewModel
        setupRV()

        //Кастомная функция для обработки щелчка по статье и передачи этой статью в соответствующий экран
        articlesAdapter.setOnItemClickListener {article ->
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

        //Делаем задержку при поиске по всем новостям, чтобы не нагружать сетевые запросы и приложение
        var job: Job? = null
        mBinding.etSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(Constants.SEARCH_REQUEST_DELAY)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForAllNews(it.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner) { responseNews ->

            when (responseNews) {
                is Resource.Success -> {
                    hideProgressBar()
                    responseNews.data?.let {
                        articlesAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    responseNews.message?.let {
                        Log.e(TAG, "Error is: $it")
                    }
                }
                is Resource.Loading -> {
                    discloseProgressBar()
                }
            }

        }
    }

    private fun hideProgressBar() {
        mBinding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun discloseProgressBar() {
        mBinding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRV() {
        articlesAdapter = ArticlesAdapter()
        mBinding.rvSearchNews.apply {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}