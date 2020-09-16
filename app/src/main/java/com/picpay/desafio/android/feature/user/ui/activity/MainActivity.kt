package com.picpay.desafio.android.feature.user.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.commom.data.model.State
import com.picpay.desafio.android.feature.user.data.model.UserData
import com.picpay.desafio.android.feature.user.domain.extensions.mapToUserModel
import com.picpay.desafio.android.feature.user.domain.model.UserModel
import com.picpay.desafio.android.feature.user.ui.MainActivityInteractor
import com.picpay.desafio.android.feature.user.ui.UsersViewModel
import com.picpay.desafio.android.feature.user.ui.adapter.UserListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), MainActivityInteractor.View {

    private val adapter: UserListAdapter by lazy {
        UserListAdapter()
    }

    private val viewModel by viewModel<UsersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureView()
        observeResults()
    }


    override fun configureView() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun observeResults() {
        viewModel.getUsersLiveData().observe(this, Observer { data ->
            when (data) {
                is State.Loading -> handleLoading(data)
                is State.Success -> handleSuccess(data)
                is State.Failure -> handleError(data)
            }
        })
    }

    override fun handleLoading(result: State.Loading<List<UserData>>) {
        showLoading()
        result.data?.let {
            showData(it.map { item -> item.mapToUserModel() })
        }
    }

    override fun handleSuccess(result: State.Success<List<UserData>>) {
        showData(result.data.map { item -> item.mapToUserModel() })
    }

    override fun handleError(result: State.Failure<List<UserData>>) {
        result.data?.let {
            showData(it.map { item -> item.mapToUserModel() })
        } ?: showError()
    }

    override fun showLoading() {
        user_list_progress_bar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun showData(users: List<UserModel>) {
        user_list_progress_bar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        adapter.users = users
    }

    override fun showError() {
        user_list_progress_bar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        Toast.makeText(this@MainActivity, getString(R.string.error), Toast.LENGTH_SHORT)
            .show()
    }
}
