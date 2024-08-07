package com.example.myapplication.Login

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.myapplication.R

class CustomSpinnerAdapter(context: Context, roles: MutableList<String>) :
    ArrayAdapter<String>(context, R.layout.spinner_item, roles) {

    private var editTextView: EditText? = null
    private var selectedPosition: Int = -1

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = when (position) {
            0 -> getEditTextView(parent)
            else -> getTextView(position, convertView, parent)
        }

        view.setPadding(
            view.paddingLeft,
            if (position == 0) 50 else 30,
            view.paddingRight,
            if (position == 0) 50 else 30
        )

        // EditText일 경우 클릭 이벤트 처리
        if (position == 0) {
            view.setOnClickListener {
                (view as EditText).apply {
                    requestFocus()
                    showSoftKeyboard(context, this)
                }
            }
        }

        return view
    }

    private fun getEditTextView(parent: ViewGroup): EditText {
        return (editTextView ?: EditText(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            hint = "직접 작성할게요"
            setPadding(40, 60, 40, 60)
            setHintTextColor(Color.GRAY)
            setBackgroundResource(R.drawable.ic_login_edittext_background)
            setTextColor(Color.BLACK)
            textSize = 16f
            isFocusableInTouchMode = true
            isFocusable = true

            setOnClickListener {
                requestFocus()
                showSoftKeyboard(context, this)
            }

            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    showSoftKeyboard(context, this)
                }
            }

            setOnEditorActionListener { _, actionId, event ->
                if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.keyCode == android.view.KeyEvent.KEYCODE_ENTER && event.action == android.view.KeyEvent.ACTION_DOWN)
                ) {
                    val newRole = text.toString().trim()
                    if (newRole.isNotEmpty() && !getItems().contains(newRole)) {
                        insert(newRole, 1)  // EditText 다음 위치에 새 역할 추가
                        notifyDataSetChanged()
                        text.clear()  // EditText 내용 초기화
                    }
                    hideSoftKeyboard(context, this)
                    clearFocus()
                    true
                } else {
                    false
                }
            }
        })
    }

    private fun getTextView(position: Int, convertView: View?, parent: ViewGroup): TextView {
        return (convertView as? TextView ?: TextView(context)).apply {
            text = getItem(position)
            setTextColor(Color.parseColor("#484848"))
            textSize = 16f
            typeface = Typeface.create("Pretendard400", Typeface.NORMAL)
            setPadding(40, 20, 40, 20)
            background = if (position == selectedPosition) {
                setTextColor(Color.BLACK)
                typeface = Typeface.create("Pretendard700", Typeface.BOLD)
                ContextCompat.getDrawable(context, R.drawable.ic_login_selected)
            } else {
                null
            }
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return (super.getView(position, convertView, parent) as TextView).apply {
            text = when {
                selectedPosition == 0 -> "역할을 선택해주세요"
                selectedPosition > 0 -> getItem(selectedPosition)
                else -> null
            }
            setTextColor(if (text == "역할을 선택해주세요" || text == "직접 작성할게요") Color.parseColor("#797979") else Color.BLACK)
            textSize = 16f
            typeface = Typeface.create("Pretendard500", Typeface.NORMAL)
        }
    }

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    fun getSelectedRole(): String {
        return when {
            selectedPosition == 0 -> editTextView?.text?.toString() ?: ""
            selectedPosition > 0 -> getItem(selectedPosition) ?: ""
            else -> ""
        }
    }

    private fun getItems(): List<String> {
        return (0 until count).mapNotNull { getItem(it) }
    }

    override fun getCount(): Int {
        return super.getCount()
    }

    override fun getItem(position: Int): String? {
        return super.getItem(position)

    }

    fun showSoftKeyboard(ctx: Context, v: View?) {
        val imm = ctx.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun hideSoftKeyboard(ctx: Context, v: View) {
        val inputManager = ctx.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }
}