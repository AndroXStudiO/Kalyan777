package com.snpinfo.upipayment.listener;

import com.snpinfo.upipayment.entity.TransactionResponse;

public interface PaymentStatusListener {
    void onTransactionCompleted(TransactionResponse transactionDetails);
    void onTransactionSuccess(TransactionResponse transactionDetails);
    void onTransactionSubmitted();
    void onTransactionFailed();
    void onTransactionCancelled();
}
