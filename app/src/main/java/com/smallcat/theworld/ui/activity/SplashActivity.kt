package com.smallcat.theworld.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.smallcat.theworld.model.db.*
import com.smallcat.theworld.utils.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jxl.Workbook
import org.litepal.crud.DataSupport

class SplashActivity : AppCompatActivity() {

    private val FILE_NAME = "theWorld.xls"
    private val FILE_NAME2 = "boss.xls"
    private val tabTitles = arrayOf("武器", "头盔", "衣服", "饰品", "翅膀")
    private val types = arrayOf("boss", "材料", "徽章", "其他")

    private val exclusiveList = ArrayList<Exclusive>()
    private val equipList = ArrayList<Equip>()
    private val bossList = ArrayList<Boss>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fitSystemAllScroll(this)
        initData()
    }

    private fun initData(){
        val oldVersion = sharedPref.versionName
        val newVersion = AppUtils.getVerName(this)
        var isFirst = sharedPref.isFirst

        if (oldVersion != newVersion){
            isFirst = true
            CleanMessageUtil.cleanApplicationData(this)
            AppUtils.clean()
            sharedPref.versionName = newVersion
        }

        if (isFirst) {
            //第一次打开
            getData()
        } else {
            Handler().postDelayed({
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 500)
        }
    }

    private fun getData(){
        Observable.create<String> {
            it.onNext("初始化数据中，请耐心等待QAQ")
            getXlsData(FILE_NAME)
            getXlsData(FILE_NAME2)
            saveData()
            it.onComplete()
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    ToastUtil.shortShow(it)
                },{
                    ToastUtil.shortShow("加载错误,请重试")
                },{
                    sharedPref.isFirst = false
                    equipList.clear()
                    bossList.clear()
                    exclusiveList.clear()
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                })
    }

    private fun getXlsData(xlsName: String) {
        val assetManager = assets
        try {
            val workbook = Workbook.getWorkbook(assetManager.open(xlsName))//jxl只能创建一个wookbook对象
            val sheetNum = workbook.numberOfSheets
            for (k in 0 until sheetNum) {
                val sheet = workbook.getSheet(k)
                val sheetRows = sheet.rows
                /* int sheetColumns = sheet.getColumns();
                Log.d("AAA", "the num of sheets is " + sheetNum);
                Log.d("AAA", "the name of sheet is  " + sheet.getName());
                Log.d("AAA", "total rows is 行=" + sheetRows);
                Log.d("AAA", "total cols is 列=" + sheetColumns);*/
                if (xlsName == FILE_NAME) {
                    if (k == 5) {
                        for (i in 1 until sheetRows) {//从第二行开始读
                            val exclusive = Exclusive()
                            exclusive.equipName = sheet.getCell(0, i).contents
                            exclusive.profession = sheet.getCell(1, i).contents
                            exclusive.skill = sheet.getCell(2, i).contents
                            exclusive.effect = sheet.getCell(3, i).contents
                            exclusiveList.add(exclusive)
                        }
                    } else {
                        val imgArray: IntArray = PictureRes.getImgList(tabTitles[k])
                        for (i in 1 until sheetRows) {//从第二行开始读
                            val equip = Equip()
                            equip.equipName = (sheet.getCell(0, i).contents)
                            equip.quality = (sheet.getCell(1, i).contents)
                            equip.equipmentProperty = (sheet.getCell(2, i).contents)
                            equip.level = (sheet.getCell(3, i).contents)
                            val access = sheet.getCell(4, i).contents
                            equip.access = access
                            equip.dataList = AppUtils.needEquip(access)
                            equip.exclusive = sheet.getCell(5, i).contents
                            equip.type = tabTitles[k]
                            equip.imgId = imgArray[i - 1]
                            equipList.add(equip)
                        }
                    }
                }else{
                    if (k == 0) {
                        val imgArray: IntArray = PictureRes.getImgList(types[k])
                        for (i in 1 until sheetRows) {//从第二行开始读
                            val boss = Boss()
                            boss.bossName = sheet.getCell(0, i).contents
                            boss.hp = sheet.getCell(1, i).contents
                            boss.power = sheet.getCell(2, i).contents
                            boss.nail = sheet.getCell(3, i).contents
                            boss.resistance = sheet.getCell(4, i).contents
                            boss.distance = sheet.getCell(5, i).contents
                            boss.level = sheet.getCell(6, i).contents
                            boss.skill = sheet.getCell(7, i).contents
                            boss.strategy = sheet.getCell(8, i).contents
                            boss.drop = sheet.getCell(9, i).contents
                            boss.call = sheet.getCell(10, i).contents
                            boss.imgId = imgArray[i - 1]
                            bossList.add(boss)
                        }
                    } else {
                        val imgArray: IntArray = PictureRes.getImgList(types[k])
                        for (i in 1 until sheetRows) {//从第二行开始读
                            val equip = Equip()
                            equip.equipName = (sheet.getCell(0, i).contents)
                            val access = sheet.getCell(1, i).contents
                            equip.access = access
                            equip.equipmentProperty = (sheet.getCell(2, i).contents)
                            equip.dataList = AppUtils.needEquip(access)
                            equip.type = types[k]
                            equip.imgId = imgArray[i - 1]
                            equipList.add(equip)
                        }
                    }
                }
            }
            workbook.close()
        } catch (e: Exception) {
            LogUtil.e("read error=$e")
        }
    }

    private fun saveData(){
        DataSupport.saveAll(equipList)
        DataSupport.saveAll(exclusiveList)
        DataSupport.saveAll(bossList)
    }
}
