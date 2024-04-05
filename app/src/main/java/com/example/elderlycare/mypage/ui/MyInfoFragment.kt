package com.example.elderlycare.mypage.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.elderlycare.R
import com.example.elderlycare.databinding.FragmentMyInfoBinding
import com.example.elderlycare.mypage.service.SeniorPageService
import com.example.elderlycare.mypage.vo.SeniorDTO
import com.example.elderlycare.utils.Constants
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.prefs.Preferences

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentMyInfoBinding
    private lateinit var retrofit: Retrofit
    private lateinit var service:  SeniorPageService
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_my_info, container, false)
        binding = FragmentMyInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // 프로필 사진
        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )
        {
            try {
                val calRatio = calculateInSampleSize(
                    it.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.imgSize)
                )
                val option = BitmapFactory.Options()
                option.inSampleSize = calRatio

                var inputStream = requireActivity().contentResolver.openInputStream(it.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close()
                inputStream = null

                bitmap?.let {
                    binding.userImageView.setImageBitmap(bitmap)
                } ?: let {
                    Log.d(">>", "bitmap null")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 갤러리 버튼 클릭 리스너 설정
        binding.galleryButton.setOnClickListener {
            // 갤러리 앱에서 이미지 선택
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }


        // 나의 정보 불러오기
        setupRetrofit()
//        val preferences = this.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // SharedPreferences에서 userEmail 가져오기
//        val userEmail = preferences.getString("user.email", )
        getSeniorInfo(21) // 사용자 로그인 아이디 줘야함

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment myInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = requireActivity().contentResolver.openInputStream(fileUri)

            //inJustDecodeBounds 값을 true 로 설정한 상태에서 decodeXXX() 를 호출.
            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정 된다.
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //비율 계산........................
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }


    private fun setupRetrofit() {
        val client = setupOkHttpClient()

        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL+"/m/seniorPage/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        service = retrofit.create(SeniorPageService::class.java)
    }

    private fun setupOkHttpClient(): OkHttpClient {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        val cookieJar = JavaNetCookieJar(cookieManager)

        return OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private fun getSeniorInfo(userId: Long) {
        service.SeniorInfo(userId).enqueue(object : Callback<SeniorDTO> {
            override fun onResponse(call: Call<SeniorDTO>, response: Response<SeniorDTO>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val seniorDTO = response.body()
                        Log.d(">>>", "dto: ${seniorDTO?.toString()}")
                        binding.tvName.text = seniorDTO?.name
                        binding.tvEmail.text = seniorDTO!!.email
                        binding.tvRole.text = seniorDTO?.roleStr
                        binding.edAddress.setText(seniorDTO?.address ?: "")
                        binding.edPhone.setText(seniorDTO.phoneNumber ?: "")
                        binding.edHealth.setText(seniorDTO.health ?: "")
                        binding.edReq.setText(seniorDTO.requirements ?: "")
                    }
                } else {
                    Log.e(">>", "Failed to fetch user info")
                }
            }

            override fun onFailure(call: Call<SeniorDTO>, t: Throwable) {
                Log.e(">>", "Error: ${t.message}", t)
            }
        })
    }



}