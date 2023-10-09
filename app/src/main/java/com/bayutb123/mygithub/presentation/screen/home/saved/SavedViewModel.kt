package com.bayutb123.mygithub.presentation.screen.home.saved

import com.bayutb123.mygithub.domain.model.User
import com.bayutb123.mygithub.domain.usecase.DatabaseUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavedViewModel @Inject constructor(
    private val savedUserUseCase: DatabaseUseCase
) {
    init {
        CoroutineScope(Dispatchers.IO).launch {
            savedUserUseCase.getAllSavedUsers()
            savedUserUseCase.saveUser(User(1, "Aasd", "1233", "asd","asd","asd","asd", false))
        }
    }
}