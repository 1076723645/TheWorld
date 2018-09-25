package com.smallcat.theworld.model.db;


import com.smallcat.theworld.R;

/**
 *
 * @author hui
 * @date 2017/12/27
 */

public class PictureRes {

    private static final int[] imgList0 = new int[]{R.drawable.ruanbu, R.drawable.jingliang, R.drawable.zhen_ruanbu, R.drawable.zhen_jingliang
            , R.drawable.wuqi_1, R.drawable.wuqi_2, R.drawable.wuqi_3, R.drawable.wuqi_4, R.drawable.wuqi_5
            , R.drawable.wuqi_6, R.drawable.wuqi_7, R.drawable.wuqi_8, R.drawable.wuqi_9, R.drawable.wuqi_10, R.drawable.wuqi_11, R.drawable.wuqi_12
            , R.drawable.wuqi_13, R.drawable.liliang, R.drawable.shuiguo, R.drawable.wuqi_14, R.drawable.wuqi_15, R.drawable.wuqi_16, R.drawable.wuqi_17
            , R.drawable.wuqi_18, R.drawable.wuqi_19
            , R.drawable.wuqi_14, R.drawable.wuqi_15, R.drawable.wuqi_16, R.drawable.wuqi_17, R.drawable.wuqi_18, R.drawable.wuqi_19, R.drawable.wuqi_14
            , R.drawable.wuqi_15, R.drawable.wuqi_18, R.drawable.wuqi_16, R.drawable.wuqi_17, R.drawable.wuqi_19, R.drawable.wuqi_14, R.drawable.wuqi_15
            , R.drawable.wuqi_16, R.drawable.wuqi_17, R.drawable.wuqi_18, R.drawable.wuqi_20, R.drawable.wuqi_21, R.drawable.wuqi_22, R.drawable.wuqi_23
            , R.drawable.wuqi_24, R.drawable.wuqi_25, R.drawable.wuqi_26, R.drawable.wuqi_27, R.drawable.wuqi_22, R.drawable.wuqi_23, R.drawable.wuqi_24
            , R.drawable.wuqi_25, R.drawable.wuqi_26, R.drawable.wuqi_27, R.drawable.wuqi_28, R.drawable.wuqi_29, R.drawable.wuqi_30, R.drawable.wuqi_31
            , R.drawable.wuqi_32, R.drawable.wuqi_33, R.drawable.wuqi_34, R.drawable.wuqi_35, R.drawable.wuqi_36, R.drawable.wuqi_37, R.drawable.tanguo
            , R.drawable.haohan, R.drawable.wuqi_38
            , R.drawable.wuqi_39, R.drawable.wuqi_40, R.drawable.wuqi_41, R.drawable.wuqi_42, R.drawable.wuqi_43, R.drawable.wuqi_44, R.drawable.wuqi_45
            , R.drawable.wuqi_46, R.drawable.wuqi_47, R.drawable.wuqi_48, R.drawable.wuqi_49, R.drawable.wuqi_50, R.drawable.wuqi_51, R.drawable.wuqi_52
            , R.drawable.wuqi_53, R.drawable.wuqi_54, R.drawable.wuqi_55, R.drawable.wuqi_56, R.drawable.wuqi_57, R.drawable.wuqi_58, R.drawable.tanguo
            , R.drawable.haohan, R.drawable.wuqi_59
            , R.drawable.wuqi_60, R.drawable.wuqi_61, R.drawable.wuqi_62, R.drawable.wuqi_63, R.drawable.wuqi_64, R.drawable.wuqi_65, R.drawable.wuqi_66
            , R.drawable.shenyuan, R.drawable.tianguo
            , R.drawable.wuqi_67, R.drawable.wuqi_68, R.drawable.wuqi_69, R.drawable.wuqi_70, R.drawable.wuqi_71, R.drawable.wuqi_72, R.drawable.wuqi_73
            , R.drawable.wuqi_74, R.drawable.wuqi_75, R.drawable.wuqi_76, R.drawable.wuqi_77, R.drawable.wuqi_78, R.drawable.wuqi_79, R.drawable.wuqi_80
            , R.drawable.wuqi_81, R.drawable.wuqi_82, R.drawable.wuqi_83, R.drawable.wuqi_84, R.drawable.wuqi_85, R.drawable.wuqi_86, R.drawable.wuqi_87
            , R.drawable.wuqi_88, R.drawable.wuqi_89, R.drawable.wuqi_90, R.drawable.wuqi_91, R.drawable.wuqi_92, R.drawable.wuqi_93, R.drawable.wuqi_94
            , R.drawable.wuqi_95, R.drawable.wuqi_96, R.drawable.wuqi_97, R.drawable.wuqi_98, R.drawable.wuqi_99, R.drawable.wuqi_100, R.drawable.wuqi_101
            , R.drawable.shangqiu, R.drawable.tianguo
            , R.drawable.wuqi_102, R.drawable.wuqi_103, R.drawable.wuqi_104, R.drawable.wuqi_105, R.drawable.wuqi_106, R.drawable.wuqi_107, R.drawable.wuqi_108
            , R.drawable.wuqi_109, R.drawable.wuqi_111, R.drawable.wuqi_112, R.drawable.wuqi_113, R.drawable.wuqi_115
            , R.drawable.wuqi_116, R.drawable.wuqi_117, R.drawable.wuqi_118, R.drawable.wuqi_120, R.drawable.wuqi_121, R.drawable.wuqi_122
            , R.drawable.wuqi_123, R.drawable.wuqi_124, R.drawable.wuqi_125, R.drawable.wuqi_126, R.drawable.wuqi_127, R.drawable.wuqi_128, R.drawable.wuqi_129
            , R.drawable.wuqi_131, R.drawable.wuqi_132, R.drawable.wuqi_133, R.drawable.wuqi_135
            , R.drawable.wuqi_137, R.drawable.wuqi_138, R.drawable.wuqi_140, R.drawable.wuqi_141, R.drawable.wuqi_142
            , R.drawable.wuqi_144, R.drawable.wuqi_145, R.drawable.wuqi_146, R.drawable.wuqi_147, R.drawable.wuqi_148, R.drawable.wuqi_149, R.drawable.wuqi_150
            , R.drawable.wuqi_152, R.drawable.wuqi_153, R.drawable.wuqi_154, R.drawable.wuqi_155, R.drawable.wuqi_156, R.drawable.wuqi_157
            , R.drawable.wuqi_158, R.drawable.wuqi_159, R.drawable.wuqi_161, R.drawable.wuqi_162, R.drawable.wuqi_163, R.drawable.wuqi_164, R.drawable.wuqi_165
            , R.drawable.wuqi_160};

