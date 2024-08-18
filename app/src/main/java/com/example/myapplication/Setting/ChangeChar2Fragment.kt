package com.example.myapplication

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
import com.example.myapplication.Data.Response.HomeChangeCharResponse
import com.example.myapplication.Retrofit.HomeIF
import com.example.myapplication.Retrofit.RetrofitService
import com.example.myapplication.Setting.ChangeCharAdapter
import com.example.myapplication.Setting.HomeSettingFragment
import com.example.myapplication.Setting.ItemDetailDialogFragment
import com.example.myapplication.databinding.FragmentChangeCharBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//캐릭터 변경 -> 후기
class ChangeChar2Fragment : Fragment() {

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