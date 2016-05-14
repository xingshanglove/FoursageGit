package com.lanjiaomao.foursage.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.lanjiaomao.foursage.R;
import com.lanjiaomao.foursage.activity.base.BaseActivity;
import com.lanjiaomao.foursage.adapter.ChoseFoldersAdapter;
import com.lanjiaomao.foursage.adapter.ChoseImagesAdapter;
import com.lanjiaomao.foursage.bean.Contacts;
import com.lanjiaomao.foursage.bean.ImageBean;
import com.lanjiaomao.foursage.utils.GetAllImages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 2016/5/14.
 */
public class ChosePictureActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private  String type;
    private int maxCount=0;
    private GridView gv_folders;
    private GridView gv_imgs;

    private ChoseFoldersAdapter folderAdapter;
    private ChoseImagesAdapter imgAdapter;
    private List<ImageBean> imageBeanList;

    private Handler iHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            folderAdapter=new ChoseFoldersAdapter(ChosePictureActivity.this,imageBeanList);
            gv_folders.setAdapter(folderAdapter);
            gv_folders.setOnItemClickListener(ChosePictureActivity.this);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosepicture);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        if(type.equals(Contacts.CHOSE_PICTURE_FOR_ICON)){
            //选择用户头像
        }else if(type.equals(Contacts.CHOSE_PICTURE_FOR_DESC)){
            //选择描述照片
            maxCount=intent.getIntExtra("maxCount",0);
        }

        gv_folders= (GridView) this.findViewById(R.id.gv_folders);
        gv_imgs= (GridView) this.findViewById(R.id.gv_imgs);

        getImages();
    }

    /**
     * 读取手机上的全部图片
     */
    public void getImages(){
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("加载中...");
        pd.setCancelable(false);
        pd.show();
        GetAllImages.getAllImages(ChosePictureActivity.this, new GetAllImages.getImagesListner() {
            @Override
            public void getImagesSuccess(HashMap<String, List<String>> imageGroups) {
                pd.dismiss();
                if(imageGroups.size()!=0){
                    imageBeanList=new ArrayList<ImageBean>();
                    Iterator<Map.Entry<String,List<String>>> iterator=imageGroups.entrySet().iterator();
                    while (iterator.hasNext()){
                        Map.Entry<String,List<String>> entry= iterator.next();
                        ImageBean imageBean=new ImageBean();
                        String key=entry.getKey();
                        List<String> value=entry.getValue();

                        imageBean.setFolderName(key);
                        imageBean.setImageCount(value.size());
                        imageBean.setTopImagePath(value.get(0));
                        imageBean.setImages(value);
                        imageBeanList.add(imageBean);
                    }
                    iHandler.sendEmptyMessage(0x111);
                }
            }
            @Override
            public void getImagesFail() {

            }
        });
    }

    /**
     * 选择图片目录
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        imgAdapter=new ChoseImagesAdapter(ChosePictureActivity.this,imageBeanList.get(position));
        gv_imgs.setAdapter(imgAdapter);
        gv_folders.setVisibility(View.GONE);
        gv_imgs.setVisibility(View.VISIBLE);
    }
}
