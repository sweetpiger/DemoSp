package com.example.demo.enlys.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

//@Slf4j
@RestController
@RequestMapping("/api/bmi")
public class BMIController {


    private static final Logger log = LoggerFactory.getLogger(BMIController.class);

    /**
     * 计算BMI指数和推荐体重范围
     * @param height 身高(cm)
     * @param weight 体重(kg)
     * @return BMI计算结果和推荐信息
     */
    @GetMapping("/calculate")
    public Map<String, Object> calculateBMI(@RequestParam double height, @RequestParam double weight) {
        Map<String, Object> result = new HashMap<>();

        // 计算BMI = 体重(kg) / (身高(m) * 身高(m))
        double heightInMeter = height / 100.0;
        double bmi = weight / (heightInMeter * heightInMeter);

        // 确定BMI分类
        String category = getCategory(bmi);

        // 计算推荐体重范围
        Map<String, Double> idealWeightRange = getIdealWeightRange(height);

        result.put("height", height);
        result.put("weight", weight);
        result.put("bmi", Math.round(bmi * 100.0) / 100.0); // 保留两位小数
        result.put("category", category);
        result.put("idealWeightMin", Math.round(idealWeightRange.get("min") * 100.0) / 100.0);
        result.put("idealWeightMax", Math.round(idealWeightRange.get("max") * 100.0) / 100.0);
        result.put("message", generateHealthMessage(category));
        log.info(result.toString());
        return result;
    }

    /**
     * 根据BMI值确定分类
     * @param bmi BMI值
     * @return 分类描述
     */
    private String getCategory(double bmi) {
        if (bmi < 18.5) {
            return "偏瘦";
        } else if (bmi < 24) {
            return "正常";
        } else if (bmi < 28) {
            return "偏胖";
        } else {
            return "肥胖";
        }
    }

    /**
     * 根据身高计算理想体重范围(BMI 18.5-23.9为正常范围)
     * @param height 身高(cm)
     * @return 推荐体重范围
     */
    private Map<String, Double> getIdealWeightRange(double height) {
        double heightInMeter = height / 100.0;
        double minWeight = 18.5 * heightInMeter * heightInMeter;
        double maxWeight = 23.9 * heightInMeter * heightInMeter;

        Map<String, Double> range = new HashMap<>();
        range.put("min", minWeight);
        range.put("max", maxWeight);
        return range;
    }

    /**
     * 生成健康建议消息
     * @param category BMI分类
     * @return 健康建议
     */
    private String generateHealthMessage(String category) {
        switch (category) {
            case "偏瘦":
                return "您的体重偏轻，建议适当增加营养摄入，进行适量的力量训练来增加肌肉量。";
            case "正常":
                return "恭喜！您的体重在正常范围内，请继续保持良好的生活习惯。";
            case "偏胖":
                return "您的体重略高于正常范围，建议控制饮食，增加运动量。";
            case "肥胖":
                return "您的体重已达到肥胖标准，建议尽快调整饮食结构，增加体育锻炼，必要时咨询医生。";
            default:
                return "请注意保持健康的生活方式。";
        }
    }
}
