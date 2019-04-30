package com.smallcat.theworld.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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
import com.smallcat.theworld.viewmodel.SearchViewModel
import org.litepal.crud.DataSupport

class SearchActivity : BaseActivity() {

    //private var dataList: MutableList<Equip> = ArrayList()
    private lateinit var mSearchViewModel: SearchViewModel

    override val layoutId: Int
        get() = R.layout.activity_search

    override fun initData() {
        val textView = findViewById<TextView>(R.id.tv_no_search)
        val etSearch = findViewById<EditText>(R.id.et_search)
        val back = findViewById<ImageView>(R.id.iv_back)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_search)

        mSearchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val list = mSearchViewModel.getEquipList()
        val adapter = EquipAdapter(list)
        adapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(this@SearchActivity, EquipDetailActivity::class.java)
            intent.putExtra("id", list[position].id.toString())
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        mSearchViewModel.getValue()?.observe(this, Observer<MutableList<Equip>> {
            adapter.setNewData(it)
        })

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.isNotEmpty()) {
                    val equips = DataSupport
                            .where("equipName like ?", "%$charSequence%")
                            .order("quality")
                            .find(Equip::class.java)
                    val result = equips.size
                    if (result == 0) {
                        textView.visibility = View.VISIBLE
                        list.clear()
                        mSearchViewModel.setValue(list)
                    } else {
                        textView.visibility = View.GONE
                        list.clear()
                        list.addAll(equips)
                        mSearchViewModel.setValue(list)
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
        back.setOnClickListener { finish() }
    }

}
