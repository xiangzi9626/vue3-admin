package com.example.springbootvue3.controller.admin;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.springbootvue3.entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class FileController {
    @RequestMapping("/file_list")
    public Map<String, Object> fileList(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        int start = (currentPage - 1) * pageSize;
        int end = start + pageSize;
        String path = System.getProperty("user.dir") + "/upload/img/goods";
        List<String> names = FileUtil.listFileNames(path);
        Map<String, Object> map = new HashMap<>();
        map.put("total", names.size());
        List<String> imgList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (i >= names.size()) {
                break;
            }
            imgList.add(names.get(i));
        }
        map.put("img", imgList);
        return map;
    }

    @RequestMapping("/upload_goods_img")
    public Map<String, Object> uploadImg(@RequestParam MultipartFile file, HttpServletRequest request) {
        //文件超初名称
        String originalFileName = file.getOriginalFilename();
        //文件后缀名称
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        if (!extension.equals(".jpg") && !extension.equals(".jpeg") && !extension.equals(".png") && !extension.equals(".gif")) {
            return Result.error("只支持jpg png jpeg gif图片");
        }
//使用hutool生成随机数
        int random = RandomUtil.randomInt(1000, 9999);
//新文件名
        String newFileName = System.currentTimeMillis() + "" + random + extension;
        try {
            //获取文件保存的文件夹
            //如果文件夹不存在创建
            //System.getProperty("user.dir")获取springboot当前运行的目录
            File folder = new File(System.getProperty("user.dir") + "/upload/img/goods/");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            file.transferTo(new File(folder, newFileName));
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/upload/img/goods/" + newFileName;
            Map<String, String> map = new HashMap<>();
            map.put("fileName", newFileName);
            return Result.res(map);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传失败请重试");
        }
    }

    @RequestMapping("/batch_delete_img")
    public Map<String, Object> batchDeleteImg(@RequestBody List<String> names) {
        for (int i = 0; i < names.size(); i++) {
            String fileName = names.get(i);
            File folder = new File(System.getProperty("user.dir") + "/upload/img/goods");
            FileUtil.del(folder + "/" + fileName);
        }
        return Result.success("删除成功");
    }

    @RequestMapping("/delete_goods_img")
    public void deleteGoodsImg(@RequestBody Map<String, String> param) {
        String fileName = param.get("fileName");
        File folder = new File(System.getProperty("user.dir") + "/upload/img/goods");
        FileUtil.del(folder + "/" + fileName);
    }

    @RequestMapping("/editor_upload_img")
    public Map<String, Object> editorUploadImg(@RequestParam MultipartFile file, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        //文件超初名称
        String originalFileName = file.getOriginalFilename();
        //文件后缀名称
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        if (!extension.equals(".jpg") && !extension.equals(".jpeg") && !extension.equals(".png") && !extension.equals(".gif")) {
            map.put("errno", 1);
            map.put("message", "只支持jpg png jpeg gif图片");
            return map;
        }
//使用hutool生成随机数
        int random = RandomUtil.randomInt(1000, 9999);
//新文件名
        String newFileName = System.currentTimeMillis() + "" + random + extension;
        try {
            //获取文件保存的文件夹
            //如果文件夹不存在创建
            //System.getProperty("user.dir")获取springboot当前运行的目录
            File folder = new File(System.getProperty("user.dir") + "/upload/img/goods/");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            file.transferTo(new File(folder, newFileName));
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/upload/img/goods/" + newFileName;
            map.put("errno", 0); // 注意：值是数字，不能是字符串
            data.put("url", url); // 图片 src ，必须
            data.put("alt", "");// 图片描述文字，非必须
            data.put("href", "");// 图片的链接，非必须
            map.put("data", data);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("errno", 1);
            map.put("message", "上传失败请重试");
            return map;
        }
    }

}
