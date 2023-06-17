package com.elephant.controller;

import com.cunw.framework.exception.BusinessException;
import com.elephant.common.enums.ExceptionEnum;
import com.elephant.config.PassToken;
import com.elephant.utils.QRCodeUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qrcode")
public class QRCodeController {

    @ApiOperation(value = "返回二维码流", notes = "通过推广者id返回二维码流")
    @GetMapping("/one")
    @PassToken
    public void qrcode(@SpringQueryMap @Validated QRCodeQueryDTO dto,
                       HttpServletResponse response) throws Exception{

        final String qrcode = dto.getUrl();
        int height = dto.getHeight();
        int width = dto.getWidth();


        if(height <= 0){
            height = 200;
        }
        if(width <= 0){
            width = 200;
        }
//        String qrcode =  "https://www.baidu.com";
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 指定编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 指定纠错级别(L--7%,M--15%,Q--25%,H--30%)
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 编码内容,编码类型(这里指定为二维码),生成图片宽度,生成图片高度,设置参数
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrcode, BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage bufferedImage = QRCodeUtils.createImage(bitMatrix, "", height, width);
        //设置请求头
        response.setHeader("Content-Type","application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + "QRCODE.png");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", bos);
        response.getOutputStream().write(bos.toByteArray());

    }
}