    private static final int[] imgList2 = new int[]{R.drawable.head_1, R.drawable.head_2, R.drawable.head_3, R.drawable.head_4, R.drawable.head_5
            , R.drawable.head_6, R.drawable.head_7, R.drawable.head_8, R.drawable.head_9, R.drawable.head_10, R.drawable.head_11, R.drawable.head_12
            , R.drawable.head_13, R.drawable.head_14, R.drawable.head_15, R.drawable.head_16, R.drawable.head_17, R.drawable.head_18, R.drawable.head_19
            , R.drawable.head_20, R.drawable.head_21, R.drawable.head_22, R.drawable.head_23, R.drawable.head_24, R.drawable.head_25, R.drawable.head_26
            , R.drawable.head_27, R.drawable.head_28, R.drawable.head_29, R.drawable.head_30, R.drawable.head_31, R.drawable.head_32, R.drawable.head_33
            , R.drawable.head_34, R.drawable.head_35, R.drawable.head_36, R.drawable.head_37, R.drawable.head_38, R.drawable.head_39, R.drawable.head_40
            , R.drawable.head_42, R.drawable.head_43, R.drawable.head_44, R.drawable.head_45, R.drawable.head_46, R.drawable.head_47};

    private static final int[] imgList3 = new int[]{R.drawable.nail_1, R.drawable.nail_2, R.drawable.nail_3, R.drawable.nail_4, R.drawable.nail_5
            , R.drawable.nail_6, R.drawable.nail_7, R.drawable.nail_8, R.drawable.nail_9, R.drawable.nail_10, R.drawable.nail_11, R.drawable.nail_12
            , R.drawable.nail_13, R.drawable.nail_14, R.drawable.nail_15, R.drawable.nail_16, R.drawable.nail_17, R.drawable.nail_18, R.drawable.nail_19
            , R.drawable.nail_20, R.drawable.nail_21, R.drawable.nail_22, R.drawable.nail_23, R.drawable.nail_24, R.drawable.nail_25, R.drawable.nail_26
            , R.drawable.nail_27, R.drawable.nail_28, R.drawable.nail_29, R.drawable.nail_30, R.drawable.nail_31, R.drawable.nail_32, R.drawable.nail_33
            , R.drawable.nail_34, R.drawable.nail_35, R.drawable.nail_36, R.drawable.nail_37, R.drawable.nail_38, R.drawable.nail_39, R.drawable.nail_40
            , R.drawable.nail_41, R.drawable.nail_42, R.drawable.nail_43, R.drawable.nail_44, R.drawable.nail_45, R.drawable.nail_46, R.drawable.nail_47
            , R.drawable.nail_48, R.drawable.nail_50, R.drawable.nail_51, R.drawable.nail_49};

