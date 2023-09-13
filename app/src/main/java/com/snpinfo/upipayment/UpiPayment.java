package com.snpinfo.upipayment;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.snpinfo.upipayment.entity.PaymentPayload;
import com.snpinfo.upipayment.listener.PaymentStatusListener;
import com.snpinfo.upipayment.ui.PaymentActivity;

public final class UpiPayment {
    private Activity mActivity;
    private PaymentPayload mPayment;

    private UpiPayment(@NonNull final Activity mActivity, @NonNull PaymentPayload mPayment) {
        this.mActivity = mActivity;
        this.mPayment = mPayment;
    }

    /**
     * Starts the payment Transaction. This method is used to pass PaymentPayload
     * and shows Pre-installed UPI apps in device with BottomSheet UI.
     */
   /* public void pay() {
        Intent payIntent = new Intent(mActivity, PaymentActivity.class);
        payIntent.putExtra("payment", mPayment);
        mActivity.startActivity(payIntent);
    }*/

    /**
     * Starts the payment Transaction. This method is used to pass PaymentPayload
     * and shows Pre-installed UPI apps in device with BottomSheet UI.
     *
     * @param paymentApp title for Bottom Sheet Dialog
     */
    public void pay(String paymentApp) {
        Intent payIntent = new Intent(mActivity, PaymentActivity.class);
        payIntent.putExtra("payment", mPayment);
        payIntent.putExtra("paymentApp", paymentApp);
        mActivity.startActivity(payIntent);
    }

    /**
     * Used To Sets the PaymentStatusListener.
     *
     * @param mListener Implementation of PaymentStatusListener
     */
    public void setPaymentStatusListener(@NonNull PaymentStatusListener mListener) {
        Singleton singleton = Singleton.getInstance();
        singleton.setListener(mListener);
    }

    /**
     * Used to Removes the PaymentStatusListener .
     */
    public void detachPaymentListener() {
        Singleton.getInstance().detachListener();
    }

    /**
     * Builder for {@link UpiPayment}.
     */
    public static final class Builder {
        private Activity activity;
        private PaymentPayload payment;

        /**
         * Binds the Activity with PaymentPayload.
         *
         * @param activity where payment is to be implemented.
         * @return this, for chaining.
         */
        @NonNull
        public Builder with(@NonNull Activity activity) {
            this.activity = activity;
            payment = new PaymentPayload();
            return this;
        }

        /**
         * Sets the Payee VPA (e.g. abc@upi, abc123@upi).
         *
         * @param vpa Payee VPA address (e.g. abc@upi, abc123@upi).
         * @return this, for create builder.
         */
        @NonNull
        public Builder setPayeeVpa(@NonNull String vpa) {
            if (!vpa.contains("@")) {
                throw new IllegalStateException("Payee VPA address should be valid (For e.g. example@vpa)");
            }
            payment.setVpa(vpa);
            return this;
        }

        /**
         * Sets the Payee Name.
         *
         * @param name Payee Name.
         * @return this, for create builder.
         */
        @NonNull
        public Builder setPayeeName(@NonNull String name) {
            if (name.trim().length() == 0) {
                throw new IllegalStateException("Payee Name Should be Valid!");
            }
            payment.setName(name);
            return this;
        }

        /**
         * Sets the Merchant Code. Passed if present.
         *
         * @param merchantCode Payee Merchant code if present it should be passed.
         * @return this, for create builder.
         */
        @NonNull
        public Builder setPayeeMerchantCode(@NonNull String merchantCode) {
            if (merchantCode.trim().length() == 0) {
                throw new IllegalStateException("Merchant Code Should be Valid!");
            }
            payment.setPayeeMerchantCode(merchantCode);
            return this;
        }

        /**
         * Sets the Transaction ID. This field is used in Merchant Payments generated by PSP app.
         * This is UNIQUE
         * @param id field is used in Merchant Payments generated by PSPs.
         * @return this, for create builder.
         */
        @NonNull
        public Builder setTransactionId(@NonNull String id) {
            if (id.trim().length() == 0) {
                throw new IllegalStateException("Invalid Transaction ID. Transaction ID Should be Valid!");
            }
            payment.setTxnId(id);
            return this;
        }

        /**
         * This is UNIQUE
         * Sets the Transaction Reference ID. This could be your order number,
         * subscription number, Bill ID, booking ID, insurance renewal reference, etc.
         * Needed for merchant transactions and dynamic URL generation.
         *
         * @param refId field is used in Merchant Payments generated by PSPs.
         * @return this, for create builder.
         */
        @NonNull
        public Builder setTransactionRefId(@NonNull String refId) {
            if (refId.trim().length() == 0) {
                throw new IllegalStateException("RefId Should be Valid!");
            }
            payment.setTxnRefId(refId);
            return this;
        }

        /**
         * Sets the Description. Provide note or description about your payment. for e.g. For Home Rent
         *
         * @param description field have to provide valid small note or description about payment. for e.g. For Rent
         * @return this, for create builder.
         */
        @NonNull
        public Builder setDescription(@NonNull String description) {
            if (description.trim().length() == 0) {
                throw new IllegalStateException("Description Should be Valid!");
            }
            payment.setDescription(description);
            return this;
        }

        /**
         * Currently Sets the Amount in INR only and Format should be decimal e.g. 01.50)
         *
         * @param amount here amount in String decimal format (xx.xx) .
         * @return this, for create builder.
         */
        @NonNull
        public Builder setAmount(@NonNull String amount) {
            if (!amount.contains(".")) {
                throw new IllegalStateException("Amount should be in decimal format XX.XX (For e.g. 101.00)");
            }
            payment.setAmount(amount);
            return this;
        }


        @NonNull
        public Builder setUrl(@NonNull String scheme, @NonNull String authority, @NonNull String appendPath) {
            if (!scheme.contains("http")) {
                throw new IllegalStateException("URL should be in correct format");
            }
            payment.setUrl(scheme, authority, appendPath);
            return this;
        }

        /**
         * Build the {@link UpiPayment} object.
         * This is used to build UpiPayment with mandatory parameters. and provide error if any.
         */
        @NonNull
        public UpiPayment build() {
            if (activity == null) {
                throw new IllegalStateException("Activity must be specified using with() call before build() UpiPayment");
            }

            if (payment == null) {
                throw new IllegalStateException("PaymentPayload null. PaymentPayload details must be initialized before build()");
            }

            if (payment.getVpa() == null) {
                throw new IllegalStateException("You need to set payee upi id i.e. VPA. Must call setPayeeVpa() before build().");
            }

            if (payment.getTxnId() == null) {
                throw new IllegalStateException("You need to set UNIQUE TxnId. Must call setTransactionId() before build");
            }

            if (payment.getTxnRefId() == null) {
                throw new IllegalStateException("You need to set UNIQUE TxnRefId. Must call setTransactionRefId() before build");
            }

            if (payment.getName() == null) {
                throw new IllegalStateException("You need to set name. Must call setPayeeName() before build().");
            }

            if (payment.getAmount() == null) {
                throw new IllegalStateException("You need to set AMOUNT in decimal format. Must call setAmount() before build().");
            }

            if (payment.getDescription() == null) {
                throw new IllegalStateException("You need to set description. Must call setDescription() before build().");
            }

            return new UpiPayment(activity, payment);
        }
    }
}