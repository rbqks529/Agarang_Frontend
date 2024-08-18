package com.example.myapplication.Setting

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Data.Response.HomeChangeCharResponse
import com.example.myapplication.Data.Response.HomeSettingResponse
import com.example.myapplication.R
import com.example.myapplication.Retrofit.HomeIF
import com.example.myapplication.Retrofit.RetrofitService
import com.example.myapplication.databinding.FragmentChangeCharBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//캐릭터 변경 -> 초기
class ChangeCharFragment : Fragment() {

    /*private val imageResources = intArrayOf(
        R.drawable.mouse_1,
        R.drawable.cow_1,
        R.drawable.tiger_1,
        R.drawable.rabbit_1,
        R.drawable.dragon_1,
        R.drawable.snake_1,
        R.drawable.horse_1,
        R.drawable.sheep_1,
        R.drawable.monkey_1,
        R.drawable.chick_1,
        R.drawable.dog_1,
        R.drawable.pig_1
    )
    private val imageResources2 = intArrayOf(
        R.drawable.mouse_2,R.drawable.cow_2,R.drawable.tiger_2,R.drawable.rabbit_2,R.drawable.dragon_2, R.drawable.snake_2,
        R.drawable.horse_2,R.drawable.sheep_2,R.drawable.monkey_2,R.drawable.chick_2,R.drawable.dog_2,R.drawable.pig_2
    )

    private val names = arrayOf(
        "쥐", "소", "호랑이", "토끼", "용", "뱀", "말", "양", "원숭이", "닭", "개", "돼지"
    )
    private val descriptions = arrayOf(
        "똑똑하고 재치 있는 아기가 될 거예요. 작은 손발로도 무엇이든 잘 할 수 있는 만능 재주꾼!",
        "성실하고 꾸준히 노력하는 아기가 될 거예요. 느긋하면서도 듬직한 작은 힘꾼!",
        "씩씩하고 용맹한 아기가 될 거예요. 어디서든 당당하게 자신을 표현할 수 있는 강한 리더!",
        "상냥하고 부드러운 아기가 될 거예요. 조용하고 차분하게 주위를 따뜻하게 만드는 매력쟁이!",
        "지혜롭고 카리스마 있는 아기가 될 거예요. 언제나 빛나는 존재로 성장할 작은 리더!",
        "깊이 있는 지혜와 통찰력을 가진 아기가 될 거예요. 차분하고 세심하게 모든 것을 알아가는 꼬마 탐정!",
        "활발하고 자유로운 아기가 될 거예요. 어디든 자유롭게 달려가는 작은 모험가!",
        "온화하고 따뜻한 마음을 가진 아기가 될 거예요. 모두에게 사랑받는 부드러운 작은 천사!",
        "재치 있고 창의적인 아기가 될 거예요. 유쾌하게 모두를 웃게 만드는 장난꾸러기!",
        "성실하고 부지런한 아기가 될 거예요. 아침을 깨우는 작은 부지런쟁이!",
        "진실하고 충성스러운 아기가 될 거예요. 언제나 친구들과 함께하는 든든한 작은 수호자!",
        "풍요롭고 복이 많은 아기가 될 거예요. 웃음이 넘치고 모두에게 사랑받는 작은 행복이!"
    )*/

    lateinit var binding: FragmentChangeCharBinding
    private lateinit var changeCharAdapter: ChangeCharAdapter
    private var selectedCharacterId: Int = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeCharBinding.inflate(inflater, container, false)

        fetchCharacterData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // GridView 아이템 클릭 시 이벤트 처리
        binding.gvChange.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                selectedCharacterId = (changeCharAdapter.getItem(position) as HomeChangeCharResponse.Character).characterId
                changeCharAdapter.setSelectedPosition(position)
            }

        // 완료 버튼 클릭 시 이벤트 처리
        binding.finishButton.setOnClickListener {
            if (selectedCharacterId != -1) {
                saveSelectedCharacter(selectedCharacterId)
                Toast.makeText(requireContext(), "캐릭터가 선택되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "캐릭터를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun fetchCharacterData() {
        val service = RetrofitService.createRetrofit(requireContext()).create(HomeIF::class.java)

        service.getCharacterData().enqueue(object : Callback<HomeChangeCharResponse> {
            override fun onResponse(
                call: Call<HomeChangeCharResponse>,
                response: Response<HomeChangeCharResponse>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()

                    if (apiResponse != null && apiResponse.isSuccess) {
                        setupGridView(apiResponse.result)
                    } else {
                        Log.e("오류", "API 요청이 성공하지 못했습니다: ${apiResponse?.message}")
                    }
                } else {
                    Log.e("오류", "Response error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<HomeChangeCharResponse>, t: Throwable) {
                Log.e("오류", "API 요청 실패: ${t.message}")
            }
        })
    }

    private fun setupGridView(characters: List<HomeChangeCharResponse.Character>) {
        changeCharAdapter = ChangeCharAdapter(requireContext(), characters) { selectedCharacter ->
            // 캐릭터 선택 시 처리 로직
            saveSelectedCharacter(selectedCharacter.characterId)
            updateCharacterInView(selectedCharacter)
        }

        binding.gvChange.adapter = changeCharAdapter
    }

    private fun saveSelectedCharacter(characterId: Int) {
        val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("selected_char", characterId).apply()
    }

    private fun updateCharacterInView(character: HomeChangeCharResponse.Character) {
        // 선택된 캐릭터 정보를 UI에 반영하는 로직
        // 이 예시에서는 단순히 이미지를 업데이트하지만, 추가적인 로직이 있을 수 있습니다.
        // 예: 이미지 리소스를 매핑하는 로직 필요
        Toast.makeText(requireContext(), "선택된 캐릭터: ${character.name}", Toast.LENGTH_SHORT).show()
    }
}





