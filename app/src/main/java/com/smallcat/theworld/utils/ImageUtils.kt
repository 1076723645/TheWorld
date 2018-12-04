package com.smallcat.theworld.utils

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.support.annotation.IntRange

class ImageUtils(val context: Context) {

    private var renderScript: RenderScript? = RenderScript.create(context)

    fun gaussianBlur(@IntRange(from = 1, to = 25) radius:Int,original: Bitmap):Bitmap{
        val input = Allocation.createFromBitmap(renderScript,original)
        val output = Allocation.createTyped(renderScript,input.type)
        val scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        scriptIntrinsicBlur.setRadius(radius.toFloat())
        scriptIntrinsicBlur.setInput(input)
        scriptIntrinsicBlur.forEach(output)
        output.copyTo(original)
        return original
    }
}