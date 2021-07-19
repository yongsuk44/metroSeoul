package com.young.metro.util

import timber.log.Timber


class DebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return String.format("[L:%s] [M:%s] [C:%s]" ,
            element.lineNumber ,
            element.methodName ,
            super.createStackElementTag(element)
        )
    }

//    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
//        super.log(priority, tag, message, t)
//        FirebaseCrashlytics.getInstance().run {
//            t?.let {
//                this.recordException(t)
//            }
//        }
//
//    }
}
