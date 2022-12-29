package com.hyq.hovel.controller;

import com.hyq.hovel.util.ConverUtils;
import com.hyq.hovel.util.OpenCVUtils;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/opencv")
public class OpenCVController {
    @GetMapping(value = "test")
    public void testOpencv(){
        OpenCVUtils util = new OpenCVUtils();
        BufferedImage buff = ConverUtils.bufferRead("/opt/opencv_test/picture_source/1593547294916157440.jpg");
        //全景图切割
        Mat[] cube = util.shear(buff, 1024, 1024);
        for (int i = 0; i < cube.length; i++) {
            Imgcodecs.imwrite("/opt/opencv_test/picture_temple/"+ i +".jpg", cube[i]);
        }

        //预览图合成
        Mat preview = util.mergeImage(cube, 512);

//        Imgcodecs.imwrite("/opt/opencv_test/picture_target/1593547294916157440.jpg", preview);
    }
}
