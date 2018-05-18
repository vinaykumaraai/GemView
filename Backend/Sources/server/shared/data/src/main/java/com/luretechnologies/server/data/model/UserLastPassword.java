/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.server.data.model;

import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.constraints.LastPasswordFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@LastPasswordFormat(message = Messages.INVALID_LAST_PASSWORD)
@javax.persistence.Entity
@Table(name = "User_Last_Password")
public class UserLastPassword implements Serializable {

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    private long id;
    
    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)})
    @ApiModelProperty(value = "The user.", required = true)
    private User user;
    
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "password1", nullable = true, length = 100)
    @ApiModelProperty(value = "The password.")
    private String password1;
    
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "password2", nullable = true, length = 100)
    @ApiModelProperty(value = "The password.")
    private String password2;
    
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "password3", nullable = true, length = 100)
    @ApiModelProperty(value = "The password.")
    private String password3;
    
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "password4", nullable = true, length = 100)
    @ApiModelProperty(value = "The password.")
    private String password4;
    
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "password5", nullable = true, length = 100)
    @ApiModelProperty(value = "The password.")
    private String password5;
    
    public UserLastPassword() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the password1
     */
    public String getPassword1() {
        return password1;
    }

    /**
     * @param password1 the password1 to set
     */
    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    /**
     * @return the password2
     */
    public String getPassword2() {
        return password2;
    }

    /**
     * @param password2 the password2 to set
     */
    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    /**
     * @return the password3
     */
    public String getPassword3() {
        return password3;
    }

    /**
     * @param password3 the password3 to set
     */
    public void setPassword3(String password3) {
        this.password3 = password3;
    }

    /**
     * @return the password4
     */
    public String getPassword4() {
        return password4;
    }

    /**
     * @param password4 the password4 to set
     */
    public void setPassword4(String password4) {
        this.password4 = password4;
    }

    /**
     * @return the password5
     */
    public String getPassword5() {
        return password5;
    }

    /**
     * @param password5 the password5 to set
     */
    public void setPassword5(String password5) {
        this.password5 = password5;
    }
    
}
