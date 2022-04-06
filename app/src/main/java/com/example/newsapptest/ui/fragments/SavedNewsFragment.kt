package com.example.newsapptest.ui.fragments

import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapptest.R
import com.example.newsapptest.adapters.ArticlesAdapter
import com.example.newsapptest.databinding.FragmentSavedNewsBinding
import com.example.newsapptest.ui.MainNewsActivity
import com.example.newsapptest.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class SavedNewsFragment : Fragment() {

    lateinit var viewModel: NewsViewModel
    lateinit var articlesAdapter: ArticlesAdapter
    lateinit var mBinding: FragmentSavedNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSavedNewsBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainNewsActivity).viewModel
        setupRV()

        //Кастомная функция для обработки щелчка по статье и передачи этой статью в соответствующий экран
        articlesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }

        //Специальный обработчик для удаления статей из сохраненных с помощью
        val itemClickHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //Определяем позицию элемента который нужно будет удалить
                val pos = viewHolder.bindingAdapterPosition
                val article = articlesAdapter.differ.currentList[pos]
                viewModel.deleteArticle(article)
                //Возможность отменить удаление пока есть снекбар
                Snackbar.make(view, "Статья успешно удалена", Snackbar.LENGTH_LONG).apply {
                    setAction("Отменить") {
                        viewModel.saveArticleInDb(article)
                    }
                    show()
                }

            }
        }

        ItemTouchHelper(itemClickHelperCallback).apply {
            attachToRecyclerView(mBinding.rvSavedNews)
        }

        viewModel.getSavedArticle().observe(viewLifecycleOwner) {
            articlesAdapter.differ.submitList(it)
        }
    }


    private fun setupRV() {
        articlesAdapter = ArticlesAdapter()
        mBinding.rvSavedNews.apply {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}