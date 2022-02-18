package com.cms.common.tool.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码工具类
 * @author DT
 * @date 2021/6/8 22:17
 */
public class VerifyCodeUtils {

    /**
     * 传入BufferedImage对象，并将生成好的验证码保存到BufferedImage中
     */
    public static String drawRandomText(BufferedImage bufferedImage, int width, int height) {
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        // 验证码背景色
        graphics.setColor(new Color(245, 247, 250));
        // 填充线条背景
        graphics.fillRect(0, 0, width, height);
        graphics.setFont(new Font("宋体,楷体,微软雅黑", Font.BOLD, 28));
        // 数字和字母的组合
        String baseNumLetter = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder builder = new StringBuilder();
        // 旋转原点的 x 坐标
        int x = 10;
        String ch;
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            graphics.setColor(getRandomColor());
            //设置字体旋转角度,角度小于30度
            int degree = random.nextInt() % 25;
            int dot = random.nextInt(baseNumLetter.length());
            ch = baseNumLetter.charAt(dot) + "";
            builder.append(ch);
            //正向旋转
            graphics.rotate(degree * Math.PI / 180, x, 35);
            graphics.drawString(ch, x, 35);
            //反向旋转
            graphics.rotate(-degree * Math.PI / 180, x, 35);
            x += 48;
        }
        // 画干扰线
        for (int i = 0; i < 6; i++) {
            // 设置随机颜色
            graphics.setColor(getRandomColor());
            // 随机画线
            graphics.drawLine(random.nextInt(width), random.nextInt(height),random.nextInt(width), random.nextInt(height));
        }
//        // 添加噪点
//        for (int i = 0; i < 30; i++) {
//            int x1 = random.nextInt(width);
//            int y1 = random.nextInt(height);
//            graphics.setColor(getRandomColor());
//            graphics.fillRect(x1, y1, 2, 2);
//        }
        return builder.toString();
    }

    /**
     * 随机取色
     */
    private static Color getRandomColor() {
        Random ran = new Random();
        return new Color(ran.nextInt(256),ran.nextInt(256), ran.nextInt(256));
    }
}

