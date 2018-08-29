package com.somnus.springboot.mybatis.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tcim_mer_account")
public class TcimMerAccount {
    /**
     * 账户ID
     */
    @Id
    @Column(name = "ACCT_ID")
    private Integer acctId;

    /**
     * 交易账户号
     */
    @Column(name = "ACCT_CODE")
    private String acctCode;

    /**
     * 商户号
     */
    @Column(name = "MER_CODE")
    private String merCode;

    /**
     * 交易账户名
     */
    @Column(name = "ACCT_NAME")
    private String acctName;

    /**
     * 币种_156：人民币
     */
    @Column(name = "CURRENCY")
    private String currency;

    /**
     * 开户银行
     */
    @Column(name = "BANK_CODE")
    private String bankCode;

    /**
     * 开户银行名称
     */
    @Column(name = "BANK_NAME")
    private String bankName;

    /**
     * 银行联行号
     */
    @Column(name = "BANK_KEY")
    private String bankKey;

    /**
     * 分行编号_开户行编号
     */
    @Column(name = "BRANCH_CODE")
    private String branchCode;

    /**
     * 分行名称_开户行名称
     */
    @Column(name = "BRANCH_NAME")
    private String branchName;

    /**
     * 商户银行账号
     */
    @Column(name = "BANK_ACCT_NO")
    private String bankAcctNo;

    /**
     * 商户银行户名
     */
    @Column(name = "BANK_ACCT_NAME")
    private String bankAcctName;

    /**
     * 版本状态_1:审核中 2:有效 3:无效
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * 修改人
     */
    @Column(name = "MODIFY_BY")
    private String modifyBy;

    /**
     * 修改时间
     */
    @Column(name = "MODIFY_TIME")
    private Date modifyTime;

    /**
     * 获取账户ID
     *
     * @return ACCT_ID - 账户ID
     */
    public Integer getAcctId() {
        return acctId;
    }

    /**
     * 设置账户ID
     *
     * @param acctId 账户ID
     */
    public void setAcctId(Integer acctId) {
        this.acctId = acctId;
    }

    /**
     * 获取交易账户号
     *
     * @return ACCT_CODE - 交易账户号
     */
    public String getAcctCode() {
        return acctCode;
    }

    /**
     * 设置交易账户号
     *
     * @param acctCode 交易账户号
     */
    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    /**
     * 获取商户号
     *
     * @return MER_CODE - 商户号
     */
    public String getMerCode() {
        return merCode;
    }

    /**
     * 设置商户号
     *
     * @param merCode 商户号
     */
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    /**
     * 获取交易账户名
     *
     * @return ACCT_NAME - 交易账户名
     */
    public String getAcctName() {
        return acctName;
    }

    /**
     * 设置交易账户名
     *
     * @param acctName 交易账户名
     */
    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    /**
     * 获取币种_156：人民币
     *
     * @return CURRENCY - 币种_156：人民币
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 设置币种_156：人民币
     *
     * @param currency 币种_156：人民币
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 获取开户银行
     *
     * @return BANK_CODE - 开户银行
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * 设置开户银行
     *
     * @param bankCode 开户银行
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * 获取开户银行名称
     *
     * @return BANK_NAME - 开户银行名称
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 设置开户银行名称
     *
     * @param bankName 开户银行名称
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 获取银行联行号
     *
     * @return BANK_KEY - 银行联行号
     */
    public String getBankKey() {
        return bankKey;
    }

    /**
     * 设置银行联行号
     *
     * @param bankKey 银行联行号
     */
    public void setBankKey(String bankKey) {
        this.bankKey = bankKey;
    }

    /**
     * 获取分行编号_开户行编号
     *
     * @return BRANCH_CODE - 分行编号_开户行编号
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * 设置分行编号_开户行编号
     *
     * @param branchCode 分行编号_开户行编号
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    /**
     * 获取分行名称_开户行名称
     *
     * @return BRANCH_NAME - 分行名称_开户行名称
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * 设置分行名称_开户行名称
     *
     * @param branchName 分行名称_开户行名称
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * 获取商户银行账号
     *
     * @return BANK_ACCT_NO - 商户银行账号
     */
    public String getBankAcctNo() {
        return bankAcctNo;
    }

    /**
     * 设置商户银行账号
     *
     * @param bankAcctNo 商户银行账号
     */
    public void setBankAcctNo(String bankAcctNo) {
        this.bankAcctNo = bankAcctNo;
    }

    /**
     * 获取商户银行户名
     *
     * @return BANK_ACCT_NAME - 商户银行户名
     */
    public String getBankAcctName() {
        return bankAcctName;
    }

    /**
     * 设置商户银行户名
     *
     * @param bankAcctName 商户银行户名
     */
    public void setBankAcctName(String bankAcctName) {
        this.bankAcctName = bankAcctName;
    }

    /**
     * 获取版本状态_1:审核中 2:有效 3:无效
     *
     * @return STATUS - 版本状态_1:审核中 2:有效 3:无效
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置版本状态_1:审核中 2:有效 3:无效
     *
     * @param status 版本状态_1:审核中 2:有效 3:无效
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取修改人
     *
     * @return MODIFY_BY - 修改人
     */
    public String getModifyBy() {
        return modifyBy;
    }

    /**
     * 设置修改人
     *
     * @param modifyBy 修改人
     */
    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    /**
     * 获取修改时间
     *
     * @return MODIFY_TIME - 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}