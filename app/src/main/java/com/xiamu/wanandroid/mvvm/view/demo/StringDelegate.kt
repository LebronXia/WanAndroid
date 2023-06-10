package com.xiamu.wanandroid.mvvm.view.demo

import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by zxb in 2022/1/20
 */

//自定义委托
class StringDelegate(private var s: String = "Hello"): ReadWriteProperty<Owner, String> {

    //对于 var 修饰的属性，我们必须要有 getValue、setValue 这两个方法，同时，这两个方法必须有 operator 关键字修饰。
    override operator fun getValue(thisRef: Owner, property: KProperty<*>): String {
        return s
    }
    override operator fun setValue(thisRef: Owner, property: KProperty<*>, value: String) {
        s = value
    }
}


class SmartDelegator {

    //属性委托之前再做一些额外的判断工作，我们可以使用 provideDelegate 来实现。
    operator fun provideDelegate(
        thisRef: Owner,
        prop: KProperty<*>
    ): ReadWriteProperty<Owner, String> {

        return if (prop.name.contains("log")) {
            StringDelegate("log")
        } else {
            StringDelegate("normal")
        }
    }
}

class Owner {
    var normalText: String by SmartDelegator()
    var logText: String by SmartDelegator()
}

fun main() {
    val owner = Owner()
    println(owner.normalText)
    println(owner.logText)
}


operator fun TextView.provideDelegate(value: Any?, property: KProperty<*>) = object : ReadWriteProperty<Any?, String?> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String? = text.toString()
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
        text = value
    }
}

//map委托



