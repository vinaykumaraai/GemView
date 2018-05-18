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
package com.luretechnologies.client.restlib.service.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 *
 *
 *
 * @author developer
 */
@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-11-09T17:35:21.421-05:00")
public class Lodging implements Serializable{

private String duration = null;
private String checkInDate = null;
private String checkOutDate = null;
private String dailyRoomRate1 = null;
private String dailyRoomRate2 = null;
private String dailyRoomRate3 = null;
private String roomNights1 = null;
private String roomNights2 = null;
private String roomNights3 = null;
private String guestSmokingPreference = null;
private String numberOfRoomsBooked = null;
private String numberOfGuests = null;
private String roomBedType = null;
private String roomTaxElements = null;
private String roomRateType = null;
private String guestName = null;
private String customerServicePhoneNumber = null;
private String corporateClientCode = null;
private String promotionalCode = null;
private String additionalCoupon = null;
private String roomLocation = null;
private String specialProgramCode = null;
private String tax = null;
private String prepaidCost = null;
private String foodAndBeverageCost = null;
private String roomTax = null;
private String adjustmentAmount = null;
private String phoneCost = null;
private String restaurantCost = null;
private String roomServiceCost = null;
private String miniBarCost = null;
private String laundryCost = null;
private String miscellaneousCost = null;
private String giftShopCost = null;
private String movieCost = null;
private String healthClubCost = null;
private String valetParkingCost = null;
private String cashDisbursementCost = null;
private String nonRoomCost = null;
private String businessCenterCost = null;
private String loungeBarCost = null;
private String transportationCost = null;
private String gratuityCost = null;
private String conferenceRoomCost = null;
private String audioVisualCost = null;
private String banquetCost = null;
private String internetAccessCost = null;
private String earlyCheckOutCost = null;
private String nonRoomTax = null;
private String travelAgencyCode = null;
private String travelAgencyName = null;
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Lodging {\n");
        //sb.append("    type: ").append(StringUtil.toIndentedString(type)).append("\n");
        //sb.append("    amount: ").append(StringUtil.toIndentedString(amount)).append("\n");
        sb.append("}");
        return sb.toString();
    }
    /**
     * @return the duration
     */
    
    @ApiModelProperty(value = "The length of the upcoming Lodging stay (from 01 to 99).")
    @JsonProperty("duration")
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * @return the checkInDate
     */
    @ApiModelProperty(value = "The date the cardholder checks in to the hotel. The format is MMDDYYYY.")
    @JsonProperty("checkInDate")
    public String getCheckInDate() {
        return checkInDate;
    }

    /**
     * @param checkInDate the checkInDate to set
     */
    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * @return the checkOutDate
     */
    @ApiModelProperty(value = "The date the cardholder checked out of the hotel. The format is MMDDYYYY.")
    @JsonProperty("checkOutDate")
    public String getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * @param checkOutDate the checkOutDate to set
     */
    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    /**
     * @return the dailyRoomRate1
     */
    @ApiModelProperty(value = "Daily rate being charged for the room. Up to 3 different rates can be sent.")
    @JsonProperty("dailyRoomRate1")
    public String getDailyRoomRate1() {
        return dailyRoomRate1;
    }

    /**
     * @param dailyRoomRate1 the dailyRoomRate1 to set
     */
    public void setDailyRoomRate1(String dailyRoomRate1) {
        this.dailyRoomRate1 = dailyRoomRate1;
    }

    /**
     * @return the dailyRoomRate2
     */
    @ApiModelProperty(value = "")
    @JsonProperty("dailyRoomRate2")    
    public String getDailyRoomRate2() {
        return dailyRoomRate2;
    }

    /**
     * @param dailyRoomRate2 the dailyRoomRate2 to set
     */
    public void setDailyRoomRate2(String dailyRoomRate2) {
        this.dailyRoomRate2 = dailyRoomRate2;
    }

