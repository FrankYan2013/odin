package com.huifu.odin.facade.service.trans;

import java.io.Serializable;
import java.util.List;

/**
 * @author frank
 */
public class AcctTransRequestPeg implements Serializable {

    private static final long serialVersionUID = -2893794609578239721L;

    private String versionId;

    private String sysId;

    private String reqSeqId;

    private String transCnt;

    private String verifyType;

    public String getPrivateFields() {
        return privateFields;
    }

    public void setPrivateFields(String privateFields) {
        this.privateFields = privateFields;
    }


    private String privateFields;

    private List<AcctUnfreezeRequestDetailDTO> acctUnfreezeRequestDetailDTOs;

    private List<AcctTransRequestDetailDTO> acctTransDetailList;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getReqSeqId() {
        return reqSeqId;
    }

    public void setReqSeqId(String reqSeqId) {
        this.reqSeqId = reqSeqId;
    }

    public String getTransCnt() {
        return transCnt;
    }

    public void setTransCnt(String transCnt) {
        this.transCnt = transCnt;
    }

    public String getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(String verifyType) {
        this.verifyType = verifyType;
    }

    public List<AcctTransRequestDetailDTO> getAcctTransDetailList() {
        return acctTransDetailList;
    }

    public void setAcctTransDetailList(List<AcctTransRequestDetailDTO> acctTransDetailList) {
        this.acctTransDetailList = acctTransDetailList;
    }

    public List<AcctUnfreezeRequestDetailDTO> getAcctUnfreezeRequestDetailDTOs() {
        return acctUnfreezeRequestDetailDTOs;
    }

    public void setAcctUnfreezeRequestDetailDTOs(List<AcctUnfreezeRequestDetailDTO> acctUnfreezeRequestDetailDTOs) {
        this.acctUnfreezeRequestDetailDTOs = acctUnfreezeRequestDetailDTOs;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AcctTransRequestPeg{");
        sb.append("versionId='").append(versionId).append('\'');
        sb.append(", sysId='").append(sysId).append('\'');
        sb.append(", reqSeqId='").append(reqSeqId).append('\'');
        sb.append(", transCnt='").append(transCnt).append('\'');
        sb.append(", verifyType='").append(verifyType).append('\'');
        sb.append(", privateFields='").append(privateFields).append('\'');
        sb.append(", acctUnfreezeRequestDetailDTOs=").append(acctUnfreezeRequestDetailDTOs);
        sb.append(", acctTransDetailList=").append(acctTransDetailList);
        sb.append('}');
        return sb.toString();
    }
}