    private static final int[] imgList4 = new int[]{R.drawable.ship_1, R.drawable.ship_2, R.drawable.ship_3, R.drawable.ship_4, R.drawable.ship_5
            , R.drawable.ship_6, R.drawable.ship_7, R.drawable.ship_8, R.drawable.ship_9, R.drawable.ship_10, R.drawable.ship_11, R.drawable.ship_12
            , R.drawable.ship_13, R.drawable.ship_14, R.drawable.ship_15, R.drawable.ship_16, R.drawable.ship_17, R.drawable.ship_18, R.drawable.ship_19
            , R.drawable.ship_20, R.drawable.ship_21, R.drawable.ship_22, R.drawable.ship_23, R.drawable.ship_24, R.drawable.ship_25, R.drawable.ship_26
            , R.drawable.ship_27, R.drawable.ship_28, R.drawable.ship_29, R.drawable.ship_30, R.drawable.ship_31, R.drawable.ship_32, R.drawable.ship_33
            , R.drawable.ship_34, R.drawable.ship_35, R.drawable.ship_36, R.drawable.ship_61, R.drawable.ship_37, R.drawable.ship_38, R.drawable.ship_39
            , R.drawable.ship_40, R.drawable.ship_41, R.drawable.ship_42, R.drawable.ship_43, R.drawable.ship_44, R.drawable.ship_45, R.drawable.ship_46, R.drawable.ship_47
            , R.drawable.ship_48, R.drawable.ship_49, R.drawable.ship_50, R.drawable.ship_51, R.drawable.ship_52, R.drawable.ship_53, R.drawable.ship_54
            , R.drawable.ship_55, R.drawable.ship_56, R.drawable.ship_58, R.drawable.ship_60, R.drawable.ship_62 , R.drawable.ship_63, R.drawable.ship_64
            , R.drawable.ship_57, R.drawable.ship_59};

    private static final int[] imgList = new int[]{R.drawable.chib_1, R.drawable.chib_2, R.drawable.chib_3, R.drawable.chib_4, R.drawable.chib_5
            , R.drawable.chib_6, R.drawable.chib_7, R.drawable.chib_8, R.drawable.chib_9, R.drawable.chib_10, R.drawable.chib_sx, R.drawable.chib_11
            , R.drawable.chib_12, R.drawable.chib_13, R.drawable.chib_14, R.drawable.chib_15, R.drawable.chib_16, R.drawable.chib_18, R.drawable.chib_19
            , R.drawable.chib_20, R.drawable.chib_21, R.drawable.chib_22, R.drawable.chib_23, R.drawable.chib_24, R.drawable.chib_25, R.drawable.chib_26
            , R.drawable.chib_27};

    private static final int[] huizList = new int[]{R.drawable.huiz_1, R.drawable.huiz_2, R.drawable.huiz_3, R.drawable.huiz_4, R.drawable.huiz_5
            , R.drawable.huiz_6, R.drawable.huiz_7, R.drawable.huiz_8, R.drawable.huiz_9, R.drawable.huiz_10, R.drawable.huiz_11, R.drawable.huiz_12
            , R.drawable.huiz_13, R.drawable.huiz_14, R.drawable.huiz_15, R.drawable.huiz_16, R.drawable.huiz_17, R.drawable.huiz_18, R.drawable.huiz_19
            , R.drawable.huiz_20, R.drawable.huiz_21};

