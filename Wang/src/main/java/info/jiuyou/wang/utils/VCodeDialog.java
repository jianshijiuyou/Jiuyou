package info.jiuyou.wang.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import info.jiuyou.wang.R;
import info.jiuyou.wang.widget.VerificationCodeView;

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/4/24 0024  11:06
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
public class VCodeDialog {

    private AlertDialog alertDialog;

    public void show(Context c, final OnVerificationListener listener) {
        View content = View.inflate(c, R.layout.dialog_verification_code, null);
        final VerificationCodeView cv = (VerificationCodeView) content.findViewById(R.id.verifycodeview);
        final EditText etCode = (EditText) content.findViewById(R.id.et_code);
        Button btnOk = (Button) content.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cv.getvCode().toLowerCase().equals(etCode.getText().toString().toLowerCase())) {
                    alertDialog.dismiss();
                    listener.success();
                } else {
                    if (!listener.failing(cv, etCode)) {
                        T.Toast("验证码错误");
                        cv.refreshCode();
                        etCode.setText("");
                    }
                }
            }
        });
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((VerificationCodeView) view).refreshCode();
            }
        });
        alertDialog = new AlertDialog.Builder(c)
                .setView(content)
                .create();
        alertDialog.show();
    }


    interface OnVerificationListener {
        void success();

        boolean failing(VerificationCodeView cv, EditText et);
    }

    public static abstract class SimpleOnVerificationListener implements OnVerificationListener {

        @Override
        public abstract void success();

        @Override
        public boolean failing(VerificationCodeView cv, EditText et) {
            return false;
        }
    }
}
