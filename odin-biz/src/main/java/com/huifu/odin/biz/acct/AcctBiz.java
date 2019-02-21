package com.huifu.odin.biz.acct;

import com.huifu.odin.biz.base.BaseValidationBiz;
import com.huifu.odin.dal.entity.DtAcctInfo;
import com.huifu.odin.dal.mapper.DtAcctInfoMapper;
import com.huifu.odin.facade.service.acct.AddAcctInfoDetailRequest;
import com.huifu.odin.facade.service.acct.AddAcctInfoDetailResult;
import com.huifu.odin.facade.service.acct.ModifyAcctInfoRequest;
import com.huifu.odin.util.common.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author frank
 */
@Service
public class AcctBiz extends BaseValidationBiz {


    @Autowired
    private DtAcctInfoMapper dtAcctInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    public List<AddAcctInfoDetailResult> batchAddAcct(List<AddAcctInfoDetailRequest> addAcctInfoDetailRequests) {
        final List<AddAcctInfoDetailResult> dtAcctInfoDOs = new ArrayList<>();
        for (AddAcctInfoDetailRequest addAcctInfoDetailRequest : addAcctInfoDetailRequests) {
            checkAllParaResult(addAcctInfoDetailRequest);
            checkAcctExist(addAcctInfoDetailRequest);
            DtAcctInfo dtAcctInfo = new DtAcctInfo();
            dtAcctInfo.setCustId(addAcctInfoDetailRequest.getCustId());
            dtAcctInfo.setSubAcctId(addAcctInfoDetailRequest.getSubAcctId());
            dtAcctInfo.setAcctName(addAcctInfoDetailRequest.getAcctName());
            dtAcctInfo.setAcctType(addAcctInfoDetailRequest.getAcctType());
            dtAcctInfo.setCustInfo(addAcctInfoDetailRequest.getCustInfo());
            dtAcctInfo.setAcctStatus(AcctStatusEnum.N.getCode());
            dtAcctInfo.setBdepId(addAcctInfoDetailRequest.getBdepId());
            dtAcctInfo.setSysId(addAcctInfoDetailRequest.getSysId());
            dtAcctInfo.setCapType(addAcctInfoDetailRequest.getCapType());
            dtAcctInfo.setCuryType(addAcctInfoDetailRequest.getCuryType());
            dtAcctInfo.setAcctBal(BigDecimal.valueOf(0));
            dtAcctInfo.setAvlBal(BigDecimal.valueOf(0));
            dtAcctInfo.setFrzBal(BigDecimal.valueOf(0));
            dtAcctInfo.setIntBal(BigDecimal.valueOf(0));
            dtAcctInfo.setLastAvlBal(BigDecimal.valueOf(0));
            dtAcctInfo.setLastFrzBal(BigDecimal.valueOf(0));
            dtAcctInfo.setLastIntDate(" ");
            dtAcctInfo.setLastUpdDate(DateUtils.toFormatDateString(new Date(), DateUtils.SHORT_FORMAT));
            dtAcctInfo.setOpenDate(DateUtils.toFormatDateString(new Date(), DateUtils.SHORT_FORMAT));
            dtAcctInfo.setOpenTime(DateUtils.toFormatDateString(new Date(), DateUtils.TIME_FORMAT));
            dtAcctInfo.setTodayAvlBal(BigDecimal.valueOf(0));
            dtAcctInfo.setTodayFrzBal(BigDecimal.valueOf(0));
            dtAcctInfoMapper.insert(dtAcctInfo);
            dtAcctInfoDOs.add(setupResults(dtAcctInfo));
        }
        return dtAcctInfoDOs;
    }

