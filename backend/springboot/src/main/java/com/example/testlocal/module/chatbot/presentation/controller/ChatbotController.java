package com.example.testlocal.module.chatbot.presentation.controller;

import com.example.testlocal.config.ApiKey;
import com.example.testlocal.config.Constants;
import com.example.testlocal.domain.dto.ChatbotDTO;
import com.example.testlocal.module.chatbot.domain.entity.Chatbot;
import com.example.testlocal.module.chatbot.application.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Deprecated
@Slf4j
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@CrossOrigin(origins = Constants.URL, allowCredentials = "true")
public class ChatbotController {

    private final ChatbotService chatbotService;
    private final Long CHATBOT_ID = 4L;
    //private final ApiKey apiKey = ApiKey.getInstance();

    private ApiKey apiKey;

    @PostMapping("/chatbotMessage/roomId/{roomId}")
    public List<Chatbot> findAllByRoomId(@PathVariable Long roomId) {
        return chatbotService.findByRoomId(roomId);
    }

    //@MessageMapping("/sendMessage") //client -> server & "/app/sendMessage"
    //@SendTo("/topic/public")    //server -> client

    @PostMapping("/chatbotMessage/message/{roomId}/{userId}")
    public Map<Object, Object> send(HttpServletRequest request, @RequestBody Map<String, Object> map, @PathVariable Long roomId, @PathVariable Long userId) throws IOException, InterruptedException, TimeoutException {

        Map<Object, Object> resultMap = new HashMap<>();
        List<String> recoList = new ArrayList<>();

        int cRoomId = (int) map.get("cRoomId");
        String botLang = ( Long.valueOf(cRoomId) == roomId) ? "c" : "p";
        map.put("botLang",botLang);

        ChatbotDTO input = null, result = null;
        String chatMessage = (String)map.get("message");
        String resultMessage = "", resultBotMsg = "";
        input = new ChatbotDTO(userId,roomId, chatMessage, "");
        input.setCreateTime(new Timestamp((Long) map.get("time")));

        if(chatMessage.trim().toLowerCase().equals("tip") || chatMessage.trim().toLowerCase().equals("help") ||
                chatMessage.trim().equals("?????????") ||chatMessage.trim().equals("???") ||chatMessage.trim().equals("?????????")){
            resultBotMsg = Constants.CHATBOT_TIP;
        }
        else {
            HttpHeaders headers = new HttpHeaders();

            RestTemplate restTemplate = new RestTemplate();

            resultMessage = restTemplate.postForObject(Constants.BOT_PREDICTION_URL, new HttpEntity<>(map, headers), String.class);

            JSONObject resultJson = new JSONObject(resultMessage);
            resultBotMsg = (String) resultJson.get("botMsg");

            JSONArray resultReco = resultJson.getJSONArray("reco");

            for (int i = 0; i < resultReco.length(); i++)
                recoList.add((String) resultReco.get(i));
        }

        result = new ChatbotDTO(CHATBOT_ID ,roomId, resultBotMsg, recoList.toString());

        resultMap.put("result", result);
        resultMap.put("recommend", recoList);

        chatbotService.create(input);
        chatbotService.create(result);
        return resultMap;
    }

//    @PostMapping("/chatbotMessage/send/{roomId}/{userId}")
//    public ChatbotDTO sendMessage(@RequestBody Map<String, Object> map,@PathVariable Long roomId, @PathVariable Long userId) throws IOException {
//
//        ChatbotDTO input = null, result = null;
//        String chatMessage = (String)map.get("message");
//        String sendMessage = chatMessage;
//
//        URL url = new URL(apiUrl);
//        String message =  getReqMessage(chatMessage);
//        String encodeBase64String = makeSignature(message);
//
//        // api?????? ??????
//        HttpURLConnection con = (HttpURLConnection)url.openConnection();
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Content-Type", "application/json;UTF-8");
//        con.setRequestProperty("X-NCP-CHATBOT_SIGNATURE", encodeBase64String);
//
//        con.setDoOutput(true);
//        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//
//        wr.write(message.getBytes("UTF-8"));
//        wr.flush();
//        wr.close();
//        int responseCode = con.getResponseCode();
//
//        if(responseCode==200) { // ?????? ??????
//
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(
//                            con.getInputStream(), "UTF-8"));
//            String decodedString;
//            String jsonString = "";
//            while ((decodedString = in.readLine()) != null) {
//                jsonString = decodedString; //jsonString??? ??? ????????????
//            }
//
//            //????????? ?????? ???????????? ??????
//            JSONParser jsonParser = new JSONParser();
//            //JSONParser jsonParser = new JSONParser(jsonString);
//            try {
//                JSONObject jsonObject = (JSONObject)jsonParser.parse(jsonString);
//
//                // "bubbles": [ {"type": "text",
//                //"data" : { "description" : "postback text of welcome action" } } ],
//                //"event": "open"
//                JSONArray bubblesArray = (JSONArray)jsonObject.get("bubbles");
//                JSONObject bubbles = (JSONObject)bubblesArray.get(0);
//
//                JSONObject data = (JSONObject)bubbles.get("data");
//                String description = "";
//                description = (String)data.get("description");
//                chatMessage = description;
//
//                input = new ChatbotDTO(userId,roomId, sendMessage);
//                input.setCreateTime(new Timestamp((Long) map.get("time")));
//                result = new ChatbotDTO(CHATBOT_ID ,roomId, chatMessage);
//
//                chatbotService.create(input);
//                chatbotService.create(result);
//
//            } catch (Exception e) {
//
//                e.printStackTrace();
//            }
//
//            in.close();
//        }
//
//        else {  // ?????? ??????
//            chatMessage = con.getResponseMessage();
//        }
//
//        return result;
//    }
//
//    //?????? ???????????? ??????????????? ???????????? ???????????? ??????????????? ?????????
//    public static String makeSignature(String message) {
//
//        String signatureHeader = "";
//
//        // ?????? Signature ??????
//        try {
//            byte[] secrete_key_bytes = secretKey.getBytes(StandardCharsets.UTF_8);
//         //   byte[] secrete_key_bytes = secretKey.getBytes("UTF_8");
//
//            SecretKeySpec secretKeySpec = new SecretKeySpec(secrete_key_bytes, "HmacSHA256");
//            Mac mac = Mac.getInstance("HmacSHA256");
//            mac.init(secretKeySpec);
//
//            byte[] signature  = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
//           // byte[] signature  = mac.doFinal(message.getBytes("UTF-8"));
//
//            //String signatureHeader = Base64.getEncoder().encodeToString(signature);
//            signatureHeader= Base64.encodeBase64String(signature);
//            //  signatureHeader = Base64.encodeToString(signature, Base64.NO_WRAP);
//
//            return signatureHeader;
//
//        } catch (Exception e){
//            System.out.println(e);
//        }
//        return signatureHeader;
//    }
//
//    //?????? ???????????? ????????? ????????? ???????????? ??????????????? ?????????
//    public static String getReqMessage(String sendMessage) {
//
//        String requestBody = "";
//
//        //sendMessage = "???????????? ??????";
//
//        try {
//
//            JSONObject jsonObject = new JSONObject();
//
//            long timestamp = new Date().getTime();
//
//            //System.out.println("##"+timestamp);
//
//            jsonObject.put("version", "v2");
//            // userid??? ????????? ???????????? ???????????? ??????
//            // ?????? ?????? ??? ????????????
//            jsonObject.put("userId", "U47b00b58c90f8e47428af8b7bddc1231heo2");
//            jsonObject.put("timestamp", timestamp);
//
//            JSONObject bubbles_obj = new JSONObject();
//
//            //bubbles_obj.put("type", "text");
//
//            JSONObject data_obj = new JSONObject();
//            data_obj.put("description", sendMessage);
//
//            bubbles_obj.put("type", "text");
//            bubbles_obj.put("data", data_obj);
//
//            JSONArray bubbles_array = new JSONArray();
//            bubbles_array.add(bubbles_obj);
//
//            jsonObject.put("bubbles", bubbles_array);
//            jsonObject.put("event", "send");
//
//
//            requestBody = jsonObject.toString();
//
//        } catch (Exception e){
//            System.out.println("## Exception : " + e);
//        }
//        return requestBody;
//    }



}
