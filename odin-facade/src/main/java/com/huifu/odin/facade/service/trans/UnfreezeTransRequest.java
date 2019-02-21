package com.huifu.odin.facade.service.trans;

import java.io.Serializable;
import java.util.List;

/**
 * @author frank
 */
public class UnfreezeTransRequest implements Serializable {

    private static final long serialVersionUID = 5258156243033195413L;

    private String versionId;

    private String sysId;

    private String reqSeqId;

    private List<AcctUnfreezeRequestDetailDTO> acctUnfreezeRequestDetailDTOs;

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

    public List<AcctUnfreezeRequestDetailDTO> getAcctUnfreezeRequestDetailDTOs() {
        return acctUnfreezeRequestDetailDTOs;
    }

    public void setAcctUnfreezeRequestDetailDTOs(List<AcctUnfreezeRequestDetailDTO> acctUnfreezeRequestDetailDTOs) {
        this.acctUnfreezeRequestDetailDTOs = acctUnfreezeRequestDetailDTOs;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UnfreezeTransRequest{");
        sb.append("versionId='").append(versionId).append('\'');
        sb.append(", sysId='").append(sysId).append('\'');
        sb.append(", reqSeqId='").append(reqSeqId).append('\'');
        sb.append(", acctUnfreezeRequestDetailDTOs=").append(acctUnfreezeRequestDetailDTOs);
        sb.append('}');
        return sb.toString();
    }
}
