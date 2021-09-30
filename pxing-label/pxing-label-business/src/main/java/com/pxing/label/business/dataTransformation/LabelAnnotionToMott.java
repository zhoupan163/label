package com.pxing.label.business.dataTransformation;

import com.alibaba.fastjson.JSONObject;
import com.pxing.label.business.domain.entity.TaskImageEntity;
import com.pxing.label.business.domain.vo.LabelTaskImageVo;

import java.text.DecimalFormat;
import java.util.*;

public class LabelAnnotionToMott {

    /**
     * @param taskImageEntity
     * @return String data
     */


    public static List<String> getMott(TaskImageEntity taskImageEntity){
        List<String> list= new ArrayList<>();
        String sep= " ";
        DecimalFormat df = new DecimalFormat("#0.0000");
        List<JSONObject> annotationInfo= taskImageEntity.getAnnotationInfo();
        String motData= "";
        String ids= "";
        double imgWidth= taskImageEntity.getWidth();
        double imgHeight= taskImageEntity.getHeight();
        for(JSONObject jsonObject: annotationInfo){
            JSONObject shapeAttributes= jsonObject.getJSONObject("shape_attributes");
            double x= shapeAttributes.getDouble("x");
            double y= shapeAttributes.getDouble("y");
            double w= shapeAttributes.getDouble("width");
            double h= shapeAttributes.getDouble("height");

            JSONObject regionAttributes= jsonObject.getJSONObject("region_attributes");
            int block = regionAttributes.getInteger("block");
            int fuzzy= regionAttributes.getInteger("fuzzy");
            int side= regionAttributes.getInteger("side");
            JSONObject cropJson= regionAttributes.getJSONObject("crop");
            String crop= "";
            if(cropJson.containsKey("0")){
                crop= "0";
            }else{
                crop= String.join("", cropJson.keySet());
            }

            String id= regionAttributes.getString("id");
            ids+= id+ sep;
            motData+= taskImageEntity.getImageId()+ sep + id +sep + df.format((x+w/2)/imgWidth)+ sep +df.format((y+h/2)/imgHeight) + sep +
                    df.format(w/imgWidth)+ sep + df.format(h/imgHeight) + sep + block + sep + fuzzy + sep +side + sep + crop +"\n";
        }

        list.add(motData);
        list.add(ids);
        return  list;
    }

    public static void main(String[] args) {
        Double r=123.456789;

        //1.DecimalFormat的格式化结果是String类型的，想要结果为double需要再次强转。
        DecimalFormat df = new DecimalFormat("#.0000");
        System.out.println(df.format(r).toString());

        Map<String,String> map= new HashMap<>();
        map.put("aa","bb");
        System.out.println("aa");

    }

}
