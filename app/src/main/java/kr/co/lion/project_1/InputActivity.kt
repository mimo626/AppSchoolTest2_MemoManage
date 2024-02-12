package kr.co.lion.project_1

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.project_1.databinding.ActivityInputBinding
import java.time.LocalDate

class InputActivity : AppCompatActivity() {

    lateinit var activityInputBinding: ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityInputBinding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(activityInputBinding.root)

        setToolbar()
        initView()
        setEvent()
    }

    fun setToolbar(){
        activityInputBinding.apply {
            toolbarInput.apply {
                // 타이틀
                title = "메뉴 작성"
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
                    when(it.itemId){
                        // Done
                        R.id.menuItemDone -> {
                            // 유효성 검사 메서드를 호출한다.
                            checkInput()
                        }
                    }
                    true
                }
            }
        }
    }
    fun initView(){
        // 이름 입력칸에 포커스를 준다.
        Util.showSoftInput(activityInputBinding.editTextWriteTitle, this@InputActivity)
    }
    fun setEvent(){

    }
    // 입력 유효성 검사
    fun checkInput() {
        activityInputBinding.apply {
            // 제목
            val name = editTextWriteTitle.text.toString()
            if (name.trim().isEmpty()) {
                Util.showInfoDialog(
                    this@InputActivity,
                    "제목 입력 오류",
                    "제목을 입력해주세요"
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showSoftInput(editTextWriteTitle, this@InputActivity)
                }
                return
            }

            // 내용
            val age = editTextWriteContent.text.toString()
            if (age.trim().isEmpty()) {
                Util.showInfoDialog(
                    this@InputActivity,
                    "내용 입력 오류",
                    "내용을 입력해주세요"
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showSoftInput(editTextWriteContent, this@InputActivity)
                }
                return
            }
            // 저장처리
            addAnimalData()
            // 액티비티를 종료한다.
            finish()
        }
    }

    // 저장처리
    fun addAnimalData(){
        activityInputBinding.apply {
            val content = Content()

            content.title = editTextWriteTitle.text.toString()
            content.content = editTextWriteContent.text.toString()
            content.date = LocalDate.now()

            Util.contentList.add(content)
        }
    }

}