/**
 * COPYRIGHT @ Lure Technologies, LLC.
 * ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or
 * form other than in accordance with and subject to the terms of a written
 * license from Lure or with the prior written consent of Lure or as
 * permitted by applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure.  If you are neither the
 * intended recipient, nor an agent, employee, nor independent contractor
 * responsible for delivering this message to the intended recipient, you are
 * prohibited from copying, disclosing, distributing, disseminating, and/or
 * using the information in this email in any manner. If you have received
 * this message in error, please advise us immediately at
 * legal@luretechnologies.com by return email and then delete the message from your
 * computer and all other records (whether electronic, hard copy, or
 * otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.server.constraints.impl;

import com.luretechnologies.common.enums.AmountFormatEnum;
import com.luretechnologies.common.enums.E2EMethodEnum;
import com.luretechnologies.common.enums.EntryMethodEnum;
import com.luretechnologies.common.enums.OperationEnum;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.constraints.TransactionValid;
import com.luretechnologies.server.data.display.payment.Tax;
import com.luretechnologies.server.data.display.payment.TransactionRequest;
import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 *
 * @author developer
 */
public class TransactionValidator implements ConstraintValidator<TransactionValid, TransactionRequest> {

    /**
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(final TransactionValid constraintAnnotation) {

    }

    /**
     *
     * @param transactionRequest
     * @param context
     * @return
     */
    @Override
    public boolean isValid(final TransactionRequest transactionRequest, final ConstraintValidatorContext context) {
        if (transactionRequest == null) {
            return true;
        }

        boolean isValid = true;
        String property = "";

        if (transactionRequest.getLocalDate() == null || transactionRequest.getLocalDate().isEmpty()) {
            isValid = false;
            property = "LocalDate";
        } else if (transactionRequest.getLocalTime() == null || transactionRequest.getLocalTime().isEmpty()) {
            isValid = false;
            property = "LocalTime";
        }
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(Messages.VALUE_IS_EMPTY)
                    .addPropertyNode(property)
                    .addConstraintViolation();
            return isValid;
        }
        if (transactionRequest.getOperation() != null) {
            switch (transactionRequest.getOperation()) {
                //case REFUND:
                //case VOID:
                case ADVICE: {
                    if (transactionRequest.getOriginalTransactionId() == null) {
                        isValid = false;
                        property = "OriginalTransactionId";
                    }
                    break;
                }
                case SALE: {
                    if (transactionRequest.getEntryMethod() == null) {
                        isValid = false;
                        property = "EntryMethod";
                    }
                    break;
                }
            }
        }
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(Messages.VALUE_IS_EMPTY)
                    .addPropertyNode(property)
                    .addConstraintViolation();
            return isValid;
        }

        if (transactionRequest.getEntryMethod() != null) {
            switch (transactionRequest.getEntryMethod()) {
                case MANUAL: {
                    if (transactionRequest.getCardPan() == null) {
                        isValid = false;
                        property = "CardPan";
                    } else if (transactionRequest.getCardExpDate() == null) {
                        isValid = false;
                        property = "CardExpDate";
                    } else if (transactionRequest.getCardHolderName() == null) {
                        isValid = false;
                        property = "CardHolderName";
                    }
                    break;
                }
                case SWIPE: {
                    if (transactionRequest.getCardTrack() == null) {
                        isValid = false;
                        property = "CardTrack";
                    } else if (transactionRequest.getE2eMethod() != null && transactionRequest.getE2eMethod() != E2EMethodEnum.NONE) {
                        if (transactionRequest.getE2eKSN() == null) {
                            isValid = false;
                            property = "E2eKSN";
                        }
                    } else if (transactionRequest.getE2eMethod() == null || transactionRequest.getE2eMethod() == E2EMethodEnum.NONE) {
                        if (transactionRequest.getCardHolderName() == null) {
                            isValid = false;
                            property = "CardHolderName";
                        }
                    }
                    break;
                }
                case EMV_CONTACT: {
                    if( transactionRequest.getOperation() == OperationEnum.AUTHORIZATION || transactionRequest.getOperation() == OperationEnum.SALE ){
                        if (transactionRequest.getEmvDataTLV() == null) {
                            isValid = false;
                            property = "EmvDataTLV";
                        }
                    }
                    break;
                }
            }
        }
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(Messages.VALUE_IS_EMPTY)
                    .addPropertyNode(property)
                    .addConstraintViolation();
            return isValid;
        }

        if (transactionRequest.getMode() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(Messages.VALUE_IS_EMPTY)
                    .addPropertyNode("Mode")
                    .addConstraintViolation();
            return false;
        }
        switch (transactionRequest.getMode()) {
            case DEBIT: {
//                if (!transactionRequest.getOperation().equals(OperationEnum.VOID)) {
                if (transactionRequest.getCardTrack() == null) {
                    isValid = false;
                    property = "CardTrack";

                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(Messages.VALUE_IS_EMPTY)
                            .addPropertyNode(property)
                            .addConstraintViolation();
                    return isValid;
                }
//                }

                if (transactionRequest.getEntryMethod() == EntryMethodEnum.MANUAL) {
                    isValid = false;
                    property = "EntryMethod";

                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(Messages.INCORRECT_DATA_VALUE)
                            .addPropertyNode(property)
                            .addConstraintViolation();
                    return isValid;
                }
            }
        }

        String amount = transactionRequest.getAmount();
        String tipAmount = transactionRequest.getTipAmount();
        String cashBackAmount = transactionRequest.getCashBackAmount();

        if ((amount != null && amount.contains("."))
                || (tipAmount.contains("."))
                || (cashBackAmount.contains("."))) {
            transactionRequest.setAmountFormat(AmountFormatEnum.DECIMAL);

            try {
                BigDecimal.valueOf(amount != null ? Double.valueOf(amount) : Double.valueOf("0"));
            } catch (NumberFormatException ex) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(Messages.INVALID_DATA_FORMAT)
                        .addPropertyNode("Amount")
                        .addConstraintViolation();
                return false;
            }

            try {
                BigDecimal.valueOf(Double.valueOf(tipAmount));
            } catch (NumberFormatException ex) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(Messages.INVALID_DATA_FORMAT)
                        .addPropertyNode("TipAmount")
                        .addConstraintViolation();
                return false;
            }

            try {
                BigDecimal.valueOf(Double.valueOf(cashBackAmount));
            } catch (NumberFormatException ex) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(Messages.INVALID_DATA_FORMAT)
                        .addPropertyNode("CashBackAmount")
                        .addConstraintViolation();
                return false;
            }

            try {
                for (Tax tax : transactionRequest.getTaxAmounts()) {
                    BigDecimal.valueOf(Double.valueOf(tax.getAmount()));
                }
            } catch (NumberFormatException ex) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(Messages.INVALID_DATA_FORMAT)
                        .addPropertyNode("TaxAmount")
                        .addConstraintViolation();
                return false;
            }
        } else {
            transactionRequest.setAmountFormat(AmountFormatEnum.INTEGER);

            try {
                Integer.valueOf(amount != null ? amount : "0");
            } catch (NumberFormatException ex) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(Messages.INVALID_DATA_FORMAT)
                        .addPropertyNode("Amount")
                        .addConstraintViolation();
                return false;
            }

            try {
                Integer.valueOf(tipAmount);
            } catch (NumberFormatException ex) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(Messages.INVALID_DATA_FORMAT)
                        .addPropertyNode("TipAmount")
                        .addConstraintViolation();
                return false;
            }

            try {
                Integer.valueOf(cashBackAmount);
            } catch (NumberFormatException ex) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(Messages.INVALID_DATA_FORMAT)
                        .addPropertyNode("CashBackAmount")
                        .addConstraintViolation();
                return false;
            }

            try {
                for (Tax tax : transactionRequest.getTaxAmounts()) {
                    Integer.valueOf(tax.getAmount());
                }
            } catch (NumberFormatException ex) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(Messages.INVALID_DATA_FORMAT)
                        .addPropertyNode("TaxAmount")
                        .addConstraintViolation();
                return false;
            }
        }
        return isValid;
    }
}
