package com.huifu.odin.biz.trans;

import com.huifu.odin.biz.base.BaseValidationBiz;
import com.huifu.odin.biz.custmag.CustmagTransTypeEnum;
import com.huifu.odin.facade.service.trans.AcctTransRequestDetailDTO;
import com.huifu.odin.facade.service.trans.AcctTransRequestPeg;
import com.huifu.odin.facade.service.trans.AcctUnfreezeRequestDetailDTO;
import com.huifu.odin.facade.service.trans.UnfreezeTransRequest;
import com.huifu.odin.util.common.CurrencyUtils;
import com.huifu.odin.util.common.DateUtils;
import com.huifu.pyxis.client.PyxisConfig;
import org.springframework.stereotype.Service;

/**
 * @author frank
 */
@Service
public class TransValidationBiz extends BaseValidationBiz {

    public TransValidationResult baseValidation(AcctTransRequestPeg acctTransRequestPeg) {
        Integer permissionTransCount = PyxisConfig.getInstance().getIntProperty("permission.trans.count", 20);
        Integer permissionUnfreezeCount = PyxisConfig.getInstance().getIntProperty("permission.unfreeze.count", 5);

        if (!checkPara(acctTransRequestPeg.getSysId(), false, 5)) {
            return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailSystemIdError);
        }
        if (!checkPara(acctTransRequestPeg.getVersionId(), false, 2)) {
            return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailVersionIdError);
        }
        if (!checkPara(acctTransRequestPeg.getReqSeqId(), false, 12)) {
            return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailReqSeqIdError);
        }
        if (acctTransRequestPeg.getAcctTransDetailList() == null || acctTransRequestPeg.getAcctTransDetailList().size() == 0) {
            return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailListIsNull);
        }
        if (acctTransRequestPeg.getAcctTransDetailList().size() > permissionTransCount) {
            return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailListCountToLong);
        }
        if (!acctTransRequestPeg.getTransCnt().equals(String.valueOf(acctTransRequestPeg.getAcctTransDetailList().size()))) {
            return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailListCountError);
        }
        for (AcctTransRequestDetailDTO dto : acctTransRequestPeg.getAcctTransDetailList()) {

            if (CustmagTransTypeEnum.valueByCode(dto.getTransType()) != null && dto.isPayAcct() && !checkPara(dto.getMerId(), false, 20)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailIsPayAcctMerIdIsNull);
            }

            if (!DateUtils.chkDateFormat(dto.getFrtDate())) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailReqDateError);
            }
            if (!checkPara(dto.getFrtSeqId(), false, 12)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrtSeqIdError);
            }
            if (!checkPara(dto.getTransType(), false, 4)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailTransTypeError);
            }
            if (!checkPara(dto.getCustId(), false, 16)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailCustIdError);
            }
            if (!checkPara(dto.getSubAcctId(), false, 9)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailSubAcctIdError);
            }
            if (!checkPara(dto.getAcctType(), false, 6)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailAcctTypeError);
            }
            if (!checkPara(dto.getBedpId(), false, 3)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailBedpIdError);
            }
            if (!checkPara(dto.getFrtTxnId1(), true, 40)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrtTxnIdError);
            }
            if (!checkPara(dto.getFrtTxnId2(), true, 40)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrtTxnIdError);
            }
            if (!checkPara(dto.getFrtTxnId3(), true, 40)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrtTxnIdError);
            }
            if (!checkPara(dto.getTransObj(), true, 128)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailTransObjError);
            }
            if (!checkPara(dto.getCustId2(), true, 16)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailCustId2Error);
            }
            if (!CurrencyUtils.isCurrency(dto.getTransAmt())) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailAmtFormatError);
            }
            if (!CurrencyUtils.isCurrency(dto.getFeeAmount())) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFeeAmtFromatError);
            }
            if (!checkPara(dto.getTransAmt(), false, 14)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailAmtBaseError);
            }
            if (!checkPara(dto.getFeeAmount(), false, 10)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFeeAmtBaseError);
            }
            if (!checkPara(dto.getTransName(), true, 64)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailTransNameError);
            }

        }
        if (acctTransRequestPeg.getAcctUnfreezeRequestDetailDTOs() != null) {
            if (acctTransRequestPeg.getAcctUnfreezeRequestDetailDTOs().size() > permissionUnfreezeCount) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailListUnfreezeCountToLong);
            }
            for (AcctUnfreezeRequestDetailDTO dto : acctTransRequestPeg.getAcctUnfreezeRequestDetailDTOs()) {

                if (!DateUtils.chkDateFormat(dto.getFrtDate())) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailReqDateError);
                }
                if (!checkPara(dto.getFrtSeqId(), false, 12)) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrtSeqIdError);
                }
                if (!checkPara(dto.getCustId(), false, 16)) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailCustIdError);
                }
                if (!checkPara(dto.getSubAcctId(), false, 9)) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailSubAcctIdError);
                }
                if (!checkPara(dto.getAcctType(), false, 6)) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailAcctTypeError);
                }
                if (!checkPara(dto.getBedpId(), false, 3)) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailBedpIdError);
                }
                if (!checkPara(dto.getFrtTxnId1(), true, 40)) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrtTxnIdError);
                }
                if (!checkPara(dto.getFrtTxnId2(), true, 40)) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrtTxnIdError);
                }
                if (!checkPara(dto.getFrtTxnId3(), true, 40)) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrtTxnIdError);
                }
                if (!checkPara(dto.getTransObj(), true, 128)) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailTransObjError);
                }
                if (!checkPara(dto.getMerId(), true, 6)) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailMerIdError);
                }
                if (!CurrencyUtils.isCurrency(dto.getTransAmt())) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailAmtFormatError);
                }
                if (!checkPara(dto.getTransAmt(), false, 14)) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailAmtBaseError);
                }
                if (!checkPara(dto.getTransName(), true, 64)) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailTransNameError);
                }
                if (!checkPara(dto.getFrzCode(), false, 10)) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrzCodeError);
                }
                if (!checkPara(dto.getFrozenAcctDate(), false, 8)) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrozenAcctDateError);
                }
                if (!checkPara(dto.getFrozenAcctSeqId(), false, 12)) {
                    return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrozenAcctSeqIdError);
                }
            }
        }
        return new TransValidationResult();
    }


    public TransValidationResult baseValidation(UnfreezeTransRequest unfreezeTransRequest) {

        Integer permissionUnfreezeCount = PyxisConfig.getInstance().getIntProperty("permission.unfreeze.count", 5);

        if (!checkPara(unfreezeTransRequest.getSysId(), false, 5)) {
            return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailSystemIdError);
        }
        if (!checkPara(unfreezeTransRequest.getVersionId(), false, 2)) {
            return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailVersionIdError);
        }
        if (!checkPara(unfreezeTransRequest.getReqSeqId(), false, 12)) {
            return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailReqSeqIdError);
        }
        if (null == unfreezeTransRequest.getAcctUnfreezeRequestDetailDTOs() || unfreezeTransRequest.getAcctUnfreezeRequestDetailDTOs().size() == 0) {
            return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailListUnfreezeCountIsNull);
        }
        if (unfreezeTransRequest.getAcctUnfreezeRequestDetailDTOs().size() > permissionUnfreezeCount) {
            return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailListUnfreezeCountToLong);
        }
        for (AcctUnfreezeRequestDetailDTO dto : unfreezeTransRequest.getAcctUnfreezeRequestDetailDTOs()) {
            if (!checkPara(dto.getFrtSeqId(), false, 12)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrtSeqIdError);
            }

            if (!DateUtils.chkDateFormat(dto.getFrtDate())) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailReqDateError);
            }

            if (!checkPara(dto.getCustId(), false, 16)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailCustIdError);
            }
            if (!checkPara(dto.getSubAcctId(), false, 9)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailSubAcctIdError);
            }
            if (!checkPara(dto.getAcctType(), false, 6)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailAcctTypeError);
            }
            if (!checkPara(dto.getBedpId(), false, 3)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailBedpIdError);
            }
            if (!checkPara(dto.getFrtTxnId1(), true, 40)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrtTxnIdError);
            }
            if (!checkPara(dto.getFrtTxnId2(), true, 40)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrtTxnIdError);
            }
            if (!checkPara(dto.getFrtTxnId3(), true, 40)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrtTxnIdError);
            }
            if (!checkPara(dto.getTransObj(), true, 128)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailTransObjError);
            }
            if (!checkPara(dto.getMerId(), true, 6)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailMerIdError);
            }
            if (!CurrencyUtils.isCurrency(dto.getTransAmt())) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailAmtFormatError);
            }
            if (!checkPara(dto.getTransAmt(), false, 14)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailAmtBaseError);
            }
            if (!checkPara(dto.getTransName(), true, 64)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailTransNameError);
            }
            if (!checkPara(dto.getFrzCode(), false, 10)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrzCodeError);
            }
            if (!checkPara(dto.getFrozenAcctDate(), false, 8)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrozenAcctDateError);
            }
            if (!checkPara(dto.getFrozenAcctSeqId(), false, 12)) {
                return new TransValidationResult(TransValidationExceptionEnum.TransReqValdationFailFrozenAcctSeqIdError);
            }
        }
        return new TransValidationResult();
    }
}
