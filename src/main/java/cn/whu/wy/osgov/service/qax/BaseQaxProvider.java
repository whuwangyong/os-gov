package cn.whu.wy.osgov.service.qax;

import cn.whu.wy.osgov.service.qax.response.QaxResponse;
import cn.whu.wy.osgov.service.qax.response.QaxResponsePageableData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @Author WangYong
 * @Date 2022/09/27
 * @Time 18:15
 */
@Slf4j
public class BaseQaxProvider {

    @Autowired
    private RestTemplate qaxRestTemplate;

    private static final int SUCCESS_CODE = 1;

    protected <T> T get(String url, ParameterizedTypeReference<QaxResponse<T>> reference) {
        ResponseEntity<QaxResponse<T>> response = qaxRestTemplate.exchange(url,
                HttpMethod.GET,
                null,
                reference);
        QaxResponse<T> body = response.getBody();
        assert body != null;
        if (body.getCode() == SUCCESS_CODE) {
            return body.getData();
        } else {
            log.error("get {} error!", url);
            throw new RuntimeException(body.toString());
        }
    }

    /**
     * 获取指定taskId的全部结果
     */
    protected <T> List<T> post(String url, int taskId, int pageSize, ParameterizedTypeReference<QaxResponse<QaxResponsePageableData<T>>> reference) {
        // 先获取第一页，看看总量
        QaxResponsePageableData<T> page1 = postForOnePage(url, taskId, 0, pageSize, reference);
        int total = page1.getTotal();
        // 如果只有1页，直接返回
        if (total <= pageSize) {
            return page1.getContent() == null ? Collections.emptyList() : page1.getContent();
        } else {
            // 如果多页，则多次获取，累加后再返回
            List<T> contents = new ArrayList<>(total);
            contents.addAll(page1.getContent());
            int pages = total / pageSize + (total % pageSize == 0 ? 0 : 1);
            for (int pageIndex = 1; pageIndex < pages; pageIndex++) {
                QaxResponsePageableData<T> data = postForOnePage(url, taskId, pageIndex, pageSize, reference);
                contents.addAll(data.getContent());
            }
            return contents;
        }
    }

    /**
     * @param pageIndex 页码，从0开始
     * @param pageSize  页面大小
     * @return 按指定size分页，返回指定的那一页数据
     */
    private <T> QaxResponsePageableData<T> postForOnePage(String url, int taskId, int pageIndex, int pageSize,
                                                          ParameterizedTypeReference<QaxResponse<QaxResponsePageableData<T>>> reference) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("taskId", taskId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        HttpEntity<HashMap<Object, Object>> entity = new HttpEntity<>(map);
        ResponseEntity<QaxResponse<QaxResponsePageableData<T>>> response = qaxRestTemplate.exchange(url,
                HttpMethod.POST,
                entity,
                reference);
        QaxResponse<QaxResponsePageableData<T>> body = response.getBody();
        assert body != null;
        if (body.getCode() == SUCCESS_CODE) {
            return body.getData();
        } else {
            log.error("post {} error! taskId={}, pageIndex={}, pageSize={}", url, taskId, pageIndex, pageSize);
            throw new RuntimeException(body.toString());
        }
    }
}
