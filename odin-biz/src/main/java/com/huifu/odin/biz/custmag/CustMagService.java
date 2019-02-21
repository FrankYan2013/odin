package com.huifu.odin.biz.custmag;

import com.alibaba.fastjson.JSON;
import com.huifu.custmag.facade.model.TradeCancelDto;
import com.huifu.custmag.facade.model.TradeLimitDto;
import com.huifu.custmag.facade.response.TradeCancelResult;
import com.huifu.custmag.facade.response.TradeLimitResult;
import com.huifu.odin.facade.service.trans.AcctTransRequestDetailDTO;
import com.huifu.odin.facade.service.trans.AcctTransRequestPeg;
import com.huifu.odin.integration.custmag.CustmagClient;
import com.huifu.odin.util.common.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author frank
 */
@Service
public class CustMagService {

    @Autowired
    private CustmagClient custmagClient;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    static String TRANS_TYPE_2007 = "2007";


    public TradeLimitResult tradeLimitControl(AcctTransRequestPeg acctTransRequestPeg) throws Exception {
        ArrayList<TradeLimitDto> tradeLimitDtoList = new ArrayList<>();
        if (acctTransRequestPeg.getAcctTransDetailList() != null && acctTransRequestPeg.getAcctTransDetailList().size() > 0) {
            TradeLimitDto tradeLimitDto = new TradeLimitDto();
            for (AcctTransRequestDetailDTO acctTransRequestDetailDTO : acctTransRequestPeg.getAcctTransDetailList()) {
                String transType = acctTransRequestDetailDTO.getTransType();

                if (CustmagTransTypeEnum.valueByCode(transType) != null && acctTransRequestDetailDTO.isPayAcct()) {
                    tradeLimitDto = new TradeLimitDto();
                    tradeLimitDto.setAcctId(acctTransRequestDetailDTO.getSubAcctId());
                    tradeLimitDto.setAmount(acctTransRequestDetailDTO.getTransAmt());
                    tradeLimitDto.setCustId(acctTransRequestDetailDTO.getCustId());
                    tradeLimitDto.setMerId(acctTransRequestDetailDTO.getMerId());
                    tradeLimitDto.setReqSeq(acctTransRequestPeg.getReqSeqId());
                    tradeLimitDto.setReqTime(DateUtils.toFormatDateString(new Date(), DateUtils.MAIL_DATE_FORMAT));
                    tradeLimitDto.setSystemId(acctTransRequestPeg.getSysId());
                    tradeLimitDto.setTradeType(CustmagTransTypeEnum.valueByCode(transType).getCustmagTradeType());
                    tradeLimitDto.setTradeVerType(acctTransRequestPeg.getVerifyType());
                    tradeLimitDto.setTransDate(acctTransRequestDetailDTO.getFrtDate());
                    tradeLimitDto.setTransReq(acctTransRequestDetailDTO.getFrtSeqId());
                    if (StringUtils.equals(TRANS_TYPE_2007, acctTransRequestDetailDTO.getTransType())) {
                        tradeLimitDto.setInAcctId(acctTransRequestDetailDTO.getInAcctId());
                        tradeLimitDto.setInCustId(acctTransRequestDetailDTO.getCustId2());
                        tradeLimitDto.setInSystemId(acctTransRequestPeg.getSysId());
                    }
                    tradeLimitDtoList.add(tradeLimitDto);
                }
            }
        }
        if (tradeLimitDtoList.size() > 0) {
            logger.info("账户支付限额申请入参：{}", JSON.toJSONString(tradeLimitDtoList));
            TradeLimitResult tradeLimitResult = custmagClient.tradeLimitBatch(tradeLimitDtoList);
            logger.info("账户支付限额申请返回参数：{}", JSON.toJSONString(tradeLimitResult));
            return tradeLimitResult;
        } else {
            return null;
        }
    }

    public void tradeCancelControl(AcctTransRequestPeg acctTransRequestPeg, boolean needCancelControl) throws Exception {
        if (!needCancelControl) {
            return;
        }
        ArrayList<TradeCancelDto> tradeCancelDtoList = new ArrayList<>();
        if (acctTransRequestPeg.getAcctTransDetailList() != null && acctTransRequestPeg.getAcctTransDetailList().size() > 0) {
            for (AcctTransRequestDetailDTO acctTransRequestDetailDTO : acctTransRequestPeg.getAcctTransDetailList()) {
                String transType = acctTransRequestDetailDTO.getTransType();
                TradeCancelDto tradeCancelDto = new TradeCancelDto();
                if (CustmagTransTypeEnum.valueByCode(transType) != null && acctTransRequestDetailDTO.isPayAcct()) {
                    tradeCancelDto = new TradeCancelDto();
                    if (CustmagTransTypeEnum.valueByCode(transType) != null) {
                        tradeCancelDto.setAcctId(acctTransRequestDetailDTO.getSubAcctId());
                        tradeCancelDto.setCancelAmount(acctTransRequestDetailDTO.getTransAmt());
                        tradeCancelDto.setCustId(acctTransRequestDetailDTO.getCustId());
                        tradeCancelDto.setMerId(acctTransRequestDetailDTO.getMerId());
                        tradeCancelDto.setReqSeq(acctTransRequestPeg.getReqSeqId());
                        tradeCancelDto.setReqTime(DateUtils.toFormatDateString(new Date(), DateUtils.MAIL_DATE_FORMAT));
                        tradeCancelDto.setSystemId(acctTransRequestPeg.getSysId());
                        tradeCancelDto.setOriTradeType(CustmagTransTypeEnum.valueByCode(transType).getCustmagTradeType());
                        tradeCancelDto.setOriTransDate(acctTransRequestDetailDTO.getFrtDate());
                        tradeCancelDto.setOriTransReq(acctTransRequestDetailDTO.getFrtSeqId());
                        tradeCancelDto.setCancelDate(DateUtils.toFormatDateString(new Date(), DateUtils.MAIL_DATE_DT_PART_FORMAT));
                        tradeCancelDto.setCancelType(CustmagCancelTransTypeEnum.valueByCode(transType).getCustmagCancelTradeType());
                        tradeCancelDto.setCancelSeq(acctTransRequestPeg.getReqSeqId());
                        tradeCancelDtoList.add(tradeCancelDto);
                    }
                }
            }
        }
        if (tradeCancelDtoList.size() > 0) {
            logger.info("账户支付限额撤销入参：{}", JSON.toJSONString(tradeCancelDtoList));
            TradeCancelResult tradeCancelResult = custmagClient.tradeCancelBatch(tradeCancelDtoList);
            logger.info("账户支付限额撤销返回参数：{}", JSON.toJSONString(tradeCancelResult));
        }
    }

}
