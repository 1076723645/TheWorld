package com.smallcat.theworld.ui.activity

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseActivity
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.ui.adapter.EquipAdapter
import org.litepal.crud.DataSupport
import java.util.ArrayList

class SearchActivity : BaseActivity() {

    private var dataList: MutableList<Equip> = ArrayList()

    override val layoutId: Int
        get() = R.layout.activity_search

    override fun initData() {
        val textView = findViewById<TextView>(R.id.tv_no_search)
        val searchData = findViewById<EditText>(R.id.et_search)
        val back = findViewById<ImageView>(R.id.iv_back)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_search)
        val adapter = EquipAdapter(dataList)
        adapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(this@SearchActivity, EquipDetailActivity::class.java)
            intent.putExtra("id", dataList[position].id.toString())
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        searchData.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.isNotEmpty()) {
                    val equips = DataSupport
                            .where("equipName like ?", "%$charSequence%")
                            .order("quality")
                            .find(Equip::class.java)
                    val result = equips.size
                    //       Log.e("eee", String.valueOf(result));
                    if (result == 0) {
                        textView.visibility = View.VISIBLE
                        dataList.clear()
                        adapter.notifyDataSetChanged()
                    } else {
                        textView.visibility = View.GONE
                        dataList.clear()
                        dataList.addAll(equips)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
        back.setOnClickListener { finish() }
    }

}
