package com.culturemoa.cultureMoaProject.payment.repository;

import com.culturemoa.cultureMoaProject.payment.entity.PaymentHistory;
import com.culturemoa.cultureMoaProject.payment.entity.PaymentStatus;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDAO {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public int insertPaymentHistory(PaymentHistory paymentHistory){
        return sqlSessionTemplate.insert("paymentMapper.insertPaymentHistory", paymentHistory);
    }

    public int insertPaymentStatus(PaymentStatus paymentStatus){
        return sqlSessionTemplate.insert("paymentMapper.insertPaymentStatus", paymentStatus);
    }

    public int selectAmountByTid(String tid) {
        return sqlSessionTemplate.selectOne("paymentMapper.selectAmountByTid", tid);
    }
}
