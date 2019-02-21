package com.huifu.odin.biz.trans;

import com.huifu.odin.biz.base.BaseBiz;
import com.huifu.odin.facade.service.trans.AcctUnfreezeRequestDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author frank
 */
@Service
public class UnfreezeBiz extends BaseBiz {

    @Autowired
    UnfreezeService unfreezeService;


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional(rollbackFor = Exception.class)
    public void doUnfreeze(String dbSysDate, String dbSysTimeToSave, List<AcctUnfreezeRequestDetailDTO> acctUnfreezeRequestDetailDTOs, String sysId) {
        try {
            unfreezeService.doUnfreeze(dbSysDate, dbSysTimeToSave, acctUnfreezeRequestDetailDTOs, sysId);
        } catch (DuplicateKeyException duplicateKeyException) {
            super.bizLogDebug("解冻接口重复交易", null, logger);
            logger.error("解冻接口交易失败，此交易为重复交易，交易信息:" + acctUnfreezeRequestDetailDTOs.toString());
            throw new UnFreezeException(UnFreezeExceptionEnum.DuplicateKeyException);
        } catch (DataAccessException dataAccessException) {
            super.bizLogDebug("解冻接口交易失败，数据库操作异常", null, logger);
            logger.error("解冻接口交易失败，数据库操作失败:" + dataAccessException.getMessage() + "，交易信息:" + acctUnfreezeRequestDetailDTOs.toString());
            throw new UnFreezeException(UnFreezeExceptionEnum.Db_Error);
        } catch (UnFreezeException unFreezenEx) {
            super.bizLogDebug("解冻接口解冻失败", null, logger);
            throw unFreezenEx;
        } catch (Exception e) {
            super.bizLogDebug("解冻接口系统异常信息", e, logger);
            logger.error("出入账失败，系统异常:，交易信息:" + acctUnfreezeRequestDetailDTOs.toString());
            throw new UnFreezeException(UnFreezeExceptionEnum.SystemError);
        }
    }

}
