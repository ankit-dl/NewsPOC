package com.robosoft.newspoc.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ScrollView
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.robosoft.newspoc.Const
import com.robosoft.newspoc.R
import com.robosoft.newspoc.model.Resource
import com.robosoft.newspoc.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_article_preview.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val mainViewModel: HomeViewModel by viewModel()
    lateinit var newsAdapter: NewsAdapter
    val TAG = "HomeFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("news", it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_detailFragment,
                bundle
            )
        }
        mainViewModel.popularnews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / Const.QUERY_PAGE_SIZE + 2
                        isLastPage = mainViewModel.popularnewsPage == totalPages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

        mainViewModel.topnews.observe(viewLifecycleOwner) {article->
            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
            tvSource.text = article.source?.name
            tvTitle.text = article.title
            tvDescription.text = article.description
        }
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvPopularNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            ViewCompat.setNestedScrollingEnabled(this, false)
            nsv.setOnScrollChangeListener{_,_,_,_,_->
                val lastview = nsv.getChildAt(nsv.childCount - 1)
                val diff = lastview.bottom - (nsv.height + nsv.scrollY)
                if (diff == 0) {
                    mainViewModel.getnews()
                    isScrolling = false
                }
            }



        }
    }
}