package com.kimp.calculatorbot.upbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimp.calculatorbot.config.UpbitConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UpbitCoinController {

    private final UpbitConfig upbitConfig;

    @GetMapping("/market/all")
    public String getMarketAll(){
        Map<String, Object> result = new LinkedHashMap<>();

        String jsonInString = "";

        try {
            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);

            String url = "https://api.upbit.com/v1/market/all";

            //이 한줄의 코드로 API를 호출해 MAP타입으로 전달 받는다.
            ResponseEntity<List> resultList = upbitConfig.restTemplate().exchange(url, HttpMethod.GET, entity, List.class);
            result.put("statusCode", resultList.getStatusCodeValue()); //http status code를 확인
            result.put("header", resultList.getHeaders()); //헤더 정보 확인
            result.put("body", resultList.getBody()); //실제 데이터 정보 확인

            System.out.println("result = " + result);
            //데이터를 제대로 전달 받았는지 확인 string형태로 파싱해줌


            System.out.println("result.get(BTC-NMR) = " + result.get("body"));


            ObjectMapper mapper = new ObjectMapper();
            jsonInString = mapper.writeValueAsString(resultList.getBody());
            log.info("mapper to String : {}", jsonInString);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            result.put("body"  , e.getStatusText());
            System.out.println(e.toString());

        } catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body"  , "excpetion오류");
            System.out.println(e.toString());
        }

        return jsonInString;
    }
}
