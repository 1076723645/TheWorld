package com.smallcat.theworld.ui.activity

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseActivity
import com.smallcat.theworld.base.RxActivity
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.ui.adapter.EquipAdapter
import org.litepal.crud.DataSupport

class SearchActivity : RxActivity() {

    private var list: MutableList<Equip> = ArrayList()

    override val layoutId: Int
        get() = R.layout.activity_search

    override fun initData() {
        val textView = findViewById<TextView>(R.id.tv_no_search)
        val etSearch = findViewById<EditText>(R.id.et_search)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_search)

        val adapter = EquipAdapter(list)
        adapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(this@SearchActivity, EquipDetailActivity::class.java)
            intent.putExtra("id", list[position].id.toString())
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        etSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.isNotEmpty()) {
                    val equips = DataSupport
                            .where("equipName like ?", "%$charSequence%")
                            .order("level desc")
                            .find(Equip::class.java)
                    val result = equips.size
                    if (result == 0) {
                        textView.visibility = View.VISIBLE
                        list.clear()
                    } else {
                        textView.visibility = View.GONE
                        list.clear()
                        list.addAll(equips)
                    }
                    adapter.setNewData(list)
                }
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

}
