package me.step4.SearchPlace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.step4.SearchPlace.model.CommonResponse;
import me.step4.SearchPlace.model.CommonResult;
import me.step4.SearchPlace.model.ListResult;
import me.step4.SearchPlace.model.SingleResult;

/**
 * 결과처리 서비스
 * @author Sihun
 *
 */
@RequiredArgsConstructor
@Service
public class ResponseService {
    /**
     * 단일건 결과를 처리
     * @param <T>
     * @param data
     * @return
     */
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }
    /**
     * 다중건 결과를 처리
     * @param <T>
     * @param list
     * @return
     */
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }
    /**
     * 성공 결과 처리
     * @return
     */
    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }
    /**
     * 실패 결과 처리
     * @return
     */
    public CommonResult getFailResult(CommonResponse resp) {
        return getFailResult(resp, null);
    }
    /**
     * 실패 결과 처리
     * @return
     */
    public CommonResult getFailResult(CommonResponse resp, String detailErrorMessage) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(resp.getCode());
        result.setMessage(resp.getMessage());
        result.setDetailErrorMessage(detailErrorMessage);
        return result;
    }
    /**
     * 결과 모델에 api 요청 성공 데이터를 셋팅
     * @param result
     */
    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMessage(CommonResponse.SUCCESS.getMessage());
    }
}



