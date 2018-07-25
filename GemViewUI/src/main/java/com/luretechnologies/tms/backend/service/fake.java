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
package com.luretechnologies.tms.backend.service;

public class fake {

//	private static void constructSuggestionQuery(ItemSuggestions itemSuggestions, SimpleQuery query) {
//
//
//
//        final String longDescriptionField = ItemCriteriaHelper.getLongDescriptionField();
//
//
//
//        final Criteria codeCriteria = expressionCriteria(
//
//                new Criteria(AbstractItem.ITEM_CODE),
//
//                itemSuggestions.getCodePattern());
//
//
//
//        final Criteria descriptionCriteria = expressionCriteria(
//
//                new Criteria(longDescriptionField),
//
//                itemSuggestions.getDescriptionPattern());
//
//        
//
//        final Criteria packageIdentifierCriteria = expressionCriteria(
//
//                new Criteria(Item.PACKAGE_IDENTIFIERS),
//
//                itemSuggestions.getPackageIdentifierPattern());
//
//
//
//        final Criteria categoryCriteria = isCriteria(
//
//                new Criteria(Item.MERCHANDISE_CATEGORY_ID),
//
//                itemSuggestions.getMerchandiseCategoryId());
//
//
//
//        if (StringUtils.isNotEmpty(itemSuggestions.getCodePattern())
//
//                && StringUtils.isNotEmpty(itemSuggestions.getDescriptionPattern())
//
//                && StringUtils.isNotEmpty(itemSuggestions.getPackageIdentifierPattern())) {
//
//            query.addCriteria(
//
//                    (categoryCriteria).and((descriptionCriteria).or(codeCriteria).or(packageIdentifierCriteria)));
//
//        } else if (StringUtils.isNotEmpty(itemSuggestions.getCodePattern())
//
//                && StringUtils.isEmpty(itemSuggestions.getDescriptionPattern())
//
//                && StringUtils.isEmpty(itemSuggestions.getPackageIdentifierPattern())) {
//
//            query.addCriteria((categoryCriteria).and(codeCriteria));
//
//        } else if (StringUtils.isEmpty(itemSuggestions.getCodePattern())
//
//                && StringUtils.isNotEmpty(itemSuggestions.getDescriptionPattern())
//
//                && StringUtils.isEmpty(itemSuggestions.getPackageIdentifierPattern())) {
//
//            query.addCriteria((categoryCriteria).and(descriptionCriteria));
//
//        } else if ((StringUtils.isEmpty(itemSuggestions.getCodePattern())
//
//                && StringUtils.isEmpty(itemSuggestions.getDescriptionPattern())
//
//                && StringUtils.isNotEmpty(itemSuggestions.getPackageIdentifierPattern()))) {
//
//            query.addCriteria((categoryCriteria).and(packageIdentifierCriteria));
//
//        } else if ((StringUtils.isNotEmpty(itemSuggestions.getCodePattern())
//
//                && StringUtils.isEmpty(itemSuggestions.getDescriptionPattern())
//
//                && StringUtils.isNotEmpty(itemSuggestions.getPackageIdentifierPattern()))) {
//
//            query.addCriteria((categoryCriteria).and((codeCriteria).or(packageIdentifierCriteria)));
//
//        } else if ((StringUtils.isNotEmpty(itemSuggestions.getCodePattern())
//
//                && StringUtils.isNotEmpty(itemSuggestions.getDescriptionPattern())
//
//                && StringUtils.isEmpty(itemSuggestions.getPackageIdentifierPattern()))) {
//
//            query.addCriteria((categoryCriteria).and((codeCriteria).or(descriptionCriteria)));
//
//        } else if ((StringUtils.isEmpty(itemSuggestions.getCodePattern())
//
//                && StringUtils.isNotEmpty(itemSuggestions.getDescriptionPattern())
//
//                && StringUtils.isNotEmpty(itemSuggestions.getPackageIdentifierPattern()))) {
//
//            query.addCriteria((categoryCriteria).and((descriptionCriteria).or(packageIdentifierCriteria)));
//
//
//
//        } else {
//
//            query.addCriteria(categoryCriteria);
//
//        }
//
//
//
//        query.addProjectionOnFields(
//
//
//
//                AbstractItem.ITEM_CODE,
//
//                Item.PACKAGE_IDENTIFIERS,
//
//                Item.STATUS,
//
//                Item.MERCHANDISE_CATEGORY_ID,
//
//                Item.DEPARTMENT_ID,
//
//                Item.FAMILY_CODE,
//
//                Item.MANUFACTURER_CODE,
//
//                Item.IS_NON_MERCHANDISE,
//
//                AbstractCatalogEntity.OBJECT_VERSION,
//
//                longDescriptionField,
//
//                ItemCriteriaHelper.getShortDescriptionField());
//
//    }


}
