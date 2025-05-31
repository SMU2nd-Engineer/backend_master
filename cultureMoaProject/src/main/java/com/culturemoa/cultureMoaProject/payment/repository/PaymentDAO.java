package com.culturemoa.cultureMoaProject.payment.repository;

import com.culturemoa.cultureMoaProject.payment.dto.ProductFlagUpdate;
import com.culturemoa.cultureMoaProject.payment.entity.PaymentHistory;
import com.culturemoa.cultureMoaProject.payment.entity.PaymentStatus;
import com.culturemoa.cultureMoaProject.user.dto.UserTransactionDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDAO {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public void insertPaymentHistory(PaymentHistory paymentHistory){
        sqlSessionTemplate.insert("paymentMapper.insertPaymentHistory", paymentHistory);
    }

    public void insertPaymentStatus(PaymentStatus paymentStatus){
        sqlSessionTemplate.insert("paymentMapper.insertPaymentStatus", paymentStatus);
    }

    public int selectAmountByTid(String tid) {
        return sqlSessionTemplate.selectOne("paymentMapper.selectAmountByTid", tid);
    }

    public void updatePaymentStatusInfo(PaymentStatus paymentStatus) {
        sqlSessionTemplate.update("paymentMapper.updatePaymentStatusInfo", paymentStatus);
    }

    public void updateProductFlag(ProductFlagUpdate productFlagUpdate) {
        sqlSessionTemplate.update("productMapper.updateProductFlag", productFlagUpdate);
    }

    public int getProductIdxByTid(String tid) {
        return sqlSessionTemplate.selectOne("paymentMapper.getProductIdxByTid", tid);
    }

    public void insertUserTransaction(UserTransactionDTO userTransaction){
        sqlSessionTemplate.insert("paymentMapper.insertUserTransaction", userTransaction);
    }

    public boolean existUserTransactionByProductIdx(int productIdx){
        Integer count = sqlSessionTemplate.selectOne("paymentMapper.countUserTransactionByProductIdx", productIdx);
        System.out.println("거래내역에 productIdx 존재하는지 :"+count);
        return count != null && count > 0;
    }

}
