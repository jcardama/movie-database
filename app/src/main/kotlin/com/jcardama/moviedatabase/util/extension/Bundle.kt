package com.jcardama.moviedatabase.util.extension

import android.os.Bundle

inline fun bundle(initFun: Bundle.() -> Unit) = Bundle().apply(initFun)
