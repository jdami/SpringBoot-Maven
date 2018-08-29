package com.somnus.springboot.mybatis.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tcim_mer_account")
public class TcimMerAccount {
    /**
     * �˻�ID
     */
    @Id
    @Column(name = "ACCT_ID")
    private Integer acctId;

    /**
     * �����˻���
     */
    @Column(name = "ACCT_CODE")
    private String acctCode;

    /**
     * �̻���
     */
    @Column(name = "MER_CODE")
    private String merCode;

    /**
     * �����˻���
     */
    @Column(name = "ACCT_NAME")
    private String acctName;

    /**
     * ����_156�������
     */
    @Column(name = "CURRENCY")
    private String currency;

    /**
     * ��������
     */
    @Column(name = "BANK_CODE")
    private String bankCode;

    /**
     * ������������
     */
    @Column(name = "BANK_NAME")
    private String bankName;

    /**
     * �������к�
     */
    @Column(name = "BANK_KEY")
    private String bankKey;

    /**
     * ���б��_�����б��
     */
    @Column(name = "BRANCH_CODE")
    private String branchCode;

    /**
     * ��������_����������
     */
    @Column(name = "BRANCH_NAME")
    private String branchName;

    /**
     * �̻������˺�
     */
    @Column(name = "BANK_ACCT_NO")
    private String bankAcctNo;

    /**
     * �̻����л���
     */
    @Column(name = "BANK_ACCT_NAME")
    private String bankAcctName;

    /**
     * �汾״̬_1:����� 2:��Ч 3:��Ч
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * �޸���
     */
    @Column(name = "MODIFY_BY")
    private String modifyBy;

    /**
     * �޸�ʱ��
     */
    @Column(name = "MODIFY_TIME")
    private Date modifyTime;

    /**
     * ��ȡ�˻�ID
     *
     * @return ACCT_ID - �˻�ID
     */
    public Integer getAcctId() {
        return acctId;
    }

    /**
     * �����˻�ID
     *
     * @param acctId �˻�ID
     */
    public void setAcctId(Integer acctId) {
        this.acctId = acctId;
    }

    /**
     * ��ȡ�����˻���
     *
     * @return ACCT_CODE - �����˻���
     */
    public String getAcctCode() {
        return acctCode;
    }

    /**
     * ���ý����˻���
     *
     * @param acctCode �����˻���
     */
    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    /**
     * ��ȡ�̻���
     *
     * @return MER_CODE - �̻���
     */
    public String getMerCode() {
        return merCode;
    }

    /**
     * �����̻���
     *
     * @param merCode �̻���
     */
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    /**
     * ��ȡ�����˻���
     *
     * @return ACCT_NAME - �����˻���
     */
    public String getAcctName() {
        return acctName;
    }

    /**
     * ���ý����˻���
     *
     * @param acctName �����˻���
     */
    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    /**
     * ��ȡ����_156�������
     *
     * @return CURRENCY - ����_156�������
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * ���ñ���_156�������
     *
     * @param currency ����_156�������
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * ��ȡ��������
     *
     * @return BANK_CODE - ��������
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * ���ÿ�������
     *
     * @param bankCode ��������
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * ��ȡ������������
     *
     * @return BANK_NAME - ������������
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * ���ÿ�����������
     *
     * @param bankName ������������
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * ��ȡ�������к�
     *
     * @return BANK_KEY - �������к�
     */
    public String getBankKey() {
        return bankKey;
    }

    /**
     * �����������к�
     *
     * @param bankKey �������к�
     */
    public void setBankKey(String bankKey) {
        this.bankKey = bankKey;
    }

    /**
     * ��ȡ���б��_�����б��
     *
     * @return BRANCH_CODE - ���б��_�����б��
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * ���÷��б��_�����б��
     *
     * @param branchCode ���б��_�����б��
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    /**
     * ��ȡ��������_����������
     *
     * @return BRANCH_NAME - ��������_����������
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * ���÷�������_����������
     *
     * @param branchName ��������_����������
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * ��ȡ�̻������˺�
     *
     * @return BANK_ACCT_NO - �̻������˺�
     */
    public String getBankAcctNo() {
        return bankAcctNo;
    }

    /**
     * �����̻������˺�
     *
     * @param bankAcctNo �̻������˺�
     */
    public void setBankAcctNo(String bankAcctNo) {
        this.bankAcctNo = bankAcctNo;
    }

    /**
     * ��ȡ�̻����л���
     *
     * @return BANK_ACCT_NAME - �̻����л���
     */
    public String getBankAcctName() {
        return bankAcctName;
    }

    /**
     * �����̻����л���
     *
     * @param bankAcctName �̻����л���
     */
    public void setBankAcctName(String bankAcctName) {
        this.bankAcctName = bankAcctName;
    }

    /**
     * ��ȡ�汾״̬_1:����� 2:��Ч 3:��Ч
     *
     * @return STATUS - �汾״̬_1:����� 2:��Ч 3:��Ч
     */
    public String getStatus() {
        return status;
    }

    /**
     * ���ð汾״̬_1:����� 2:��Ч 3:��Ч
     *
     * @param status �汾״̬_1:����� 2:��Ч 3:��Ч
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * ��ȡ�޸���
     *
     * @return MODIFY_BY - �޸���
     */
    public String getModifyBy() {
        return modifyBy;
    }

    /**
     * �����޸���
     *
     * @param modifyBy �޸���
     */
    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    /**
     * ��ȡ�޸�ʱ��
     *
     * @return MODIFY_TIME - �޸�ʱ��
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * �����޸�ʱ��
     *
     * @param modifyTime �޸�ʱ��
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}