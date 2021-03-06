package ru.justd.githubrepos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.get
import ru.justd.githubrepos.app.Router

/**
 * Тестовое github api два фрагмента, первый строка ввода (пользователь вводит имя
 * github user и на первом экране смотри список репозиториев. Клик по репозиторию
 * показывает список коммитов. Нельзя использовать retrofit, можно gson, okhttp.
 * поворот должен быть проработан. Делай как приложение в прод. Котлин корутины.
 *
 * https://developer.github.com/v3/repos/#list-user-repositories
 */
class MainActivity : AppCompatActivity() {

    private val router by lazy { get<Router>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        router.init(this)

        if (savedInstanceState == null) {
            router.showSearchRepositoriesPage()
        }
    }
}
