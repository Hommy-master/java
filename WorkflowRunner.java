import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class WorkflowRunner {
    
    private static final String API_URL = "https://api.coze.cn/v1/workflow/run";
    private static final String WORKFLOW_ID = "7595817621703245864";
    private static final String AUTHORIZATION = "Bearer pat_clArWkwdGCGWd7VZpG96sCGriConBBdKTCGJ50mdEJuCKAA2HshshGRveHsuMfiX";
    
    private static void log(String message) {
        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println("[" + timestamp + "] " + message);
    }
    
    private static void runWorkflow(String workflowId, String[] input, int num) {
        log("Begin runWorkflow " + workflowId + " " + String.join(",", input) + " " + num);
        
        try {
            // 创建HTTP连接
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", AUTHORIZATION);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            // 构建JSON请求体
            String jsonInputString = String.format(
                "{\"workflow_id\": \"%s\",\"parameters\": {\"input\": \"%s\", \"num\": \"%d\"},\"is_async\": false}",
                workflowId, String.join(",", input), num
            );
            
            // 发送请求
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            // 读取响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    log("Response: " + response.toString());
                }
            } else {
                log("Error: HTTP response code " + responseCode);
                // 读取错误流
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine;
                    while ((errorLine = br.readLine()) != null) {
                        errorResponse.append(errorLine.trim());
                    }
                    log("Error response: " + errorResponse.toString());
                }
            }
            
            connection.disconnect();
            
        } catch (Exception e) {
            log("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
        
        log("End runWorkflow " + workflowId + " " + String.join(",", min) + " " + num);
    }
    
    // 程序入口 - 唯一的主方法
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java WorkflowRunner <start> <end>");
            System.err.println("Example: java WorkflowRunner 1 10");
            System.exit(1);
        }
        
        try {
            int start = Integer.parseInt(args[0]);
            int end = Integer.parseInt(args[1]);
            
            if (start > end) {
                System.err.println("Error: Start value must be less than or equal to end value");
                System.exit(1);
            }
            
            for (int i = start; i <= end; i++) {
                String[] minArray = {String.valueOf(i)};
                runWorkflow(WORKFLOW_ID, minArray, i);
            }
            
        } catch (NumberFormatException e) {
            System.err.println("Error: Both arguments must be integers");
            System.err.println("Invalid input: '" + args[0] + "' and/or '" + args[1] + "'");
            System.exit(1);
        }
    }
}