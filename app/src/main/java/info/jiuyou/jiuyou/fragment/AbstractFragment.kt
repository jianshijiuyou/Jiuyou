package info.jiuyou.jiuyou.fragment

import android.Manifest
import android.support.v4.app.FragmentManager
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.PermissionListener
import info.jiuyou.wang.fragment.BaseFragment
import info.jiuyou.wang.log.L

/**
 * ==========================================
 *
 *
 * 版   权 ：jianshijiuyou(c) 2017
 * <br></br>
 * 作   者 ：wq
 * <br></br>
 * 版   本 ：1.0
 * <br></br>
 * 创建日期 ：2017/5/25 0025  10:07
 * <br></br>
 * 描   述 ：
 * <br></br>
 * 修订历史 ：
 *
 * ==========================================
 */
abstract class AbstractFragment: BaseFragment(){
    override fun initData() {
        super.initData()
        // 申请权限。
        AndPermission.with(this)
                .requestCode(100)
                .permission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .rationale { requestCode, rationale -> AndPermission.rationaleDialog(context, rationale).show() }
                .callback(listener)
                .start()
    }

    private val listener = object : PermissionListener {
        override fun onSucceed(requestCode: Int, grantedPermissions: List<String>) {

        }

        override fun onFailed(requestCode: Int, deniedPermissions: List<String>) {
            // 权限申请失败回调。
            L.d("Permission onFailed")
            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(this@AbstractFragment, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(this@AbstractFragment, 123).show()

                // 第二种：用自定义的提示语。
                // AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
                // .setTitle("权限申请失败")
                // .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                // .setPositiveButton("好，去设置")
                // .show();

                // 第三种：自定义dialog样式。
                // SettingService settingService =
                //    AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
                // 你的dialog点击了确定调用：
                // settingService.execute();
                // 你的dialog点击了取消调用：
                // settingService.cancel();
            }
        }
    }
}

