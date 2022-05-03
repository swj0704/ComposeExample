package com.wonjoon.compose.utils

sealed class Event {
    object FinishEvent : Event()
    object ErrorEvent : Event()
    object BlankEvent : Event()
}