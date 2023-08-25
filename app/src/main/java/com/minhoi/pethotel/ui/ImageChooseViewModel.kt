package com.minhoi.pethotel.ui

import android.app.Application
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageChooseViewModel(application : Application) : AndroidViewModel(application) {
    private val _images = MutableLiveData<List<Uri>>()
    val images : LiveData<List<Uri>> = _images


    private fun getCursor() : Cursor? {
        val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        //쿼리가 반환해야 할 열의 집합 = projection
        val projection = arrayOf(
            MediaStore.Images.ImageColumns.DATA, // 파일 경로
            MediaStore.Images.ImageColumns.DISPLAY_NAME, // 이름
            MediaStore.Images.ImageColumns.SIZE, // 크기
            MediaStore.Images.ImageColumns.DATE_TAKEN, // 날짜
            MediaStore.Images.ImageColumns.DATE_ADDED, // 추가된 날짜
            MediaStore.Images.ImageColumns._ID // 고유 ID
        )
        // 모든 이미지 가져옴(조건 X)
        val selection : String? = null
        var selectionArgs : Array<String>? = null
        val sortOrder = "${MediaStore.Images.ImageColumns.DATE_TAKEN} DESC"

        //데이터베이스 쿼리에서 반환된 결과 테이블의 행들을 가르키는 것
        val cursor = getApplication<Application>().contentResolver.query(
            uriExternal,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        return cursor

    }

    fun getImages() {
        val cursor = getCursor()
        Log.d("vmvmvm", "cursor : ${cursor?.count}")
        val tempImageList = mutableListOf<Uri>()
        //그러나 실제 코드에서는 별도의 스레드에서 비동기식으로 쿼리를 실행해야 합니다.
        viewModelScope.launch(Dispatchers.IO) {
            try{
                when(cursor?.count) {
                    null -> {
                        Log.d("vmvmvm", "getImages: null ")}
                    0 -> {
                        Log.d("vmvmvm", "getImages: 0")
                    }
                    else -> {
                        // 커서를 하나씩 이동하면서 각 행에있는 정보 가져옴
                        while(cursor.moveToNext()) {
                            val idColNum =
                                cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)

                            val id = cursor.getLong(idColNum)

                            val imageUri =
                                ContentUris.withAppendedId(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    id
                                )
                            Log.d("vmvmvm", "getImages: $imageUri ")
                            tempImageList.add(imageUri)
                        }
                    }
                }
            } catch(e : Exception) {
                Log.d("vmvmvm", "getImages: ${e.message} ")
            } finally {
                cursor?.close()
            }
        }
        _images.value = tempImageList
    }
}