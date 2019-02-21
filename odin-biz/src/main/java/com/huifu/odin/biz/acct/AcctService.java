package com.huifu.odin.biz.acct;

import com.huifu.odin.biz.base.BaseBiz;
import com.huifu.odin.biz.monitor.TransMonitor;
import com.huifu.odin.facade.service.acct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author frank
 */
@Service
@TransMonitor
public class AcctService extends BaseBiz {

    Logger logger = LoggerFactory.getLogger(AcctService.class);

    @Autowired
    private AcctBiz acctBiz;

    public AddAcctInfoResult batchAddAcctInfosWithMonitor(List<AddAcctInfoDetailRequest> addAcctInfoDetailRequests) {
        bizLogDebug("批量添加账户入参数", addAcctInfoDetailRequests, logger);
        AddAcctInfoResult addAcctInfoResult = new AddAcctInfoResult();
        try {
            List<AddAcctInfoDetailResult> addAcctInfoDetailResults = acctBiz.batchAddAcct(addAcctInfoDetailRequests);
            addAcctInfoResult.setRespCode(AcctCheckEnum.BATCH_ADD_ACCT_SUCCESS.getCode());
            addAcctInfoResult.setRespDesc(AcctCheckEnum.BATCH_ADD_ACCT_SUCCESS.getType());
            addAcctInfoResult.setAddAcctInfoDetailResults(addAcctInfoDetailResults);
        } catch (AcctCheckException ace) {
            addAcctInfoResult.setRespCode(ace.getCode());
            addAcctInfoResult.setRespDesc(ace.getMessage());
        } catch (Exception e) {
            addAcctInfoResult.setRespCode(AcctCheckEnum.SYSTEM_ERROR.getCode());
            addAcctInfoResult.setRespDesc(AcctCheckEnum.SYSTEM_ERROR.getType());
        }
        bizLogDebug("批量添加账户返回", addAcctInfoResult, logger);
        return addAcctInfoResult;
    }

    public ModifyAcctInfoResult modifyAcctInfoWithMonitor(ModifyAcctInfoRequest modifyAcctInfoRequest) {
        bizLogDebug("修改账户入参数", modifyAcctInfoRequest, logger);
        ModifyAcctInfoResult modifyAcctInfoResult = new ModifyAcctInfoResult();
        try {
            acctBiz.modifyAcctInfo(modifyAcctInfoRequest);
            modifyAcctInfoResult.setRespCode(AcctCheckEnum.MODIFY_ACCT_SUCCESS.getCode());
            modifyAcctInfoResult.setRespDesc(AcctCheckEnum.MODIFY_ACCT_SUCCESS.getType());
        } catch (AcctCheckException ace) {
            modifyAcctInfoResult.setRespCode(ace.getCode());
            modifyAcctInfoResult.setRespDesc(ace.getMessage());
        } catch (Exception e) {
            modifyAcctInfoResult.setRespCode(AcctCheckEnum.SYSTEM_ERROR.getCode());
            modifyAcctInfoResult.setRespDesc(AcctCheckEnum.SYSTEM_ERROR.getType());
        }
        bizLogDebug("修改账户返回", modifyAcctInfoResult, logger);
        return modifyAcctInfoResult;
    }
}
