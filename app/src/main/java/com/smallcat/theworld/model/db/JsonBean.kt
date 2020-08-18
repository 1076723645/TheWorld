package com.smallcat.theworld.model.db

data class JsonBean(
        /**
         * 掉落
         * */
        val drops: List<Drop>,
        /**
         * 专属
         */
        val exclusives: List<ExclusiveJson>,
        /**
         * 英雄
         */
        val heroes: List<Heroe>,
        val makes: List<Make>,
        val stageGoods: List<StageGood>,
        val stageUnits: List<StageUnit>
)

data class StageUnit(
        val displayName: String,
        val id: String,
        val img: String,
        val mdx: String,
        val name: String,
        val stage: Int,
        val ua1b: Int,
        val ua1c: Int,
        val ua1r: Int,
        val ua1t: String,
        val uacq: Int,
        val uarm: String,
        val ucol: Int,
        val udef: Int,
        val udty: String,
        val uhpm: Int,
        val uhpr: Int,
        val umpm: Int,
        val umvh: Int,
        val umvr: Double,
        val umvs: Int,
        val upro: String,
        val usca: Double,
        val usid: Int,
        val usin: Int
)

data class Make(
        val choose: Boolean,
        val id: String,
        val num: Int,
        val subId: String
)

data class Drop(
        val chance: Int,
        val desc: String,
        val dropId: String,
        val drops: List<DropX>,
        val id: String
)

data class DropX(
        val chance: Double,
        val id: String
)

data class Heroe(
        val displayName: String,
        val id: String,
        val img: String,
        val mdx: String,
        val name: String,
        val skills: List<SkillJson>,
        val ua1b: Int,
        val ua1c: Double,
        val ua1r: Int,
        val ua1t: String,
        val uacq: Int,
        val uarm: String,
        val ucol: Int,
        val udef: Int,
        val udty: String,
        val uhpm: Int,
        val uhpr: Int,
        val umpm: Int,
        val umvh: Int,
        val umvr: Int,
        val umvs: Int,
        val upro: String,
        val usca: Double,
        val usid: Int,
        val usin: Int
)

data class SkillJson(
        val desc: String,
        val displayName: String,
        val hotKeys: String,
        val img: String
)

data class ExclusiveJson(
        val desc: String,
        val goodId: String,
        val heroId: String,
        val on: String
)

data class StageGood(
        val abilityDamageInc: Int,
        val agi: Int,
        val atk: Int,
        val atkDamageInc: Int,
        val atkSpeed: Int,
        val cat: List<String>,
        val def: Int,
        val desc: String,
        val displayName: String,
        val expInc: Int,
        val fireDamageInc: Double,
        val goodType: Int,
        val hp: Int,
        val hpInc: Int,
        val hpRegenInc: Int,
        val iceDamageInc: Double,
        val id: String,
        val img: String,
        val int: Int,
        val level: Int,
        val limit: String,
        val magicDefInc: Int,
        val mainAttrInc: Int,
        val missInc: Int,
        val mp: Int,
        val mpInc: Int,
        val name: String,
        val natureAttrInc: Int,
        val punchAsChanceInc: Double,
        val punchChance: Int,
        val punchChanceInc: Int,
        val quality: Int,
        val resurrectionTimeDec: Int,
        val stage: Int,
        val stillDamageInc: Int,
        val str: Int,
        val sufferCureInc: Int,
        val sufferDamageDec: Int,
        val windAttrInc: Int
)