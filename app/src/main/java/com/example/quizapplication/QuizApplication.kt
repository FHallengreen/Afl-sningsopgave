package com.example.androidexam

import android.app.Application
import com.example.quizapplication.data.AppContainer
import com.example.quizapplication.data.DefaultAppContainer


class QuizApplication: Application() {
        lateinit var container: AppContainer

        override fun onCreate() {
                super.onCreate()
                container = DefaultAppContainer(context = applicationContext)
        }
}