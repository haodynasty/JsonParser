/*
 * FileName: IActivity.java
 * Copyright (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * 
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : service@plusub.com
 * date     : 2014-12-1 上午11:16:49
 * last modify author :
 * version : 1.0
 */
package com.blakequ.parser;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;

import java.lang.reflect.Field;

/**
 * 
 * @ClassName: ViewInjectUtils
 * @Description: TODO 注解工具类
 * @author blakequ@gmail.com
 * @date 2014-12-1 下午7:54:47
 * @version v1.0
 */
public class ViewInjectUtils {
    /**
     * @param currentClass
     *            当前类，一般为Activity或Fragment
     * @param sourceView
     *            待绑定控件的直接或间接父控件
     */
    public static void initBindView(Object currentClass,
            View sourceView) {
        // 通过反射获取到全部属性，反射的字段可能是一个类（静态）字段或实例字段
        Field[] fields = currentClass.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                // 返回BindView类型的注解内容
                BindView bindView = field
                        .getAnnotation(BindView.class);
                if (bindView != null) {
                    int viewId = bindView.id();
                    boolean clickLis = bindView.click();
                    try {
                        field.setAccessible(true);
                        if (clickLis) {
                            sourceView
                                    .findViewById(viewId)
                                    .setOnClickListener(
                                            (OnClickListener) currentClass);
                        }
                        // 将currentClass的field赋值为sourceView.findViewById(viewId)
                        field.set(currentClass,
                                sourceView.findViewById(viewId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    ResInject resInject = field.getAnnotation(ResInject.class);
                    if (resInject != null) {
                        try {
                            Object res = ResourceUtils.loadRes(
                                    resInject.type(), sourceView.getContext(), resInject.id());
                            if (res != null) {
                                field.setAccessible(true);
                                field.set(currentClass, res);
                            }
                        } catch (Throwable e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 必须在setContentView之后调用
     * 
     * @param aty
     */
    public static void initBindView(Activity aty) {
        initBindView(aty, aty.getWindow().getDecorView());
    }

    /**
     * 必须在setContentView之后调用
     * 
     * @param view
     *            侵入式的view，例如使用inflater载入的view
     */
    public static void initBindView(View view) throws IllegalArgumentException {
        Context cxt = view.getContext();
        if (cxt instanceof Activity) {
            initBindView((Activity) cxt);
        } else {
            throw new IllegalArgumentException("the view don't have root view");
        }
    }

    /**
     * 必须在setContentView之后调用
     * 
     * @param frag
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void initBindView(Fragment frag) {
        initBindView(frag, frag.getActivity().getWindow()
                .getDecorView());
    }
}
