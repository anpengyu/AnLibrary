package com.apy.library.utils

import java.io.File

class FileUtils {

    //重命名文件
    fun renameFile(oldPath: String, newPath: String): Boolean {
        val oleFile = File(oldPath)
        val newFile = File(newPath)
        return oleFile.renameTo(newFile)
    }

    //删除单个文件
    fun deleteSingleFile(fileStr:String){
        val file = File(fileStr)
        if(file.exists()){
            file.delete()
        }
    }
}