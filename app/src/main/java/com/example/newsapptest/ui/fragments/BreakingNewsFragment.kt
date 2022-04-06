package com.example.newsapptest.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapptest.R
import com.example.newsapptest.adapters.ArticlesAdapter
import com.example.newsapptest.databinding.FragmentBreakingNewsBinding
import com.example.newsapptest.ui.MainNewsActivity
import com.example.newsapptest.ui.NewsViewModel
import com.example.newsapptest.utils.Resource


class BreakingNewsFragment : Fragment() {

    val TAG = "BreakingNewsFragment"

    lateinit var viewModel: NewsViewModel
    lateinit var articlesAdapter: ArticlesAdapter
    lateinit var mBinding: FragmentBreakingNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentBreakingNewsBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel  = (activity as MainNewsActivity).viewModel
        setupRV()

        //Кастомная функция для обработки щелчка по статье и передачи этой статью в соответствующий экран
        articlesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle)
        }

        viewModel.brNews.observe(viewLifecycleOwner) { responseNews ->

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

    private fun hideProgressBar(){
        mBinding.paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun discloseProgressBar(){
        mBinding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRV() {
        articlesAdapter = ArticlesAdapter()
        mBinding.rvBreakingNews.apply {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}