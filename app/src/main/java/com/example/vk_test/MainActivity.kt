//___________________MainActivity_________________________
package com.example.vk_test
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

//constants
const val LIMIT_SEARCH_QUERY = 25 //Число загружаемых гифок
const val API_KEY = "GXza97tMvqscoGacCogX8qbblRJ47yJb"//Ключ
const val BASE_URL="https://api.giphy.com/v1/"
const val TAG="MainActivity"

class MainActivity : AppCompatActivity()
{
    lateinit var searchView: SearchView

    //RETROFIT
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

    //SERVICE
    val  retroService=retrofit.create(DataService::class.java)
    val gifs = mutableListOf<DataObject>()
    val adapter=GifsAdapter(this,gifs)



    override fun onCreate(savedInstanceState: Bundle?)
      {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_main)
          val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
          recyclerView.adapter=adapter
          recyclerView.setHasFixedSize(true)
          recyclerView.layoutManager=GridLayoutManager(this,2)//два столбца с GIF
          //
          adapter.setOnItemClickListener(object : GifsAdapter.OnItemClickListener
          {
              override fun onItemClick(position: Int)
              {
                  val intent = Intent(this@MainActivity, SecondActivity::class.java)
                  intent.putExtra("url",gifs[position].images.ogImage.url)
                  intent.putExtra("title",gifs[position].title)
                  startActivity(intent)
              }

          })


          //Получение трендовых гифок и заполнение списка
          retroService.getGifs(LIMIT_SEARCH_QUERY, API_KEY).enqueue(object : Callback<DataResult?>
          {
              override fun onResponse(call: Call<DataResult?>, response: Response<DataResult?>)
              {
                  val body = response.body()
                  if(body==null)
                  {
                      Log.d(TAG,"onResponse: No Response")
                  }
                  gifs.addAll(body!!.res)//заполняем список
                  adapter.notifyDataSetChanged()

              }

              override fun onFailure(call: Call<DataResult?>, t: Throwable) {
                  TODO("Not yet implemented")
              }
          })
          //Работа с "окном поиска"
          searchView = findViewById(R.id.searchView)
          searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

              override fun onQueryTextSubmit(query: String): Boolean
              {
                  GetSearch(query)//вызываем функцию поиска, передаем "искомое" слово
                  return false
              }
              override fun onQueryTextChange(newText: String): Boolean {
                  return false
              }
          })

      }
    //поиск
    fun GetSearch(query: String){
    retroService.searchGifs(query,LIMIT_SEARCH_QUERY, API_KEY).enqueue(object : Callback<DataResult?>
    {
        override fun onResponse(call: Call<DataResult?>, response: Response<DataResult?>)
        {
            gifs.clear();//очищаем список
            val body = response.body()
            if(body==null)
            {
                Log.d(TAG,"onResponse: No Response")
            }
            gifs.addAll(body!!.res)//загружаем новые данные
            adapter.notifyDataSetChanged()
        }

        override fun onFailure(call: Call<DataResult?>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })}

}