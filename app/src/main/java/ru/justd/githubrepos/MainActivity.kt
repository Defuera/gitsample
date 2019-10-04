package ru.justd.githubrepos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Тестовое github api два фрагмента, первый строка ввода (пользователь вводит имя
 * github user и на первом экране смотри список репозиториев. Клик по репозиторию
 * показывает список коммитов. Нельзя использовать retrofit, можно gson, okhttp.
 * поворот должен быть проработан. Делай как приложение в прод. Котлин корутины.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
