package kr.co.lion.project_1

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kr.co.lion.project_1.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {
    lateinit var activityInfoBinding: ActivityInfoBinding

    lateinit var editActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityInfoBinding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(activityInfoBinding.root)

        setLauncher()
        setToolbar()
        setView()
    }
    // 런처 설정
    fun setLauncher(){
        // ModifyActivity 런처
        val contract1 = ActivityResultContracts.StartActivityForResult()
        editActivityLauncher = registerForActivityResult(contract1){

        }
    }

    fun setToolbar() {
        activityInfoBinding.apply {
            toolbarInfo.apply {
                // 타이틀
                title = "메뉴 보기"
                setTitleTextColor(Color.WHITE)
                // back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
                // 메뉴
                inflateMenu(R.menu.input_menu)
                setOnMenuItemClickListener {
                    // 사용자가 선택한 메뉴 항목의 id로 분기한다.
                    when (it.itemId) {
                        // 수정
                        R.id.menuItemInputEdit -> {
                            // ModifyActivity 실행
                            val modifyIntent = Intent(this@InfoActivity, EditActivity::class.java)

                            // 동물 순서값을 저정한다.
                            val position = intent.getIntExtra("position", 0)
                            modifyIntent.putExtra("position", position)

                            editActivityLauncher.launch(modifyIntent)
                        }
                        // 삭제
                        R.id.menuItemInputdel -> {
                            // 항목 순서 값을 가져온다.
                            val position = intent.getIntExtra("position", 0)
                            // 항목 번째 객체를 리스트에서 제거한다.
                            Util.contentList.removeAt(position)
                            finish()
                        }
                    }

                    true
                }
            }
        }
    }
    fun setView() {
        activityInfoBinding.apply {
            textViewInfo.apply {
                // 항목 순서값을 가져온다.
                val position = intent.getIntExtra("position", 0)
                // 포지션 번째 객체를 추출한다.
                val content = Util.contentList[position]

                // 공통
                text = "제목 : ${content.title}\n"
                append("작성 날짜 : ${content.dateAndtime}\n")
                append("내용 : ${content.content}살\n")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // 다른곳 갔다 왔을 경우 출력 내용을 다시 구성해준다.
        setView()
    }
}