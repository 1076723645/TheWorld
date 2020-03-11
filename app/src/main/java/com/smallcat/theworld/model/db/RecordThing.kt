package com.smallcat.theworld.model.db

import android.os.Parcel
import android.os.Parcelable
import org.litepal.crud.DataSupport

//存档详情
class RecordThing() : DataSupport(), Parcelable {

    var id: Long = 0
    var recordId: Long = 0//属于哪个记录
    var equipName: String = ""//装备名称
    var equipImg: Int = 0//装备图片
    var type: Int = 0//类别 1期望 2拥有
    var number: Int = 1//拥有数量
    var part: String = ""//部位
    var partId: Int = 0//部位id

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        recordId = parcel.readLong()
        equipImg = parcel.readInt()
        type = parcel.readInt()
        number = parcel.readInt()
        part = parcel.readString()!!
        partId = parcel.readInt()
        equipName = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(recordId)
        parcel.writeInt(equipImg)
        parcel.writeInt(type)
        parcel.writeInt(number)
        parcel.writeString(part)
        parcel.writeInt(partId)
        parcel.writeString(equipName)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "RecordThing(id=$id, recordId=$recordId, equipName='$equipName', equipImg=$equipImg, type=$type, number=$number, part='$part', partId=$partId)"
    }

    companion object CREATOR : Parcelable.Creator<RecordThing> {
        override fun createFromParcel(parcel: Parcel): RecordThing {
            return RecordThing(parcel)
        }

        override fun newArray(size: Int): Array<RecordThing?> {
            return arrayOfNulls(size)
        }
    }


}