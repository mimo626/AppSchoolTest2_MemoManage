package kr.co.lion.project_1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.project_1.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    lateinit var activityEditBinding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityEditBinding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(activityEditBinding.root)

        setToolbar()
        initView()
    }

    fun setToolbar(){
        activityEditBinding.apply {
            toolbarEdit.apply {
                // 타이틀
                title = "메뉴 수정"
                setTitleTextColor(Color.WHITE)
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
                // 메뉴
                inflateMenu(R.menu.simple_menu)
                setOnMenuItemClickListener {
                    modifyData()
                    finish()
                    true
                }
            }
        }

    }
    fun initView(){
        activityEditBinding.apply {
            // 위치값을 가져온다.
            val position = intent.getIntExtra("position", 0)
            // position번째 객체를 가져온다.
            val content = Util.contentList[position]

            editTextEditTitle.setText(content.title)
            editTextEditContent.setText(content.content)
        }

    }
    // 수정 처리
    fun modifyData() {
        // 위치값을 가져온다.
        val position = intent.getIntExtra("position", 0)
        // position 번째 객체를 가져온다.
        val content = Util.contentList[position]

        activityEditBinding.apply {
            content.title = editTextEditTitle.text.toString()
            content.content = editTextEditContent.text.toString()
        }
    }
}