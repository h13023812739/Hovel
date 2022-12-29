package com.hyq.hovel.util;

import com.google.common.util.concurrent.*;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class OpenCVUtil {

    private String saveUrl;
    public OpenCVUtil(String saveUrl){
        this.saveUrl=saveUrl;
    }


    double[][] imageTransform = {
            {0, 0},
            {Math.PI /2,0},
            {Math.PI,0},
            {-Math.PI /2,0},
            {0,-Math.PI/2},
            {0,Math.PI/2}
    };
    public OpenCVUtil(){}

    /**
     * 处理函数 多个 文件
     * @param fileListUrl
     */
    public  List<List<Mat>> handels( List<String> fileListUrl ){
        final ExecutorService pool = Executors.newFixedThreadPool(fileListUrl.size());
        List<List<Mat>> matLis = new ArrayList<>();

        try {
            ListeningExecutorService executorService = MoreExecutors.listeningDecorator(pool);
            List<ListenableFuture<List<Mat>>> futures = new ArrayList<>();
            for (String url : fileListUrl) {
                ListenableFuture<List<Mat>> listenableFuture = executorService.submit(new ImageHandel(url));
                Futures.addCallback(listenableFuture, new FutureCallback<List<Mat>>() {
                    public void onSuccess(List<Mat> result) {
                        matLis.add(result);
                    }

                    public void onFailure(Throwable t) {
                        //  LOG.info("Add result value into value list error.", t);
                        throw new RuntimeException(t);
                    }
                }, executorService);
                futures.add(listenableFuture);
            }
            Futures.allAsList(futures).get();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            pool.shutdown();
        }
        return matLis;
    }

    class ImageHandel implements Callable<List<Mat>> {
        private String path;
        public ImageHandel(String path){
            this.path=path;
        }
        @Override
        public List<Mat> call() {
            return handel(path);
        }
    }
    /**
     * 处理函数 单个图片
     * @param imageUrl
     */
    public static List<Mat> handel(String imageUrl){
        //  System.out.println(System.currentTimeMillis());
        OpenCVUtil util = new OpenCVUtil();
        BufferedImage buff = ConverUtil.bufferRead(imageUrl);//
        Mat[] cube = util.shear(buff, 1936, 1936);  //全景图切割
        List<Mat> mats=new ArrayList<Mat>();
        mats.add(cube[3]);
        mats.add(cube[0]);
        mats.add(cube[1]);
        mats.add(cube[2]);
        mats.add(cube[4]);
        mats.add(cube[5]);
        Mat preview=new Mat(256,1536,CvType.CV_8UC1);
        Core.vconcat(mats,preview);

        Mat handelpreview = new Mat();
        Imgproc.resize(preview, handelpreview, new Size(256,1536), 0, 0, Imgproc.INTER_LINEAR);
        mats.add(handelpreview);

        Mat thumb = new Mat();
        Imgproc.resize(mats.get(1), thumb, new Size(240,240), 0, 0, Imgproc.INTER_LINEAR);
        mats.add(thumb);
        return mats;
        //  ConverUtil.matSave(util.saveUrl+"preview.jpg", endst);
        // System.out.println(System.currentTimeMillis());
        //  Mat preview = util.mergeImage(cube, 512);           //预览图合成
    }

    /**
     * 全景图切割，返回6张图
     */
    public Mat[] shear(BufferedImage buff,int targetWidth,int targetHeight)
    {
        //Mat mat = com.imagehandel.ConverUtil.matRead("/home/night/webvr/vr.jpg");
        Mat mat = ConverUtil.bufferToMat(buff, buff.getType());
        final ExecutorService pool = Executors.newFixedThreadPool(6);
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(pool);
        List<ListenableFuture<HashMap<Integer,Mat>>> futures = new ArrayList<>();
        try {
            Mat[] cube = new Mat[6];
            for (int i = 0; i < 6; i++) {
                //  cube[i] = sideCubeMapImage(mat, i, targetWidth, targetHeight);
                ListenableFuture<HashMap<Integer,Mat>> listenableFuture = executorService.submit(new MatHandel(mat,i,targetWidth,targetHeight));
                Futures.addCallback(listenableFuture, new FutureCallback<HashMap<Integer,Mat>>() {
                    public void onSuccess(HashMap<Integer,Mat> result) {
                        for(int i:result.keySet()){
                            cube[i]=result.get(i);
                        }
                    }
                    public void onFailure(Throwable t) {
                        //  LOG.info("Add result value into value list error.", t);
                        throw new RuntimeException(t);
                    }
                },executorService);
                futures.add(listenableFuture);
            }
            ListenableFuture<List<HashMap<Integer,Mat>>> allAsList = Futures.allAsList(futures);
            allAsList.get();
            return cube;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            pool.shutdown();
        }
        return null;
    }

    class MatHandel implements  Callable<HashMap<Integer,Mat>>{
        private Mat mat;
        private int index;
        private int width;
        private int height;
        public MatHandel( Mat mat,int index, int width, int height){
            this.mat=mat;
            this.index=index;
            this.width=width;
            this.height=height;
        }
        public HashMap<Integer,Mat> call() throws Exception {
            System.out.println("call execute..");
            Mat mat = sideCubeMapImage(this.mat, index, width, height);
            //   TimeUnit.SECONDS.sleep(1);
            HashMap<Integer,Mat> matHashMap=new HashMap<>();
            matHashMap.put(index,mat);
            return matHashMap;
        }
    }
    /**
     * 全景图切割，单面处理
     * @param source
     * @param sideId
     * @param sideWidth
     * @param sideHeight
     * @return
     */
    private Mat sideCubeMapImage(Mat source, final int sideId,final int sideWidth,final int sideHeight)
    {
        Mat result = new Mat(sideWidth, sideHeight, source.type());

        System.out.println("==========handle "+sideId+" start ===========");
        float sourceWidth = source.cols();
        float sourceHeight = source.rows();     // 获取图片的行列数量

        Mat mapx = new Mat(sideHeight, sideWidth, CvType.CV_32F);
        Mat mapy = new Mat(sideHeight, sideWidth, CvType.CV_32F);       //分配图的x,y轴

        // Calculate adjacent (ak) and opposite (an) of the
        // triangle that is spanned from the sphere center
        //to our cube face.
        final double an = Math.sin(Math.PI / 4);
        final double ak = Math.cos(Math.PI / 4);                                          //计算相邻ak和相反an的三角形张成球体中心

        double ftu = imageTransform[sideId][0];
        double ftv = imageTransform[sideId][1];

        // For each point in the target image,
        // calculate the corresponding source coordinates.                      对于每个图像计算相应的源坐标
        for (int y = 0; y < sideHeight; y++) {
            for (int x = 0; x < sideWidth; x++) {

                // Map face pixel coordinates to [-1, 1] on plane               将坐标映射在平面上
                float nx = (float)y / (float)sideHeight - 0.5f;
                float ny = (float)x / (float)sideWidth - 0.5f;

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
                    // Center faces
                    u = Math.atan2(nx, ak);
                    v = Math.atan2(ny * Math.cos(u), ak);
                    u += ftu;
                }
                else if (ftv > 0) {
                    // Bottom face
                    double d = Math.sqrt(nx * nx + ny * ny);
                    v = Math.PI / 2 - Math.atan2(d, ak);
                    u = Math.atan2(ny, nx);
                }
                else {
                    // Top face
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

                u = u*(sourceWidth-1);
                v = v*(sourceHeight-1);

                mapx.put(x, y, u);
                mapy.put(x, y, v);
            }
        }

        /**
         if (result.cols() != width || result.rows() != height ||
         result.type() != source.type()) {
         result = new Mat(width, height, source.type());
         }**/

        Imgproc.remap(source, result, mapx, mapy, Imgproc.INTER_LINEAR, Core.BORDER_CONSTANT, new Scalar(0, 0, 0));

        if (sideId == 0)
        {
            //   ConverUtil.matSave("D:\\img_lib\\pano_f.jpg", result);
            //   Mat endst = new Mat();
            // Imgproc.resize(result, endst, new Size(240,240), 0, 0, Imgproc.INTER_LINEAR);
            // ConverUtil.matSave(this.saveUrl+"thumb.jpg", endst);
        }
        else if (sideId == 1)
        {
            //ConverUtil.matSave(this.saveUrl+"pano_r.jpg", result);
        }
        else if (sideId == 2)
        {
            //  ConverUtil.matSave(this.saveUrl+"pano_b.jpg", result);
        }
        else if (sideId == 3)
        {
            //ConverUtil.matSave(this.saveUrl+"pano_l.jpg", result);
        }
        else if (sideId == 4)
        {
            result= rotateRight(result,1);
            // ConverUtil.matSave(this.saveUrl+"pano_u.jpg", mat);
        }
        else if (sideId == 5)
        {
            result= rotateRight(result,0);
            // ConverUtil.matSave(this.saveUrl+"pano_d.jpg", mat);
        }

        System.out.println("==========handle "+sideId+" over ===========");

        return result;
    }


    /**
     * 图像整体向左旋转90度
     *
     * @param src Mat
     * @return 旋转后的Mat
     */
    public static Mat rotateRight(Mat src,int flipCode) {
        Mat tmp = new Mat();
        // 此函数是转置、（即将图像逆时针旋转90度，然后再关于x轴对称）
        Core.transpose(src, tmp);
        Mat result = new Mat();
        // flipCode = 0 绕x轴旋转180， 也就是关于x轴对称
        // flipCode = 1 绕y轴旋转180， 也就是关于y轴对称
        // flipCode = -1 此函数关于原点对称
        Core.flip(tmp, result, flipCode);
        return result;
    }

    /**
     * 全景预览图合成
     */
    private Mat mergeImage(Mat[] cube,int width)
    {

        Mat mat = new Mat(width * cube.length, width, cube[0].type());
        for(int i=0;i<cube.length;i++) {
            Mat side = ConverUtil.matResize(cube[i], 512, 512);
            mat.put(i*512, 0, getByte(side));
        }

        ConverUtil.matSave("D:\\img_lib\\test2_cube11.jpg", mat);
        return mat;
    }

    public byte[] getByte(Mat mat) {
        int width = mat.cols();
        int height = mat.rows();
        int dims = mat.channels();
        byte[] rgbdata = new byte[width*height*dims];
        mat.get(0, 0, rgbdata);
        return rgbdata;
    }

}