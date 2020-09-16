package com.picpay.desafio.android.feature.user.ui.activity

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.commom.data.model.State
import com.picpay.desafio.android.feature.user.data.model.UserData
import com.picpay.desafio.android.feature.user.data.model.UserPayload
import com.picpay.desafio.android.feature.user.domain.extensions.mapToUserModel
import com.picpay.desafio.android.feature.user.domain.model.UserModel
import com.picpay.desafio.android.feature.user.ui.MainActivityInteractor
import com.picpay.desafio.android.feature.user.ui.UsersViewModel
import com.picpay.desafio.android.feature.user.ui.adapter.UserListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_user.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(R.layout.activity_main), MainActivityInteractor.View {

    private val adapter: UserListAdapter by lazy {
        UserListAdapter()
    }

    private val viewModel by viewModel<UsersViewModel>()

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        configureView()
        observeResults()
        return super.onCreateView(name, context, attrs)
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
        showError()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun showData(users: List<UserModel>) {
        progressBar.visibility = View.GONE
        adapter.users = users
    }

    override fun showError() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        Toast.makeText(this@MainActivity, getString(R.string.error), Toast.LENGTH_SHORT)
            .show()
    }
}
