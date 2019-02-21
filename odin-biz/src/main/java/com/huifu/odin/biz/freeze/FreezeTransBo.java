package com.huifu.odin.biz.freeze;

import com.huifu.odin.biz.validate.TransAmt;
import com.huifu.odin.biz.validate.group.First;
import com.huifu.odin.biz.validate.group.Last;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.GroupSequence;

/**
 * @author frank
 */
@GroupSequence({FreezeTransBo.class, First.class, Last.class})
public class FreezeTransBo {

    @NotEmpty(message = "versionId不能为空")
    @Length(max = 2, message = "versionId最长为2")
    private String versionId;

    @NotEmpty(message = "reqSeqId不能为空")
    @Length(max = 12, message = "reqSeqId最长为12")
    private String reqSeqId;

    @NotEmpty(message = "frtDate不能为空")
    @Length(min = 8, max = 8, message = "frtDate定长为8")
    private String frtDate;

    @NotEmpty(message = "frtSeqId不能为空")
    @Length(max = 12, message = "frtSeqId最长为12")
    private String frtSeqId;

    @NotEmpty(message = "custId不能为空")
    @Length(max = 16, message = "custId最长为16")
    private String custId;

    @NotEmpty(message = "subAcctId不能为空")
    @Length(max = 9, message = "subAcctId最长为9")
    private String subAcctId;

    @NotEmpty(message = "acctType不能为空")
    @Length(max = 6, message = "acctType最长为6")
    private String acctType;

    @NotEmpty(message = "transAmt不能为空")
    @Length(max = 14, message = "transAmt最长为14")
    @TransAmt(groups = {First.class})
    private String transAmt;

    @NotEmpty(message = "bedpId不能为空")
    @Length(max = 3, message = "bedpId最长为3")
    private String bedpId;

    @Length(max = 64, message = "transName最长为64")
    private String transName;

    @Length(max = 128, message = "transObj最长为128")
    private String transObj;

    @NotEmpty(message = "frzCode不能为空")
    @Length(max = 10, message = "frzCode最长为10")
    private String frzCode;

    @NotEmpty(message = "sysId不能为空")
    @Length(max = 5, message = "sysId最长为5")
    private String sysId;

    public String getReqSeqId() {
        return reqSeqId;
    }

    public void setReqSeqId(String reqSeqId) {
        this.reqSeqId = reqSeqId;
    }

    public String getFrtDate() {
        return frtDate;
    }

    public void setFrtDate(String frtDate) {
        this.frtDate = frtDate;
    }

    public String getFrtSeqId() {
        return frtSeqId;
    }

    public void setFrtSeqId(String frtSeqId) {
        this.frtSeqId = frtSeqId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getSubAcctId() {
        return subAcctId;
    }

    public void setSubAcctId(String subAcctId) {
        this.subAcctId = subAcctId;
    }

    public String getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }

    public String getBedpId() {
        return bedpId;
    }

    public void setBedpId(String bedpId) {
        this.bedpId = bedpId;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public String getTransObj() {
        return transObj;
    }

    public void setTransObj(String transObj) {
        this.transObj = transObj;
    }

    public String getFrzCode() {
        return frzCode;
    }

    public void setFrzCode(String frzCode) {
        this.frzCode = frzCode;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FreezeTransBo{");
        sb.append("versionId='").append(versionId).append('\'');
        sb.append(", reqSeqId='").append(reqSeqId).append('\'');
        sb.append(", frtDate='").append(frtDate).append('\'');
        sb.append(", frtSeqId='").append(frtSeqId).append('\'');
        sb.append(", custId='").append(custId).append('\'');
        sb.append(", subAcctId='").append(subAcctId).append('\'');
        sb.append(", acctType='").append(acctType).append('\'');
        sb.append(", transAmt='").append(transAmt).append('\'');
        sb.append(", bedpId='").append(bedpId).append('\'');
        sb.append(", transName='").append(transName).append('\'');
        sb.append(", transObj='").append(transObj).append('\'');
        sb.append(", frzCode='").append(frzCode).append('\'');
        sb.append(", sysId='").append(sysId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }
}
