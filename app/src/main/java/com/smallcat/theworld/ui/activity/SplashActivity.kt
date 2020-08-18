package com.smallcat.theworld.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.*
import com.smallcat.theworld.utils.*
import com.tbruyelle.rxpermissions2.RxPermissions
import com.tencent.bugly.crashreport.CrashReport
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jxl.Workbook
import org.litepal.crud.DataSupport


class SplashActivity : AppCompatActivity() {

    private val fileName = "theWorld.xls"
    private val fileName2 = "boss.xls"
    private val fileName1 = "hero.xls"
    private val tabTitles = arrayOf("武器", "头盔", "衣服", "饰品", "翅膀")
    private val types = arrayOf("boss", "材料", "藏品", "其他")

    private val exclusiveList = ArrayList<Exclusive>()
    private val equipList = ArrayList<Equip>()
    private val bossList = ArrayList<Boss>()
    private val heroList = ArrayList<Hero>()
    private val skillList = ArrayList<Skill>()
    private val heroStrategyList = ArrayList<HeroStrategy>()
    private val recommendList = ArrayList<EquipRecommend>()

    private var mMediaPlayer: MediaPlayer? = null

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        AppStatusManager.getInstance().appStatus = AppStatusManager.AppStatusConstant.APP_NORMAL
        super.onCreate(savedInstanceState)
        fitSystemAllScroll(this)
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (!granted) { // Always true pre-M
                        "需要读写权限".toast()
                    }
                    initData()
                }
    }

    private fun initData() {
        val oldVersion = sharedPref.versionName
        val newVersion = AppUtils.getVerName(this)
        var isFirst = sharedPref.isFirst
        if (oldVersion != newVersion) {
            isFirst = true
            sharedPref.recoveryRecord = true
            try {
                AppUtils.clean()
            } catch (e: Exception) {
                "清除旧数据错误，请重新安装程序".toast()
            }
            sharedPref.versionName = newVersion
        }
        if (isFirst) {
            //第一次打开
            getData()
        } else {
            whiteOrBlack()
        }
    }

    /**
     * 欧皇还是非酋
     * 基础概率0.6%。每次黑提高0.01%
     */
    private fun whiteOrBlack() {
        val number = DataUtil.randInt(1, 10000)
        //number.toString().logE()
        var times = sharedPref.times
        val length = times + 60
        if (number in 1..length) {
            //播放ding，开启手机扬声器模式
            mMediaPlayer = MediaPlayer.create(this, R.raw.ding)
            val mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            mAudioManager.mode = AudioManager.MODE_NORMAL
            mAudioManager.isSpeakerphoneOn = true
            mMediaPlayer?.setOnCompletionListener { goMain() }
            mMediaPlayer?.start()

            /*when {
                times < 3 -> "第${times + 1}次掉落了橡树果实, 欧皇在世,无可匹敌！！！".toastLong()
                times < 10 -> "第${times + 1}次掉落了火灵之纹, 火灵精华, 你还是人吗？？？".toastLong()
                times < 50 -> "第${times + 1}次掉落了恩德拉的苏醒, 欧皇必死QAQ".toastLong()
                times < 100 -> "第${times + 1}次掉落了混沌碎片, 你今天很欧噢QAQ".toastLong()
                times < 150 -> "第${times + 1}次掉落了极寒碎片, 你今天不错喔QAQ".toastLong()
                times < 200 -> "第${times + 1}次掉落了覆灭之戒, 你需要洗洗手了QAQ".toastLong()
                times < 300 -> "第${times + 1}次掉落了生命源泉, 非酋啊".toastLong()
                else -> "第${times + 1}次掉落了自然之翼, 你已经没救了，建议弃坑！！！".toastLong()
            }*/
            sharedPref.times = 0
        } else {
            times++
            sharedPref.times = times
            goMain()
        }
    }

    private fun goMain() {
        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 500)
    }

    @SuppressLint("CheckResult")
    private fun getData() {
        Observable.create<String> {
            it.onNext("初始化数据中，请耐心等待QAQ")
            getXlsData(fileName)
            getXlsData(fileName2)
            getXlsData(fileName1)
            saveData()
            if (sharedPref.recoveryRecord) {
                it.onNext("存档恢复中，请耐心等待QAQ")
                recoveryRecord()
            }
            it.onComplete()
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    ToastUtil.shortShow(it)
                }, {
                    ToastUtil.shortShow("未知错误,请重新安装")
                }, {
                    sharedPref.isFirst = false
                    cleanList()
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
            when (xlsName) {
                fileName -> {
                    for (k in 0 until sheetNum) {
                        val sheet = workbook.getSheet(k)
                        val sheetRows = sheet.rows
                        if (k == 5) {
                            val imgArray: IntArray = PictureRes.getImgList("专属")
                            for (i in 1 until sheetRows) {//从第二行开始读
                                val exclusive = Exclusive()
                                exclusive.equipName = sheet.getCell(0, i).contents
                                exclusive.profession = sheet.getCell(1, i).contents
                                exclusive.skill = sheet.getCell(2, i).contents
                                exclusive.effect = sheet.getCell(3, i).contents
                                exclusive.imgId = imgArray[i - 1]
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
                                equip.typeId = k
                                equip.imgId = imgArray[i - 1]
                                equipList.add(equip)
                            }
                        }
                    }
                }
                fileName1 -> {
                    for (k in 0 until sheetNum) {
                        val sheet = workbook.getSheet(k)
                        val sheetRows = sheet.rows
                        when (k) {
                            1 -> {
                                val imgArray: IntArray = PictureRes.getImgList("技能")
                                for (i in 1 until sheetRows) {//从第二行开始读
                                    val skill = Skill()
                                    skill.heroName = sheet.getCell(0, i).contents
                                    skill.skillKey = sheet.getCell(1, i).contents
                                    skill.skillName = sheet.getCell(2, i).contents
                                    skill.skillEffect = sheet.getCell(3, i).contents
                                    skill.imgId = imgArray[i - 1]
                                    skillList.add(skill)
                                }
                            }
                            0 -> {
                                val imgArray: IntArray = PictureRes.getImgList("英雄")
                                for (i in 1 until sheetRows) {//从第二行开始读
                                    val hero = Hero()
                                    val name = sheet.getCell(0, i).contents
                                    hero.heroName = name
                                    hero.back = sheet.getCell(1, i).contents
                                    hero.type = sheet.getCell(2, i).contents
                                    hero.main = sheet.getCell(3, i).contents
                                    hero.distance = sheet.getCell(4, i).contents
                                    hero.position = sheet.getCell(5, i).contents
                                    hero.speed = sheet.getCell(6, i).contents
                                    hero.imgId = imgArray[i - 1]
                                    heroList.add(hero)
                                }
                            }
                            2 -> for (i in 1 until sheetRows) {//从第二行开始读
                                val heroStrategy = HeroStrategy()
                                heroStrategy.heroName = sheet.getCell(0, i).contents
                                heroStrategy.address = sheet.getCell(1, i).contents
                                heroStrategy.title = sheet.getCell(2, i).contents
                                heroStrategy.auther = sheet.getCell(3, i).contents
                                heroStrategy.version = sheet.getCell(4, i).contents
                                heroStrategyList.add(heroStrategy)
                            }
                            3 -> for (i in 1 until sheetRows) {
                                val equipRecommend = EquipRecommend()
                                equipRecommend.heroName = sheet.getCell(0, i).contents
                                equipRecommend.title = sheet.getCell(1, i).contents
                                equipRecommend.equipEarly = AppUtils.needEquip(sheet.getCell(2, i).contents)
                                equipRecommend.resonEarly = sheet.getCell(3, i).contents
                                equipRecommend.equipFinal = AppUtils.needEquip(sheet.getCell(4, i).contents)
                                equipRecommend.resonFinal = sheet.getCell(5, i).contents
                                equipRecommend.auther = sheet.getCell(6, i).contents
                                equipRecommend.action = sheet.getCell(7, i).contents
                                recommendList.add(equipRecommend)
                            }
                        }
                    }
                }
                fileName2 -> {
                    for (k in 0 until sheetNum) {
                        val sheet = workbook.getSheet(k)
                        val sheetRows = sheet.rows
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
                                equip.typeId = k + 5
                                if (i <= imgArray.size) {
                                    equip.imgId = imgArray[i - 1]
                                }
                                equipList.add(equip)
                            }
                        }
                    }
                }
            }
            workbook.close()
        } catch (e: Exception) {
            LogUtil.e("read error=$e")
            CrashReport.postCatchedException(e)
        }
    }

    private fun saveData() {
        DataSupport.saveAll(equipList)
        DataSupport.saveAll(exclusiveList)
        DataSupport.saveAll(bossList)
        DataSupport.saveAll(skillList)
        DataSupport.saveAll(heroList)
        DataSupport.saveAll(heroStrategyList)
        DataSupport.saveAll(recommendList)
    }

    private fun cleanList() {
        equipList.clear()
        bossList.clear()
        exclusiveList.clear()
        heroList.clear()
        skillList.clear()
        heroStrategyList.clear()
        recommendList.clear()
    }

    //修复存档图片索引错乱的问题, 修改名称
    private fun recoveryRecord() {
        DataUtil.initMap()
        val bagList = DataSupport.findAll(RecordThing::class.java)
        for (i in bagList) {
            //"物品名称${i.equipName}".logE()
            DataUtil.changeRecordEquipName(i.equipName)?.let {
                i.equipName = it
                //"修改为${i.equipName}".logE()
            }
            val data = DataSupport.where("equipName = ?", i.equipName).find(Equip::class.java)
            if (data.isNotEmpty()) {
                i.equipImg = data[0].imgId
                i.partId = data[0].typeId
            } else {
                i.delete()
            }
        }
        DataUtil.clearMap()
        DataSupport.saveAll(bagList)
    }

    override fun onDestroy() {
        mMediaPlayer?.release()
        super.onDestroy()
    }
}
