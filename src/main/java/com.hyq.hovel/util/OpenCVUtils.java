package com.hyq.hovel.util;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.io.File;


public class OpenCVUtils {

    static {
//        String opencvDllName = "D:\\Downloads\\Programs\\opencv\\build\\java\\x64\\opencv_java460.dll";               //Linux 环境
//        String opencvSoName = "/usr/local/opencv-4.6.0/build/lib/libopencv_java460.so";               //Linux中so文件地址
        String opencvSoName = "/usr/local/share/java/opencv4/libopencv_java460.so";
        System.load(opencvSoName);


    }

    double[][] imageTransform =
            {
                    {0, 0},
                    {Math.PI / 2, 0},
                    {Math.PI, 0},
                    {-Math.PI / 2, 0},
                    {0, -Math.PI / 2},
                    {0, Math.PI / 2}
            };

    /**
     * 全景图切割，返回6张图
     * ossPanoPath  oss存储地址
     * panoImgPath  本地存储地址
     */
    public Mat[] shear(BufferedImage buff, int targetWidth, int targetHeight) {
        Mat mat = ConverUtils.bufferToMat(buff, buff.getType());
        Mat[] cube = new Mat[6];
        for (int i = 0; i < 6; i++) {
            cube[i] = sideCubeMapImage(mat, i, targetWidth, targetHeight);
        }
        return cube;
    }

    /**
     * @Description: 全景图切割，单面处理
     * @Param: [source, sideId, sideWidth, sideHeight]
     * @return: org.opencv.core.Mat
     * @Author: XQD
     * @Date:2021/10/22 13:51
     */
    private Mat sideCubeMapImage(Mat source, final int sideId, final int sideWidth, final int sideHeight) {
        Mat result = new Mat(sideWidth, sideHeight, source.type());
        System.out.println("==========handle " + sideId + " start ===========");
        // 获取图片的行列数量
        float sourceWidth = source.cols();
        float sourceHeight = source.rows();
        //分配图的x,y轴
        Mat mapx = new Mat(sideHeight, sideWidth, CvType.CV_32F);
        Mat mapy = new Mat(sideHeight, sideWidth, CvType.CV_32F);

        //计算相邻ak和相反an的三角形张成球体中心
        final double an = Math.sin(Math.PI / 4);
        final double ak = Math.cos(Math.PI / 4);

        double ftu = imageTransform[sideId][0];
        double ftv = imageTransform[sideId][1];

        //对于每个图像计算相应的源坐标
        for (int y = 0; y < sideHeight; y++) {
            for (int x = 0; x < sideWidth; x++) {

                //将坐标映射在平面上
                float nx = (float) y / (float) sideHeight - 0.5f;
                float ny = (float) x / (float) sideWidth - 0.5f;

                nx *= 2;
                ny *= 2;

                // Map [-1, 1] plane coord to [-an, an]
                // thats the coordinates in respect to a unit sphere
                // that contains our box.
                nx *= an;
                ny *= an;

                double u, v;

                // Project from plane to sphere surface.
                if (ftv == 0) {
                    // Center faces 中心面
                    u = Math.atan2(nx, ak);
                    v = Math.atan2(ny * Math.cos(u), ak);
                    u += ftu;
                } else if (ftv > 0) {
                    // Bottom face 低面
                    double d = Math.sqrt(nx * nx + ny * ny);
                    v = Math.PI / 2 - Math.atan2(d, ak);
                    u = Math.atan2(ny, nx);
                } else {
                    // Top face 顶面
                    //cout << "aaa";
                    double d = Math.sqrt(nx * nx + ny * ny);
                    v = -Math.PI / 2 + Math.atan2(d, ak);
                    u = Math.atan2(-ny, nx);
                }

                // Map from angular coordinates to [-1, 1], respectively.
                u = u / (Math.PI);
                v = v / (Math.PI / 2);

                // Warp around, if our coordinates are out of bounds.
                while (v < -1) {
                    v += 2;
                    u += 1;
                }
                while (v > 1) {
                    v -= 2;
                    u += 1;
                }

                while (u < -1) {
                    u += 2;
                }
                while (u > 1) {
                    u -= 2;
                }

                // Map from [-1, 1] to in texture space
                u = u / 2.0f + 0.5f;
                v = v / 2.0f + 0.5f;

                u = u * (sourceWidth - 1);
                v = v * (sourceHeight - 1);

                mapx.put(x, y, u);
                mapy.put(x, y, v);
            }
        }

        // Do actual  using OpenCV's remap
        Imgproc.remap(source, result, mapx, mapy, Imgproc.INTER_LINEAR, Core.BORDER_CONSTANT, new Scalar(0, 0, 0));
        // 均值模糊(降噪)
        Mat image = source.clone();
        Imgproc.blur(source, image, new Size(3,3),new Point(-1,-1));
        if (sideId == 0) {
            ConverUtils.matSave("E:\\data\\kct.tiles\\a.jpg", result);
        } else if (sideId == 1) {
            ConverUtils.matSave("E:\\data\\kct.tiles\\b.jpg", result);
        } else if (sideId == 2) {
            ConverUtils.matSave("E:\\data\\kct.tiles\\c.jpg", result);
        } else if (sideId == 3) {
            ConverUtils.matSave("E:\\data\\kct.tiles\\d.jpg", result);
        } else if (sideId == 4) {
            //旋转角度
            result = ConverUtils.rotateRight(result, 1);
            ConverUtils.matSave("E:\\data\\kct.tiles\\e.jpg", result);
        } else if (sideId == 5) {
            //旋转角度
            result = ConverUtils.rotateRight(result, 0);
            ConverUtils.matSave("E:\\data\\kct.tiles\\f.jpg", result);
        }
        System.out.println("==========handle " + sideId + " over ===========");
        return result;
    }

    /**
     * 全景预览图合成
     */
    public Mat mergeImage(Mat[] cube, int width) {
        Mat mat = new Mat(width * cube.length, width, cube[0].type());
        for (int i = 0; i < cube.length; i++) {
            Mat side = ConverUtils.matResize(cube[i], 512, 512);
            mat.put(i * 512, 0, getByte(side));
        }
        ConverUtils.matSave("/opt/opencv_test/picture_target/1593547294916157440.jpg", mat);
        return mat;
    }

    public byte[] getByte(Mat mat) {
        int width = mat.cols();
        int height = mat.rows();
        int dims = mat.channels();
        byte[] rgbdata = new byte[width * height * dims];
        mat.get(0, 0, rgbdata);
        return rgbdata;
    }



}