    /**
     * @return the dailyRoomRate3
     */
    @ApiModelProperty(value = "")
    @JsonProperty("dailyRoomRate3")    
    public String getDailyRoomRate3() {
        return dailyRoomRate3;
    }

    /**
     * @param dailyRoomRate3 the dailyRoomRate3 to set
     */
    public void setDailyRoomRate3(String dailyRoomRate3) {
        this.dailyRoomRate3 = dailyRoomRate3;
    }

    /**
     * @return the roomNights1
     */
    @ApiModelProperty(value = "The number of nights being billed for the correspnding lodgingData_dailyRoom# field")
    @JsonProperty("roomNights1")    
    public String getRoomNights1() {
        return roomNights1;
    }

    /**
     * @param roomNights1 the roomNights1 to set
     */
    public void setRoomNights1(String roomNights1) {
        this.roomNights1 = roomNights1;
    }

    /**
     * @return the roomNights2
     */
    @ApiModelProperty(value = "")
    @JsonProperty("roomNights2")    
    public String getRoomNights2() {
        return roomNights2;
    }

    /**
     * @param roomNights2 the roomNights2 to set
     */
    public void setRoomNights2(String roomNights2) {
        this.roomNights2 = roomNights2;
    }

    /**
     * @return the roomNights3
     */
    @ApiModelProperty(value = "")
    @JsonProperty("roomNights3")    
    public String getRoomNights3() {
        return roomNights3;
    }

    /**
     * @param roomNights3 the roomNights3 to set
     */
    public void setRoomNights3(String roomNights3) {
        this.roomNights3 = roomNights3;
    }

    /**
     * @return the guestSmokingPreference
     */
    @ApiModelProperty(value = "The smoking preference of the guest")
    @JsonProperty("guestSmokingPreference")    
    public String getGuestSmokingPreference() {
        return guestSmokingPreference;
    }

    /**
     * @param guestSmokingPreference the guestSmokingPreference to set
     */
    public void setGuestSmokingPreference(String guestSmokingPreference) {
        this.guestSmokingPreference = guestSmokingPreference;
    }

    /**
     * @return the numberOfRoomsBooked
     */
    @ApiModelProperty(value = "The total number of rooms being booked")
    @JsonProperty("numberOfRoomsBooked")    
    public String getNumberOfRoomsBooked() {
        return numberOfRoomsBooked;
    }

    /**
     * @param numberOfRoomsBooked the numberOfRoomsBooked to set
     */
    public void setNumberOfRoomsBooked(String numberOfRoomsBooked) {
        this.numberOfRoomsBooked = numberOfRoomsBooked;
    }

    /**
     * @return the numberOfGuests
     */
    @ApiModelProperty(value = "The total number of adults in all rooms being booked")
    @JsonProperty("numberOfGuests")    
    public String getNumberOfGuests() {
        return numberOfGuests;
    }

