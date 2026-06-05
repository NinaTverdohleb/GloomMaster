package com.rumpilstilstkin.gloomhavenhelper

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GlHelperApp : Application()
