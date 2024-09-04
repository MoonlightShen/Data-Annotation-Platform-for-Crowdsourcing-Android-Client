package com.echo.datatag3.util.kt

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.text.InputType
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.input.InputCallback
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.ItemListener
import com.afollestad.materialdialogs.list.listItems
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.echo.datatag3.R
import com.echo.datatag3.adapter.UserListAdapter
import com.echo.datatag3.interfaces.callback.user.SearchUserCallback
import com.rengwuxian.materialedittext.MaterialEditText

class DialogUtil {
    companion object {
        @JvmStatic
        fun loginSettings(context: Context, callback: LoginSettingsCallback) {
            MaterialDialog(context, ModalDialog).show {
                title(text = "修改远程服务器的连接配置")
                message(text = "请注意，此功能仅供调试者使用，使用此功能后可能导致未知的错误，请参考软件说明书使用！")
                customView(R.layout.login_settings, scrollable = true, horizontalPadding = true)
                positiveButton(text = "确定"){ dialog ->
                    val view = dialog.getCustomView()
                    val ip1: MaterialEditText = view.findViewById(R.id.ip1)
                    val ip2: MaterialEditText = view.findViewById(R.id.ip2)
                    val ip3: MaterialEditText = view.findViewById(R.id.ip3)
                    val ip4: MaterialEditText = view.findViewById(R.id.ip4)
                    val port: MaterialEditText = view.findViewById(R.id.port)
                    callback.ipAndPort(
                        ip1.text.toString(),
                        ip2.text.toString(),
                        ip3.text.toString(),
                        ip4.text.toString(),
                        port.text.toString()
                    )
                }
                negativeButton(text = "取消")
            }
        }

        interface LoginSettingsCallback {
            fun ipAndPort(ip1: String, ip2: String, ip3: String, ip4: String, port: String)
        }

        @SuppressLint("CheckResult")
        @JvmStatic
        fun addTag(context: Context, title: String, callback: InputCallback) {
            MaterialDialog(context).show {
                title(text = title)
                input(
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS,
                    callback = callback
                )
                positiveButton(text = "确定")
                negativeButton(text = "取消")
            }
        }

        @SuppressLint("CheckResult")
        @JvmStatic
        fun editTag(context: Context, title : String, content:String , callback:InputCallback, negativeCallback: DialogCallback){
            MaterialDialog(context).show {
                title(text = title)
                input(
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS,
                    callback = callback,
                    prefill = content
                )
                positiveButton(text = "确定")
                negativeButton(text = "删除", click = negativeCallback)
            }
        }

        @JvmStatic
        fun changeDataType(context: Context, positiveCallback : DialogCallback, negativeCallback: DialogCallback){
            MaterialDialog(context).show {
                message(text = "您正在尝试修改数据类型，这会清空已上传的文件，此操作不可恢复，是否继续？")
                positiveButton(text = "确定", click = positiveCallback)
                negativeButton(text = "取消", click = negativeCallback)
                cancelOnTouchOutside(false)
            }
        }

        @JvmStatic
        fun searchUser(context: Context, callback: SearchUserCallback){
            val dialogBehavior: DialogBehavior = BottomSheet(LayoutMode.WRAP_CONTENT)
            val dialog = MaterialDialog(context, dialogBehavior).show {
                customView(R.layout.query_user, scrollable = true, horizontalPadding = true)
            }

            val customView = dialog.getCustomView()
            val searchView :FloatingSearchView = customView.findViewById(R.id.search_view)
            val recycler:RecyclerView = customView.findViewById(R.id.recycler)
            val adapter = UserListAdapter()
            recycler.adapter = adapter
            val searchListener = object : FloatingSearchView.OnSearchListener {
                override fun onSuggestionClicked(searchSuggestion: SearchSuggestion) {
                }

                override fun onSearchAction(currentQuery: String) {
                    callback.onSearch(currentQuery, customView.findViewById(R.id.status_view), adapter)
                }
            }
            searchView.setOnSearchListener(searchListener)
        }

        @JvmStatic
        fun confirmDeleteDataset(context: Context, datasetName: String, inputCallback: InputCallback){
            MaterialDialog(context).show {
                title(text = "确认要删除吗？")
                message(text = "此操作不可恢复，请输入数据集名称以确认：$datasetName")
                input(
                    hint = "",
                    prefill = "",
                    inputType = InputType.TYPE_CLASS_TEXT,
                    callback = inputCallback
                )
                positiveButton(text = "确定")
                negativeButton(text = "取消")
            }
        }

        @JvmStatic
        fun confirmDeleteTask(context: Context, taskName: String, inputCallback: InputCallback) {
            MaterialDialog(context).show {
                title(text = "确认要删除吗？")
                message(text = "此操作不可恢复，请输入任务标题以确认：$taskName")
                input(
                    hint = "",
                    prefill = "",
                    inputType = InputType.TYPE_CLASS_TEXT,
                    callback = inputCallback
                )
                positiveButton(text = "确定")
                negativeButton(text = "取消")
            }
        }

        @JvmStatic
        fun confirmImportDuplicateFiles(
            context: Context,
            items: List<CharSequence>,
            positiveCallback: DialogCallback
        ) {
            MaterialDialog(context).show {
                title(text = "以下文件与已经导入的文件路径重复，是否继续导入？")
                listItems(items = items)
                positiveButton(click = positiveCallback)
            }
        }

        @JvmStatic
        fun chooseDataFileImportMethod(context: Context, itemListener: ItemListener) {
            val list: MutableList<CharSequence> = ArrayList()
            list.add("选择已导入的数据文件")
            list.add("选择本地设备的文件")
            MaterialDialog(context, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                title(text = "请选择导入数据文件的方式")
                listItems(items = list, selection = itemListener)
            }
        }

        @JvmStatic
        fun createInputDialog(
            context: Context,
            title: String?,
            message: String?,
            positiveCallback: DialogCallback?,
            negativeCallback: DialogCallback?,
            inputCallback: InputCallback
        ) {
            MaterialDialog(context).show {
                title(text = title)
                message(text = message)
                input(
                    inputType = InputType.TYPE_CLASS_TEXT,
                    callback = inputCallback
                )
                positiveButton(text = "确定", click = positiveCallback)
                negativeButton(text = "取消", click = negativeCallback)
            }
        }

        @JvmStatic
        fun createBasicButtonsDialog(
            context: Context,
            message: String,
            positiveCallback: DialogCallback?,
            negativeCallback: DialogCallback?,
            cancelCallback: DialogInterface.OnCancelListener?
        ) {
            MaterialDialog(context).show {
                message(text = message)
                positiveButton(text = "确定", click = positiveCallback)
                negativeButton(text = "取消", click = negativeCallback)
                setOnCancelListener(cancelCallback)
                cancelOnTouchOutside(true)
            }
        }

        @JvmStatic
        fun createCustomDialog(
            context: Context,
            title: String?,
            message: String?,
            positiveButtonText: String? = "确定",
            negativeButtonText: String? = "取消",
            positiveCallback: DialogCallback?,
            negativeCallback: DialogCallback?
        ) {
            MaterialDialog(context).show {
                title(text = title)
                message(text = message)
                positiveButton(text = positiveButtonText, click = positiveCallback)
                negativeButton(text = negativeButtonText, click = negativeCallback)
            }
        }
    }
}