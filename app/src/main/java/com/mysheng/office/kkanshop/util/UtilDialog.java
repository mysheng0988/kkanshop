package com.mysheng.office.kkanshop.util;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mysheng.office.kkanshop.R;


/**
 * Created by myaheng on 2018/4/23.
 */

public class UtilDialog {

    private static EditText userID;
    private static EditText userPassword;
    private static EditText userOld;
    private static EditText userNew;
    private static EditText userAgain;
    private static EditText IPsetting;


    public static void dialogUpdate(final Context context, final String title){//弹出登录框
        new MikyouCommonDialog(context, R.layout.login_update_passwrod,title,"确定","取消")
                .setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
                    @Override
                    public void dialogPositiveListener(View customView, DialogInterface dialogInterface, int which) {
                        userOld=customView.findViewById(R.id.user_old);
                        userNew=customView.findViewById(R.id.user_new);
                        userAgain=customView.findViewById(R.id.user_again);
                        String oldUser= userNew.getText().toString();
                        String newUser=  userOld.getText().toString();
                        String againUser=  userAgain.getText().toString();
                        if("".equals(oldUser)||"".equals(newUser)) {
                            Toast.makeText(context,"填写旧密码和新密码", Toast.LENGTH_LONG).show();
                        }else if(!newUser.equals(againUser)){
                            Toast.makeText(context,"旧密码和新密码不相同", Toast.LENGTH_LONG).show();
                        }else{
                            dialogInterface.dismiss();
//                            String urlPath = new ReadLoadPathProperties().getPropertiesURL(SettingActivity.this,"dataPath")+"/acClinicDatePath.do?action=LoginCheck&user_id=" + user_id + "&user_password=" + user_password + "&EXE_CDE=";
//                            UserBeanLoginAsyncTask loginAsyncTask=new UserBeanLoginAsyncTask(SettingActivity.this);
//                            loginAsyncTask.setCallBackAsyncTask(new UserBeanLoginAsyncTask.CallBackAsyncTask() {
//                                @Override
//                                public void onCallBack(UserBean userBean) {
//                                    // readLoginData.setLoginData(SettingActivity.this,"load_user_password",user_password);
//                                    finish();
//                                }
//                            });
//                            loginAsyncTask.execute(urlPath);
                        }
                    }

                    @Override
                    public void dialogNegativeListener(View customView, DialogInterface dialogInterface, int which) {

                    }
                }).showDialog();
    }
    public static void dialogIPSetting(final Context context, final String title){//弹出登录框
        new MikyouCommonDialog(context, R.layout.ip_edit_layout,title,"测试连接","保存")
                .setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
                    @Override
                    public void dialogPositiveListener(View customView, DialogInterface dialogInterface, int which) {
                        IPsetting=customView.findViewById(R.id.setting_ip);
                        String IP= IPsetting.getText().toString();
                        if("".equals(IP)) {
                            Toast.makeText(context,"IP地址不能为空", Toast.LENGTH_LONG).show();
                        }else{
                            dialogInterface.dismiss();
//                            String urlPath = new ReadLoadPathProperties().getPropertiesURL(SettingActivity.this,"dataPath")+"/acClinicDatePath.do?action=LoginCheck&user_id=" + user_id + "&user_password=" + user_password + "&EXE_CDE=";
//                            UserBeanLoginAsyncTask loginAsyncTask=new UserBeanLoginAsyncTask(SettingActivity.this);
//                            loginAsyncTask.setCallBackAsyncTask(new UserBeanLoginAsyncTask.CallBackAsyncTask() {
//                                @Override
//                                public void onCallBack(UserBean userBean) {
//                                    // readLoginData.setLoginData(SettingActivity.this,"load_user_password",user_password);
//                                    finish();
//                                }
//                            });
//                            loginAsyncTask.execute(urlPath);
                        }
                    }

                    @Override
                    public void dialogNegativeListener(View customView, DialogInterface dialogInterface, int which) {

                    }
                }).showDialog();
    }
    public static void dialogIPFlow(final Context context, final String title){//弹出登录框
        new MikyouCommonDialog(context, R.layout.ip_edit_layout,title,"保存","取消")
                .setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
                    @Override
                    public void dialogPositiveListener(View customView, DialogInterface dialogInterface, int which) {
                        IPsetting=customView.findViewById(R.id.setting_ip);
                        String IP= IPsetting.getText().toString();
                        if("".equals(IP)) {
                            Toast.makeText(context,"IP地址不能为空", Toast.LENGTH_LONG).show();
                        }else{
                            dialogInterface.dismiss();
//                            String urlPath = new ReadLoadPathProperties().getPropertiesURL(SettingActivity.this,"dataPath")+"/acClinicDatePath.do?action=LoginCheck&user_id=" + user_id + "&user_password=" + user_password + "&EXE_CDE=";
//                            UserBeanLoginAsyncTask loginAsyncTask=new UserBeanLoginAsyncTask(SettingActivity.this);
//                            loginAsyncTask.setCallBackAsyncTask(new UserBeanLoginAsyncTask.CallBackAsyncTask() {
//                                @Override
//                                public void onCallBack(UserBean userBean) {
//                                    // readLoginData.setLoginData(SettingActivity.this,"load_user_password",user_password);
//                                    finish();
//                                }
//                            });
//                            loginAsyncTask.execute(urlPath);
                        }
                    }

                    @Override
                    public void dialogNegativeListener(View customView, DialogInterface dialogInterface, int which) {

                    }
                }).showDialog();
    }

}