    private AddAcctInfoDetailResult setupResults(DtAcctInfo dtAcctInfo) {
        AddAcctInfoDetailResult addAcctInfoDetailResult = new AddAcctInfoDetailResult();
        if (dtAcctInfo == null) {
            return addAcctInfoDetailResult;
        }
        addAcctInfoDetailResult.setCustId(dtAcctInfo.getCustId());
        addAcctInfoDetailResult.setSubAcctId(dtAcctInfo.getSubAcctId());
        addAcctInfoDetailResult.setAcctName(dtAcctInfo.getAcctName());
        addAcctInfoDetailResult.setAcctType(dtAcctInfo.getAcctType());
        addAcctInfoDetailResult.setCustInfo(dtAcctInfo.getCustInfo());
        addAcctInfoDetailResult.setAcctStatus(dtAcctInfo.getAcctStatus());
        addAcctInfoDetailResult.setBdepId(dtAcctInfo.getBdepId());
        addAcctInfoDetailResult.setSysId(dtAcctInfo.getSysId());
        addAcctInfoDetailResult.setCapType(dtAcctInfo.getCapType());
        addAcctInfoDetailResult.setCuryType(dtAcctInfo.getCuryType());
        addAcctInfoDetailResult.setOpenDate(dtAcctInfo.getOpenDate());
        addAcctInfoDetailResult.setOpenTime(dtAcctInfo.getOpenTime());
        return addAcctInfoDetailResult;
    }


    private void checkAllParaResult(AddAcctInfoDetailRequest addAcctInfoDetailRequest) {
        if (checkPara(addAcctInfoDetailRequest.getCustId(), false, 16) == false || hasSpecialCharacter(addAcctInfoDetailRequest.getCustId())) {
            throw new AcctCheckException(AcctCheckEnum.REQ_PARAMS_ILLEGAL, "客户号不合法");
        }
        if (checkPara(addAcctInfoDetailRequest.getSubAcctId(), false, 9) == false) {
            throw new AcctCheckException(AcctCheckEnum.REQ_PARAMS_ILLEGAL, "子账号不合法");
        }
        if (checkPara(addAcctInfoDetailRequest.getAcctName(), false, 20) == false || hasSpecialCharacter(addAcctInfoDetailRequest.getAcctName())) {
            throw new AcctCheckException(AcctCheckEnum.REQ_PARAMS_ILLEGAL, "账户名称不合法");
        }
        if (checkPara(addAcctInfoDetailRequest.getAcctType(), false, 6) == false) {
            throw new AcctCheckException(AcctCheckEnum.REQ_PARAMS_ILLEGAL, "账户类型不合法");
        }
        if (checkPara(addAcctInfoDetailRequest.getCustInfo(), false, 40) == false) {
            throw new AcctCheckException(AcctCheckEnum.REQ_PARAMS_ILLEGAL, "客户信息不合法");
        }
        if (checkPara(addAcctInfoDetailRequest.getBdepId(), false, 3) == false || hasSpecialCharacter(addAcctInfoDetailRequest.getBdepId())) {
            throw new AcctCheckException(AcctCheckEnum.REQ_PARAMS_ILLEGAL, "事业部代号不合法");
        }
        if (checkPara(addAcctInfoDetailRequest.getSysId(), false, 5) == false || hasSpecialCharacter(addAcctInfoDetailRequest.getSysId())) {
            throw new AcctCheckException(AcctCheckEnum.REQ_PARAMS_ILLEGAL, "系统号不合法");
        }
        if (checkPara(addAcctInfoDetailRequest.getCapType(), false, 2) == false || hasSpecialCharacter(addAcctInfoDetailRequest.getCapType())) {
            throw new AcctCheckException(AcctCheckEnum.REQ_PARAMS_ILLEGAL, "资金类型不合法");
        }
        if (checkPara(addAcctInfoDetailRequest.getCuryType(), false, 3) == false || hasSpecialCharacter(addAcctInfoDetailRequest.getCuryType())) {
            throw new AcctCheckException(AcctCheckEnum.REQ_PARAMS_ILLEGAL, "币种不合法");
        }
        // 检查子账户号是否与账户类型匹配
        if (!isSubAcctIdMatchAcctType(addAcctInfoDetailRequest.getSubAcctId(), addAcctInfoDetailRequest.getAcctType())) {
            throw new AcctCheckException(AcctCheckEnum.SUB_ACCT_ID_NOT_MATCH_ACCT_TYPE, AcctCheckEnum.SUB_ACCT_ID_NOT_MATCH_ACCT_TYPE.getType());
        }
    }