    /**
     * @param numberOfGuests the numberOfGuests to set
     */
    public void setNumberOfGuests(String numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    /**
     * @return the roomBedType
     */
    @ApiModelProperty(value = "The type of bed in the room (twin, full, queen, king, etc).")
    @JsonProperty("roomBedType")    
    public String getRoomBedType() {
        return roomBedType;
    }

    /**
     * @param roomBedType the roomBedType to set
     */
    public void setRoomBedType(String roomBedType) {
        this.roomBedType = roomBedType;
    }

    /**
     * @return the roomTaxElements
     */
    @ApiModelProperty(value = "The specific tax elements about the room (tourist, hotel, etc).")
    @JsonProperty("roomTaxElements")    
    public String getRoomTaxElements() {
        return roomTaxElements;
    }

    /**
     * @param roomTaxElements the roomTaxElements to set
     */
    public void setRoomTaxElements(String roomTaxElements) {
        this.roomTaxElements = roomTaxElements;
    }

    /**
     * @return the roomRateType
     */
    @ApiModelProperty(value = "Types of rate used (AAA, AARP, Government, etc).")
    @JsonProperty("roomRateType")    
    public String getRoomRateType() {
        return roomRateType;
    }

    /**
     * @param roomRateType the roomRateType to set
     */
    public void setRoomRateType(String roomRateType) {
        this.roomRateType = roomRateType;
    }

    /**
     * @return the guestName
     */
    @ApiModelProperty(value = "Name of the guest.")
    @JsonProperty("guestName")    
    public String getGuestName() {
        return guestName;
    }

    /**
     * @param guestName the guestName to set
     */
    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    /**
     * @return the customerServicePhoneNumber
     */
    @ApiModelProperty(value = "Phone number for the cardholder to contact the hotel or hotel chain.")
    @JsonProperty("customerServicePhoneNumber")    
    public String getCustomerServicePhoneNumber() {
        return customerServicePhoneNumber;
    }

    /**
     * @param customerServicePhoneNumber the customerServicePhoneNumber to set
     */
    public void setCustomerServicePhoneNumber(String customerServicePhoneNumber) {
        this.customerServicePhoneNumber = customerServicePhoneNumber;
    }

    /**
     * @return the corporateClientCode
     */
    @ApiModelProperty(value = "A code assigned to a business/corporation entity that the hotel or hotel chain uses to identify corporate rates or discounts")
    @JsonProperty("corporateClientCode")    
    public String getCorporateClientCode() {
        return corporateClientCode;
    }

    /**
     * @param corporateClientCode the corporateClientCode to set
     */
    public void setCorporateClientCode(String corporateClientCode) {
        this.corporateClientCode = corporateClientCode;
    }

    /**
     * @return the promotionalCode
     */
    @ApiModelProperty(value = "A code that describes a current discount program at the hotel")
    @JsonProperty("promotionalCode")    
    public String getPromotionalCode() {
        return promotionalCode;
    }

    /**
     * @param promotionalCode the promotionalCode to set
     */
    public void setPromotionalCode(String promotionalCode) {
        this.promotionalCode = promotionalCode;
    }

    /**
     * @return the additionalCoupon
     */
    @ApiModelProperty(value = "Any additional coupon or discount.")
    @JsonProperty("additionalCoupon")    
    public String getAdditionalCoupon() {
        return additionalCoupon;
    }

    /**
     * @param additionalCoupon the additionalCoupon to set
     */
    public void setAdditionalCoupon(String additionalCoupon) {
        this.additionalCoupon = additionalCoupon;
    }

    /**
     * @return the roomLocation
     */
    @ApiModelProperty(value = "The location of the room: Lake View, Ocean View, etc.")
    @JsonProperty("roomLocation")    
    public String getRoomLocation() {
        return roomLocation;
    }

    /**
     * @param roomLocation the roomLocation to set
     */
    public void setRoomLocation(String roomLocation) {
        this.roomLocation = roomLocation;
    }

    /**
     * @return the specialProgramCode
     */
    @ApiModelProperty(value = "The code that identifies the type of purchase")
    @JsonProperty("specialProgramCode")    
    public String getSpecialProgramCode() {
        return specialProgramCode;
    }

    /**
     * @param specialProgramCode the specialProgramCode to set
     */
    public void setSpecialProgramCode(String specialProgramCode) {
        this.specialProgramCode = specialProgramCode;
    }

    /**
     * @return the tax
     */
    @ApiModelProperty(value = "The total amount of tax for the stay.")
    @JsonProperty("tax")    
    public String getTax() {
        return tax;
    }

    /**
     * @param tax the tax to set
     */
    public void setTax(String tax) {
        this.tax = tax;
    }

    /**
     * @return the prepaidCost
     */
    @ApiModelProperty(value = "The amount of the deposit or other prepaid amounts for the stay.")
    @JsonProperty("prepaidCost")    
    public String getPrepaidCost() {
        return prepaidCost;
    }

    /**
     * @param prepaidCost the prepaidCost to set
     */
    public void setPrepaidCost(String prepaidCost) {
        this.prepaidCost = prepaidCost;
    }

    /**
     * @return the foodAndBeverageCost
     */
    @ApiModelProperty(value = "The total amount of all food and beverage charges during the stay")
    @JsonProperty("foodAndBeverageCost")    
    public String getFoodAndBeverageCost() {
        return foodAndBeverageCost;
    }

    /**
     * @param foodAndBeverageCost the foodAndBeverageCost to set
     */
    public void setFoodAndBeverageCost(String foodAndBeverageCost) {
        this.foodAndBeverageCost = foodAndBeverageCost;
    }

    /**
     * @return the roomTax
     */
    @ApiModelProperty(value = "The total amount of tax for the room charges only")
    @JsonProperty("roomTax")    
    public String getRoomTax() {
        return roomTax;
    }

    /**
     * @param roomTax the roomTax to set
     */
    public void setRoomTax(String roomTax) {
        this.roomTax = roomTax;
    }

    /**
     * @return the adjustmentAmount
     */
    @ApiModelProperty(value = "The amount of any adjustment made to the final folio/bill.")
    @JsonProperty("adjustmentAmount")    
    public String getAdjustmentAmount() {
        return adjustmentAmount;
    }

    /**
     * @param adjustmentAmount the adjustmentAmount to set
     */
    public void setAdjustmentAmount(String adjustmentAmount) {
        this.adjustmentAmount = adjustmentAmount;
    }

    /**
     * @return the phoneCost
     */
    @ApiModelProperty(value = "The total amount of phone charges.")
    @JsonProperty("phoneCost")    
    public String getPhoneCost() {
        return phoneCost;
    }

    /**
     * @param phoneCost the phoneCost to set
     */
    public void setPhoneCost(String phoneCost) {
        this.phoneCost = phoneCost;
    }

    /**
     * @return the restaurantCost
     */
    @ApiModelProperty(value = "The total amount of restaurant charges.")
    @JsonProperty("restaurantCost")    
    public String getRestaurantCost() {
        return restaurantCost;
    }

    /**
     * @param restaurantCost the restaurantCost to set
     */
    public void setRestaurantCost(String restaurantCost) {
        this.restaurantCost = restaurantCost;
    }

    /**
     * @return the roomServiceCost
     */
    @ApiModelProperty(value = "The total amount of Room Service charges")
    @JsonProperty("roomServiceCost")    
    public String getRoomServiceCost() {
        return roomServiceCost;
    }

    /**
     * @param roomServiceCost the roomServiceCost to set
     */
    public void setRoomServiceCost(String roomServiceCost) {
        this.roomServiceCost = roomServiceCost;
    }

    /**
     * @return the miniBarCost
     */
    @ApiModelProperty(value = "The total amount of in-room Mini Bar charges")
    @JsonProperty("miniBarCost")    
    public String getMiniBarCost() {
        return miniBarCost;
    }

    /**
     * @param miniBarCost the miniBarCost to set
     */
    public void setMiniBarCost(String miniBarCost) {
        this.miniBarCost = miniBarCost;
    }

    /**
     * @return the laundryCost
     */
    @ApiModelProperty(value = "The total amount of Laundry charges")
    @JsonProperty("laundryCost")    
    public String getLaundryCost() {
        return laundryCost;
    }

    /**
     * @param laundryCost the laundryCost to set
     */
    public void setLaundryCost(String laundryCost) {
        this.laundryCost = laundryCost;
    }

    /**
     * @return the miscellaneousCost
     */
    @ApiModelProperty(value = "The total amount of charges not elsewhere classified.")
    @JsonProperty("miscellaneousCost")    
    public String getMiscellaneousCost() {
        return miscellaneousCost;
    }

    /**
     * @param miscellaneousCost the miscellaneousCost to set
     */
    public void setMiscellaneousCost(String miscellaneousCost) {
        this.miscellaneousCost = miscellaneousCost;
    }

    /**
     * @return the giftShopCost
     */
    @ApiModelProperty(value = "The total amount of Gift Shop charges.")
    @JsonProperty("giftShopCost")    
    public String getGiftShopCost() {
        return giftShopCost;
    }

    /**
     * @param giftShopCost the giftShopCost to set
     */
    public void setGiftShopCost(String giftShopCost) {
        this.giftShopCost = giftShopCost;
    }

    /**
     * @return the movieCost
     */
    @ApiModelProperty(value = "The total amount of Movie charges.")
    @JsonProperty("movieCost")    
    public String getMovieCost() {
        return movieCost;
    }

    /**
     * @param movieCost the movieCost to set
     */
    public void setMovieCost(String movieCost) {
        this.movieCost = movieCost;
    }

    /**
     * @return the healthClubCost
     */
    @ApiModelProperty(value = "The total amount of Health Club charges.")
    @JsonProperty("healthClubCost")    
    public String getHealthClubCost() {
        return healthClubCost;
    }

    /**
     * @param healthClubCost the healthClubCost to set
     */
    public void setHealthClubCost(String healthClubCost) {
        this.healthClubCost = healthClubCost;
    }

    /**
     * @return the valetParkingCost
     */
    @ApiModelProperty(value = "The total amount of Valet Parking charges.")
    @JsonProperty("valetParkingCost")    
    public String getValetParkingCost() {
        return valetParkingCost;
    }

    /**
     * @param valetParkingCost the valetParkingCost to set
     */
    public void setValetParkingCost(String valetParkingCost) {
        this.valetParkingCost = valetParkingCost;
    }

    /**
     * @return the cashDisbursementCost
     */
    @ApiModelProperty(value = "The total amount of Cash Disbursment charges.")
    @JsonProperty("cashDisbursementCost")    
    public String getCashDisbursementCost() {
        return cashDisbursementCost;
    }

    /**
     * @param cashDisbursementCost the cashDisbursementCost to set
     */
    public void setCashDisbursementCost(String cashDisbursementCost) {
        this.cashDisbursementCost = cashDisbursementCost;
    }

    /**
     * @return the nonRoomCost
     */
    @ApiModelProperty(value = "The total amount of charges not related to the room rate (restaurant, gift shop, etc).")
    @JsonProperty("nonRoomCost")    
    public String getNonRoomCost() {
        return nonRoomCost;
    }

    /**
     * @param nonRoomCost the nonRoomCost to set
     */
    public void setNonRoomCost(String nonRoomCost) {
        this.nonRoomCost = nonRoomCost;
    }

    /**
     * @return the businessCenterCost
     */
    @ApiModelProperty(value = "The total amount of Business Center charges.")
    @JsonProperty("businessCenterCost")    
    public String getBusinessCenterCost() {
        return businessCenterCost;
    }

    /**
     * @param businessCenterCost the businessCenterCost to set
     */
    public void setBusinessCenterCost(String businessCenterCost) {
        this.businessCenterCost = businessCenterCost;
    }

    /**
     * @return the loungeBarCost
     */
    @ApiModelProperty(value = "The total amount of Lounge and/or Bar charges.")
    @JsonProperty("loungeBarCost")    
    public String getLoungeBarCost() {
        return loungeBarCost;
    }

    /**
     * @param loungeBarCost the loungeBarCost to set
     */
    public void setLoungeBarCost(String loungeBarCost) {
        this.loungeBarCost = loungeBarCost;
    }

    /**
     * @return the transportationCost
     */
    @ApiModelProperty(value = "The total amount of Transportation charges.")
    @JsonProperty("transportationCost")    
    public String getTransportationCost() {
        return transportationCost;
    }

    /**
     * @param transportationCost the transportationCost to set
     */
    public void setTransportationCost(String transportationCost) {
        this.transportationCost = transportationCost;
    }

    /**
     * @return the gratuityCost
     */
    @ApiModelProperty(value = "The total amount of Gratuity charges.")
    @JsonProperty("gratuityCost")    
    public String getGratuityCost() {
        return gratuityCost;
    }

    /**
     * @param gratuityCost the gratuityCost to set
     */
    public void setGratuityCost(String gratuityCost) {
        this.gratuityCost = gratuityCost;
    }

    /**
     * @return the conferenceRoomCost
     */
    @ApiModelProperty(value = "The total amount of Conference Room charges.")
    @JsonProperty("conferenceRoomCost")    
    public String getConferenceRoomCost() {
        return conferenceRoomCost;
    }

    /**
     * @param conferenceRoomCost the conferenceRoomCost to set
     */
    public void setConferenceRoomCost(String conferenceRoomCost) {
        this.conferenceRoomCost = conferenceRoomCost;
    }

    /**
     * @return the audioVisualCost
     */
    @ApiModelProperty(value = "The total amount of Audio Visual charges.")
    @JsonProperty("audioVisualCost")    
    public String getAudioVisualCost() {
        return audioVisualCost;
    }

    /**
     * @param audioVisualCost the audioVisualCost to set
     */
    public void setAudioVisualCost(String audioVisualCost) {
        this.audioVisualCost = audioVisualCost;
    }

    /**
     * @return the banquetCost
     */
    @ApiModelProperty(value = "The total amount of Banquet charges.")
    @JsonProperty("banquetCost")    
    public String getBanquetCost() {
        return banquetCost;
    }

    /**
     * @param banquetCost the banquetCost to set
     */
    public void setBanquetCost(String banquetCost) {
        this.banquetCost = banquetCost;
    }

    /**
     * @return the internetAccessCost
     */
    @ApiModelProperty(value = "The total amount of Internet Access charges.")
    @JsonProperty("internetAccessCost")    
    public String getInternetAccessCost() {
        return internetAccessCost;
    }

    /**
     * @param internetAccessCost the internetAccessCost to set
     */
    public void setInternetAccessCost(String internetAccessCost) {
        this.internetAccessCost = internetAccessCost;
    }

    /**
     * @return the earlyCheckOutCost
     */
    @ApiModelProperty(value = "The fee charged for an early departure.")
    @JsonProperty("earlyCheckOutCost")    
    public String getEarlyCheckOutCost() {
        return earlyCheckOutCost;
    }

    /**
     * @param earlyCheckOutCost the earlyCheckOutCost to set
     */
    public void setEarlyCheckOutCost(String earlyCheckOutCost) {
        this.earlyCheckOutCost = earlyCheckOutCost;
    }

    /**
     * @return the nonRoomTax
     */
    @ApiModelProperty(value = "The total amount of tax not related to the room rate.")
    @JsonProperty("nonRoomTax")    
    public String getNonRoomTax() {
        return nonRoomTax;
    }

    /**
     * @param nonRoomTax the nonRoomTax to set
     */
    public void setNonRoomTax(String nonRoomTax) {
        this.nonRoomTax = nonRoomTax;
    }

    /**
     * @return the travelAgencyCode
     */
    @ApiModelProperty(value = "The code used by travel agencies to identify who made the reservation")
    @JsonProperty("travelAgencyCode")    
    public String getTravelAgencyCode() {
        return travelAgencyCode;
    }

    /**
     * @param travelAgencyCode the travelAgencyCode to set
     */
    public void setTravelAgencyCode(String travelAgencyCode) {
        this.travelAgencyCode = travelAgencyCode;
    }

    /**
     * @return the travelAgencyName
     */
    @ApiModelProperty(value = "The name of the agency that made the reservation.")
    @JsonProperty("travelAgencyName")    
    public String getTravelAgencyName() {
        return travelAgencyName;
    }

    /**
     * @param travelAgencyName the travelAgencyName to set
     */
    public void setTravelAgencyName(String travelAgencyName) {
        this.travelAgencyName = travelAgencyName;
    }
}
