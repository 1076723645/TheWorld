package com.smallcat.theworld.utils

import java.util.*
import kotlin.collections.HashMap

/**
 * @author smallCut
 * @date 2018/9/13
 */
object DataUtil {

    private val equipMap = HashMap<String, String>()

    private val oldNames = listOf("深渊之手,绝望的湮灭", "德瑞诺斯,深渊领主的戒指", "深渊之手,最后的耳语", "信仰 圣佑之盔", "真 奥布里皮帽",
            "诸神黄昏,深渊领主的龙笛", "誓约之戒-勇气", "深渊魔镐", "克罗洛斯,血羽之甲", "凛冰契约,霜枭", "星辰闪耀,圣堂之翼", "灾变,蛮荒之预言",
            "真 软布背包", "真 精良的背包", "拉克夏,剧毒卫冕之剑", "莱恩哈特,魔龙之剑", "真 柏丽娜龙炮", "风暴之力,凛风长弓", "葬礼,血祭之矛", "葬礼,统治血剑",
            "真 采佩什血炮", "灾变,诸界毁灭者", "幽魂破尽 金顶佛光圣物", "真,魔渊 法力之源", "魔渊 法力之源", "丝克尔,不洁魔盔", "真 魔龙战盔", "生命咏叹,守护头盔",
            "无尽虚空,破界之眼", "漩涡 风之守护", "真 魔龙战甲", "生命咏叹,守护圣甲", "真 血精石甲", "生命咏叹,破界守望", "生命咏叹,梦境童话", "德里克斯,堕落魔甲",
            "亡灵挽歌,灾祸法袍", "冥谕 铸血甲胄", "魔法之心 卓越", "魔法之心 霜寒", "诸神黄昏,混沌之心", "亡灵挽歌,月穷天坠", "亡灵挽歌,咒印心石",
            "狂暴 堕落统治魂翼", "雪絮 寒影圣辉", "末日 火舞之怒,焰纹封魔炮", "亡灵挽歌,王者之剑", "斩神,亚特兰蒂斯的毁灭圣剑", "冬之泪,冰极天雪剑",
            "神降,亚特兰蒂斯的毁灭圣弓", "冬之泪,沧澜雪印", "亡灵挽歌,虚灵权杖", "信仰,亚特兰蒂斯的毁灭圣杖", "生命咏叹,守望之锤", "不朽,潜行者",
            "冬之泪,冰霜怨弓", "追忆,呼啸长风", "噩耗 魑魅神杖", "荣耀,鲜血残戟", "真 无名之剑", "熔岩血海,妖龙叹息", "毁灭,末日回响", "地狱熔岩,幻舞者",
            "火舞之怒,焰纹封魔炮", "真龙炙舞,毁天叹息", "冥虹镜芒,破碎之命运", "爆裂花雨,碧水青龙炮", "真·埃克斯米亚,不洁之刃", "埃克斯米亚,不洁之刃",
            "真·卡拉菲米亚,神圣之剑", "卡拉菲米亚, 神圣之剑", "生命咏叹,自然之力", "冬之泪,冰怨战矛", "死亡呼吸,剧毒之影", "真 阿拉卡特,不洁之弓", "阿拉卡特,不洁之弓",
            "霜寒.冰霜圣弓", "真·克雷提亚,圣光权杖", "克雷提亚,圣光权杖", "赤红 南瓜权杖", "真 战神,神圣加农炮", "战神,神圣加农炮", "魔龙头盔", "奥哈里康")

    private val newNames = listOf("深渊之手·绝望的煙灭", "德瑞诺斯·深渊领主的戒指", "深渊之手·最后的耳语", "信仰·圣佑之盔", "真·奥布里皮帽",
            "诸神黄昏·深渊领主的龙笛", "誓约之戒·勇气", "魔渊之镐", "克罗洛斯·血羽之甲", "凛冰契约·霜枭", "星辰闪耀·圣堂之翼", "灾变·蛮荒之预言",
            "真·软布背包", "真·精良的背包", "拉克夏·剧毒卫冕之剑", "莱恩哈特·魔龙之剑", "真·柏丽娜龙炮", "风暴之力·凛风长弓", "葬礼·血祭之矛", "葬礼·统治血剑",
            "真·采佩什血炮", "灾变·诸界毁灭者", "幽魂破尽·金顶佛光圣物", "真·魔渊·法力之源", "魔渊·法力之源", "丝克尔·不洁魔盔", "真·魔龙战盔", "生命咏叹·守护头盔",
            "无尽虚空·破界之眼", "漩涡·风之守护", "真·魔龙战甲", "生命咏叹·守护圣甲", "真·血精石甲", "生命咏叹·破界守望", "生命咏叹·梦境童话", "德里克斯·堕落魔甲",
            "亡灵挽歌·灾祸法袍", "冥谕·铸血甲胄", "魔法之心·卓越", "魔法之心·霜寒", "诸神黄昏·混沌之心", "亡灵挽歌·月穷天坠", "亡灵挽歌·咒印心石",
            "狂暴·堕落统治魂翼", "雪絮·寒影圣辉", "末日·火舞之怒·焰纹封魔炮", "亡灵挽歌·王者之剑", "斩神·亚特兰蒂斯的毁灭圣剑", "冬之泪·冰极天雪剑",
            "神降·亚特兰蒂斯的毁灭圣弓", "冬之泪·沧澜雪印", "亡灵挽歌·虚灵权杖", "信仰·亚特兰蒂斯的毁灭圣杖", "生命咏叹·守望之锤", "不朽·潜行者",
            "冬之泪·冰霜怨弓", "追忆·呼啸长风", "噩耗·魑魅神杖", "荣耀·鲜血残戟", "真·无名之剑", "熔岩血海·妖龙叹息", "毁灭·末日回响", "地狱熔岩·幻舞者",
            "火舞之怒·焰纹封魔炮", "真龙炙舞·毁天叹息", "冥虹镜芒·破碎之命运", "爆裂花雨·碧水青龙炮", "真·埃克斯米亚·不洁之刃", "埃克斯米亚·不洁之刃",
            "真·卡拉菲米亚·神圣之剑", "卡拉菲米亚·神圣之剑", "生命咏叹·自然之力", "冬之泪·冰怨战矛", "死亡呼吸·剧毒之影", "真·阿拉卡特·不洁之弓", "阿拉卡特·不洁之弓",
            "霜寒·冰霜圣弓", "真·克雷提亚·圣光权杖", "克雷提亚·圣光权杖", "赤红·南瓜权杖", "真·战神·神圣加农炮", "战神·神圣加农炮", "魔龙战盔", "奥哈利康")

    /**
     * 生成一个伪随机数
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random.nextInt
     */
    fun randInt(min: Int, max: Int): Int {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        val rand = Random()

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive

        return rand.nextInt(max - min + 1) + min
    }

    fun initMap() {
        for (i in oldNames.indices) {
            equipMap[oldNames[i]] = newNames[i]
        }
    }

    fun clearMap() {
        equipMap.clear()
    }

    fun changeRecordEquipName(s: String) = equipMap[s]

}