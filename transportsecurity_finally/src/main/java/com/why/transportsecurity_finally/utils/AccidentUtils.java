package com.why.transportsecurity_finally.utils;

import java.util.Iterator;
import java.util.List;

/**
 * @Description TODO 事故处理工具类
 * @Author why
 * @Date 2021/7/21 14:20
 * Version 1.0
 **/
public class AccidentUtils {

    /**
     * 计算vx
     * @param ax
     * @return
     */
    public static double vx(List<Double> ax){
        double vx = 0.00;
        Iterator<Double> iterator = ax.iterator();
        while (iterator.hasNext()) {
            Double next = iterator.next();
            vx = vx + next;
        }
        return vx * 0.011;
    }

    /**
     * 计算vy
     * @param ay
     * @return
     */
    public static double vy(List<Double> ay){
        double vy = 0.00;
        Iterator<Double> iterator = ay.iterator();
        while (iterator.hasNext()) {
            Double next = iterator.next();
            vy = vy + next;
        }
        return vy * 0.021;
    }

    /**
     * 计算pdof
     * @param vx
     * @param vy
     * @return
     */
    public static double pdof(double vx,double vy){
        if (vx == 0.00){
            throw new RuntimeException("分母不能为零");
        }
        double pdof = 0.00;
        double temp = Math.toDegrees(Math.atan(vy/vx));
        if (vx >= 0 && vy <=0){
            pdof = temp + 180;
        }else if (vx >= 0 && vy >= 0){
            pdof = temp - 180;
        }else if (vx < 0){
            pdof = temp;
        }
        return pdof;
    }

    /**
     * 计算碰撞方向
     * 1：正面碰撞，2：左侧碰撞，3：追尾碰撞，4：右侧碰撞
     * @param pdof
     * @return
     */
    public static int direction(double pdof){
        int flag = 0;
        if (pdof >= -45 && pdof < 45){
            flag = 1;
        }else if (pdof >= -135 && pdof <-45){
            flag = 2;
        }else if (pdof >= -135 && pdof < 135){
            flag = 3;
        }else {
            flag = 4;
        }
        return flag;
    }

    /**
     * 计算驾驶员损伤程度
     * @param direction 碰撞方向
     * @param isBounce 安全气囊是否弹开，true：弹开，false：未弹开
     * @return true：严重损伤，false：一般损伤
     */
    public static boolean driverDegreeOfDamage(int direction,boolean isBounce,double vx){
        double p = 0.00;
        if (direction == 1){//正面碰撞
            if (isBounce){//安全气囊弹开
                p = 1.000/(1.000 + Math.exp(7.91+0.145*vx));
            }else {//安全气囊未弹开
                p = 1.000 / (1.000 + Math.exp(5.43+0.145*vx));
            }
        }else if (direction == 2){
            if (isBounce){//安全气囊弹开
                p = 1.000/(1.000 + Math.exp(5.134+0.145*vx));
            }else {//安全气囊未弹开
                p = 1.000 / (1.000 + Math.exp(2.654+0.145*vx));
            }
        }

        return p > 0.189;
    }

    /**
     * 后排乘客损伤程度
     * @param vx
     * @return true：严重损伤，false：一般损伤
     */
    public static boolean passengerDegreeOfDamage(double vx){
        if (vx == 0.000){
            throw new RuntimeException("分母不能为0");
        }
        double p = 1.000 / (1.000 + Math.exp(10.614-3.477 * (1.000/vx) + 0.2128*vx));
        return p > 0.29;
    }
}