    private static final int[] cailList = new int[]{R.drawable.cail_0, R.drawable.cail_1, R.drawable.cail_2, R.drawable.cail_3, R.drawable.cail_4, R.drawable.cail_5
            , R.drawable.cail_6, R.drawable.cail_7, R.drawable.cail_8, R.drawable.cail_9, R.drawable.cail_10, R.drawable.cail_11, R.drawable.cail_12
            , R.drawable.cail_13, R.drawable.cail_14, R.drawable.cail_15, R.drawable.cail_16, R.drawable.cail_17, R.drawable.cail_18, R.drawable.cail_19
            , R.drawable.cail_20, R.drawable.cail_21, R.drawable.cail_22, R.drawable.cail_23, R.drawable.cail_24, R.drawable.cail_25, R.drawable.cail_26, R.drawable.cailiao_57
            , R.drawable.cail_27, R.drawable.cail_28, R.drawable.cail_29, R.drawable.cail_30, R.drawable.cail_31, R.drawable.cail_32, R.drawable.cail_33
            , R.drawable.cail_34, R.drawable.cail_35, R.drawable.cail_36, R.drawable.cail_37, R.drawable.cail_38, R.drawable.cail_39, R.drawable.cail_40
            , R.drawable.cail_41, R.drawable.cail_42, R.drawable.cail_43, R.drawable.cail_44, R.drawable.cail_45, R.drawable.cail_46, R.drawable.cail_47
            , R.drawable.cail_48, R.drawable.cail_49, R.drawable.cail_50, R.drawable.cail_51, R.drawable.cail_53, R.drawable.cail_52, R.drawable.cail_54
            , R.drawable.cail_55, R.drawable.cail_56};

    private static final int[] gao = new int[]{R.drawable.gao_1, R.drawable.gao_1, R.drawable.gao_2, R.drawable.gao_1, R.drawable.gao_1, R.drawable.gao_1
            , R.drawable.shu, R.drawable.haojiao, R.drawable.jiej_1, R.drawable.jiej_2, R.drawable.jiej_3, R.drawable.jiej_4, R.drawable.jiej_5, R.drawable.jiej_6
            , R.drawable.fenmo_1, R.drawable.fenmo_2, R.drawable.fenmo_3, R.drawable.fenmo_4, R.drawable.fenmo_5};

    private static final int[] boss = new int[]{R.drawable.bs_1, R.drawable.bs_2, R.drawable.bs_3, R.drawable.bs_4, R.drawable.bs_5, R.drawable.bs_6
            , R.drawable.bs_7, R.drawable.bs_8, R.drawable.bs_9, R.drawable.bs_10, R.drawable.bs_11, R.drawable.bs_12, R.drawable.bs_13
            , R.drawable.bs_14, R.drawable.bs_15, R.drawable.bs_16, R.drawable.bs_17, R.drawable.bs_18, R.drawable.bs_19, R.drawable.bs_20
            , R.drawable.bs_21, R.drawable.bs_22, R.drawable.bs_23, R.drawable.bs_24, R.drawable.bs_25, R.drawable.bs_26, R.drawable.bs_27
            , R.drawable.bs_28, R.drawable.bs_29, R.drawable.bs_30, R.drawable.bs_31, R.drawable.bs_32, R.drawable.bs_33, R.drawable.bs_34
            , R.drawable.bs_35, R.drawable.bs_36, R.drawable.bs_38, R.drawable.bs_37};

    public static int[] getImgList(String type){
        switch (type) {
            case "武器":
                return imgList0;
            case "材料":
                return cailList;
            case "头盔":
                return imgList2;
            case "衣服":
                return imgList3;
            case "饰品":
                return imgList4;
            case "徽章":
                return huizList;
            case "其他":
                return gao;
            case "boss":
                return boss;
            default:
                return imgList;
        }
    }
}
