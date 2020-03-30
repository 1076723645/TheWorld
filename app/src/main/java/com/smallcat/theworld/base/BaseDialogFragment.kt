package androidx.fragment.app


open class BaseDialogFragment : DialogFragment(){

    override fun show(manager: FragmentManager, tag: String?) {
        mDismissed = false
        mShownByMe = true
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        // 这里吧原来的commit()方法换成了commitAllowingStateLoss()
        ft.commitAllowingStateLoss()
    }

}