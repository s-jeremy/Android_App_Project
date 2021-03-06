package com.android_app_project.app.ui.view

/**
 * Abstract Event from ViewModel
 */
open class ViewModelEvent

/**
 * Generic Pending Event
 */
object Pending : ViewModelEvent()

/**
 * Generic Success Event
 */
object Success : ViewModelEvent()

/**
 * Generic Failed Event
 */
data class Error(val error: Throwable) : ViewModelEvent()