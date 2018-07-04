package online.omnia.dict;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lollipop on 25.08.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        HttpMethodsUtils httpMethodsUtils = new HttpMethodsUtils();
        System.out.println("Getting access token");
        String token = httpMethodsUtils.getToken("https://api.ori.cmcm.com/",
                "13256", "ae3a27715fb432f9ba036f163354e598");
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(TokenEntity.class, new JsonTokenDeserializer());
        builder.registerTypeAdapter(List.class, new JsonModelListDeserializer());
        Gson gson = builder.create();
        TokenEntity tokenEntity = gson.fromJson(token, TokenEntity.class);
        if (tokenEntity == null) return;
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json,application/x.orion.v1+json");
        headers.put("Authorization", "Bearer " + tokenEntity.getAccessToken());
        System.out.println("Getting interests");
        String answer = httpMethodsUtils.getMethod("https://api.ori.cmcm.com/dict/deviceModel", headers);
        List<Model> modelList = gson.fromJson(answer, List.class);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = hssfWorkbook.createSheet();
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("value");
        for (int i = 0; i < modelList.size(); i++) {
            row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(modelList.get(i).getId());
            row.createCell(1).setCellValue(modelList.get(i).getValue());
        }
        modelList = null;
        File file = new File("models.xls");
        if (file.exists()) file.delete();
        file.createNewFile();
        hssfWorkbook.write(new FileOutputStream(file));
        hssfWorkbook.close();
    }
}
