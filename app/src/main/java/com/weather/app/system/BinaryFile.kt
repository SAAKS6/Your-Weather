package com.weather.app.system

import android.content.Context
import android.widget.Toast
import com.weather.app.user.UserData
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


class BinaryFile {

    val file: String = ""

    fun getData(context: Context): UserData? {

        var userData: UserData?

        try {
            val fileIn: FileInputStream = context.openFileInput("userData.bin")
            val objectIn = ObjectInputStream(fileIn)
            userData = objectIn.readObject() as UserData?
            objectIn.close()
            fileIn.close()

        } catch (e: IOException) {
            userData = null

        } catch (e: ClassNotFoundException) {
            userData = null
        }

        return userData
    }

    fun setData(userData: UserData?, context: Context): Boolean{
        return try {
            val fileOut: FileOutputStream = context.openFileOutput("userData.bin", Context.MODE_PRIVATE)
            val objectOut = ObjectOutputStream(fileOut)
            objectOut.writeObject(userData)
            objectOut.close()
            fileOut.close()

            true
        } catch (e: IOException) {
            Toast.makeText(context, "Unexpected error occurred", Toast.LENGTH_LONG).show()
            false
        }
    }
}