package com.blockchain.cray.utils;

import android.widget.ImageView;

import com.blockchain.cray.R;

public class ImageUtils {

    private static final String TYPE_CRAY_A = "黄金虾";
    private static final String TYPE_CRAY_B = "澳洲大龙虾";
    private static final String TYPE_CRAY_C = "樱花虾";
    private static final String TYPE_CRAY_D = "雪球虾";
    private static final String TYPE_CRAY_E = "绿晶虾";
    private static final String TYPE_CRAY_F = "水晶虾";
    private static final String TYPE_CRAY_G = "皮皮虾";
    private static final String TYPE_CRAY_H = "虎纹虾";
    private static final String TYPE_CRAY_I = "小龙虾";

    public static void showCrayPortrait(String name,ImageView iv){
           switch (name){
               case TYPE_CRAY_A:
                   iv.setBackgroundResource(R.mipmap.ic_type_cray_a);
                   break;
               case TYPE_CRAY_B:
                   iv.setBackgroundResource(R.mipmap.ic_type_cray_b);
                   break;
               case TYPE_CRAY_C:
                   iv.setBackgroundResource(R.mipmap.ic_type_cray_c);
                   break;
               case TYPE_CRAY_D:
                   iv.setBackgroundResource(R.mipmap.ic_type_cray_d);
                   break;
               case TYPE_CRAY_E:
                   iv.setBackgroundResource(R.mipmap.ic_type_cray_e);
                   break;
               case TYPE_CRAY_F:
                   iv.setBackgroundResource(R.mipmap.ic_type_cray_f);
                   break;
               case TYPE_CRAY_G:
                   iv.setBackgroundResource(R.mipmap.ic_type_cray_g);
                   break;
               case TYPE_CRAY_H:
                   iv.setBackgroundResource(R.mipmap.ic_type_cray_h);
                   break;
               case TYPE_CRAY_I:
                   iv.setBackgroundResource(R.mipmap.ic_type_cray_i);

                   break;
           }
    }
}
