package br.unifor.cct.recyclerview.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.unifor.cct.recyclerview.R
import br.unifor.cct.recyclerview.adapter.RandomUserAdapter
import br.unifor.cct.recyclerview.repository.RandomUserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(){

    private lateinit var userList:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val randomUserAdapter = RandomUserAdapter(null)
        val viewLayout = LinearLayoutManager(this)

        userList = findViewById<RecyclerView>(R.id.main_recyclerview_user_list).apply {
            layoutManager = viewLayout
            adapter = randomUserAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            var randomUser = RandomUserRepository.getRandomUsers(10)
            var aux = RandomUserRepository.getRandomUsers(10)

            var adapter = userList.adapter as RandomUserAdapter

            if(adapter.randomUser == null){
                adapter.randomUser = randomUser
            }else{
                adapter?.randomUser?.results?.addAll(randomUser?.results?.toMutableList()!!)
            }

            val handler = Handler(Looper.getMainLooper())
            handler.post{
                adapter.notifyDataSetChanged()
                userList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (!recyclerView.canScrollVertically(1)) {
                            Thread(Runnable{
                                aux = RandomUserRepository.getRandomUsers(5)
                                runOnUiThread{
                                  adapter?.randomUser?.results?.addAll(aux?.results?.toMutableList()!!)
                                }
                            }).start()
                            adapter.notifyDataSetChanged()
                        }
                    }
                })
            }
        }

    }



}