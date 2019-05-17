package com.smallcat.theworld.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxActivity
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.ui.adapter.EquipAdapter
import com.smallcat.theworld.utils.AppUtils
import com.smallcat.theworld.utils.sharedPref
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_eqipe_add.*
import kotlinx.android.synthetic.main.normal_toolbar.*
import org.litepal.crud.DataSupport

class EquipAddActivity : RxActivity() {

    private val tabTitles = arrayOf("武器", "头盔", "衣服", "饰品", "翅膀", "材料", "藏品", "其他")
    private var mEquipList: MutableList<Equip> = ArrayList()
    private lateinit var adapter: EquipAdapter
    private var flag: Int = 0
    private var isBack = false

    companion object {
        private const val TAG = "EquipAddActivity"
        fun getIntent(mContext: Context, type: Int) =
                Intent(mContext, EquipAddActivity::class.java).apply {
                    putExtra(TAG, type)
                }
    }

    override val layoutId: Int
        get() = R.layout.activity_eqipe_add

    @SuppressLint("SetTextI18n")
    override fun initData() {
        flag = intent.getIntExtra(TAG, 0)
        val textView = findViewById<TextView>(R.id.tv_no_search)
        val etSearch = findViewById<EditText>(R.id.et_search)
        etSearch.hint = "搜索${tabTitles[flag]}"
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                val equips = DataSupport
                        .where("equipName like ? and type = ?", "%$charSequence%", tabTitles[flag])
                        .order("quality desc")
                        .find(Equip::class.java)
                val result = equips.size
                if (result == 0) {
                    textView.visibility = View.VISIBLE
                    mEquipList.clear()
                    adapter.setNewData(mEquipList)
                } else {
                    textView.visibility = View.GONE
                    mEquipList.clear()
                    mEquipList.addAll(equips)
                    adapter.setNewData(mEquipList)
                }
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
        adapter = EquipAdapter(null)
        adapter.setOnItemClickListener { _, _, position ->
            val intent = Intent()
            intent.putExtra("data", Gson().toJson(mEquipList[position], Equip::class.java))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        adapter.emptyView = AppUtils.getEmptyView(mContext, "数据加载中")
        rv_list.adapter = adapter

        isBack = mContext.sharedPref.isBack
        loadData()
    }

    private fun loadData() {
        addSubscribe(Observable.create<List<Equip>> {
            val list = if (isBack) {
                DataSupport.where("type = ?", tabTitles[flag]).order("id desc").find(Equip::class.java)
            } else {
                DataSupport.where("type = ?", tabTitles[flag]).find(Equip::class.java)
            }
            it.onNext(list)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    showList(it)
                })
    }

    private fun showList(data: List<Equip>) {
        mEquipList.clear()
        mEquipList.addAll(data)
        adapter.setNewData(mEquipList)
    }

}