    private boolean isSubAcctIdMatchAcctType(String subAcctId, String acctType) {
        if (StringUtils.equals(acctType, AcctTypeEnum.BASEDT.getCode())) {
            if (StringUtils.length(subAcctId) > 9) {
                return false;
            }
        } else if (StringUtils.equals(acctType, AcctTypeEnum.SLB.getCode())) {
            if (StringUtils.length(subAcctId) > 9) {
                return false;
            }
        } else if (StringUtils.equals(acctType, AcctTypeEnum.RECV.getCode())) {
            if (StringUtils.length(subAcctId) != 8) {
                return false;
            }
            if (!StringUtils.startsWith(subAcctId.trim(), AcctTypeEnum.RECV.getSubAcctHeader())) {
                return false;
            }
            if (!StringUtils.isNumeric(StringUtils.right(subAcctId, subAcctId.length() - AcctTypeEnum.RECV.getSubAcctHeader().length()))) {
                return false;
            }
        } else if (StringUtils.equals(acctType, AcctTypeEnum.PAY.getCode())) {
            if (StringUtils.length(subAcctId) != 3) {
                return false;
            }
            if (!StringUtils.equals(subAcctId, acctType)) {
                return false;
            }
        } else if (StringUtils.equals(acctType, AcctTypeEnum.MERDT.getCode())) {
            if (StringUtils.length(subAcctId) != 9) {
                return false;
            }
            if (!StringUtils.startsWith(subAcctId.trim(), AcctTypeEnum.MERDT.getSubAcctHeader())) {
                return false;
            }
            if (!StringUtils.isNumeric(StringUtils.right(subAcctId, subAcctId.length() - AcctTypeEnum.MERDT.getSubAcctHeader().length()))) {
                return false;
            }
        } else if (StringUtils.equals(acctType, AcctTypeEnum.FUND.getCode())) {
            if (StringUtils.length(subAcctId) != 4) {
                return false;
            }
            if (!StringUtils.equals(subAcctId, acctType)) {
                return false;
            }
        } else if (StringUtils.equals(acctType, AcctTypeEnum.BUY.getCode())) {
            if (StringUtils.length(subAcctId) != 3) {
                return false;
            }
            if (!StringUtils.equals(subAcctId, acctType)) {
                return false;
            }
        } else if (StringUtils.equals(acctType, AcctTypeEnum.RED.getCode())) {
            if (StringUtils.length(subAcctId) != 3) {
                return false;
            }
            if (!StringUtils.equals(subAcctId, acctType)) {
                return false;
            }
        } else if (StringUtils.equals(acctType, AcctTypeEnum.FAS.getCode())) {
            if (StringUtils.length(subAcctId) != 9) {
                return false;
            }
            if (!StringUtils.startsWith(subAcctId.trim(), AcctTypeEnum.FAS.getSubAcctHeader())) {
                return false;
            }
            if (!StringUtils.isNumeric(StringUtils.right(subAcctId, subAcctId.length() - AcctTypeEnum.FAS.getSubAcctHeader().length()))) {
                return false;
            }
        } else if (StringUtils.equals(acctType, AcctTypeEnum.INT.getCode())) {
            if (StringUtils.length(subAcctId) != 9) {
                return false;
            }
            if (!StringUtils.startsWith(subAcctId.trim(), AcctTypeEnum.INT.getSubAcctHeader())) {
                return false;
            }
            if (!StringUtils.isNumeric(StringUtils.right(subAcctId, subAcctId.length() - AcctTypeEnum.INT.getSubAcctHeader().length()))) {
                return false;
            }
        } else if (StringUtils.equals(acctType, AcctTypeEnum.FC.getCode())) {
            if (StringUtils.length(subAcctId) != 5) {
                return false;
            }
            if (!StringUtils.startsWith(subAcctId.trim(), AcctTypeEnum.FC.getSubAcctHeader())) {
                return false;
            }
            if (!StringUtils.isNumeric(StringUtils.right(subAcctId, subAcctId.length() - AcctTypeEnum.FC.getSubAcctHeader().length()))) {
                return false;
            }
        } else if (StringUtils.equals(acctType, AcctTypeEnum.TG.getCode())) {
            if (StringUtils.length(subAcctId) != 8) {
                return false;
            }
            if (!StringUtils.startsWith(subAcctId.trim(), AcctTypeEnum.TG.getSubAcctHeader())) {
                return false;
            }
            if (!StringUtils.isNumeric(StringUtils.right(subAcctId, subAcctId.length() - AcctTypeEnum.TG.getSubAcctHeader().length()))) {
                return false;
            }
        } else if (StringUtils.equals(acctType, AcctTypeEnum.FS.getCode())) {
            if (StringUtils.length(subAcctId) != 8) {
                return false;
            }
            if (!StringUtils.startsWith(subAcctId.trim(), AcctTypeEnum.FS.getSubAcctHeader())) {
                return false;
            }
            if (!StringUtils.isNumeric(StringUtils.right(subAcctId, subAcctId.length() - AcctTypeEnum.FS.getSubAcctHeader().length()))) {
                return false;
            }
        } else if (StringUtils.equals(acctType, AcctTypeEnum.PT.getCode())) {
            if (StringUtils.length(subAcctId) != 2) {
                return false;
            }
            if (!StringUtils.equals(subAcctId, acctType)) {
                return false;
            }
        } else if (StringUtils.equals(acctType, AcctTypeEnum.SPEDT.getCode())) {
            if (StringUtils.length(subAcctId) > 9) {
                return false;
            }
        } else if (StringUtils.equals(acctType, AcctTypeEnum.DEP.getCode())) {
            if (StringUtils.length(subAcctId) != 9) {
                return false;
            }
            if (!StringUtils.startsWith(subAcctId.trim(), AcctTypeEnum.DEP.getSubAcctHeader())) {
                return false;
            }
            if (!StringUtils.isNumeric(StringUtils.right(subAcctId, subAcctId.length() - AcctTypeEnum.DEP.getSubAcctHeader().length()))) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private void checkAcctExist(AddAcctInfoDetailRequest addAcctInfoDetailRequest) {
        if (null != dtAcctInfoMapper.selectByPrimaryKey(addAcctInfoDetailRequest.getCustId(), addAcctInfoDetailRequest.getSubAcctId())) {
            throw new AcctCheckException(AcctCheckEnum.DT_ACCT_EXIST, AcctCheckEnum.DT_ACCT_EXIST.getType());
        }
    }

    private boolean hasSpecialCharacter(String check) {
        String regEx = "[`~!#$%^&*+=|{}':;',\\[\\].<>/?~！#￥%……&*+|{}‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(check);
        return m.find();
    }

    @Transactional(rollbackFor = Exception.class)
    public void modifyAcctInfo(ModifyAcctInfoRequest modifyAcctInfoRequest) {
        if (checkPara(modifyAcctInfoRequest.getCustId(), false, 16) == false || hasSpecialCharacter(modifyAcctInfoRequest.getCustId())) {
            throw new AcctCheckException(AcctCheckEnum.REQ_PARAMS_ILLEGAL, "客户号不合法");
        }
        if (checkPara(modifyAcctInfoRequest.getSubAcctId(), false, 9) == false) {
            throw new AcctCheckException(AcctCheckEnum.REQ_PARAMS_ILLEGAL, "子账号不合法");
        }
        if (checkPara(modifyAcctInfoRequest.getAcctStatus(), false, 1) == false) {
            throw new AcctCheckException(AcctCheckEnum.REQ_PARAMS_ILLEGAL, "账户状态不合法");
        }
        if (!"N".equals(modifyAcctInfoRequest.getAcctStatus()) && !"C".equals(modifyAcctInfoRequest.getAcctStatus()) && !"F".equals(modifyAcctInfoRequest.getAcctStatus()) && !"D".equals(modifyAcctInfoRequest.getAcctStatus())) {
            throw new AcctCheckException(AcctCheckEnum.REQ_PARAMS_ILLEGAL, "账户状态不合法");
        }


        DtAcctInfo acctInfoModify = new DtAcctInfo();
        acctInfoModify.setCustId(modifyAcctInfoRequest.getCustId());
        acctInfoModify.setSubAcctId(modifyAcctInfoRequest.getSubAcctId());
        acctInfoModify.setAcctStatus(modifyAcctInfoRequest.getAcctStatus());
        acctInfoModify.setLastUpdDate(DateUtils.toFormatDateString(new Date(), DateUtils.SHORT_FORMAT));
        int i = dtAcctInfoMapper.updateByPrimaryKeySelective(acctInfoModify);
        if (i == 0) {
            throw new AcctCheckException(AcctCheckEnum.DT_ACCT_NOTEXIST, "账户更新失败,请确认账户信息");
        }
    }
}
