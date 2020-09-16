package com.picpay.desafio.android.feature.user.ui.activity

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.feature.user.data.datasource.remote.PicPayService
import com.picpay.desafio.android.R
import com.picpay.desafio.android.feature.user.data.model.UserPayload
import com.picpay.desafio.android.feature.user.ui.UsersViewModel
import com.picpay.desafio.android.feature.user.ui.adapter.UserListAdapter
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter

    private val viewModel by viewModel<UsersViewModel>()

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onResume() {
        super.onResume()

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)

        adapter =
            UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        progressBar.visibility = View.VISIBLE
        service.getUsers()
            .enqueue(object : Callback<List<UserPayload>> {
                override fun onFailure(call: Call<List<UserPayload>>, t: Throwable) {
                    val message = getString(R.string.error)

                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE

                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<List<UserPayload>>,
                    response: Response<List<UserPayload>>
                ) {
                    progressBar.visibility = View.GONE

                    adapter.users = response.body()!!
                }
            })
    }
}
