package kr.co.lion.project_1

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.project_1.databinding.ActivityMainBinding
import kr.co.lion.project_1.databinding.RowMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    // Activity 런처
    lateinit var inputActivityLauncher : ActivityResultLauncher<Intent>
    lateinit var showActivityLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setLauncher()
        setToolbar()
        setView()
        setEvent()
    }

    fun setLauncher(){
        // InputActivity 런처
        val contract1 = ActivityResultContracts.StartActivityForResult()
        inputActivityLauncher = registerForActivityResult(contract1){

        }

        // ShowActivity 런처
        val contract2 = ActivityResultContracts.StartActivityForResult()
        showActivityLauncher = registerForActivityResult(contract2){

        }
    }

    override fun onResume() {
        super.onResume()
        activityMainBinding.apply {
            // 리사이클러뷰 갱신
            recyclerViewMain.adapter?.notifyDataSetChanged()

        }
    }

    fun setToolbar(){
        activityMainBinding.apply {
            toolbarMain.apply {
                inflateMenu(R.menu.main_menu)
                title = "메모 관리"
                setTitleTextColor(Color.WHITE)

                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuItemMain -> {
                            val inputIntent = Intent(this@MainActivity, InputActivity::class.java)
                            inputActivityLauncher.launch(inputIntent)
                        }
                    }
                    true
                }
            }
        }
    }
    fun setView(){
        activityMainBinding.apply {
            // RecyclerView
            recyclerViewMain.apply {
                // 어뎁터
                adapter = RecyclerViewMainAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(this@MainActivity)
                // 데코레이션
                val deco = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    fun setEvent(){
        activityMainBinding.apply {

        }
    }
    // RecyclerView의 어뎁터
    inner class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolderMain>() {
        // ViewHolder
        inner class ViewHolderMain(rowMainBinding: RowMainBinding) :
            RecyclerView.ViewHolder(rowMainBinding.root) {
            val rowMainBinding: RowMainBinding

            init {
                this.rowMainBinding = rowMainBinding

                this.rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                // 항목을 누르면 ShowActivity를 실행한다.
                this.rowMainBinding.root.setOnClickListener {
                    val showIntent = Intent(this@MainActivity, InfoActivity::class.java)
                    // 현재 번째의 순서값을 담아준다.
                    showIntent.putExtra("position", adapterPosition)

                    showActivityLauncher.launch(showIntent)
                }

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMain {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
            val viewHolderMain = ViewHolderMain(rowMainBinding)

            return viewHolderMain
        }

        override fun getItemCount(): Int {
            return Util.contentList.size
        }

        override fun onBindViewHolder(holder: ViewHolderMain, position: Int) {

            val content = Util.contentList[position]

            holder.rowMainBinding.textView.text = "제목: ${content.title}\n"
            holder.rowMainBinding.textView.append("작성 날짜: ${content.date}")
        }
    }
}
